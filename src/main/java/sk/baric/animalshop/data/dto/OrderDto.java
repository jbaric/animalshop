package sk.baric.animalshop.data.dto;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonView;

import sk.baric.animalshop.data.View;
import sk.baric.animalshop.data.entities.Order;

@XmlRootElement(name = "order")
public class OrderDto {

	@JsonView(View.OrderView.OwnersOrder.class)
	private Long id;

	@JsonView(View.OrderView.OwnersOrder.class)
	private double totalPrice;

	@JsonView(View.OrderView.OwnersOrder.class)
	private Instant time;

	@JsonView(View.OrderView.OwnersOrder.class)
	private List<Long> productIds;

	@JsonView(View.OrderView.AdminOrder.class)
	private Long userId;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public OrderDto(Order order) {
		super();
		this.id = order.getId();
		this.totalPrice = order.getTotalPrice();
		this.time = order.getTime();
		this.productIds = order.getProducts().stream().map(p -> p.getId()).collect(Collectors.toList());
		this.userId = order.getUser().getId();
	}

	/**
	 * @return the totalPrice
	 */
	public double getTotalPrice() {
		return totalPrice;
	}

	/**
	 * @param totalPrice the totalPrice to set
	 */
	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	/**
	 * @return the time
	 */
	public Instant getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(Instant time) {
		this.time = time;
	}

	/**
	 * @return the productIds
	 */
	public List<Long> getProductIds() {
		return productIds;
	}

	/**
	 * @param productIds the productIds to set
	 */
	public void setProductIds(List<Long> productIds) {
		this.productIds = productIds;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
