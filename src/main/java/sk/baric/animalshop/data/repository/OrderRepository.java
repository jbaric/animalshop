package sk.baric.animalshop.data.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sk.baric.animalshop.data.entities.Order;
import sk.baric.animalshop.data.entities.User;

/**
 * Repository for orders
 * 
 * @author Juraj Baric
 *
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
	
	/**
	 * Query to find orders for a specific user
	 * 
	 * @param user
	 * @return list of user's orders
	 */
	@Query("SELECT o FROM Order o WHERE o.user = :user")
	List<Order> findByUser(@Param("user") User user);
	
}
