package com.bankofmagic.service;

import com.bankofmagic.entities.Account;

public interface AccountService {

	void saveAccount(Account account);

	long getTotalDistinctBranches();

	double getTotalMoneyCollected();

}
