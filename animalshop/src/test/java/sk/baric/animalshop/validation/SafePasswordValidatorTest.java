package sk.baric.animalshop.validation;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.junit.jupiter.api.Test;

import sk.baric.animalshop.data.dto.UserDto;

class SafePasswordValidatorTest {

	@Test
	void isValidTest() {
		SafePasswordValidator validator = new SafePasswordValidator();
		validator.initialize(null);

		assertFalse(validator.isValid("slabeheslo", null));
		assertFalse(validator.isValid("Slabeheslo", null));
		assertFalse(validator.isValid("slabeheslo1", null));
		assertFalse(validator.isValid("slabeh?eslo", null));
		assertFalse(validator.isValid("slabe.heslo", null));

		assertTrue(validator.isValid("S1ln3.heslo!", null));
	}
	
	private ConstraintViolation<UserDto> validatePassword(String password) {
		UserDto userData = new UserDto();
		userData.setEmail("correct@email.com");
		userData.setUsername("correct-username");
		userData.setPassword(password);

		Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
		
		Set<ConstraintViolation<UserDto>> vr = validator.validate(userData);
		
		if (vr.isEmpty())
			return null;
		return vr.iterator().next();
	}

	@Test
	void testStringPasswordIT() {
		assertNull(validatePassword("VeryStr0ng_Password!?"));
	}

	@Test
	void testStringPassword2IT() {
		assertNull(validatePassword("VeryStr0ngPassword**"));
	}

	@Test
	void testWeakPasswordIT() {		
		ConstraintViolation<UserDto> cr = validatePassword("weak-password");
		assertNotNull(cr);
		String msg = cr.getMessage();
		System.out.println("Validation message SafePasswordValidatorTest.testIT: " + msg);
		assertThat(msg, is("Password must be at least 8 characters long (max 25), and must contain at least 1 character from each of thease groups: lower case, upper caser alphabets, digits, special characters (?!<>,.{}[])"));
	}

	@Test
	void testWeakPassword2IT() {		
		ConstraintViolation<UserDto> cr = validatePassword("weak-password");
		assertNotNull(cr);
		assertThat(cr.getMessageTemplate(), is("{password_safety_guidlines}"));
	}

}
