package sk.baric.animalshop.data.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import sk.baric.animalshop.data.entities.User;
import sk.baric.animalshop.services.AuthorizationServiceImpl;
import sk.baric.animalshop.services.UserService;

@SpringBootTest
public class AuthorizationServiceImplUnitTest {

	@Inject
	AuthorizationServiceImpl service;

	@MockBean
	UserService userService;

	@Test
	public void loginByEmail() {
		String email = "test@test.com";
		String plaintextPassword = "123456789";
		
		User user = new User();
		user.setEmail(email);
		user.setUsername("Test");

		Mockito.when(userService.loginByEmail(email, plaintextPassword)).thenReturn(user);

		String token = service.loginByEmail(email, plaintextPassword);

		assertEquals(service.getAuthorizedUser(token), user);
		assertThat(token.length(), is(96));

		Mockito.verify(userService, times(1)).loginByEmail(email, plaintextPassword);
	}

}
