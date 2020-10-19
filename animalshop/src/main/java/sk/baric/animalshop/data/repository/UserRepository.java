package sk.baric.animalshop.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sk.baric.animalshop.data.entities.User;

/**
 * Repository for users
 * 
 * @author Juraj Baric
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	/**
	 * Query to find products filtered by username
	 * 
	 * @param username
	 * @return unique user
	 */
	@Query("SELECT u FROM User u WHERE u.username = :username")
	User findUserByName(@Param("username") String username);

	/**
	 * Query to find products filtered by email
	 * 
	 * @param email
	 * @return unique user
	 */
	@Query("SELECT u FROM User u WHERE u.email = :email")
	User findUserByEmail(@Param("email") String email);
}
