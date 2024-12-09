package com.bankofmagic.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankofmagic.entities.Account;
import com.bankofmagic.repository.AccountRepository;
import com.bankofmagic.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepository accountRepository;

	@Override
	public void saveAccount(Account account) {

		accountRepository.save(account);
	}

	@Override
	public long getTotalDistinctBranches() {

		return accountRepository.countDistinctBranch();
	}

	@Override
	public double getTotalMoneyCollected() {

		return accountRepository.getTotalMoneyCollected();
	}

}
