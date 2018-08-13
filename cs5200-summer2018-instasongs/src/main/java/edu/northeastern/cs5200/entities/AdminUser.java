package edu.northeastern.cs5200.entities;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
public class AdminUser extends User {

	Date startDate;
	
	@ManyToMany
	@JoinTable(name = "AdminArtistFollow")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Artist> following;
	
	public AdminUser() {
		super();
		following = new ArrayList<>();	
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public List<Artist> getFollowing() {
		return following;
	}

	public void setFollowing(List<Artist> following) {
		this.following = following;
	}
	
	public void addArtistToFollowing(Artist artist) {
		this.following.add(artist);
		if(!artist.getAdminFollowers().contains(this)) {
			artist.getAdminFollowers().add(this);
		}
	}
	
	public void removeArtistFromFollowing(Artist artist) {
		this.following.remove(artist);
		if(artist.getAdminFollowers().contains(this)) {
			artist.getAdminFollowers().remove(this);
		}
	}
	
	public void set(AdminUser admin) {
		
		super.set(admin);
		this.setStartDate(admin.getStartDate() != null ? admin.getStartDate() : this.getStartDate());
		
		if(admin.getFollowing() != null) {
			if(this.getFollowing() == null) {
				this.setFollowing(admin.getFollowing());
			}
			else if(!this.getFollowing().equals(admin.getFollowing())) {
				this.setFollowing(admin.getFollowing());
			}
		}
		
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof AdminUser) {
			AdminUser admin = (AdminUser) obj;
			if(this.getId() == admin.getId()) {
				return true;
			}
		}
		return false;
	}
	
	
}
