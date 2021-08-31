package com.vcs.test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.vcs.dao.CustomerDao;
import com.vcs.exception.CustomerAlreadyExistsException;
import com.vcs.exception.InvalidLoginCredsException;
import com.vcs.exception.MailIdAlreadyExistsException;
import com.vcs.exception.PhoneNumberAlreadyExistsException;
import com.vcs.model.Customer;
import com.vcs.repository.CustomerRepository;
import com.vcs.service.CustomerService;

@SpringBootTest
class TestServiceAndValidations {

	@Autowired
	private CustomerService customerService;
	
	@MockBean
	private CustomerDao customerDao;
	
	@MockBean
	private CustomerRepository customerRepo;
	
	
	@Test
	void testSaveCustomerToDBPositive() throws CustomerAlreadyExistsException, MailIdAlreadyExistsException, PhoneNumberAlreadyExistsException {
		Customer customer = new Customer();
		customer.setUserName("roshan968");
		customer.setName("Roshan");
		when(customerDao.getCustomerWithUserName(Mockito.anyString())).thenReturn(new ArrayList<>());
		when(customerDao.getCustomerWithMailId(Mockito.anyString())).thenReturn(null);
		when(customerRepo.save(Mockito.any(Customer.class))).thenReturn(customer);
		when(customerDao.saveCustomerToDB(Mockito.any(Customer.class))).thenReturn(customer);
		assertEquals(customer.getUserName(), customerService.saveCustomerToDB(customer).getUserName());
	}
	
	
	@Test
	void testRetrieveCustomerFromDBPositive() throws InvalidLoginCredsException {
		Customer customer = new Customer();
		customer.setUserName("roshan968");
		customer.setName("Roshan");
		customer.setPassword("password");
		List<Customer> list = new ArrayList<Customer>();
		list.add(customer);
		when(customerRepo.getCustomerWithUserName(Mockito.anyString())).thenReturn(list);
		when(customerDao.getCustomerWithUserName(Mockito.anyString())).thenReturn(list);
		assertEquals(customer.getName(), customerService.getCustomerWithUnameAndPassword(customer.getUserName(), customer.getPassword()).getName());
	}
	
	
	@Test
	void testUserNameAlreadyExistsPositive() {
		Customer customer = new Customer();
		customer.setUserName("roshan968");
		List<Customer> list = new ArrayList<>();
		list.add(customer);
		when(customerRepo.getCustomerWithUserName(Mockito.anyString())).thenReturn(list);
		when(customerDao.getCustomerWithUserName(Mockito.anyString())).thenReturn(list);
		assertEquals(true, customerService.userNameAlreadyExists("roshan968"));
	}
	
	
	@Test
	void testUserNameAlreadyExistsNegative() {
		when(customerRepo.getCustomerWithUserName(Mockito.anyString())).thenReturn(new ArrayList<>());
		when(customerDao.getCustomerWithUserName(Mockito.anyString())).thenReturn(new ArrayList<>());
		assertEquals(false, customerService.userNameAlreadyExists("vishnu456"));
	}
	
	
	@Test
	void testMailIdAlreadyExistsPositive() {
		Customer customer = new Customer();
		customer.setUserName("roshan968");
		customer.setEmail("mproshan968");
		when(customerRepo.getCustomerWithMailId(Mockito.anyString())).thenReturn(customer);
		when(customerDao.getCustomerWithMailId(Mockito.anyString())).thenReturn(customer);
		assertEquals(true, customerService.mailIdAlreadyEists("mproshan968@gmail.com"));
	}

	
	@Test
	void testMailIdAlreadyExistsNegative() {
		when(customerRepo.getCustomerWithMailId(Mockito.anyString())).thenReturn(null);
		when(customerDao.getCustomerWithMailId(Mockito.anyString())).thenReturn(null);
		assertEquals(false, customerService.mailIdAlreadyEists("mproshan968@gmail.com"));
	}
	
	@Test
	void testPhoneNoAlreadyExistsPositive() {
		Customer customer = new Customer();
		customer.setUserName("roshan968");
		customer.setName("Roshan");
		customer.setPhoneNo(6281510634L);
		when(customerRepo.getCustomerWithPhoneNo(Mockito.anyLong())).thenReturn(customer);
		when(customerDao.getCustomerWithPhoneNo(Mockito.anyLong())).thenReturn(customer);
		assertEquals(true, customerService.phoneNoAlreadyExists(customer.getPhoneNo()));
	}
	
