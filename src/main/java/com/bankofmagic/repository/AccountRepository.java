package com.bankofmagic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bankofmagic.entities.Account;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

	@Query("SELECT COUNT(DISTINCT a.branchName) FROM Account a")
	long countDistinctBranch();

	@Query("SELECT SUM(a.balance) FROM Account a")
	double getTotalMoneyCollected();

}
