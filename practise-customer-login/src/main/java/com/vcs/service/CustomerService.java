package com.vcs.service;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.vcs.dao.CustomerDao;
import com.vcs.exception.CustomerAlreadyExistsException;
import com.vcs.exception.InvalidLoginCredsException;
import com.vcs.exception.MailIdAlreadyExistsException;
import com.vcs.exception.PhoneNumberAlreadyExistsException;
import com.vcs.model.Customer;

@Service
public class CustomerService {
	
	@Autowired
	private CustomerDao customerDao;

	public Customer saveCustomerToDB(Customer customer) throws CustomerAlreadyExistsException, MailIdAlreadyExistsException, PhoneNumberAlreadyExistsException {
		if(userNameAlreadyExists(customer.getUserName())) {
			throw new CustomerAlreadyExistsException("Username already taken");
		}
		if(mailIdAlreadyEists(customer.getEmail())) {
			throw new MailIdAlreadyExistsException("Mail ID already taken");
		}
		if(phoneNoAlreadyExists(customer.getPhoneNo())) {
			throw new PhoneNumberAlreadyExistsException("Phone Number already taken");
		}
		return customerDao.saveCustomerToDB(customer);
	}

	public Customer getCustomerWithUnameAndPassword(String userName, String password) throws InvalidLoginCredsException {
		if(customerDao.getCustomerWithUserName(userName).size()==0 || !customerDao.getCustomerWithUserName(userName).get(0).getPassword().equals(password)) {
			throw new InvalidLoginCredsException("Could not find user with given username and password");
		}
		return customerDao.getCustomerWithUserName(userName).get(0);
		
	}

	public boolean userNameAlreadyExists(String userName) {
		if(customerDao.getCustomerWithUserName(userName).size()==0) {
			return false;
		}
		return true;
	}

	public boolean mailIdAlreadyEists(String email) {
		if(customerDao.getCustomerWithMailId(email) != null) {
			return true;
		}
		return false;
	}

	public boolean phoneNoAlreadyExists(long phoneNo) {
		if(customerDao.getCustomerWithPhoneNo(phoneNo) != null) {
			return true;
		}
		return false;
	}
	
	

	
	
}
