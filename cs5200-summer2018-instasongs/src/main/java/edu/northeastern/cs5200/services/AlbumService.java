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
	private SongService songService;
	
	/**
	 * Creates an entry of the album 
	 * @param album
	 * @return
	 */
	@PostMapping("/api/album")
	public Album createAlbum(@RequestBody Album album) {
		return albumRepository.save(album);
		
	}
	
	/**
	 * Retrieves an album by it's id
	 * @param id
	 * @return
	 */
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
	
	/**
	 * Retrieves all albums
	 * @return
	 */
	@GetMapping("/api/album")
	public List<Album> findAllAlbums(){
		return (List<Album>) albumRepository.findAll();
	}
	
	/**
	 * Adds a song to an album
	 * @param albumId
	 * @param songId
	 * @return
	 */
	@PutMapping("/api/album/{albumId}/song/{songId}")
	public Album addSongToAlbum(@PathVariable("albumId") int albumId, @PathVariable("songId") int songId) {
		
		Album album = findAlbumById(albumId);
		Song song = songService.findSongById(songId);
		
		if(album != null && song != null) {
			album.addSongToAlbum(song);
			return albumRepository.save(album);
		}
		System.out.println("Either album or song is null!");
		return null;
	}
	
	
	/**
	 * Removes a song from an album
	 * @param albumId
	 * @param songId
	 * @return
	 */
	@DeleteMapping("/api/album/{albumId}/song/{songId}")
	public Album removeSongFromAlbum(@PathVariable("albumId") int albumId, @PathVariable("songId") int songId) {
		
		Album album = findAlbumById(albumId);
		Song song = songService.findSongById(songId);
		
		
		if(album != null && song != null) {
			album.removeSongFromAlbum(song);
			return albumRepository.save(album);
		}
		System.out.println("Either album or song is null!");
		return null;
	}
	
	
	/**
	 * Update the attributes of an album
	 * @param albumId
	 * @param album
	 * @return
	 */
	@PutMapping("/api/album/{albumId}")
	public Album updateAlbum(@PathVariable("albumId") int albumId, @RequestBody Album album) {
		Album prevAlbum = findAlbumById(albumId);
		prevAlbum.set(album);
		return albumRepository.save(prevAlbum);
	}
	
	
	/**
	 * Retrieve an album by it's name
	 * @param name
	 * @return
	 */
	@GetMapping("/api/album/name/{albumName}")
	public Album findAlbumByName(@PathVariable("albumName") String name) {
		List<Album> albums = (List<Album>) albumRepository.findAlbumByName(name);
		if(albums != null && !albums.isEmpty()) {
			return albums.get(0);
		}
		return null;
	}
	
	
	/**
	 * Delete an album by it's id
	 * @param id
	 */
	@DeleteMapping("/api/album/{id}")
	public void deleteAlbum(@PathVariable("id") int id) {
		Album album = findAlbumById(id);
		List<Song> songs = album.getSongs();
		if(songs != null && !songs.isEmpty()) {
			for(Song song: songs) {
				song.setAlbum(null);
				songService.updateSong(song.getId(), song);	
			}
		}
		albumRepository.deleteById(album.getId());
	}
	
	
	/**
	 * Delete all albums
	 */
	@DeleteMapping("/api/album")
	public void deleteAllAlbums() {
		List<Album> albumList = findAllAlbums();
		for(Album album: albumList) {
			deleteAlbum(album.getId());
		}
	}
}
