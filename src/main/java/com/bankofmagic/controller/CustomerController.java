package com.bankofmagic.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.bankofmagic.entities.Customer;
import com.bankofmagic.repository.CustomerRepository;

@Controller
public class CustomerController {

	@Autowired
	CustomerRepository customerRepository;

	@GetMapping("/customer/home")
	public String customerDashboardHandler(@CurrentSecurityContext(expression = "authentication?.name") String username,
			Model model) {

		Customer customer = customerRepository.findByUsername(username);
		String loginTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy, hh:mm a"));
		model.addAttribute("loginTime", loginTime);
		model.addAttribute("customer", customer);

		if (!customer.isVerified()) {

			return "customer/open-account";
		}
		
		return "customer/index";
	}
}
