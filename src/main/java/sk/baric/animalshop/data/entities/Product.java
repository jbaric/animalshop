package sk.baric.animalshop.data.entities;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Product data entity.
 * 
 * @author Juraj Baric
 *
 */
@Entity
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
    @Column
	private String name;
	
	@ManyToMany	(fetch = FetchType.EAGER)
	@JoinTable(
			  name = "PRODUCT_CATEGORY", 
			  inverseJoinColumns = @JoinColumn(name = "CATEGORY_NAME"))
	private Set<AnimalCategory> categories;

	@NotNull
    @Column
	private double price;

	@NotNull
    @Column
	private String description;
	
	@JsonIgnore
	@Column
	private String lowerName;
	
	@Column(name="url")
	@ElementCollection(targetClass=String.class, fetch = FetchType.EAGER)
	private Set<String> gallery;

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

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
		this.lowerName = name.toLowerCase();
	}

	/**
	 * @return the categories
	 */
	public Set<AnimalCategory> getCategories() {
		return categories;
	}

	/**
	 * @param categories the categories to set
	 */
	public void setCategories(Set<AnimalCategory> categories) {
		this.categories = categories;
	}

	/**
	 * @return the price
	 */
	public double getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(double price) {
		this.price = price;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the gallery
	 */
	public Set<String> getGallery() {
		return gallery;
	}

	/**
	 * @param gallery the gallery to set
	 */
	public void setGallery(Set<String> gallery) {
		this.gallery = gallery;
	}
	
	public String getLowerName() {
		return lowerName;
	}
	
	public void setLowerName(String lowerName) {
		this.lowerName = lowerName;
		this.name = lowerName;
	}
}

