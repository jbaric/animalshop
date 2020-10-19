package sk.baric.animalshop.rest.endpoints;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.server.ResponseStatusException;

import sk.baric.animalshop.validation.ValidationDefaults;

/**
 * Common super class for all endpoints. Defines some useful methods.
 * 
 * @author Juraj Baric
 *
 */
abstract class AbstractEndpoint {
	
	public final static String AUTHORIZATION_TYPE_PREFIX = "Basic ";

	/**
	 * Throws ResponseStatusException
	 * 
	 * @param status
	 * @param msg
	 */
	void error(HttpStatus status, String msg) {
		throw new ResponseStatusException(status, msg);		
	}
	
	/**
	 * Throws ResponseStatusException
	 * 
	 * @param status
	 * @param msg
	 */
	void clientError(HttpStatus status, String msg) {
		throw new HttpClientErrorException(status, msg);		
	}

	/**
	 * Returns error 400 - Bad request
	 * 
	 * @param msg
	 */
	void returnBadRequest(String msg) {
		clientError(HttpStatus.BAD_REQUEST, msg);
	}
	
	void checkDependencies(Object dependency) {
		if (dependency == null) {
			technicalError("server configuration failed");
		}
	}
	
	/**
	 * Returns technical error 500
	 * 
	 * @param msg
	 */
	void technicalError(String msg) {
		error(HttpStatus.INTERNAL_SERVER_ERROR, msg);
	}

	/**
	 * Returns error 401
	 * 
	 * @param msg
	 */
	void returnAuthorisation(String msg) {
		clientError(HttpStatus.UNAUTHORIZED, msg);
	}

	/**
	 * Returns error 403
	 * 
	 * @param msg
	 */
	void returnForbidden(String msg) {
		clientError(HttpStatus.FORBIDDEN, msg);
	}
		
	/**
	 * Returns error 401
	 * Call when a resource access is not authorized
	 */
	void returnUnauthorizedAccess() {
		returnAuthorisation("you do not habe permissions to access this resource");
	}
	
	/**
	 * Returns error 401
	 * Call when a operation call is not authorized
	 */
	void returnUnauthorizedOperation() {
		returnAuthorisation("you do not habe permissions to process this operation");
	}
	
	/**
	 * Extracts value from 'Authorized' basic string
	 * 
	 * @param authorizationValue
	 * @return value
	 */
	String extractBasicAuthorizationData(String authorizationValue) {
		if (authorizationValue == null || !authorizationValue.startsWith(AUTHORIZATION_TYPE_PREFIX))
			returnUnauthorizedAccess();
		
		return authorizationValue.substring(AUTHORIZATION_TYPE_PREFIX.length());
	}
	
	/**
	 * Extracts token from 'Authorized' basic string
	 * 
	 * @param authorizationValue
	 * @return token
	 */
	String extractToken(String authorizationValue) {
		String token = extractBasicAuthorizationData(authorizationValue);
		
		if (token.length() != ValidationDefaults.EXPECTED_TOKEN_LENGTH)
			returnUnauthorizedAccess();
		return token;
	}
	
	/**
	 * Extracts username and password from 'Authorized' basic string
	 * 
	 * @param authorizationValue
	 * @return {username, password}
	 */
	String[] extractUsernameAndPassword(String authorizationValue) {
		String values = extractBasicAuthorizationData(authorizationValue);
		
		int colon = values.indexOf(":");
		if (colon < 3)
			returnUnauthorizedAccess();
		
		return new String[] {values.substring(0, colon), values.substring(colon + 1)};
	}
	
	/**
	 * Adds token value to header	
	 * @param response
	 * @param token
	 */
	void addAuthorizationToken(HttpServletResponse response, String token) {
		response.addHeader("Authorization", AUTHORIZATION_TYPE_PREFIX + token);
	}
}
