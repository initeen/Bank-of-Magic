package com.bankofmagic.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
public class Transaction {

	@Id
	// @GeneratedValue(strategy = GenerationType.AUTO)
	private String id;

	private Double amount;

	private String type;

	private LocalDateTime date;

	private Double remainingBalance;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	private Long custId;

	private String status;

}
