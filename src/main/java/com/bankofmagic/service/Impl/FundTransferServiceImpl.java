package com.bankofmagic.service.Impl;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankofmagic.entities.Account;
import com.bankofmagic.entities.Customer;
import com.bankofmagic.entities.FundTransfer;
import com.bankofmagic.repository.AccountRepository;
import com.bankofmagic.repository.CustomerRepository;
import com.bankofmagic.repository.FundTransferRepository;
import com.bankofmagic.service.FundTransferService;

@Service
public class FundTransferServiceImpl implements FundTransferService {

	@Autowired
	private FundTransferRepository fundTransferRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public void fundTransfer(String receiverUsername, Double amount, Long customerId) {

		Customer customer = customerRepository.findById(customerId)
				.orElseThrow(() -> new IllegalArgumentException("Invalid sender ID"));

		Account senderAccount = customer.getAccount();

		if (senderAccount == null || senderAccount.getBalance() < amount) {
			throw new IllegalArgumentException("Insufficient funds in sender's account.");
		}

		senderAccount.setBalance(senderAccount.getBalance() - amount);

		accountRepository.save(senderAccount);

		FundTransfer transfer = new FundTransfer(amount, LocalDateTime.now(), customer, receiverUsername, "SUCCESS");

		fundTransferRepository.save(transfer);
	}

}
