package edu.northeastern.cs5200.entities;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class RegisteredUser extends User {
	
	private String planDetails;
	private Date subscriptionStartDate;
	private Date subscriptionEndDate;
	
	public RegisteredUser() {
		super();
	}

	public String getPlanDetails() {
		return planDetails;
	}

	public void setPlanDetails(String planDetails) {
		this.planDetails = planDetails;
	}

	public Date getSubscriptionStartDate() {
		return subscriptionStartDate;
	}

	public void setSubscriptionStartDate(Date subscriptionStartDate) {
		this.subscriptionStartDate = subscriptionStartDate;
	}

	public Date getSubscriptionEndDate() {
		return subscriptionEndDate;
	}

	public void setSubscriptionEndDate(Date subscriptionEndDate) {
		this.subscriptionEndDate = subscriptionEndDate;
	}
	
	
	
	
	
	

}
