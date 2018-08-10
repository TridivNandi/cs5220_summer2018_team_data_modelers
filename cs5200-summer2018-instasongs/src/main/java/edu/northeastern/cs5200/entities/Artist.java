package edu.northeastern.cs5200.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
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
	
	public Artist() {
		super();
		songs = new ArrayList<>();
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
	
	public void set(Artist artist) {
		this.setCareerDescription(artist.getCareerDescription() != null ? artist.getCareerDescription() : this.getCareerDescription());
		this.setEmail(artist.getEmail() != null ? artist.getEmail() : this.getEmail());
		this.setFirstName(artist.getFirstName() != null ? artist.getFirstName() : this.getFirstName());
		this.setLastName(artist.getLastName() != null ? artist.getLastName() : this.getLastName());
		this.setNumberOfAwards(artist.getNumberOfAwards() != null ? artist.getNumberOfAwards() : this.getNumberOfAwards());
		this.setPassword(artist.getPassword() != null ? artist.getPassword() : this.getPassword());
		this.setPhoneNumber(artist.getPhoneNumber() != null ? artist.getPhoneNumber() : this.getPhoneNumber());
		this.setUsername(artist.getUsername() != null ? artist.getUsername() : this.getUsername());
		
//		if(song.getPlaylists() != null) {
//			if(this.getPlaylists() == null) {
//				this.setPlaylists(song.getPlaylists());
//			}
//			else if(!song.getPlaylists().equals(this.getPlaylists())) {
//				this.setPlaylists(song.getPlaylists());
//			}
//		}
		
		if(artist.getSongs() != null) {
			if(this.getSongs() == null) {
				this.setSongs(artist.getSongs());
			}
			else if(!artist.getSongs().equals(this.getSongs())) {
				this.setSongs(artist.getSongs());
			}
		}
	}
	
	

}
