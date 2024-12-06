package com.bankofmagic.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bankofmagic.entities.Account;
import com.bankofmagic.entities.Customer;
import com.bankofmagic.repository.AccountRepository;
import com.bankofmagic.repository.CustomerRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class CustomerController {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	AccountRepository accountRepository;

	@GetMapping("/customer/home")
	public String customerDashboardHandler(@CurrentSecurityContext(expression = "authentication?.name") String username,
			Model model) {

		Customer customer = customerRepository.findByUsername(username);
		String loginTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy, hh:mm a"));
		model.addAttribute("loginTime", loginTime);
		model.addAttribute("customer", customer);

		/*
		 * if (!customer.isVerified()) {
		 * 
		 * return "customer/open-account"; }
		 */
		if (customer.getAccount() != null && !customer.getAccount().isStatus()) {

			return "customer/account-pending";

		} else if (!customer.isVerified()) {

			return "customer/open-account";
		}

		return "customer/index";
	}

	@PostMapping("/customer/open-acount")
	public String openAccountHandler(@CurrentSecurityContext(expression = "authentication?.name") String username,
			@RequestParam("branchName") String branchName, @RequestParam("accountType") String accountType,
			HttpSession session) {

		System.out.println("submittin open account form..");

		Customer customer = customerRepository.findByUsername(username);
		Account account = new Account();
		account.setBranchName(branchName);
		account.setAccountType(accountType);
		account.setCustomer(customer);

		accountRepository.save(account);
		System.out.println(account);

		session.setAttribute("success",
				"Dear customer your account opening request has been sent to respective branch. You will recieved an email once branch is approve request.");
		return "redirect:/custom_login";

	}
}
