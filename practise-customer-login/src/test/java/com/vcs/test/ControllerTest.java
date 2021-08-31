package com.vcs.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultHandler;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.ModelAndView;

import com.vcs.controller.VCSController;
import com.vcs.dao.CustomerDao;
import com.vcs.exception.InvalidLoginCredsException;
import com.vcs.model.Customer;
import com.vcs.repository.CustomerRepository;
import com.vcs.service.CustomerService;

import ch.qos.logback.core.boolex.Matcher;

@AutoConfigureMockMvc
@WebMvcTest(value = VCSController.class)
@RunWith(SpringRunner.class)
class ControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CustomerService customerService;
	
	@MockBean
	private CustomerDao customerDao;
	
	@MockBean
	private CustomerRepository customerRepo;
	
	
	@BeforeEach
	void setup() throws InvalidLoginCredsException {
		this.mockMvc = MockMvcBuilders.standaloneSetup(new VCSController()).build();
		
	}
	
	
	@Test
	void testLoginLandingPage() throws Exception {
		ModelAndView response = mockMvc.perform(get("/vcs/loginPage")).andReturn().getModelAndView();
		assertEquals("login", response.getViewName());
	}
	
	
	@Test
	void testSubmitLogindetailsPositive() throws Exception {
		Customer customer = new Customer();
		customer.setUserName("roshan968");
		customer.setPassword("roshan968");
		List<Customer> list = new ArrayList<Customer>();
		when(customerRepo.getCustomerWithUserName(Mockito.anyString())).thenReturn(list);
		when(customerDao.getCustomerWithUserName(Mockito.anyString())).thenReturn(list);
		when(customerService.getCustomerWithUnameAndPassword(Mockito.anyString(), Mockito.anyString())).thenReturn(customer);
		MockHttpServletRequestBuilder requestBuilder = post("http://localhost:8081/vcs/homePage").param("password", "roshan968").param("username", "roshan968");
		System.out.println(mockMvc.perform(requestBuilder));
		ModelAndView response = mockMvc.perform(requestBuilder).andReturn().getModelAndView();
		assertEquals("home", response.getViewName());
	}
		
	@Test
	void testRegister() throws Exception {
		ModelAndView response = mockMvc.perform(get("http://localhost:8081/vcs/registrationPage")).andReturn().getModelAndView();
		assertEquals("registration", response.getViewName());
	}
	
	@Test
	void testLoginAfterRegistration() throws Exception {
		Customer customer = new Customer();
		customer.setUserName("roshan968");
		
		when(customerService.saveCustomerToDB(Mockito.any(Customer.class))).thenReturn(customer);
		ModelAndView response = mockMvc.perform(post("http://localhost:8081/vcs/loginAfterRegistration")
				.param("name", "Roshan")
				.param("username", "roshan968")
				.param("password", "roshan968")
				.param("address", "DNO : 1-43, Cheruvugattu, Gajapathinagaram")
				.param("email", "mproshan968@gmail.com")
				.param("country", "India")
				.param("state", "Andhra Pradesh")
				.param("Pan", "DGAJDH678G")
				.param("phoneNo", "6281510634")
				.param("dob", "1998-04-13")
				.param("accountType", "savings"))
				.andDo((ResultHandler) customerService.saveCustomerToDB(customer))
				.andReturn().getModelAndView();
		assertEquals("login", response.getViewName());
		
	}
	
	

	
}
