package com.bankofmagic.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bankofmagic.entities.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, String> {

	// List<Transaction> findByCustomerId(Long customerId);

}
