package edu.northeastern.cs5200.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Playlist {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private Date dateOfCreation;
	private String genre;
	
	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "playlists", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Song> songs;
	
	@ManyToOne
	private RegisteredUser owner;
	
	public Playlist() {
		
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

	public Date getDateOfCreation() {
		return dateOfCreation;
	}

	public void setDateOfCreation(Date dateOfCreation) {
		this.dateOfCreation = dateOfCreation;
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

	public RegisteredUser getOwner() {
		return owner;
	}

	public void setOwner(RegisteredUser owner) {
		this.owner = owner;
	}
	
	public void addSongToPlaylist(Song song) {
		this.songs.add(song);
		if(!song.getPlaylists().contains(this)) {
			song.getPlaylists().add(this);
		}
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Playlist) {
			Playlist playlist = (Playlist) obj;
			if(this.id == playlist.id) {
				return true;
			}
		}
		return false;
	}

	public void removeSongFromPlaylist(Song song) {
		
		this.songs.remove(song);
		if(song.getPlaylists().contains(this)) {
			song.getPlaylists().remove(this);
		}
		
	}
	
	
	
	

}
