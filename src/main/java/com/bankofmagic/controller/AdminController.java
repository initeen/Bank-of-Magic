package com.bankofmagic.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bankofmagic.entities.Customer;
import com.bankofmagic.repository.CustomerRepository;
import com.bankofmagic.service.AccountService;
import com.bankofmagic.service.CustomerService;

@Controller
public class AdminController {

	@Autowired
	CustomerService customerService;

	@Autowired
	AccountService accountService;

	@GetMapping("/admin/dashboard")
	public String adminDashboardHandler(@CurrentSecurityContext(expression = "authentication?.name") String usename,
			Model model) {

		Customer customer = customerService.findByUsername(usename);
		String loginTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MMM-yyyy, hh:mm a"));

		long totalCustomersWithRole1 = customerService.getTotalCustomersWithRole1();
		long activeCustomers = customerService.getActiveCustomers();
		long deactivatedCustomers = customerService.getDeactivatedCustomers();

		long totalBranches = accountService.getTotalDistinctBranches();
		double totalMoneyCollected = accountService.getTotalMoneyCollected();

		List<Customer> pendingCustomers = customerService.getPendingCustomersWithInactiveAccount();

		model.addAttribute("loginTime", loginTime);
		model.addAttribute("customer", customer);

		model.addAttribute("totalCustomersWithRole1", totalCustomersWithRole1);
		model.addAttribute("activeCustomers", activeCustomers);
		model.addAttribute("deactivatedCustomers", deactivatedCustomers);

		model.addAttribute("totalBranches", totalBranches);
		model.addAttribute("totalMoneyCollected", totalMoneyCollected);

		model.addAttribute("pendingCustomers", pendingCustomers);
		System.out.println(activeCustomers);
		return "admin/index";
	}

	@PostMapping("/admin/approve")
	public String approveCustomer(@RequestParam("customerId") Long customerId, RedirectAttributes redirectAttributes) {
		boolean isApproved = customerService.approveCustomer(customerId);
		if (isApproved) {
			redirectAttributes.addFlashAttribute("successMessage", "Customer approved successfully.");
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", "Failed to approve the customer.");
		}
		return "redirect:/admin/dashboard";
	}

	@PostMapping("/admin/reject")
	public String rejectCustomer(@RequestParam("customerId") Long customerId, RedirectAttributes redirectAttributes) {
		boolean isRejected = customerService.rejectCustomer(customerId);
		if (isRejected) {
			redirectAttributes.addFlashAttribute("successMessage", "Customer rejected successfully.");
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", "Failed to reject the customer.");
		}
		return "redirect:/admin/dashboard";
	}

}
