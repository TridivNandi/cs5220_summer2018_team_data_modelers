package edu.northeastern.cs5200.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents the Album class. An album consists of a list of songs
 * 
 * @author Tridiv
 *
 */
@Entity
public class Album {

	// instance variables
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

	/**
	 * Default constructor
	 */
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

	/**
	 * Adds song reference passed as an argument to the Album. Additionally adds the
	 * album reference to the song's album
	 * 
	 * @param song
	 */
	public void addSongToAlbum(Song song) {
		if (song != null && !this.songs.contains(song)) {
			this.songs.add(song);
			if (song.getAlbum() != this) {
				song.setAlbum(this);
			}
		}
	}

	/**
	 * Removes the song passed as an argument from the Album. Additionally removes
	 * the album reference from the song's album
	 * 
	 * @param song
	 */
	public void removeSongFromAlbum(Song song) {

		if (this.songs.contains(song)) {
			this.songs.remove(song);
			if (song.getAlbum() == this) {
				song.setAlbum(null);
			}
		}
	}

	/**
	 * Updates the attributes of current reference with that of the album object
	 * passed as argument
	 * 
	 * @param album
	 */
	public void set(Album album) {
		this.setCountry(album.getCountry() != null ? album.getCountry() : this.getCountry());
		this.setDateOfRelease(album.getDateOfRelease() != null ? album.getDateOfRelease() : this.getDateOfRelease());
		this.setGenre(album.getGenre() != null ? album.getGenre() : this.getGenre());
		this.setLanguage(album.getLanguage() != null ? album.getLanguage() : this.getLanguage());
		this.setName(album.getName() != null ? album.getName() : this.getName());

		if (album.getSongs() != null) {
			if (this.getSongs() == null) {
				this.setSongs(album.getSongs());
			} else if (this.getSongs().equals(album.getSongs())) {
				this.setSongs(album.getSongs());
			}
		}
	}

	/**
	 * Overridden version of the equals method Two albums are considered equal only
	 * if they have the same id
	 */
	@Override
	public boolean equals(Object object) {
		if (object instanceof Album) {
			Album album = (Album) object;
			if (album.getId() == this.getId()) {
				return true;
			}
		}
		return false;
	}

}
