package edu.northeastern.cs5200.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class RegisteredUser extends User {
	
	private String planDetails;
	private Date subscriptionStartDate;
	private Date subscriptionEndDate;
	
	@OneToMany(mappedBy = "owner")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Playlist> playlists;
	
	@ManyToMany
	@JoinTable(name = "UserArtistFollow")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Artist> following;
	
	public RegisteredUser() {
		super();
		playlists = new ArrayList<>();
		following = new ArrayList<>();
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

	public void addArtistToFollowing(Artist artist) {
		this.following.add(artist);
		if(!artist.getFollowers().contains(this)) {
			artist.getFollowers().add(this);
		}
	}
	
	public void set(RegisteredUser user) {
		super.set(user);
		this.setPlanDetails(user.getPlanDetails() != null ? user.getPlanDetails() : this.getPlanDetails());
		this.setSubscriptionEndDate(user.getSubscriptionEndDate() != null ? user.getSubscriptionEndDate() : this.getSubscriptionEndDate());
		this.setSubscriptionStartDate(user.getSubscriptionStartDate() != null ? user.getSubscriptionStartDate() : this.getSubscriptionStartDate());
		
		if(user.getPlaylists() != null) {
			if(this.getPlaylists() == null) {
				this.setPlaylists(user.getPlaylists());
			}
			else if (!this.getPlaylists().equals(user.getPlaylists())) {
				this.setPlaylists(user.getPlaylists());
			}
		}
		
		if(user.getFollowing() != null) {
			if(this.getFollowing() == null) {
				this.setFollowing(user.getFollowing());
			}
			else if (!this.getFollowing().equals(user.getFollowing())) {
				this.setFollowing(user.getFollowing());
			}
		}
	}
	
	
	
	

}
