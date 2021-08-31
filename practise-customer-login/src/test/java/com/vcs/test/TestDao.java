package com.vcs.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.vcs.dao.CustomerDao;
import com.vcs.model.Customer;
import com.vcs.repository.CustomerRepository;

@SpringBootTest
class TestDao {

	@Autowired
	private CustomerDao customerDao;
	
	@MockBean
	private CustomerRepository customerRepo;
	
	
	@Test
	void testSaveCustomerToDB() {
		Customer customer = new Customer();
		customer.setUserName("roshan968");
		customer.setName("Roshan");
		customer.setPassword("roshan968");
		when(customerRepo.save(Mockito.any(Customer.class))).thenReturn(customer);
		assertEquals(customer.getUserName(), customerDao.saveCustomerToDB(customer).getUserName());
	}
	
	@Test
	void testRetrieveCustomerFromDB() {
		Customer customer = new Customer();
		customer.setUserName("roshan968");
		customer.setName("Roshan");
		customer.setPassword("password");
		customer.setAccountType("savings");
		customer.setAddress("DNO:1-98, VSKP");
		customer.setCountry("India");
		customer.setDob(LocalDate.parse("1998-04-13"));
		customer.setPan("DGFHH2345E");
		customer.setPhoneNo(6453289071L);
		customer.setState("Andhra Pradesh");
		when(customerRepo.getCustomerWithUnameAndPassword(Mockito.anyString(), Mockito.anyString())).thenReturn(customer);
		assertEquals(customer.getName(), customerDao.getCustomerWithUnameAndPassword(customer.getUserName(), customer.getPassword()).getName());
	}
	
	@Test
	void testCustomerWithMailId() {
		Customer customer = new Customer();
		customer.setUserName("roshan968");
		customer.setName("Roshan");
		customer.setPassword("roshan968");
		customer.setEmail("mproshan968@gmail.com");
		when(customerRepo.getCustomerWithMailId(Mockito.anyString())).thenReturn(customer);
		assertEquals("roshan968", customerDao.getCustomerWithMailId("mproshan968@gmail.com").getUserName());
	}
	
	@Test
	void testgetCustomerWithUserName() {
		Customer customer = new Customer();
		customer.setUserName("roshan968");
		List<Customer> list = new ArrayList<>();
		list.add(customer);
		when(customerRepo.getCustomerWithUserName(Mockito.anyString())).thenReturn(list);
		assertEquals("roshan968", customerDao.getCustomerWithUserName("roshan968").get(0).getUserName());
	}
	
	
	@Test
	void testGetCustomerWithPhoneNo() {
		Customer customer = new Customer();
		customer.setUserName("roshan968");
		customer.setName("Roshan");
		customer.setPhoneNo(6281510634L);
		when(customerRepo.getCustomerWithPhoneNo(Mockito.anyLong())).thenReturn(customer);
		assertEquals(customer.getUserName(), customerDao.getCustomerWithPhoneNo(customer.getPhoneNo()).getUserName());
	}

}
