package edu.northeastern.cs5200.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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

@Entity
public class Song {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private Date dateOfRelease;
	private String country;
	private String language;
	private String genre;
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
	
	
	public Song() {
		playlists =new ArrayList<>();
		artists = new ArrayList<>();
		reviewsRecieved = new ArrayList<>();
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
	
	public void addPlaylistToSong(Playlist playlist) {
		this.playlists.add(playlist);
		if(!playlist.getSongs().contains(this)) {
			playlist.getSongs().add(this);
		}	
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

	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Song) {
			Song song = (Song) obj;
			if(this.id == song.id) {
				return true;
			}
		}
		return false;
	}

	public void set(Song song) {
		// this.setTitle(movie.getTitle() != null ? movie.getTitle() : this.getTitle());
		this.setAlbum(song.getAlbum() != null ? song.getAlbum() : this.getAlbum());
		this.setCountry(song.getCountry() != null ? song.getCountry() : this.getCountry());
		this.setDateOfRelease(song.getDateOfRelease() != null ? song.getDateOfRelease() : this.getDateOfRelease());
		this.setGenre(song.getGenre() != null ? song.getGenre() : this.getGenre());
		this.setLanguage(song.getLanguage() != null ? song.getLanguage() : this.getLanguage());
		this.setName(song.getName() != null ? song.getName() : this.getName());
	
//		if (movie.getActors() != null) {
//			if (this.getActors() == null) {
//				this.setActors(movie.getActors());
//			} else if (!movie.getActors().equals(this.getActors())) {
//				this.setActors(movie.getActors());
//			}
//		}
		
		if(song.getPlaylists() != null) {
			if(this.getPlaylists() == null) {
				this.setPlaylists(song.getPlaylists());
			}
			else if(!song.getPlaylists().equals(this.getPlaylists())) {
				this.setPlaylists(song.getPlaylists());
			}
		}
		
		if(song.getArtists() != null) {
			if(this.getArtists() == null) {
				this.setArtists(song.getArtists());
			}
			else if(!song.getArtists().equals(this.getArtists())) {
				this.setArtists(song.getArtists());
			}
		}
	}
	
	
	
	

}
