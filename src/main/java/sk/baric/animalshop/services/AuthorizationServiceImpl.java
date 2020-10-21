package sk.baric.animalshop.services;

import java.security.SecureRandom;
import java.util.concurrent.ConcurrentHashMap;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import sk.baric.animalshop.data.entities.User;
import sk.baric.animalshop.validation.ValidationDefaults;

/**
 * {@inheritDoc}
 */
@Service
public class AuthorizationServiceImpl implements AuthorizationService {

	Logger log = LoggerFactory.getLogger(AuthorizationServiceImpl.class);
	
	@Inject
	UserService userService;
	
	ConcurrentHashMap<String, User> tokens = new ConcurrentHashMap<>();
	
	private final int tokenLen = ValidationDefaults.EXPECTED_TOKEN_LENGTH;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public User getAuthorizedUser(String token) {
		return tokens.get(token);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String loginByUsername(String username, String plaintextPassword) {		
		return registerTokenForUser(userService.loginByUsername(username, plaintextPassword));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String loginByEmail(String email, String plaintextPassword) {
		return registerTokenForUser(userService.loginByEmail(email, plaintextPassword));
	}
	
	private String registerTokenForUser(User user) {
		if (user == null)
			return null;
		
		String token = null;	
		do {
			token = generateToken();
		} while(tokens.putIfAbsent(token, user) != null);
		
		
		log.trace("New token generated for user " + user.getUsername() + " with values " + token);
		return token;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String generateToken() {
		SecureRandom sr = new SecureRandom();
		StringBuffer sb = new StringBuffer(tokenLen + 15);
		while(sb.length() < tokenLen)
			sb.append(Long.toHexString(sr.nextLong()));
		return sb.substring(0, tokenLen);
	}

	
}
