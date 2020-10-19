package sk.baric.animalshop.data.dto;

import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlRootElement;

import sk.baric.animalshop.data.entities.Product;

@XmlRootElement(name = "product")
public class ProductOvervierwDto {

	private Long id;
	
	private String name;
	
	private List<String> categories;

	private double price;
	
	public ProductOvervierwDto (Product product) {
		id = product.getId();
		name = product.getName();
		categories = product.getCategories().stream().map(c -> c.getName()).collect(Collectors.toList());
		price = product.getPrice();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the categories
	 */
	public List<String> getCategories() {
		return categories;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}
}
