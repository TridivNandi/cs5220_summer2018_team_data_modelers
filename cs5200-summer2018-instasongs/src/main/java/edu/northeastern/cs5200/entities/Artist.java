package edu.northeastern.cs5200.entities;

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
	private int numberOfAwards;
	
	@ManyToMany
	@JoinTable(name = "Artist2Song")
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<Song> songs;
	
	public Artist() {
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
