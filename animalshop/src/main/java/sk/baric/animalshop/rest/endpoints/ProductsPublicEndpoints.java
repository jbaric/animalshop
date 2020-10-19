package sk.baric.animalshop.rest.endpoints;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sk.baric.animalshop.data.dto.ProductOvervierwDto;
import sk.baric.animalshop.data.entities.Product;
import sk.baric.animalshop.data.repository.ProductRepository;
import sk.baric.animalshop.services.ProductsService;

/**
 * Controller for rest endpoints handling all services for products
 * 
 * Since it's public, non-authentificated access is allowed, use only GET methods that perform read only operations.
 * 
 * @author Juraj Baric
 *
 */
@RestController()
@RequestMapping("/products")
public class ProductsPublicEndpoints extends AbstractEndpoint {
	
	private static String PRODUCT_PREFIX = "[a-zA-Z0-9][a-zA-Z0-9\\ ]+[a-zA-Z0-9]";
	
	@Inject
	ProductRepository productsRepository;

	@Inject
	ProductsService productsService;
	
	/**
	 * Endpoint for products details
	 * URL e.g. 'server/products/1'
	 * returns product detail
	 * 
	 * @param id
	 * @return
	 */
    @GetMapping("/{id}")
    public Product getProductDetail(@PathVariable Long id) {
    	
    	checkDependencies() ;
    	
    	if (id == null || id <= 0)
    		returnBadRequest("product id must be zero or positive");
    	return productsRepository.findById(id).get();
    }

	/**
	 * endpoint for products overview with filtering possibility
	 * 
	 * @param name
	 * @param minPrice
	 * @param maxPrice
	 * @return
	 */
    @GetMapping
    public List<ProductOvervierwDto> getProducts(
    		@PageableDefault(size = Integer.MAX_VALUE) Pageable pageable,
    		@RequestParam(required = false) String name, 
    		@RequestParam(required = false, name = "min-price") Double minPrice, 
    		@RequestParam(required = false, name = "max-price") Double maxPrice) {
    	
    	checkDependencies();
    	
    	if (name == null)
    		return getProducts(pageable, minPrice, maxPrice);
    	
    	if (minPrice == null)
    		return getProducts(pageable, name, maxPrice);
    	
    	if (maxPrice == null)
    		return getProducts(pageable, name);
    	
    	checkMinMaxPrice(minPrice, maxPrice);
    	
    	checkPrefixFilter(name);
    	
    	return convertToDto(productsRepository.findProductsByPriceRange(name, minPrice, maxPrice, pageable));
    }
	
    private List<ProductOvervierwDto> getProducts(Pageable pageable) {
    	return convertToDto(productsRepository.findAll(pageable).toList());
    }
	
    private List<ProductOvervierwDto> getProducts(Pageable pageable, @RequestParam Double minPrice, @RequestParam Double maxPrice) {

    	if (minPrice == null)
    		return getProducts(pageable, maxPrice);
    	
    	checkMinMaxPrice(minPrice, maxPrice);
    	
    	return convertToDto(productsService.findProductsByPriceRange(minPrice, maxPrice, pageable));
    }
	
    private List<ProductOvervierwDto> getProducts(Pageable pageable, Double maxPrice) {
    	if (maxPrice == null)
    		return getProducts(pageable);
    	
    	return getProducts(pageable, 0d, maxPrice);
    }

	
    private List<ProductOvervierwDto> getProducts(Pageable pageable, String name, Double maxPrice) {
    	if (maxPrice == null)
    		return getProducts(pageable, name);
    	
    	return getProducts(pageable, name, 0d, maxPrice);
    }
	
    private List<ProductOvervierwDto> getProducts(Pageable pageable, String name) {
    	    	
    	checkPrefixFilter(name);
    	
    	return convertToDto(productsService.findProductsByNamePrefix(name, pageable));
    }
    
    private void checkPrefixFilter(String startWith) {    	
    	if (startWith == null || startWith.length() < 3)
    		returnBadRequest("product name prefix filter should be at least 3 characters");
    		
    	if (!startWith.matches(PRODUCT_PREFIX))
    		returnBadRequest("product name prefix filter can contain only alphanumerical characters and empty space, but it can not start or end with empty space");
    }
    
    private void checkMinMaxPrice(Double minPrice, Double maxPrice) {
    	
    	if (minPrice < 0)
    		returnBadRequest("lower price limit filter must be at least 0");
    	
    	if (minPrice > maxPrice)
    		returnBadRequest("upper price limit must be greater than min price");
    	
    	if (maxPrice <= 0)
    		returnBadRequest("upper price limit must be greater than 0");
    }
    
    /**
     * filters gallery and description
     * @param model
     */
    private List<ProductOvervierwDto> convertToDto(List<Product> products) {
		return products.stream().map(p -> new ProductOvervierwDto(p)).collect(Collectors.toList());
    }
	
	private void checkDependencies() {
		checkDependencies(productsRepository);
		checkDependencies(productsService);
	}	
}
;