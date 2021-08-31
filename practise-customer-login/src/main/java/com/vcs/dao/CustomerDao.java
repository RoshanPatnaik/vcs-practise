package com.vcs.dao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.vcs.model.Customer;
import com.vcs.repository.CustomerRepository;

@Component
public class CustomerDao {
	
	@Autowired
	private CustomerRepository customerRepo;

	public Customer saveCustomerToDB(Customer customer) {
		return customerRepo.save(customer);
	}

	public Customer getCustomerWithUnameAndPassword(String userName, String password) {
		return customerRepo.getCustomerWithUnameAndPassword(userName, password);
	}

	public List<Customer> getCustomerWithUserName(String userName) {
		return customerRepo.getCustomerWithUserName(userName);
	}

	public Customer getCustomerWithMailId(String email) {
		return customerRepo.getCustomerWithMailId(email);
	}

	public Customer getCustomerWithPhoneNo(long phoneNo) {
		return customerRepo.getCustomerWithPhoneNo(phoneNo);
	}
	
	

	
	
	
}
