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
@Table(name = "fund_transer_data")
public class FundTransfer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private Double amount;

	@Column(name = "transfer_date")
	private LocalDateTime transactionDate;

	@ManyToOne
	@JoinColumn(name = "sender_id")
	private Customer sender;

	@Column(name = "receiver_name")
	private String receiver;

	private String status;

	public FundTransfer(Double amount, LocalDateTime transferDate, Customer sender, String receiver, String status) {
		super();

		this.amount = amount;
		this.transactionDate = transferDate;
		this.sender = sender;
		this.receiver = receiver;
		this.status = status;
	}

}
