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

import edu.northeastern.cs5200.entities.Playlist;
import edu.northeastern.cs5200.entities.RegisteredUser;
import edu.northeastern.cs5200.entities.Song;
import edu.northeastern.cs5200.repositories.PlaylistRepository;
import edu.northeastern.cs5200.repositories.SongRepository;

@RestController
public class PlaylistService {
	
	@Autowired
	private PlaylistRepository playlistRepository;
	
	@Autowired
	private SongRepository songRepository;
	
	@Autowired
	private SongService songService;
	
	@Autowired
	private RegisteredUserService registeredUserService;
	
	@PostMapping("/api/playlist")
	public Playlist createPlaylist(@RequestBody Playlist playlist) {
		playlist = playlistRepository.save(playlist);
		List<Song> songs = playlist.getSongs();
		if(songs != null && !songs.isEmpty()) {
			for(Song song: songs) {
				Song songTemp = songService.findSongByName(song.getName());
				if(songTemp == null) {
					songTemp = songService.createSong(song);	
				}
				songTemp.getPlaylists().add(playlist);
				songService.updateSong(song.getId(), songTemp);
			}
		}
		RegisteredUser owner = playlist.getOwner();
		if(owner != null) {
			owner.getPlaylists().add(playlist);
			registeredUserService.updateRegisteredUser(owner.getId(), owner);
		}
		return playlist;
		
	}
	
	@GetMapping("/api/playlist/{id}")
	public Playlist findPlaylistById(@PathVariable("id") int id) {
		Optional<Playlist> playlist =  playlistRepository.findById(id);
		if(playlist != null) {
			return playlist.get();
		}
		else {
			return null;
		}
	}
	
	@GetMapping("/api/playlist")
	public List<Playlist> findAllPlaylists(){
		return (List<Playlist>) playlistRepository.findAll();
	}

	@PutMapping("/api/playlist/{playlistId}/song/{songId}")
	public Playlist addSongToPlaylist(@PathVariable("playlistId") int playlistId, @PathVariable("songId") int songId) {
		
		Playlist playlist = playlistRepository.findById(playlistId).get();
		Song song = songRepository.findById(songId).get();
		if(playlist != null && song != null) {
			playlist.addSongToPlaylist(song);
			return playlistRepository.save(playlist);
		}
		System.out.println("Either playlist or song is NULL");
		return null;
		
	}
	
	@DeleteMapping("/api/playlist/{playlistId}/song/{songId}")
	public Playlist removeSongFromPlaylist(@PathVariable("playlistId") int playlistId, @PathVariable("songId") int songId) {
		
		Playlist playlist = playlistRepository.findById(playlistId).get();
		Song song = songRepository.findById(songId).get();
		if(playlist != null && song != null) {
			playlist.removeSongFromPlaylist(song);
			return playlistRepository.save(playlist);
		}
		System.out.println("Either playlist or song is NULL");
		return null;
	}
}
