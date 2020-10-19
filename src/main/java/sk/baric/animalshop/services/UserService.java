package sk.baric.animalshop.services;

import sk.baric.animalshop.data.entities.User;

/**
 * Service class for basic user operations 
 * 
 * @author Juraj Baric
 *
 */
public interface UserService {

	/**
	 * Registers new user
	 * 
	 * @param username
	 * @param email
	 * @param plaintextPassword
	 * @return new user
	 */
	public User registerNewUser(String username, String email, String plaintextPassword);

	/**
	 * Checks the password and returns logged user
	 * 
	 * @param username
	 * @param plaintextPassword
	 * @return user if username exists and password fits, else null
	 */
	public User loginByUsername(String username, String plaintextPassword);

	/**
	 * Checks the password and returns logged user
	 * 
	 * @param email
	 * @param plaintextPassword
	 * @return user if email exists and password fits, else null
	 */
	public User loginByEmail(String email, String plaintextPassword);
}
