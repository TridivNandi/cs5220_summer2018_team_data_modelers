package edu.northeastern.cs5200.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.northeastern.cs5200.entities.AdminUser;
import edu.northeastern.cs5200.entities.Artist;
import edu.northeastern.cs5200.entities.Critic;
import edu.northeastern.cs5200.repositories.ArtistRepository;

@RestController
public class ArtistService {
	
	@Autowired
	private ArtistRepository artistRepository;
	
	@PostMapping("/api/artist")
	public Artist createArtist(@RequestBody Artist artist) {
		return artistRepository.save(artist);
		
	}
	
	@GetMapping("/api/artist/{id}")
	public Artist findArtistById(@PathVariable("id") int id) {
		Optional<Artist> artist =  artistRepository.findById(id);
		if(artist != null) {
			return artist.get();
		}
		else {
			return null;
		}
	}
	
	@GetMapping("/api/artist")
	public List<Artist> findAllArtists(){
		return (List<Artist>) artistRepository.findAll();
	}
	
	@PutMapping("/api/artist/{id}")
	public Artist updateArtist(@PathVariable int id, @RequestBody Artist artist) {
		Artist prevArtist = findArtistById(id);
		prevArtist.set(artist);
		return artistRepository.save(prevArtist);
		
	}
	
	@GetMapping("/api/artist/name/{firstName}/{lastName}")
	public Artist findArtistByName(@PathVariable String firstName, @PathVariable String lastName) {
		
		List<Artist> list =  (List<Artist>) artistRepository.findArtistByName(firstName, lastName);
		if(list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	

}
