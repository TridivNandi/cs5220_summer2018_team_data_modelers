package edu.northeastern.cs5200.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents the Review class. It contains the details of the reviews posted by
 * the critics about the songs
 * 
 * @author Tridiv
 *
 */
@Entity
public class Review {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;

	@ManyToOne
	@JsonIgnore
	private Song song;
	
	@ManyToOne
	private Critic critic;

	private String content;
	private Double ratings;

	/**
	 * Represents the default constructor
	 */
	public Review() {

	}

	// Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Song getSong() {
		return song;
	}

	public void setSong(Song song) {
		this.song = song;
	}

	public Critic getCritic() {
		return critic;
	}

	public void setCritic(Critic critic) {
		this.critic = critic;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Double getRatings() {
		return ratings;
	}

	public void setRatings(Double ratings) {
		this.ratings = ratings;
	}

	/**
	 * Updates the attributes of current reference with that of the review object
	 * passed as argument
	 * 
	 * @param review
	 */
	public void set(Review review) {
		this.setContent(review.getContent() != null ? review.getContent() : this.getContent());
		this.setRatings(review.getRatings() != null ? review.getRatings() : this.getRatings());
	}

	/**
	 * Overridden version of the equals method. Two reviews are considered
	 * equal only if they have the same id
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Review) {
			Review review = (Review) obj;
			if (review.getId() == this.getId()) {
				return true;
			}
		}
		return false;
	}

}
