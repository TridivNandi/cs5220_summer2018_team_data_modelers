package edu.northeastern.cs5200.entities;

import java.util.ArrayList;
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
	
	@OneToMany(mappedBy = "album")
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<Song> songs;
	
	public Album() {
		songs = new ArrayList<>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDateOfRelease() {
		return dateOfRelease;
	}

	public void setDateOfRelease(Date dateOfRelease) {
		this.dateOfRelease = dateOfRelease;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public List<Song> getSongs() {
		return songs;
	}

	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}
	
	public void addSongToAlbum(Song song) {
		if(song != null && !this.songs.contains(song)) {
			this.songs.add(song);
			if(song.getAlbum() !=this) {
				song.setAlbum(this);
			}
		}
	}

	public void removeSongFromAlbum(Song song) {
		
		if(this.songs.contains(song)) {
			this.songs.remove(song);
			if(song.getAlbum() == this) {
				song.setAlbum(null);
			}
		}	
	}
	
	public void set(Album album) {
		this.setCountry(album.getCountry() != null ? album.getCountry() : this.getCountry());
		this.setDateOfRelease(album.getDateOfRelease() != null ? album.getDateOfRelease() : this.getDateOfRelease());
		this.setGenre(album.getGenre() != null ? album.getGenre() : this.getGenre());
		this.setLanguage(album.getLanguage() != null ? album.getLanguage() : this.getLanguage());
		this.setName(album.getName() != null ? album.getName() : this.getName());
		
		if(album.getSongs() != null) {
			if(this.getSongs() == null) {
				this.setSongs(album.getSongs());
			}
			else if(this.getSongs().equals(album.getSongs())) {
				this.setSongs(album.getSongs());
			}
		}
	}
	
	@Override
	public boolean equals(Object object) {
		if(object instanceof Album) {
			Album album = (Album) object;
			if(album.getId() == this.getId()) {
				return true;
			}
		}
		return false;
	}
	
	

}
