package com.vcs.controller;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.vcs.exception.CustomerAlreadyExistsException;
import com.vcs.exception.InvalidLoginCredsException;
import com.vcs.exception.MailIdAlreadyExistsException;
import com.vcs.exception.PhoneNumberAlreadyExistsException;
import com.vcs.model.Customer;
import com.vcs.service.CustomerService;

@RestController
@RequestMapping("/vcs")
public class VCSController {

	@Autowired
	private CustomerService customerService;
	
	@GetMapping("/loginPage")
	public ModelAndView loginLandingPage(){
		return new ModelAndView("login");
	}
	
	@PostMapping("/homePage")
	public ModelAndView submitLoginDetails(Model model, @RequestParam(value = "username") String username, @RequestParam(value = "password") String password) {
		Customer customer;
		try {
			customer = customerService.getCustomerWithUnameAndPassword(username, password);
			model.addAttribute("name", "Welcome to Virtual Card System "+customer.getUserName());
		} catch (InvalidLoginCredsException e) {
			model.addAttribute("name", e.getMessage());
			return new ModelAndView("home");
		}
		return new ModelAndView("home");
	}
	
	@GetMapping("/registrationPage")
	public ModelAndView register() {
		return new ModelAndView("registration");
	}
	
	@PostMapping("/loginAfterRegistration")
	public ModelAndView loginAfterRegistration(Model model, @RequestParam("dob") String dob, @RequestParam("name") String name, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("address") String address, @RequestParam("email") String email, @RequestParam("country") String country, @RequestParam("state") String state, @RequestParam("Pan") String Pan, @RequestParam("phoneNo") String phoneNo, @RequestParam("accountType") String accountType) {
		Customer customer = new Customer(username, address, state, country, Pan, LocalDate.parse(dob), accountType);
		customer.setPassword(password);
		customer.setName(name);
		customer.setEmail(email);
		customer.setPhoneNo(Long.parseLong(phoneNo));
		try {
			customerService.saveCustomerToDB(customer);
		} catch (CustomerAlreadyExistsException | MailIdAlreadyExistsException | PhoneNumberAlreadyExistsException e) {
			model.addAttribute("name", e.getMessage());
			return new ModelAndView("home");
		}
		return new ModelAndView("login");
	}
	
}
