package edu.northeastern.cs5200.entities;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class AdminUser extends User {

	Date startDate;
	
	public AdminUser() {
		
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	
}
