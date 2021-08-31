package com.vcs.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.vcs.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, String> {
	
	@Query("select c from Customer c where c.userName=?1")
	List<Customer> getCustomerWithUserName(String userName);
	
	@Query("select c from Customer c where c.email=?1")
	Customer getCustomerWithMailId(String email);
	
	@Query("select c from Customer c where c.userName=?1 and c.password=?2")
	Customer getCustomerWithUnameAndPassword(String userName, String password);

	@Query("select c from Customer c w here c.phoneNo=?1")
	Customer getCustomerWithPhoneNo(long phoneNo);
	
}
