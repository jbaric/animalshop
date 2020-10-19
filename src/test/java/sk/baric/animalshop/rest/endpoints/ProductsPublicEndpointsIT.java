package sk.baric.animalshop.rest.endpoints;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import sk.baric.animalshop.AnimalShopApplication;

@SpringBootTest(classes = AnimalShopApplication.class)
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
public class ProductsPublicEndpointsIT {

	@Inject
    private MockMvc mockMvc;

    @Test
    public void testRetrievingAllProducts() throws Exception {
        mockMvc.perform(get("/products")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    public void testRetrievingOnePageOfProducts() throws Exception {
        mockMvc.perform(get("/products?size=2")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.length()", is(2)));
    }

    @Test
    public void testRetrievingProductsUnder10Eur() throws Exception {
        mockMvc.perform(get("/products?max-price=10"))
	        .andDo(print())
	        .andExpect(status().isOk())
	        .andExpect(jsonPath("$.length()", is(2)))
			.andExpect(jsonPath("$[0].price").value(lessThan(10.0)))
			.andExpect(jsonPath("$[1].price").value(lessThan(10.0)));
    }

    @Test
    public void testRetrievingSecondPageOfProducts() throws Exception {
        mockMvc.perform(get("/products?size=2&page=1")).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.length()", is(1)));
    }

    @Test
    public void testRetrievingProductsWithPrefix_Bal() throws Exception {
        mockMvc.perform(get("/products?name=bal"))
        	.andDo(print())
        	.andExpect(status().isOk())
        	.andExpect(jsonPath("$.length()", is(1)))
    		.andExpect(jsonPath("$[0].name", is("Ball")))
			.andExpect(jsonPath("$[0].categories.length()", is(2)));
    }
}
