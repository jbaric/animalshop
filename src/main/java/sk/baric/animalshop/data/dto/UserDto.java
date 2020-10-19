package sk.baric.animalshop.data.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

import sk.baric.animalshop.validation.ValidationDefaults;
import sk.baric.animalshop.validation.constraints.SafePassword;

@XmlRootElement(name = "user")
public class UserDto {

	@NotNull
	@Email
	private String email;

	@NotNull
	@Pattern(regexp=ValidationDefaults.USER_NAME_PATTERN)
	private String username;

	@NotNull
	@SafePassword
	private String password;

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
}
