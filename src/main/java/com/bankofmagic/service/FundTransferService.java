package com.bankofmagic.service;

public interface FundTransferService {

	void fundTransfer(String receiverUsername, Double amount, Long customerId);

}
