package sk.baric.animalshop.services;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import sk.baric.animalshop.data.entities.User;
import sk.baric.animalshop.data.repository.UserRepository;

/**
 * {@inheritDoc}
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	PasswordEncoder bc;
	
	@Inject
	UserRepository rep;
	
	/**
	 * {@inheritDoc}
	 */
	@Transactional
	@Override
	public User registerNewUser(String username, String email, String plaintextPassword) {
		User newUser = new User();
		newUser.setEmail(email);
		newUser.setUsername(username);
		newUser.setPasswordHash(bc.encode(plaintextPassword));
		
		newUser = rep.save(newUser);		
		return newUser;
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
