package edu.northeastern.cs5200.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 * Represents the Critic Class. Critics are a special type of user.
 * 
 * @author Tridiv
 *
 */
@Entity
public class Critic extends User {

	// instance variables
	@Column(nullable = true)
	private String careerDescription;

	@Column(nullable = true)
	private Integer numberOfAwards;

	@OneToMany(mappedBy = "critic")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Review> reviewsGiven;

	/**
	 * Represents the default constructor
	 */
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

	public Integer getNumberOfAwards() {
		return numberOfAwards;
	}

	public void setNumberOfAwards(Integer numberOfAwards) {
		this.numberOfAwards = numberOfAwards;
	}

	public List<Review> getReviewsGiven() {
		return reviewsGiven;
	}

	public void setReviewsGiven(List<Review> reviewsGiven) {
		this.reviewsGiven = reviewsGiven;
	}

	/**
	 * Updates the attributes of current reference with that of the critic object
	 * passed as argument
	 * 
	 * @param critic
	 */
	public void set(Critic critic) {
		super.set(critic);
		this.setCareerDescription(
				critic.getCareerDescription() != null ? critic.getCareerDescription() : this.getCareerDescription());
		this.setNumberOfAwards(
				critic.getNumberOfAwards() != null ? critic.getNumberOfAwards() : this.getNumberOfAwards());

		if (critic.getReviewsGiven() != null) {
			if (this.getReviewsGiven() == null) {
				this.setReviewsGiven(critic.getReviewsGiven());
			} else if (!this.getReviewsGiven().equals(critic.getReviewsGiven())) {
				this.setReviewsGiven(critic.getReviewsGiven());
			}
		}
	}

	/**
	 * Overridden version of the equals method. Two critics are considered equal
	 * only if they have the same id.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Critic) {
			Critic critic = (Critic) obj;
			if (this.getId() == critic.getId()) {
				return true;
			}
		}
		return false;
	}

}
