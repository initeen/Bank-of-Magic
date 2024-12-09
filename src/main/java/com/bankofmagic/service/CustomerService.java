package com.bankofmagic.service;

import java.util.List;

import com.bankofmagic.entities.Customer;

import jakarta.validation.Valid;

public interface CustomerService {

	void saveCustomer(@Valid Customer customer);

	boolean existsByMobileNumber(String mobileNumber);

	boolean existsByEmail(String email);

	boolean existsByUsername(String username);

	boolean existsByAadhaarNumber(String aadhaarNumber);

	Customer findByUsername(String username);

	Long getTotalCustomersWithRole1();

	long getActiveCustomers();

	long getDeactivatedCustomers();

	List<Customer> getPendingCustomersWithInactiveAccount();

	boolean approveCustomer(Long customerId);

	boolean rejectCustomer(Long customerId);

}