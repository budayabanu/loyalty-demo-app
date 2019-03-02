package com.loyalty.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.text.SimpleDateFormat;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "transaction")

@EntityListeners(AuditingEntityListener.class)
public class TransactionDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private long id;
	@Column(name = "employeeid", nullable = false)
	private long employeeid;
	@Column(name = "transtype", nullable = false)
	private String transtype;
	@Column(name = "points", nullable = false)
	private int points;
	@Column(name = "transdate", nullable = false)
	private String transdate;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(long employeeid) {
		this.employeeid = employeeid;
	}

	public String getTranstype() {
		return transtype;
	}

	public void setTranstype(String transtype) {
		this.transtype = transtype;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}

	public String getTransdate() {
		return transdate;
	}
	
	public void setTransdate(String string) {
		this.transdate = string;
	}
}
