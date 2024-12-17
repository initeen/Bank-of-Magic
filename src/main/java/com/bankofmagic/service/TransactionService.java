package com.bankofmagic.service;

import java.util.List;

import com.bankofmagic.entities.Transaction;

public interface TransactionService {

	List<Transaction> getTransactionHistory(Long CustomerId);

}
