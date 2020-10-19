package sk.baric.animalshop.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import sk.baric.animalshop.data.entities.Order;
import sk.baric.animalshop.data.entities.Product;
import sk.baric.animalshop.data.entities.User;
import sk.baric.animalshop.data.repository.OrderRepository;

/**
 * {@inheritDoc}
 */
@Service
public class OrderServiceImpl implements OrderService {
	
	@Inject
	OrderRepository repository;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Order createOrder(User user, List<Product> products) {
		Order o = new Order();
		o.setTime(Instant.now().truncatedTo(ChronoUnit.SECONDS));
		o.setUser(user);
		o.setProducts(products);
		o.setTotalPrice(products.stream().mapToDouble(p -> p.getPrice()).sum());
		o = repository.save(o);
		return o;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<Order> getOrders(User user) {
		return repository.findByUser(user);
	}

}
