package edu.northeastern.cs5200.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Album {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private Date dateOfRelease;
	private String country;
	private String language;
	private String genre;
	
	@OneToMany(mappedBy = "album", cascade = CascadeType.ALL)
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<Song> songs;
	
	

}
