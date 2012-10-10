package com.sfa.qb.model.entities;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Table(name="Payment_Schedule")

public class PaymentSchedule implements Serializable {
	
	private static final long serialVersionUID = 398970280942252224L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="Id")
	private int id;
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	@Column(name="Payment_Date")
	private Date paymentDate;
	
	@Column(name="Payment_Amount")
	private Double paymentAmount;
	
	@Column(name="Comments")
	private String comments;
	
	@ManyToOne(cascade = CascadeType.ALL)
	private Quote quote;
	
	public PaymentSchedule() {
		
	}

	public Date getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Double getPaymentAmount() {
		return paymentAmount;
	}

	public void setPaymentAmount(Double paymentAmount) {
		this.paymentAmount = paymentAmount;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public Quote getQuote() {
		return quote;
	}
	
	public void setQuote(Quote quote) {
		this.quote = quote;
	}
	
}