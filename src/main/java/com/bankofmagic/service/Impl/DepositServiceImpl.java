package com.bankofmagic.service.Impl;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankofmagic.entities.Account;
import com.bankofmagic.entities.Customer;
import com.bankofmagic.entities.Deposit;
import com.bankofmagic.repository.AccountRepository;
import com.bankofmagic.repository.CustomerRepository;
import com.bankofmagic.repository.DepositRepository;
import com.bankofmagic.service.DepositService;

@Service
public class DepositServiceImpl implements DepositService {

	@Autowired
	private DepositRepository depositRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public void depositFunds(Double amount, Long customerId) {

		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));

		Account account = customer.getAccount();
		if (account == null) {

			throw new IllegalArgumentException("Customer does not have an associated account.");
		}

		account.setBalance(account.getBalance() + amount);
		accountRepository.save(account);

		Deposit deposit = new Deposit(amount, LocalDateTime.now(), customer, "Success");
		depositRepository.save(deposit);

	}

}
