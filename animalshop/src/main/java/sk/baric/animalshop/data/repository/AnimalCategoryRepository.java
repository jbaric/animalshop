package sk.baric.animalshop.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import sk.baric.animalshop.data.entities.AnimalCategory;

/**
 * Repository for animal category
 * 
 * @author Juraj Baric
 *
 */
@Repository
public interface AnimalCategoryRepository extends JpaRepository<AnimalCategory, String> {
	
}