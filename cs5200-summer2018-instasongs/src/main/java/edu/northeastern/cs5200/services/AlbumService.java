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
import edu.northeastern.cs5200.entities.Song;
import edu.northeastern.cs5200.repositories.AlbumRepository;
import edu.northeastern.cs5200.repositories.SongRepository;

@RestController
public class AlbumService {
	
	@Autowired
	private AlbumRepository albumRepository;
	
	@Autowired
	private SongRepository songRepository;
	
	@PostMapping("/api/album")
	public Album createAlbum(@RequestBody Album album) {
		return albumRepository.save(album);
		
	}
	
	@GetMapping("/api/album/{id}")
	public Album findAlbumById(@PathVariable("id") int id) {
		Optional<Album> album =  albumRepository.findById(id);
		if(album != null) {
			return album.get();
		}
		else {
			return null;
		}
	}
	
	@GetMapping("/api/album")
	public List<Album> findAllAlbums(){
		return (List<Album>) albumRepository.findAll();
	}
	
	@PutMapping("/api/album/{albumId}/song/{songId}")
	public Album addSongToAlbum(@PathVariable("albumId") int albumId, @PathVariable("songId") int songId) {
		
		Album album = albumRepository.findById(albumId).get();
		Song song = songRepository.findById(songId).get();
		
		if(album != null && song != null) {
			album.addSongToAlbum(song);
			return albumRepository.save(album);
		}
		System.out.println("Either album or song is null!");
		return null;
	}
	
	@DeleteMapping("/api/album/{albumId}/song/{songId}")
	public Album removeSongToAlbum(@PathVariable("albumId") int albumId, @PathVariable("songId") int songId) {
		
		Album album = albumRepository.findById(albumId).get();
		Song song = songRepository.findById(songId).get();
		
		if(album != null && song != null) {
			album.removeSongFromAlbum(song);
			return albumRepository.save(album);
		}
		System.out.println("Either album or song is null!");
		return null;
	}
}
