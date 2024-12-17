package com.bankofmagic.service.Impl;

import java.security.PrivateKey;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankofmagic.entities.Account;
import com.bankofmagic.entities.Customer;
import com.bankofmagic.entities.Withdraw;
import com.bankofmagic.repository.AccountRepository;
import com.bankofmagic.repository.CustomerRepository;
import com.bankofmagic.repository.WithdrawRepository;
import com.bankofmagic.service.WithdrawService;

@Service
public class WithdrawSeriveImpl implements WithdrawService {

	@Autowired
	private WithdrawRepository withdrawRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public void withdrawFunds(Double amount, Long customerId) {

		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid customer ID"));

		Account account = customer.getAccount();
		if (account == null) {
			throw new IllegalArgumentException("Customer does not have an associated account.");
		}

		if (amount <= 0) {
			throw new IllegalArgumentException("Withdrawal amount must be greater than zero.");
		}

		if (account.getBalance() < amount) {
			throw new IllegalArgumentException("Insufficient balance.");
		}

		account.setBalance(account.getBalance() - amount);
		accountRepository.save(account);

		Withdraw withdraw = new Withdraw(amount, LocalDateTime.now(), customer, "SUCCESS");
		withdrawRepository.save(withdraw);
	}

}
