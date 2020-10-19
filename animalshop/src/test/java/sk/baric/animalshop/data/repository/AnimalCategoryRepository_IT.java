package sk.baric.animalshop.data.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import sk.baric.animalshop.data.entities.AnimalCategory;

@SpringBootTest
@AutoConfigureTestDatabase
public class AnimalCategoryRepository_IT {

	@Autowired
	AnimalCategoryRepository testedClass;
	
	@Test
	public void findAll_Test() {
		List<AnimalCategory> categories = testedClass.findAll();
		
		assertThat(categories.size(), is(3));
	}

}
