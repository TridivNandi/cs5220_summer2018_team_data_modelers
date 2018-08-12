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

import edu.northeastern.cs5200.entities.Artist;
import edu.northeastern.cs5200.entities.Critic;
import edu.northeastern.cs5200.entities.RegisteredUser;
import edu.northeastern.cs5200.repositories.ArtistRepository;
import edu.northeastern.cs5200.repositories.RegisteredUserRepository;

@RestController
public class RegisteredUserService {
	
	@Autowired
	RegisteredUserRepository registeredUserRepository;
	
	@Autowired
	ArtistService artistService;
	
	@Autowired
	ArtistRepository artistRepository;
	
	@PostMapping("/api/registereduser")
	public RegisteredUser createRegisteredUser(@RequestBody RegisteredUser registeredUser) {
		return registeredUserRepository.save(registeredUser);
		
	}
	
	@GetMapping("/api/registereduser/{id}")
	public RegisteredUser findRegisteredUserById(@PathVariable("id") int id) {
		Optional<RegisteredUser> registeredUser =  registeredUserRepository.findById(id);
		if(registeredUser != null) {
			return registeredUser.get();
		}
		else {
			return null;
		}
	}
	
	@GetMapping("/api/registereduser")
	public List<RegisteredUser> findAllRegisteredUsers(){
		return (List<RegisteredUser>) registeredUserRepository.findAll();
	}
	
	@PutMapping("/api/registereduser/{id}")
	public RegisteredUser updateRegisteredUser(@PathVariable("id") int id, @RequestBody RegisteredUser user) {
		RegisteredUser prevUser = findRegisteredUserById(id);
		prevUser.set(user);
		return registeredUserRepository.save(prevUser);
	}
	
	@PutMapping("/api/registereduser/follow/{userid}/{artistid}")
	public RegisteredUser followArtist(@PathVariable("userid") int userid, @PathVariable("artistid") int artistid) {
		RegisteredUser user = findRegisteredUserById(userid);
		Artist artist = artistService.findArtistById(artistid);
		user.addArtistToFollowing(artist);
		artistService.updateArtist(artistid, artist);
		return updateRegisteredUser(userid, user);
		
		
	}
	
	@DeleteMapping("/api/registereduser/follow/{userid}/{artistid}")
	public RegisteredUser unfollowArtist(@PathVariable("userid") int userid, @PathVariable("artistid") int artistid) {
		RegisteredUser user = findRegisteredUserById(userid);
		Artist artist = artistService.findArtistById(artistid);
		user.removeArtistFromFollowing(artist);
		artistService.updateArtist(artistid, artist);
		return updateRegisteredUser(userid, user);
	}

}
