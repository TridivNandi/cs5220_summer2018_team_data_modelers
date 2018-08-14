package edu.northeastern.cs5200.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents the RegisteredUser class. It also extends the User class.
 * 
 * @author Tridiv
 *
 */
@Entity
public class RegisteredUser extends User {

	// instance variables
	private String planDetails;
	private Date subscriptionStartDate;
	private Date subscriptionEndDate;

	@OneToMany(mappedBy = "owner")
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<Playlist> playlists;

	@ManyToMany
	@JoinTable(name = "UserArtistFollow")
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<Artist> following;

	/**
	 * Represents the default constructor
	 */
	public RegisteredUser() {
		super();
		playlists = new ArrayList<>();
		following = new ArrayList<>();
	}

	// Getters and Setters
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

	public List<Playlist> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}

	public List<Artist> getFollowing() {
		return following;
	}

	public void setFollowing(List<Artist> following) {
		this.following = following;
	}

	/**
	 * Adds the artist sent in the argument to the list of following artists
	 * Additionally updates the artist's follower list with the current instance
	 * 
	 * @param artist
	 */
	public void addArtistToFollowing(Artist artist) {
		this.following.add(artist);
		if (!artist.getFollowers().contains(this)) {
			artist.getFollowers().add(this);
		}
	}

	/**
	 * Removes the artist sent in the argument from the list of following artists
	 * Additionally updates the artist's follower list by removing the current
	 * instance of registered user
	 * 
	 * @param artist
	 */
	public void removeArtistFromFollowing(Artist artist) {
		this.following.remove(artist);
		if (artist.getFollowers().contains(this)) {
			artist.getFollowers().remove(this);
		}
	}

	/**
	 * Updates the attributes of current reference with that of the registered user
	 * object passed as argument
	 * 
	 * @param user
	 */
	public void set(RegisteredUser user) {
		super.set(user);
		this.setPlanDetails(user.getPlanDetails() != null ? user.getPlanDetails() : this.getPlanDetails());
		this.setSubscriptionEndDate(
				user.getSubscriptionEndDate() != null ? user.getSubscriptionEndDate() : this.getSubscriptionEndDate());
		this.setSubscriptionStartDate(user.getSubscriptionStartDate() != null ? user.getSubscriptionStartDate()
				: this.getSubscriptionStartDate());

		if (user.getPlaylists() != null) {
			if (this.getPlaylists() == null) {
				this.setPlaylists(user.getPlaylists());
			} else if (!this.getPlaylists().equals(user.getPlaylists())) {
				this.setPlaylists(user.getPlaylists());
			}
		}

		if (user.getFollowing() != null) {
			if (this.getFollowing() == null) {
				this.setFollowing(user.getFollowing());
			} else if (!this.getFollowing().equals(user.getFollowing())) {
				this.setFollowing(user.getFollowing());
			}
		}
	}

	/**
	 * Overridden version of the equals method. Two registered users are considered
	 * equal only if they have the same id
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RegisteredUser) {
			RegisteredUser user = (RegisteredUser) obj;
			if (this.getId() == user.getId()) {
				return true;
			}
		}
		return false;
	}

}
