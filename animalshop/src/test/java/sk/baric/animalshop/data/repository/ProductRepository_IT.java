package sk.baric.animalshop.data.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import sk.baric.animalshop.data.entities.AnimalCategory;
import sk.baric.animalshop.data.entities.Product;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class ProductRepository_IT {

	@Inject
	ProductRepository testedClass;
	
	@Test
	public void findAll_Test() {
		List<Product> products = testedClass.findAll();
		
		assertThat(products.size(), is(3));
	}

	@Test
	public void findProductsById_Test() {
		Optional<Product> productWrapper = testedClass.findById(1l);
		assertTrue(productWrapper.isPresent());
		assertProductWithId1(productWrapper.get());
		
	}

	@Test
	public void findProductsByNamePrefix_Test() {
		List<Product> products = testedClass.findProductsByNamePrefix("Bal", null);
		assertThat(products.size(), is(1));		
		assertProductWithId1(products.get(0));
		
	}
	
	private void assertProductWithId1(Product product) {
		assertThat(product.getPrice(), is(5.0));
		assertThat(product.getCategories().size(), is(2));
		assertThat(product.getGallery().size(), is(3));
	}

	@Test
	public void findProductsByPriceRange_Test() {
		List<Product> products = testedClass.findProductsByPriceRange(6, 10, null);
		
		assertThat(products.size(), is(1));
		
		Product p = products.get(0);

		assertThat(p.getName(), is("Mouse doll"));
		assertThat(p.getCategories().size(), is(1));
		
	}
	
	@Test
	public void createProduct() {
		
		AnimalCategory dog = new AnimalCategory("Dog");
		
		Product product = new Product();
		product.setId(0l);
		product.setName("granule");
		product.setCategories(Collections.singleton(dog));
		product.setPrice(5);
		product.setDescription("product");
		
		testedClass.save(product);
		
		List<Product> all = testedClass.findAll();
		
		assertThat(all.size(), is(4));
	}
	

}
