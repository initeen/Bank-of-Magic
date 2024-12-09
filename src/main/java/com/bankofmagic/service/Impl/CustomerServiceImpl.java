package com.bankofmagic.service.Impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankofmagic.entities.Account;
import com.bankofmagic.entities.Customer;
import com.bankofmagic.repository.AccountRepository;
import com.bankofmagic.repository.CustomerRepository;
import com.bankofmagic.service.CustomerService;

import jakarta.validation.Valid;

@Service
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	AccountRepository accountRepository;

	@Override
	public void saveCustomer(@Valid Customer customer) {

		customerRepository.save(customer);
	}

	@Override
	public boolean existsByMobileNumber(String mobileNumber) {

		return customerRepository.existsByMobileNumber(mobileNumber);
	}

	@Override
	public boolean existsByEmail(String email) {

		return customerRepository.existsByEmail(email);
	}

	@Override
	public boolean existsByUsername(String username) {

		return customerRepository.existsByUsername(username);
	}

	@Override
	public boolean existsByAadhaarNumber(String aadhaarNumber) {

		return customerRepository.existsByAadhaarNumber(aadhaarNumber);
	}

	@Override
	public Customer findByUsername(String username) {

		return customerRepository.findByUsername(username);
	}

	@Override
	public Long getTotalCustomersWithRole1() {

		return customerRepository.countByIdRole(1);
	}

	@Override
	public long getActiveCustomers() {

		return customerRepository.countByIsVerifiedAndIdRoleNot(true, 0);
	}

	@Override
	public long getDeactivatedCustomers() {

		return customerRepository.countByIsVerified(false);
	}

	@Override
	public List<Customer> getPendingCustomersWithInactiveAccount() {

		return customerRepository.findPendingCustomersWithInactiveAccount();
	}

	@Override
	public boolean approveCustomer(Long customerId) {
	    
		try {
	        
	        Customer customer = customerRepository.findById(customerId)
	                .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));

	        Account account = customer.getAccount();
	        if (account != null) {
	            account.setStatus(true);
	            accountRepository.save(account);
	        }

	        customer.setVerified(true);
	        customerRepository.save(customer);

	        return true;
	    } catch (Exception e) {
	        
	        return false;
	    }
	}

	@Override
	public boolean rejectCustomer(Long customerId) {
	    try {
	        
	        Customer customer = customerRepository.findById(customerId)
	                .orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));

	        Account account = customer.getAccount();
	        if (account != null) {
	            account.setStatus(false);
	            accountRepository.save(account);
	        }

	        customer.setVerified(false);
	        customerRepository.save(customer);

	        return true;
	    } catch (Exception e) {
	    	
	        return false;
	    }
	}


}
