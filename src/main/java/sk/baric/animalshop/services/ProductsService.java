package sk.baric.animalshop.services;

import java.util.List;

import org.springframework.data.domain.Pageable;

import sk.baric.animalshop.data.entities.Product;

/**
 * Service class for operations on products.
 * 
 * @author Juraj Baric
 *
 */
public interface ProductsService {

	List<Product> findProductsByNamePrefix(String prefix, Pageable pageable);

	List<Product> findProductsByPriceRange(double minPrice, double maxPrice, Pageable pageable);

	List<Product> findProductsByNamePrefixAndPriceRange(String prefix, double minPrice, double maxPrice, Pageable pageable);

}
