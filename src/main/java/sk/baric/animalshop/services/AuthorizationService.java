package sk.baric.animalshop.services;

import sk.baric.animalshop.data.entities.User;

/**
 * Service class that handles authorization
 * 
 * @author Juraj Baric
 *
 */
public interface AuthorizationService {

	User getAuthorizedUser(String token);

	public String loginByUsername(String username, String plaintextPassword);

	public String loginByEmail(String email, String plaintextPassword);
	
	public String generateToken();
}
