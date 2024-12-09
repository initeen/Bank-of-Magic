package com.bankofmagic.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.bankofmagic.entities.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

	Customer findByUsername(String username);

	boolean existsByMobileNumber(String mobileNumber);

	boolean existsByEmail(String email);

	boolean existsByUsername(String username);

	boolean existsByAadhaarNumber(String aadhaarNumber);

	Long countByIdRole(int idRole);

	long countByIsVerified(boolean isVerified);

	long countByIsVerifiedAndIdRoleNot(boolean isVerified, int idRole);

	@Query("SELECT c FROM Customer c JOIN c.account a WHERE c.idRole = 1 AND c.isVerified = false AND a.status = false")
	List<Customer> findPendingCustomersWithInactiveAccount();

}
