package sk.baric.animalshop.services;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import sk.baric.animalshop.data.entities.Product;
import sk.baric.animalshop.data.repository.ProductRepository;

/**
 * {@inheritDoc}
 */
@Service
public class ProductsServiceImpl implements ProductsService {

	@Inject
	ProductRepository repository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Product> findProductsByNamePrefix(String prefix, Pageable pageable) {
		if (prefix == null || prefix.isBlank())
			return repository.findAll();
		return repository.findProductsByNamePrefix(prefix, pageable);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Product> findProductsByPriceRange(double minPrice, double maxPrice, Pageable pageable) {		
		return findProductsByNamePrefixAndPriceRange(null, minPrice, maxPrice, pageable);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Product> findProductsByNamePrefixAndPriceRange(String prefix, double minPrice, double maxPrice, Pageable pageable) {

		if (maxPrice < minPrice) {
			double tmpMax = maxPrice;
			maxPrice = minPrice;
			minPrice = tmpMax;
		}
		
		if (minPrice < 0)
			minPrice = 0;
		
		if (maxPrice < 0)
			return Collections.emptyList();
		
		if (prefix == null)
			return repository.findProductsByPriceRange(minPrice, maxPrice, pageable);
		
		return repository.findProductsByPriceRange(prefix, minPrice, maxPrice, pageable);
	}

}
