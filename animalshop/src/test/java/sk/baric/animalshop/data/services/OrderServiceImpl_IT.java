package sk.baric.animalshop.data.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import sk.baric.animalshop.data.entities.Order;
import sk.baric.animalshop.data.entities.Product;
import sk.baric.animalshop.data.entities.User;
import sk.baric.animalshop.data.repository.ProductRepository;
import sk.baric.animalshop.data.repository.UserRepository;
import sk.baric.animalshop.services.OrderServiceImpl;

/**
 * 
 * @author Juraj Baric
 * 
 * Integration test for {@link OrderServiceImpl}.
 *
 */
@SpringBootTest
@AutoConfigureTestDatabase
public class OrderServiceImpl_IT {
	
	@Inject
	UserRepository userRepository;

	@Inject
	ProductRepository productRepository;

	@Inject
	OrderServiceImpl service;
	
	/**
	 * Test
	 *
	 * create Order as user with id 1.
	 * Then list orders for user with id 1 and check, if the new order is already listed
	 */
	@Test
	public void createOrderAndReadOrdersForUser() {
		Optional<User> userWrapper = userRepository.findById(1l);
		assertTrue(userWrapper.isPresent());		
		User user = userWrapper.get();
		
		List<Product> products = productRepository.findAll();
		
		Order o = service.createOrder(user, products);
		assertNotNull(o);
		Instant created = o.getTime();
		
		List<Order> orders = service.getOrders(user);
		assertThat(orders.size(), is(1));
		
		Order persistedOrder = orders.get(0);
		assertThat(persistedOrder.getTime(), is(created));
		assertThat(persistedOrder.getProducts().size(), is(products.size()));
		
		double expectedTotalPrice = products.stream().mapToDouble(p -> p.getPrice()).sum();
		assertThat(persistedOrder.getTotalPrice(), is(expectedTotalPrice));
	}
	
}
