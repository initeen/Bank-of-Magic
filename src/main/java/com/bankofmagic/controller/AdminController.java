package com.bankofmagic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.bankofmagic.entities.Customer;
import com.bankofmagic.repository.CustomerRepository;

@Controller
public class AdminController {

	@Autowired
	private CustomerRepository customerRepository;
	
	@GetMapping("/admin/dashboard")
	public String adminDashboardHandler(@CurrentSecurityContext(expression = "authentication?.name") String usename,
			Model model) {

		Customer admin = customerRepository.findByUsername(usename);
		model.addAttribute("admin", admin);
		return "admin/index";
	}
}
