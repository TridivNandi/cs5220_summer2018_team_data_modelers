package edu.northeastern.cs5200.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Artist extends User {
	
	private String careerDescription;
	private Integer numberOfAwards;
	
	@ManyToMany
	@JoinTable(name = "Artist2Song")
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<Song> songs;
	
	@ManyToMany(mappedBy = "following")
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<RegisteredUser> followers;
	
	@ManyToMany(mappedBy = "following")
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<AdminUser> adminFollowers;
	

    @ManyToMany(mappedBy = "artistsFollowing")
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<Artist> artistFollowers;
	
	@ManyToMany
	@JoinTable(name = "ArtistArtistFollow")
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<Artist> artistsFollowing;
	
	
	
	public Artist() {
		super();
		songs = new ArrayList<>();
		followers = new ArrayList<>();
		adminFollowers = new ArrayList<>();
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

	public List<Song> getSongs() {
		return songs;
	}

	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}
	
	public List<RegisteredUser> getFollowers() {
		return followers;
	}

	public void setFollowers(List<RegisteredUser> followers) {
		this.followers = followers;
	}
	

	public List<AdminUser> getAdminFollowers() {
		return adminFollowers;
	}

	public void setAdminFollowers(List<AdminUser> adminFollowers) {
		this.adminFollowers = adminFollowers;
	}

	public List<Artist> getArtistFollowers() {
		return artistFollowers;
	}

	public void setArtistFollowers(List<Artist> artistFollowers) {
		this.artistFollowers = artistFollowers;
	}
	
	

	public List<Artist> getArtistsFollowing() {
		return artistsFollowing;
	}

	public void setArtistsFollowing(List<Artist> artistsFollowing) {
		this.artistsFollowing = artistsFollowing;
	}

	public void set(Artist artist) {
		this.setCareerDescription(artist.getCareerDescription() != null ? artist.getCareerDescription() : this.getCareerDescription());
		this.setEmail(artist.getEmail() != null ? artist.getEmail() : this.getEmail());
		this.setFirstName(artist.getFirstName() != null ? artist.getFirstName() : this.getFirstName());
		this.setLastName(artist.getLastName() != null ? artist.getLastName() : this.getLastName());
		this.setNumberOfAwards(artist.getNumberOfAwards() != null ? artist.getNumberOfAwards() : this.getNumberOfAwards());
		this.setPassword(artist.getPassword() != null ? artist.getPassword() : this.getPassword());
		this.setPhoneNumber(artist.getPhoneNumber() != null ? artist.getPhoneNumber() : this.getPhoneNumber());
		this.setUsername(artist.getUsername() != null ? artist.getUsername() : this.getUsername());
		
		if(artist.getSongs() != null) {
			if(this.getSongs() == null) {
				this.setSongs(artist.getSongs());
			}
			else if(!artist.getSongs().equals(this.getSongs())) {
				this.setSongs(artist.getSongs());
			}
		}
		
		if(artist.getFollowers() != null) {
			if(this.getFollowers() == null) {
				this.setFollowers(artist.getFollowers());
			}
			else if(!artist.getFollowers().equals(this.getFollowers())) {
				this.setFollowers(artist.getFollowers());
			}
		}
			
	}
	
	public void addArtistToFollowing(Artist artist) {
		if(!this.getArtistsFollowing().contains(artist)) {
			this.getArtistsFollowing().add(artist);
		}
		if(!artist.getArtistFollowers().contains(this)) {
			artist.getArtistFollowers().add(this);
		}
	}
	
	public void removeArtistFromFollowing(Artist artist) {
		if(this.getArtistsFollowing().contains(artist)) {
			this.getArtistsFollowing().remove(artist);
		}
		if(artist.getArtistFollowers().contains(this)) {
			artist.getArtistFollowers().remove(this);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Artist) {
			Artist artist = (Artist) obj;
			if(this.getId() == artist.getId()) {
				return true;
			}
		}
		return false;
	}
	

}
