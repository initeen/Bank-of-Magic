package com.bankofmagic.service.Impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bankofmagic.entities.Account;
import com.bankofmagic.entities.Deposit;
import com.bankofmagic.entities.FundTransfer;
import com.bankofmagic.entities.Transaction;
import com.bankofmagic.entities.Withdraw;
import com.bankofmagic.repository.AccountRepository;
import com.bankofmagic.repository.DepositRepository;
import com.bankofmagic.repository.FundTransferRepository;
import com.bankofmagic.repository.WithdrawRepository;
import com.bankofmagic.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService {

	/*
	 * @Autowired private TransactionRepository transactionRepository;
	 */

	@Autowired
	private DepositRepository depositRepository;

	@Autowired
	private WithdrawRepository withdrawRepository;

	@Autowired
	private FundTransferRepository fundTransferRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Override
	public List<Transaction> getTransactionHistory(Long customerId) {

		System.out.println("trnsc");
		System.out.println(customerId);
		List<Transaction> transactionHistory = new ArrayList<>();

		int depositCounter = 1;
		int withdrawCounter = 1;
		int fundTransferCounter = 1;

		List<Deposit> deposits = depositRepository.findByCustomerId(customerId);
		System.out.println(customerId);
		for (Deposit deposit : deposits) {
			Transaction transaction = new Transaction();
			transaction.setId("DEP-" + String.format("%03d", depositCounter++));
			transaction.setType("Deposit");
			transaction.setAmount(deposit.getAmount());
			transaction.setDate(deposit.getTransactionDate());
			System.out.println(deposit.getTransactionDate());
			transaction.setRemainingBalance(getAccountBalance(customerId));
			transaction.setStatus(deposit.getStatus());
			transaction.setCustId(deposit.getCustomer().getId());
			transactionHistory.add(transaction);
			System.out.println(transactionHistory);
		}

		List<Withdraw> withdrawals = withdrawRepository.findByCustomerId(customerId);
		for (Withdraw withdraw : withdrawals) {
			Transaction transaction = new Transaction();
			transaction.setId("WDR-" + String.format("%03d", withdrawCounter++));
			transaction.setType("Withdraw");
			transaction.setAmount(withdraw.getAmount());
			transaction.setDate(withdraw.getTransactionDate());
			transaction.setRemainingBalance(getAccountBalance(customerId));
			transactionHistory.add(transaction);
		}

		List<FundTransfer> fundTransfers = fundTransferRepository.findBySenderId(customerId);
		for (FundTransfer fundTransfer : fundTransfers) {
			Transaction transaction = new Transaction();
			transaction.setId("FTR-" + String.format("%03d", fundTransferCounter++));
			transaction.setType("Fund Transfer");
			transaction.setAmount(fundTransfer.getAmount());
			transaction.setDate(fundTransfer.getTransactionDate());
			transaction.setRemainingBalance(getAccountBalance(customerId));
			transaction.setCustId(fundTransfer.getSender().getId());
			transactionHistory.add(transaction);
		}

		transactionHistory.sort(Comparator.comparing(Transaction::getDate).reversed());

		return transactionHistory;
	}

	private Double getAccountBalance(Long customerId) {
		Account account = accountRepository.findByCustomerId(customerId);
		return account != null ? account.getBalance() : 0.0;
	}

}
