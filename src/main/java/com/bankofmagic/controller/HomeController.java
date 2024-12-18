package com.bankofmagic.controller;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bankofmagic.entities.Customer;
import com.bankofmagic.service.CustomerService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomeController {

	@Autowired
	CustomerService customerService;

	@GetMapping({ "/", "/index" })
	public String indexHandler() {

		return "public/index";
	}

	@GetMapping("/custom_login")
	public String loginHandler(Model model, HttpSession session) {

		String successMessage = (String) session.getAttribute("success");

		if (successMessage != null) {

			model.addAttribute("success", successMessage);
			session.removeAttribute("success");
		}

		return "public/custom_login";
	}

	@GetMapping("/registration")
	public String registrationHandler(Model model) {

		model.addAttribute("customer", new Customer());
		return "public/registration";
	}

	@PostMapping("/save-customer")
	public String registerCustomerHandler(@Valid @ModelAttribute("customer") Customer customer, BindingResult result,
			Model model, HttpSession session) {

		if (customerService.existsByMobileNumber(customer.getMobileNumber())) {
			result.rejectValue("mobileNumber", "error.mobileNumber", "Mobile number already exists.");
		}
		if (customerService.existsByEmail(customer.getEmail())) {
			result.rejectValue("email", "error.email", "Email already exists.");
		}
		if (customerService.existsByAadhaarNumber(customer.getAadhaarNumber())) {
			result.rejectValue("aadhaarNumber", "error.aadhaarNumber", "Aadhar Number already exists.");
		}
		if (customerService.existsByUsername(customer.getUsername())) {
			result.rejectValue("username", "error.username", "Username already exists.");
		}

		if (customer.getPassword() != null && !customer.getPassword().equals(customer.getConfirmPassword())) {
			result.rejectValue("confirmPassword", "error.confirmPassword", "Passwords do not match.");
		}
		if (result.hasErrors()) {

			System.out.println("Error" + result.toString());
			model.addAttribute("customer", customer);
			return "public/registration";
		}

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		customer.setPassword(encoder.encode(customer.getPassword()));
		customer.setCreatedOn(LocalDate.now());
		customer.setUpdatedOn(LocalDate.now());
		customer.setIdRole(1);
		customer.setVerified(false);
		customer.setVerificationToken(UUID.randomUUID().toString());

		customerService.saveCustomer(customer);
		session.setAttribute("success", "Your registration was successfully completed. Please log in.");

		return "redirect:/custom_login";
	}

	@GetMapping("/forgot")
	public String forgotHandler(Model model, HttpSession session) {

		String successMessage = (String) session.getAttribute("success");

		if (successMessage != null) {

			model.addAttribute("success", successMessage);
			session.removeAttribute("success");
		}

		return "public/forgot";
	}

	@GetMapping("/invalid")
	public String invalidHandler() {

		return "error/404";
	}

}
