package com.bankofmagic.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
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
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Table(name = "deposit_data")
public class Deposit {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Double amount;

	@Column(name = "deposit_date")
	private LocalDateTime transactionDate;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	private String status;

	public Deposit(Double amount, LocalDateTime depositDate, Customer customer, String status) {
		this.amount = amount;
		this.transactionDate = depositDate;
		this.customer = customer;
		this.status = status;
	}
}
