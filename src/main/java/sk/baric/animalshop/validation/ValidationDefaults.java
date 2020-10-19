package sk.baric.animalshop.validation;

/**
 * Defines some constants for validations
 * 
 * Do not instantiate
 * 
 * @author Juraj Baric
 *
 */
public final class ValidationDefaults {

	/**
	 * Regex patterns for user name
	 */
	public final static String USER_NAME_PATTERN = "[a-zA-Z0-9\\_\\-\\\\-]{3,18}";
	
	/**
	 * Special characters allowed to be used in a password
	 */
	public final static String ALLOWED_SPECIAL_CHARS = "?!./<>{}[]()\\-_|,=+*'\"";
	
	/**
	 * Length of a valid token
	 */
	public final static int EXPECTED_TOKEN_LENGTH = 96;
	
	private ValidationDefaults() {}
	
}
