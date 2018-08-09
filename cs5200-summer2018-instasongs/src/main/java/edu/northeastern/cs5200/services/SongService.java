package edu.northeastern.cs5200.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.northeastern.cs5200.entities.Critic;
import edu.northeastern.cs5200.entities.Song;
import edu.northeastern.cs5200.repositories.CriticRepository;
import edu.northeastern.cs5200.repositories.SongRepository;

@RestController
public class SongService {
	
	@Autowired
	private SongRepository songRepository;
	
	@PostMapping("/api/song")
	public Song createSong(@RequestBody Song song) {
		return songRepository.save(song);
		
	}
	
	@GetMapping("/api/song/{id}")
	public Song findArtistById(@PathVariable("id") int id) {
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
	 

}
