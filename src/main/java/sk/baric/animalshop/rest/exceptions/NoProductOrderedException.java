package sk.baric.animalshop.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

/**
 * Throw this exception, when no product ids are defined by the user during submiting an order.
 * 
 * @author Juraj Baric
 *
 */
@SuppressWarnings("serial")
public class NoProductOrderedException extends HttpClientErrorException {

	public NoProductOrderedException() {
		super(HttpStatus.UNPROCESSABLE_ENTITY, "You didn't define any product id");
	}

}
