package edu.northeastern.cs5200.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.northeastern.cs5200.entities.Album;
import edu.northeastern.cs5200.entities.Artist;
import edu.northeastern.cs5200.entities.Critic;
import edu.northeastern.cs5200.entities.Review;
import edu.northeastern.cs5200.entities.Song;
import edu.northeastern.cs5200.repositories.ArtistRepository;
import edu.northeastern.cs5200.repositories.CriticRepository;
import edu.northeastern.cs5200.repositories.SongRepository;

@RestController
public class SongService {
	
	@Autowired
	private SongRepository songRepository;
	
	@Autowired
	private ArtistService artistService;
	
	@Autowired
	private AlbumService albumService;
	
	@PostMapping("/api/song")
	public Song createSong(@RequestBody Song song) {
		Album album = song.getAlbum();
		if(album != null) {
			Album tempAlbum = albumService.findAlbumByName(album.getName());
			if(tempAlbum == null) {
				tempAlbum = albumService.createAlbum(album);
			}
			tempAlbum.addSongToAlbum(song);
			albumService.updateAlbum(tempAlbum.getId(), tempAlbum);
		}
		song = songRepository.save(song);
		List<Artist> artists = song.getArtists();
		if(artists != null && artists.size() != 0) {
			for(Artist artist: artists) {
				Artist tempArtist = artistService.findArtistByName(artist.getFirstName(), artist.getLastName());
				if(tempArtist == null) {
					tempArtist = artistService.createArtist(artist);
				}	
				tempArtist.getSongs().add(song);
				artistService.updateArtist(tempArtist.getId(), tempArtist);
			}
		}
		
		return song;
		
	}
	
	@GetMapping("/api/song/{id}")
	public Song findSongById(@PathVariable("id") int id) {
		Optional<Song> song =  songRepository.findById(id);
		if(song != null) {
			return song.get();
		}
		else {
			return null;
		}
	}
	
	@GetMapping("/api/song")
	public List<Song> findAllSongs(){
		return (List<Song>) songRepository.findAll();
	}
	
	@PutMapping("api/song/{id}")
	public Song updateSong(@PathVariable("id") int id, @RequestBody Song song) {
		Song prevSong = findSongById(id);
		prevSong.set(song);
		return songRepository.save(prevSong);
		
	}
	
	@GetMapping("api/song/name/{name}")
	public Song findSongByName(@PathVariable("name") String name) {
		List<Song> songs = (List<Song>) songRepository.findSongByName(name);
		if(songs != null && !songs.isEmpty()) {
			return songs.get(0);
		}
		return null;
	}
	
	@DeleteMapping("/api/song/{id}")
	public void deleteSong(@PathVariable("id") int id) {
		Song song = findSongById(id);
		List<Review> reviewsRecieved = song.getReviewsRecieved();
		
	}
	
	 

}
