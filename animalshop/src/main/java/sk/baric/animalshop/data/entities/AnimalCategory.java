package sk.baric.animalshop.data.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonView;

import sk.baric.animalshop.data.View;

/**
 * Animal categories data entity.
 * 
 * @author Juraj Baric
 *
 */
@Entity
public class AnimalCategory {

	@Id
	@JsonView({View.ProductView.Overview.class})
	private String name;
	
	public AnimalCategory() {}
	
	public AnimalCategory(String name) {this.name = name;}

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
	}
	
}