	@Test
	void testPhoneNoAlreadyExistsNegative() {
		when(customerRepo.getCustomerWithPhoneNo(Mockito.anyLong())).thenReturn(null);
		when(customerDao.getCustomerWithPhoneNo(Mockito.anyLong())).thenReturn(null);
		assertEquals(false, customerService.phoneNoAlreadyExists(6281510634L));
	}

	@Test
	void testRetrieveCustomerFromDBNegativeUserName() throws InvalidLoginCredsException {
		when(customerRepo.getCustomerWithUnameAndPassword(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
		when(customerDao.getCustomerWithUnameAndPassword(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
		when(customerDao.getCustomerWithUserName(Mockito.anyString())).thenReturn(new ArrayList<>());
		Assertions.assertThrows(InvalidLoginCredsException.class, () -> {customerService.getCustomerWithUnameAndPassword("roshan968", "roshan968");});
	}
	
	@Test
	void testRetrieveCustomerFromDBNegativePassword() throws InvalidLoginCredsException {
		Customer customer = new Customer();
		customer.setUserName("roshan968");
		customer.setPassword("ghgjg");
		List<Customer> list = new ArrayList<>();
		list.add(customer);
		when(customerRepo.getCustomerWithUnameAndPassword(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
		when(customerDao.getCustomerWithUnameAndPassword(Mockito.anyString(), Mockito.anyString())).thenReturn(null);
		when(customerDao.getCustomerWithUserName(Mockito.anyString())).thenReturn(list);
		Assertions.assertThrows(InvalidLoginCredsException.class, () -> {customerService.getCustomerWithUnameAndPassword("roshan968", "roshan968");});
	}
	
	@Test
	void testSaveCustomerToDBNegativeUsernameExists() throws CustomerAlreadyExistsException, MailIdAlreadyExistsException, PhoneNumberAlreadyExistsException {
		Customer customer = new Customer();
		customer.setUserName("roshan968");
		customer.setName("Roshan");
		customer.setEmail("mproshan968@gmail.com");
		List<Customer> list = new ArrayList<>();
		list.add(customer);
		when(customerDao.getCustomerWithUserName(Mockito.anyString())).thenReturn(list);
		Assertions.assertThrows(CustomerAlreadyExistsException.class, () -> {customerService.saveCustomerToDB(customer);});
		
	}
	
	@Test
	void testSaveCustomerToDBNegativeMailIDExists() throws CustomerAlreadyExistsException, MailIdAlreadyExistsException, PhoneNumberAlreadyExistsException {
		Customer customer = new Customer();
		customer.setUserName("roshan968");
		customer.setName("Roshan");
		customer.setEmail("mproshan968@gmail.com");
		when(customerDao.getCustomerWithUserName(Mockito.anyString())).thenReturn(new ArrayList<>());
		when(customerDao.getCustomerWithMailId(Mockito.anyString())).thenReturn(customer);
		Assertions.assertThrows(MailIdAlreadyExistsException.class, () -> {customerService.saveCustomerToDB(customer);});
	} 
	
	
	@Test
	void testSaveCustomerToDBNegativePhoneNoAlreadyExists() throws CustomerAlreadyExistsException, MailIdAlreadyExistsException, PhoneNumberAlreadyExistsException{
		Customer customer = new Customer();
		customer.setUserName("roshan968");
		customer.setName("Roshan");
		customer.setEmail("mproshan968@gmail.com");
		customer.setPhoneNo(6281510634L);
		when(customerDao.getCustomerWithUserName(Mockito.anyString())).thenReturn(new ArrayList<>());
		when(customerDao.getCustomerWithMailId(Mockito.anyString())).thenReturn(null);
		when(customerDao.getCustomerWithPhoneNo(Mockito.anyLong())).thenReturn(customer);
		Assertions.assertThrows(PhoneNumberAlreadyExistsException.class, () -> {customerService.saveCustomerToDB(customer);});
	}
	
	
}
