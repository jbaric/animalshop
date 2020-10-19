package sk.baric.animalshop.rest.endpoints;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import sk.baric.animalshop.AnimalShopApplication;
import sk.baric.animalshop.data.dto.UserDto;

@SpringBootTest(classes = AnimalShopApplication.class)
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
public class LoginEndpoints_IT {

	@Inject
    private MockMvc mockMvc;

    @Test
    public void testLoginCorrectPassword_byHeader() throws Exception {
    	
    	MvcResult result = mockMvc.perform(post("/login")
    			.header("Authorization", "Basic Juraj:Weak_Password1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
    	
    	assertValidAuthorization(result);
    }

    @Test
    public void testLoginCorrectPassword() throws Exception {
    	
    	String json = "{\"username\":\"Juraj\" , \"password\" : \"Weak_Password1\"}";
    	
    	MvcResult result = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk()).andReturn();
    	
    	assertValidAuthorization(result);
    }

    @Test
    public void testLoginCorrectPasswordByEmail() throws Exception {
    	    	
    	String json = "{\"password\" : \"Weak_Password1\" , \"email\" : \"jbaric@insolis.sk\"}";
    	
    	MvcResult result = mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk()).andReturn();
    	
    	assertValidAuthorization(result);
    	
    }
    
    void assertValidAuthorization(MvcResult result) {
    	String authorization = result.getResponse().getHeaderValue("Authorization").toString();
    	assertTrue(authorization.startsWith(AbstractEndpoint.AUTHORIZATION_TYPE_PREFIX), "invalid authorization string");
    	
    	authorization = authorization.substring(AbstractEndpoint.AUTHORIZATION_TYPE_PREFIX.length());
    	assertThat(authorization.length(), is(96));
    }

    @Test
    public void testLoginInvalPasswordFormat() throws Exception {
    	
    	UserDto data = new UserDto();
    	data.setEmail("123!ABCabc");
    	data.setEmail("jbaric@insolis.sk");
    	
    	String json = "{\"username\":\"Juraj\" , \"password\" : \"nepouzitelne-heslo\" , \"email\" : \"jbaric@insolis.sk\"}";
    	
    	mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is4xxClientError());
    	
    }

    @Test
    public void testLoginWrongPassword() throws Exception {
    	
    	UserDto data = new UserDto();
    	data.setEmail("123!ABCabc");
    	data.setEmail("jbaric@insolis.sk");
    	
    	String json = "{\"username\":\"Juraj\" , \"password\" : \"123!ABCabc\"}";
    	
    	mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().is(401));
    	
    }
}