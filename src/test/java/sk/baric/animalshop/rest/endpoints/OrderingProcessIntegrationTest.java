package sk.baric.animalshop.rest.endpoints;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

/**
 * Test for an entire user work flow. Consists of following use cases:
 * 1. register a new user
 * 2. login with new user
 * 3. create order with products 1,2,3
 * 4. create order with products 2,3
 * 5. get orders and check them
 * 
 * @author Juraj Baric
 *
 */
@SpringBootTest(classes = AnimalShopApplication.class)
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
public class OrderingProcessIntegrationTest {
	
	@Inject
    private MockMvc mockMvc;

    @Test
    public void createUserLoginOrderAndChceckOrders() throws Exception {

    	// register as Peter
    	String json = "{\"password\" : \"123!ABCabc\" , \"email\" : \"test@test.sk\", \"username\":\"Peter\"}";
    	
    	mockMvc.perform(post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    	
    	// login as Peter
    	MvcResult result = mockMvc.perform(post("/login")
    			.header("Authorization", "Basic Peter:123!ABCabc"))
                .andExpect(status().isOk()).andReturn();
    	
    	String authorization = result.getResponse().getHeaderValue("Authorization").toString();
    	assertThat(authorization.length(), is(AbstractEndpoint.AUTHORIZATION_TYPE_PREFIX.length() + 96));
    	assertTrue(authorization.startsWith(AbstractEndpoint.AUTHORIZATION_TYPE_PREFIX), "invalid authorization string"); 

    	// Create 2 orders
    	order("1,2,3", authorization);
    	order("2,3", authorization);
    	
    	// get my orders and check their count and total prices
    	result = mockMvc.perform(get("/orders")
                .contentType(MediaType.APPLICATION_JSON)
    			.header("Authorization", authorization))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(2)))
                .andExpect(jsonPath("$[0].totalPrice", is(28.0)))
                .andExpect(jsonPath("$[0].productIds.length()", is(3)))
                .andExpect(jsonPath("$[1].totalPrice", is(23.0)))
                .andExpect(jsonPath("$[1].productIds.length()", is(2)))
                .andReturn();
    }
    
    private void order(String productsListParam, String authorization) throws Exception {

    	mockMvc.perform(post("/orders?products-ids=" + productsListParam)
                .contentType(MediaType.APPLICATION_JSON)
    			.header("Authorization", authorization))
                .andExpect(status().isOk());
    	
    }
}
