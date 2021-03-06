
package com.loyalty.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "loyalty")
@EntityListeners(AuditingEntityListener.class)
public class User {
	@Id
	@Column(name = "employeeid", nullable = false)
	private long employeeid;
	@Column(name = "nickname", nullable = false)
	private String nickname;
	@Column(name = "emailaddress", nullable = false)
	private String emailaddress;
	@Column(name = "points", nullable = false)
	private int points;
	@Column(name = "message", nullable = true)
	private String message;
	@Column (name = "totalcollect", nullable = false)
	private int totalcollect;
	@Column (name = "totalredeem", nullable = false)
	private int totalredeem;
	
	public Long getEmployeeid() {
		return employeeid;
	}

	public void setEmployeeid(long employeeid) {
		this.employeeid = employeeid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getEmailaddress() {
		return emailaddress;
	}

	public void setEmailaddress(String emailaddress) {
		this.emailaddress = emailaddress;
	}

	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getTotalcollect() {
		return totalcollect;
	}

	public void setTotalcollect(int totalcollect) {
		this.totalcollect = totalcollect;
	}

	public int getTotalredeem() {
		return totalredeem;
	}

	public void setTotalredeem(int totalredeem) {
		this.totalredeem = totalredeem;
	}

	
}
