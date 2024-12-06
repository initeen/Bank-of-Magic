package com.bankofmagic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bankofmagic.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	
}
