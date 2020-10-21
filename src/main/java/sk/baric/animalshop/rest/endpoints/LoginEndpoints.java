package sk.baric.animalshop.rest.endpoints;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sk.baric.animalshop.data.dto.UserDto;
import sk.baric.animalshop.services.AuthorizationService;
import sk.baric.animalshop.services.UserService;

/**
 * Controller for login operation via rest
 * 
 * @author Juraj Baric
 *
 */
@RestController()
@RequestMapping("/login")
public class LoginEndpoints extends AbstractEndpoint {

	Logger log = LoggerFactory.getLogger(LoginEndpoints.class);

	@Inject
	UserService userService;
	
	@Inject
	AuthorizationService authorizationService;

	@Inject
	Validator validator;

	public void login(HttpServletRequest request, HttpServletResponse response, String authorizationValue) {		
		String[] data = extractUsernameAndPassword(authorizationValue);
		
		if (validator.validateValue(UserDto.class, "username", data[0]).size() > 0)
			loginFailed(request);
		
		if (validator.validateValue(UserDto.class, "password", data[1]).size() > 0)
			loginFailed(request);
		
		String token = authorizationService.loginByUsername(data[0], data[1]);
		
		if (token == null)
			loginFailed(request);
		
		addAuthorizationToken(response, token);
	}

	/**
	 * Endpoint for login service by sending post parameters
	 * 
	 * @param data
	 */
	@PostMapping(produces = MediaType.TEXT_PLAIN_VALUE)
	public void login(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody(required = false) UserDto data,
			@RequestHeader(name = "Authorization", required = false) String authorizationValue) {
		
		if (data == null) {
			login(request, response, authorizationValue);
		}
		else {
			if (validator.validateProperty(data, "password").size() > 0)
				loginFailed(request);
			
			String token = null;
			String password = data.getPassword();
			
			if (validator.validateProperty(data, "username").size() > 0) {
				
				if (validator.validateProperty(data, "email").isEmpty()) {
	
					String email = data.getEmail();				
					token = authorizationService.loginByEmail(email, password);
				}
			}
			else {
				String username = data.getUsername();
				token = authorizationService.loginByUsername(username, password);
			}
			
			if (token == null)
				loginFailed(request);
			addAuthorizationToken(response, token);
		}
	}
	
	private void loginFailed(HttpServletRequest request) {
		log.warn("Failed login attempt from " + request.getRemoteAddr());
		
		returnAuthorisation("to login a valid username or email, and a valid password required");
	}
	
}
