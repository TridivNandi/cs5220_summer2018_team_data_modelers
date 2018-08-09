package edu.northeastern.cs5200.entities;

import javax.persistence.Entity;

@Entity
public class Critic extends User {
	
	private String careerDescription;
	private int numberOfAwards;
	
	public Critic() {
		super();
	}
	
	public String getCareerDescription() {
		return careerDescription;
	}
	public void setCareerDescription(String careerDescription) {
		this.careerDescription = careerDescription;
	}
	public int getNumberOfAwards() {
		return numberOfAwards;
	}
	public void setNumberOfAwards(int numberOfAwards) {
		this.numberOfAwards = numberOfAwards;
	}
	
	

}
