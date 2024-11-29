package com.bankofmagic.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bankofmagic.entities.Customer;



@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Customer findByUsername(String username);

	boolean existsByMobileNumber(String mobileNumber);

	boolean existsByEmail(String email);

	boolean existsByUsername(String username);

}
