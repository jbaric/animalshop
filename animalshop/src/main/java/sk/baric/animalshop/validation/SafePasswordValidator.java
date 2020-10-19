package sk.baric.animalshop.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import sk.baric.animalshop.validation.constraints.SafePassword;

/**
 * Implements the password safety rules
 * 
 * Safety rules:
 * length between 8 and 25 characters
 * use at least 1 character from each of these groups:
 *  - lowercase
 *  - uppercase
 *  - digits
 *  - special characters - see {@link ValidationDefaults#ALLOWED_SPECIAL_CHARS}
 * 
 * Accepts {@code CharSequence}.
 * 
 * @author Juraj Baric
 *
 */
public class SafePasswordValidator implements ConstraintValidator<SafePassword, CharSequence> {
	
	
	private String specialCharsPatterns;
	
	/**
	 * {@inheritDoc}
	 */
	public void initialize(SafePassword constraintAnnotation) {
		String specialChars = ValidationDefaults.ALLOWED_SPECIAL_CHARS;
		StringBuffer pattern = new StringBuffer(".*[");
		for (int i = 0; i < specialChars.length(); i++) {
			pattern.append("\\");
			pattern.append(specialChars.charAt(i));
		}
		pattern.append("]+.*");
		specialCharsPatterns = pattern.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isValid(CharSequence value, ConstraintValidatorContext arg1) {
		if (value == null)
			return false;
		
		if (value.length() < 8 || value.length() > 25)
			return false;
		
		String text = value.toString();

		// must contain lower case
		if (!text.matches(".*[a-z]+.*"))
			return false;
		// must contain upper case
		if (!text.matches(".*[A-Z]+.*"))
			return false;
		// must contain numbers
		if (!text.matches(".*[0-9]+.*"))
			return false;
		// must contain special characters
		if (!text.matches(specialCharsPatterns))
			return false;
		
		return true;
	}
}
