package sk.baric.animalshop.services;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import sk.baric.animalshop.data.entities.User;
import sk.baric.animalshop.data.repository.UserRepository;
import sk.baric.animalshop.rest.exceptions.ValueAlreadyExistsException;

/**
 * {@inheritDoc}
 */
@Service
public class UserServiceImpl implements UserService {
	
	Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	PasswordEncoder bc;
	
	@Inject
	UserRepository rep;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public User registerNewUser(String username, String email, String plaintextPassword) {
		User newUser = new User();
		newUser.setEmail(email);
		newUser.setUsername(username);
		newUser.setPasswordHash(bc.encode(plaintextPassword));
		
		try {
			newUser = rep.save(newUser);
			log.info("New user with usename '" + username + "' created");
			return newUser;
		}
		catch (DataIntegrityViolationException e) {
			if (rep.findUserByName(username) != null)
				throw new ValueAlreadyExistsException("User", "username", username);
			else if (rep.findUserByEmail(email) != null)
				throw new ValueAlreadyExistsException("User", "email", email);
			throw e;
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public User loginByUsername(String username, String plaintextPassword) {
		return login(rep.findUserByName(username), plaintextPassword);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public User loginByEmail(String email, String plaintextPassword) {
		return login(rep.findUserByEmail(email), plaintextPassword);
	}
	
	public User login(User user, String plaintextPassword) {
		if (user == null)
			return null;
		if (bc.matches(plaintextPassword, user.getPasswordHash()))
			return user;
		return null;
	}

}
