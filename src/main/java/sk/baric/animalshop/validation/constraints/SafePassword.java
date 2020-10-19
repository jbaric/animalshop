package sk.baric.animalshop.validation.constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import sk.baric.animalshop.validation.SafePasswordValidator;
import sk.baric.animalshop.validation.ValidationDefaults;

/**
 * The string has to be a safe password.
 * Safety rules:
 * length between 8 and 25 characters
 * use at least 1 character from each of these groups:
 *  - lowercase
 *  - uppercase
 *  - digits
 *  - special characters - see {@link ValidationDefaults#ALLOWED_SPECIAL_CHARS}
 * 
 * Validation logic in {@link SafePasswordValidator}
 * Accepts {@code CharSequence}.
 * 
 * @author Juraj Baric
 *
 */
@Target(value = {ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { SafePasswordValidator.class })
public @interface SafePassword {

	String message() default "{password_safety_guidlines}";
	
	Class<?>[] groups() default { };
	
	Class<? extends Payload>[] payload() default { };
}
