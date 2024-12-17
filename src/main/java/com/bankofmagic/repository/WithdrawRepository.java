package com.bankofmagic.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bankofmagic.entities.Withdraw;

@Repository
public interface WithdrawRepository extends JpaRepository<Withdraw, Long> {

	List<Withdraw> findByCustomerId(Long customerId);

}
