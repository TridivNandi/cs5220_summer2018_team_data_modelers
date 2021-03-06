package edu.northeastern.cs5200.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 * Represents the Playlist class. A Playlist is being made my a registered user.
 * It contains a collection of songs
 * 
 * @author Tridiv
 *
 */
@Entity
public class Playlist {

	// instance variables
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private Date dateOfCreation;
	private String genre;

	@ManyToMany(fetch = FetchType.EAGER, mappedBy = "playlists")
	private List<Song> songs;

	@ManyToOne
	private RegisteredUser owner;

	/**
	 * Represents the Default constructor
	 */
	public Playlist() {
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

	/**
	 * Adds the song sent in the argument to the current instance of playlist
	 * Additionally update the playlist reference of the song
	 * 
	 * @param song
	 */
	public void addSongToPlaylist(Song song) {
		this.songs.add(song);
		if (!song.getPlaylists().contains(this)) {
			song.getPlaylists().add(this);
		}
	}

	/**
	 * Remove the song sent in the argument from the current instance of playlist
	 * Additionally update the playlist reference of the song
	 * 
	 * @param song
	 */
	public void removeSongFromPlaylist(Song song) {

		this.songs.remove(song);
		if (song.getPlaylists().contains(this)) {
			song.getPlaylists().remove(this);
		}

	}

	/**
	 * Updates the attributes of current reference with that of the playlist object
	 * passed as argument
	 * 
	 * @param playlist
	 */
	public void set(Playlist playlist) {

		this.setDateOfCreation(
				playlist.getDateOfCreation() != null ? playlist.getDateOfCreation() : this.getDateOfCreation());
		this.setGenre(playlist.getGenre() != null ? playlist.getGenre() : this.getGenre());
		this.setName(playlist.getName() != null ? playlist.getName() : this.getName());
		this.setOwner(playlist.getOwner() != null ? playlist.getOwner() : this.getOwner());

		if (playlist.getSongs() != null) {
			if (this.getSongs() == null) {
				this.setSongs(playlist.getSongs());
			} else if (!playlist.getSongs().equals(this.getSongs())) {
				this.setSongs(playlist.getSongs());
			}
		}
	}

	/**
	 * Overridden version of the equals method. Two playlists are considered equal
	 * only if they have the same id.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Playlist) {
			Playlist playlist = (Playlist) obj;
			if (this.id == playlist.id) {
				return true;
			}
		}
		return false;
	}

}
