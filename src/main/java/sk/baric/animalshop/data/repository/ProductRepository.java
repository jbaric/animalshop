package sk.baric.animalshop.data.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sk.baric.animalshop.data.entities.Product;

/**
 * Repository for products
 * 
 * @author Juraj Baric
 *
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

	/**
	 * Query to find products filtered by name prefix
	 * 
	 * @param prefix - name prefix filter
	 * @param pageable
	 * @return list of products
	 */
	@Query("SELECT p FROM Product p WHERE p.lowerName LIKE lower(concat(:prefix, '%'))")
	List<Product> findProductsByNamePrefix(@Param("prefix") String prefix, Pageable pageable);

	/**
	 * Query to find products filtered by a price range
	 * 
	 * @param minPrice - a lower limit of a range
	 * @param maxPrice - an upper limit of a range
	 * @param pageable
	 * @return list of products
	 */
	@Query("SELECT p FROM Product p WHERE p.price between :minPrice AND :maxPrice")
	List<Product> findProductsByPriceRange(@Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice, Pageable pageable);

	/**
	 * Query to find products filtered by a price range and name prefix
	 * 
	 * @param prefix - name prefix filter
	 * @param minPrice - a lower limit of a range
	 * @param maxPrice - an upper limit of a range
	 * @param pageable
	 * @return list of products
	 */
	@Query("SELECT p FROM Product p WHERE p.lowerName LIKE lower(concat(:prefix, '%')) AND p.price between :minPrice AND :maxPrice")
	List<Product> findProductsByPriceRange(@Param("prefix") String prefix, @Param("minPrice") double minPrice, @Param("maxPrice") double maxPrice, Pageable pageable);
}
