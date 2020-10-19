package sk.baric.animalshop.services;

import java.util.List;

import sk.baric.animalshop.data.entities.Order;
import sk.baric.animalshop.data.entities.Product;
import sk.baric.animalshop.data.entities.User;

/**
 * Service class for operations on orders.
 * 
 * @author Juraj Baric
 *
 */
public interface OrderService {

	Order createOrder(User user, List<Product> products);
	
	List<Order> getOrders(User user);

}
