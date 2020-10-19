package sk.baric.animalshop.rest.endpoints;

import static java.util.stream.Collectors.toList;

import java.util.HashSet;
import java.util.List;

import javax.inject.Inject;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import sk.baric.animalshop.data.View.OrderView;
import sk.baric.animalshop.data.dto.OrderDto;
import sk.baric.animalshop.data.entities.Order;
import sk.baric.animalshop.data.entities.Product;
import sk.baric.animalshop.data.entities.User;
import sk.baric.animalshop.data.repository.OrderRepository;
import sk.baric.animalshop.data.repository.ProductRepository;
import sk.baric.animalshop.rest.exceptions.MissingProductsException;
import sk.baric.animalshop.rest.exceptions.NoProductOrderedException;
import sk.baric.animalshop.services.AuthorizationService;
import sk.baric.animalshop.services.OrderService;
import sk.baric.animalshop.validation.ValidationDefaults;

/**
 * Controller for rest endpoints handling orders
 * 
 * Since orders can create only signed users, an authorization check is required
 * 
 * @author Juraj Baric
 *
 */
@RestController()
@JsonView(Order.class)
@RequestMapping("/orders")
public class OrdersEndpoints extends AbstractEndpoint {

	@Inject
	AuthorizationService authorizationService;
	
	@Inject
	OrderService orderService;
	
	@Inject
	ProductRepository prodductRepository;
	
	@Inject
	OrderRepository orderRepository;
	
	/**
	 * Endpoint - retrieve list of orders that belong to signed user.
	 * 
	 * @param authorizationValue
	 * @return json list
	 */
	@GetMapping
	@JsonView(OrderView.OwnersOrder.class)
	public List<OrderDto> getOrders(@RequestHeader(name = "Authorization") String authorizationValue) {		
		String token = extractToken(authorizationValue);
		return convertToDtos(getOrdersRaw(token));
	}
	
	/**
	 * Submit a new order by passing products ids. Only for signed user.
	 * 
	 * @param ids
	 * @param authorizationValue
	 * @return
	 */
	@PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
	public String createOrder(@RequestParam("products-ids") List<Long> ids, @RequestHeader("Authorization") String authorizationValue) {
		
		if (ids.isEmpty())
			throw new NoProductOrderedException();
		
		String token = extractToken(authorizationValue);		
		User user = checkAndGetUser(token);
		
		List<Product> products = prodductRepository.findAllById(ids);
		
		if (ids.size() > products.size()) {
			getMissingProductIds(products, ids);
		}
		
		Order order = orderService.createOrder(user, products);
		if (order == null || order.getId() == null)
			technicalError("order could not be created!");
		return order.getId().toString();
	}

	private List<Order> getOrdersRaw(String token) {
		
		if (token == null) {
			return orderRepository.findAll();
		}
		return orderService.getOrders(checkAndGetUser(token));
	}
	
	private List<OrderDto> convertToDtos(List<Order> orders) {
		return orders.stream().map(o -> new OrderDto(o)).collect(toList());
	}
	
	private void getMissingProductIds(List<Product> products, List<Long> ids) {
		HashSet<Long> idsSet = new HashSet<>(ids);
		for (Product p : products)
			idsSet.remove(p.getId());
		if (!idsSet.isEmpty())
			throw new MissingProductsException(idsSet);
	}
	
	private User checkAndGetUser(String token) {
		if (token == null || token.length() != ValidationDefaults.EXPECTED_TOKEN_LENGTH)
			returnUnauthorizedAccess();
		
		User user = authorizationService.getAuthorizedUser(token);
		
		if (user == null)
			returnUnauthorizedAccess();
		return user;
	}
}
