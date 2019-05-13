
package com.payconiq.ecommerce;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.UUID;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.payconiq.ecommerce.EcommerceApp;
import com.payconiq.ecommerce.beans.Stock;
import com.payconiq.ecommerce.service.StockService;

@RunWith(SpringRunner.class)
@SpringBootTest(
	classes = {	EcommerceApp.class,
			
				}
		)
@TestPropertySource(
	locations = { "classpath:application.yml"})
@AutoConfigureMockMvc
@ActiveProfiles( "test" )
public class EcommerceRestApiControllerIntegrationTest {
    
	final String listStocksURI = "/api/stocks/";
    final String crateStockURI = "/api/stocks/";
    final String updateStockURI = "/api/stocks/{id}";
    final String findStockURI = "/api/stocks/{id}";
    
	
    
	@Autowired
    private MockMvc mvc;
    
	RestTemplate restTemplate = new RestTemplate();
    
	@Autowired
	StockService stockService;

	static ObjectMapper objectMapper = new ObjectMapper();
    static Stock createStock;
    static Stock updateStock;
	@BeforeClass
	public static void setup() {
		try {
			createStock = objectMapper.readValue(createStockReq, Stock.class);
			updateStock = objectMapper.readValue(updateStockReq, Stock.class);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
    final  Long stockId = 101l;
    static HttpHeaders requestHeaders;
    static String createStockReq="{\n" + 
    		"        \"id\": 201,\n" + 
    		"        \"name\": \"ADP\",\n" + 
    		"        \"currentPrice\": 201.8,\n" + 
    		"        \"timestamp\": \"2019-05-09 01:36:53\"\n" + 
    		"	}";
	static String updateStockReq="	{\n" + 
			"		\"id\": 101,\n" + 
			"        \"name\": \"BNP Fortis\",\n" + 
			"        \"currentPrice\": 201.8,\n" + 
			"        \"timestamp\": \"2019-05-09 01:36:53\"\n" + 
			"	}";
    
    
   private HttpHeaders getHeaders() {
    	if (requestHeaders != null) {
    		return requestHeaders;
    	}
    	requestHeaders= new HttpHeaders();
    	requestHeaders.setContentType(MediaType.APPLICATION_JSON);
    	requestHeaders.add("uuid", UUID.randomUUID().toString());
    	return requestHeaders;
    }
    
	
   @Test
   public void listStocksTest() throws Exception {
   	HttpHeaders httpHeaders = getHeaders();
   	mvc.perform(get(listStocksURI)
       		.headers(httpHeaders))
   			.andDo(print())
   			.andExpect(status().is2xxSuccessful()
       		);
   }
   
	
    @Test
    public void createStockTest() throws Exception {

    	HttpHeaders httpHeaders = getHeaders();
    	mvc.perform(post(crateStockURI)
        				.headers(httpHeaders)
        				.content(objectMapper.writeValueAsString(createStock)))
    					.andDo(print())
    					.andExpect(status().is2xxSuccessful()
    					);
    }
    

    @Test
    public void updateStockTest() throws Exception {

    	HttpHeaders httpHeaders = getHeaders();
    	mvc.perform(put(updateStockURI,stockId)
        				.headers(httpHeaders)
        				.content(objectMapper.writeValueAsString(updateStock))
        				)
    			.andDo(print())
    			.andExpect(status().is2xxSuccessful()
        		);
    }
    
    
    @Test
    public void findStockTest() throws Exception {
    	HttpHeaders httpHeaders = getHeaders();
    	mvc.perform(get(findStockURI,stockId)
        		.headers(httpHeaders))
    			.andDo(print())
    			.andExpect(status().is2xxSuccessful()
        		);
    }
    
    
    //------------- -ve test cases  -----------------------------------
    
    @Test
    public void createStock_409_http_status_Test() throws Exception {
    	
    	String createStockReq="{\n" + 
        		"        \"id\": 201,\n" + 
        		"        \"nam\": \"\",\n" + 
        		"        \"currentPrice\": 201.8,\n" + 
        		"        \"timestamp\": \"2019-05-09 01:36:53\"\n" + 
        		"	}";

    	HttpHeaders httpHeaders = getHeaders();
    	mvc.perform(post(crateStockURI)
        				.headers(httpHeaders)
        				.content(createStockReq))
    					.andDo(print())
    					.andExpect(status().isBadRequest()
    					);
    }
    
    
    @Test
    public void updateStock_404_http_status_Test() throws Exception {
    	
    	
    	 String updateStockReq="	{\n" + 
    			"		\"id\": 1001,\n" + 
    			"        \"name\": \"BNP Fortis\",\n" + 
    			"        \"currentPrice\": 201.8,\n" + 
    			"        \"timestamp\": \"2019-05-09 01:36:53\"\n" + 
    			"	}";

    	HttpHeaders httpHeaders = getHeaders();
    	mvc.perform(put(updateStockURI,1001)
        				.headers(httpHeaders)
        				.content(updateStockReq)
        				)
    			.andDo(print())
    			.andExpect(status().is4xxClientError()
        		);
    }
    
    @Test
    public void findStock_() throws Exception {
    	HttpHeaders httpHeaders = getHeaders();
    	mvc.perform(get(findStockURI,3555)
        		.headers(httpHeaders))
    			.andDo(print())
    			.andExpect(status().is4xxClientError()
        		);
    }
    
   
}