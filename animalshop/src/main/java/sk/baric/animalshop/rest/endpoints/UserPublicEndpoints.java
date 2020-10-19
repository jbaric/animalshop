package sk.baric.animalshop.rest.endpoints;

import java.util.Set;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sk.baric.animalshop.data.dto.UserDto;
import sk.baric.animalshop.data.entities.User;
import sk.baric.animalshop.services.UserService;

/**
 * Defines endpoints for public user web services
 *
 * @author Juraj Baric
 */
@RestController()
@RequestMapping("/user")
public class UserPublicEndpoints extends AbstractEndpoint {

	@Inject
	UserService userService;
	
	@Inject
	Validator validator;
	
	/**
	 * Controller for registering a new user endpoint.
	 * 
	 * @param data
	 * @return
	 */
	@PostMapping("")
	public User registerNewUserEndpoint(@RequestBody UserDto data) {
		Set<ConstraintViolation<UserDto>> vr = validator.validate(data);
		for(ConstraintViolation<UserDto> cr : vr) {
			returnBadRequest(cr.getMessage());
		}
		
		return userService.registerNewUser(data.getUsername(), data.getEmail(), data.getPassword());
	}
}
