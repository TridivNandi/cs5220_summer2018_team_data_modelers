package edu.northeastern.cs5200.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Represents the Song class. A Song can be recorded by multiple artists. A song
 * can also be a part of multiple playlists.
 * 
 * @author Tridiv
 *
 */
@Entity
public class Song {

	// Instance variables
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private Long playCount;
	private String imageUrl;
	private String streamUrl;

	@ManyToOne
	private Album album;

	@OneToMany(mappedBy = "song")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Review> reviewsRecieved;

	@ManyToMany
	@JoinTable(name = "Song2Playlist")
	@LazyCollection(LazyCollectionOption.FALSE)
	@JsonIgnore
	private List<Playlist> playlists;

	@ManyToMany(mappedBy = "songs")
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Artist> artists;

	/**
	 * Represents the default constructor
	 */
	public Song() {
		playlists = new ArrayList<>();
		artists = new ArrayList<>();
		reviewsRecieved = new ArrayList<>();
	}

	// Getters and Setters
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

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}

	public List<Playlist> getPlaylists() {
		return playlists;
	}

	public void setPlaylists(List<Playlist> playlists) {
		this.playlists = playlists;
	}

	public List<Artist> getArtists() {
		return artists;
	}

	public void setArtists(List<Artist> artists) {
		this.artists = artists;
	}

	public List<Review> getReviewsRecieved() {
		return reviewsRecieved;
	}

	public void setReviewsRecieved(List<Review> reviewsRecieved) {
		this.reviewsRecieved = reviewsRecieved;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getStreamUrl() {
		return streamUrl;
	}

	public void setStreamUrl(String streamUrl) {
		this.streamUrl = streamUrl;
	}

	public Long getPlayCount() {
		return playCount;
	}

	public void setPlayCount(Long playCount) {
		this.playCount = playCount;
	}

	/**
	 * Adds the playlist reference passed in the argument to the current instance of
	 * the song Additionally updates the playlist with the current instance of the
	 * song
	 * 
	 * @param playlist
	 */
	public void addPlaylistToSong(Playlist playlist) {
		this.playlists.add(playlist);
		if (!playlist.getSongs().contains(this)) {
			playlist.getSongs().add(this);
		}
	}

	/**
	 * Overridden version of the Equals method. Two songs are considered equal only
	 * if they have same id
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Song) {
			Song song = (Song) obj;
			if (this.id == song.id) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Updates the attributes of current reference with that of the song object
	 * passed as argument
	 * 
	 * @param song
	 */
	public void set(Song song) {
		this.setAlbum(song.getAlbum() != null ? song.getAlbum() : this.getAlbum());
		this.setPlayCount(song.getPlayCount() != null ? song.getPlayCount() : this.getPlayCount());
		this.setName(song.getName() != null ? song.getName() : this.getName());
		this.setImageUrl(song.getImageUrl() != null ? song.getImageUrl() : this.getImageUrl());
		this.setStreamUrl(song.getStreamUrl() != null ? song.getStreamUrl() : this.getStreamUrl());

		if (song.getPlaylists() != null) {
			if (this.getPlaylists() == null) {
				this.setPlaylists(song.getPlaylists());
			} else if (!song.getPlaylists().equals(this.getPlaylists())) {
				this.setPlaylists(song.getPlaylists());
			}
		}

		if (song.getArtists() != null) {
			if (this.getArtists() == null) {
				this.setArtists(song.getArtists());
			} else if (!song.getArtists().equals(this.getArtists())) {
				this.setArtists(song.getArtists());
			}
		}
	}

}
