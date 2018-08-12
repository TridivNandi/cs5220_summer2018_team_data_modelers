package edu.northeastern.cs5200.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Critic extends User {
	
	private String careerDescription;
	private int numberOfAwards;
	
	@OneToMany(mappedBy = "critic")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Review> reviewsGiven;
	
	public Critic() {
		super();
		reviewsGiven = new ArrayList<>();
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

	public List<Review> getReviewsGiven() {
		return reviewsGiven;
	}

	public void setReviewsGiven(List<Review> reviewsGiven) {
		this.reviewsGiven = reviewsGiven;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Critic) {
			Critic critic = (Critic) obj;
			if(this.getId() == critic.getId()) {
				return true;
			}
		}
		return false;
	}
	
	

}
