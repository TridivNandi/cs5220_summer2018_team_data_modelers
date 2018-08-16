package edu.northeastern.cs5200.services;

import java.util.ArrayList;
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

import edu.northeastern.cs5200.entities.AdminUser;
import edu.northeastern.cs5200.entities.Artist;
import edu.northeastern.cs5200.entities.Critic;
import edu.northeastern.cs5200.entities.Playlist;
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
	
	@Autowired
	PlaylistService playlistService;
	
	/**
	 * Creates a new entry for registered users
	 * @param registeredUser
	 * @return
	 */
	@PostMapping("/api/registereduser")
	public RegisteredUser createRegisteredUser(@RequestBody RegisteredUser registeredUser) {
		return registeredUserRepository.save(registeredUser);
		
	}
	
	
	/**
	 * Retrieves a registered user by his/her id
	 * @param id
	 * @return
	 */
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
	
	
	/**
	 * Retrieves all registered users
	 * @return
	 */
	@GetMapping("/api/registereduser")
	public List<RegisteredUser> findAllRegisteredUsers(){
		return (List<RegisteredUser>) registeredUserRepository.findAll();
	}
	
	
	/**
	 * Updates the details of a registered user
	 * @param id
	 * @param user
	 * @return
	 */
	@PutMapping("/api/registereduser/{id}")
	public RegisteredUser updateRegisteredUser(@PathVariable("id") int id, @RequestBody RegisteredUser user) {
		RegisteredUser prevUser = findRegisteredUserById(id);
		prevUser.set(user);
		return registeredUserRepository.save(prevUser);
	}
	
	
	/**
	 * Makes an registered user follow an artist
	 * @param userid
	 * @param artistid
	 * @return
	 */
	@PutMapping("/api/registereduser/follow/{userid}/{artistid}")
	public RegisteredUser followArtist(@PathVariable("userid") int userid, @PathVariable("artistid") int artistid) {
		RegisteredUser user = findRegisteredUserById(userid);
		Artist artist = artistService.findArtistById(artistid);
		user.addArtistToFollowing(artist);
		artistService.updateArtist(artistid, artist);
		return updateRegisteredUser(userid, user);
		
		
	}
	
	
	/**
	 * Makes a registered user un-follow an artist
	 * @param userid
	 * @param artistid
	 * @return
	 */
	@DeleteMapping("/api/registereduser/follow/{userid}/{artistid}")
	public RegisteredUser unfollowArtist(@PathVariable("userid") int userid, @PathVariable("artistid") int artistid) {
		RegisteredUser user = findRegisteredUserById(userid);
		Artist artist = artistService.findArtistById(artistid);
		user.removeArtistFromFollowing(artist);
		artistService.updateArtist(artistid, artist);
		return updateRegisteredUser(userid, user);
	}
	
	/**
	 * Deletes a registered user by id
	 * @param id
	 */
	@DeleteMapping("/api/registereduser/{id}")
	public void deleteRegisteredUser(@PathVariable ("id") int id) {
		RegisteredUser user = findRegisteredUserById(id);
		List<Playlist> playlists = user.getPlaylists();
		List<Integer> temp = new ArrayList<>();
		if(playlists != null && !playlists.isEmpty()) {
			for(Playlist pl: playlists) {
				pl.setOwner(null);
				temp.add(pl.getId());
				playlistService.updatePlaylist(pl.getId(), pl);
			}
			
		}
		
		List<Artist> following = user.getFollowing();
		if(following != null && !following.isEmpty()) {
			for(Artist artist: following) {
				artist.getFollowers().remove(user);
				artistService.updateArtist(artist.getId(), artist);
			}
		}
		
		registeredUserRepository.deleteById(user.getId());
		if(temp != null && !temp.isEmpty()) {
			for(Integer plId: temp) {
				playlistService.deletePlaylist(plId);
			}
		}
	}
	
	/**
	 * Deletes all registered users
	 */
	@DeleteMapping("/api/registereduser")
	public void deleteAllRegisteredUsers() {
		List<RegisteredUser> userList = findAllRegisteredUsers();
		for(RegisteredUser user : userList) {
			deleteRegisteredUser(user.getId());
		}
	}
	
	/**
	 * Retrieves all the playlists for a registered user
	 * @param id
	 * @return
	 */
	@GetMapping("/api/registereduser/{id}/playlists")
	public List<Playlist> getAllPlaylists(@PathVariable ("id") int id) {
		RegisteredUser user = findRegisteredUserById(id);
		if(user != null) {
			return user.getPlaylists();
		}
		return null;
	}
	
	
	/**
	 * Retrieves all the artists that a particular registered user is following
	 * @param id
	 * @return
	 */
	@GetMapping("/api/registereduser/{id}/following")
	public List<Artist> getArtistsFollowing(@PathVariable ("id") int id){
		RegisteredUser user = findRegisteredUserById(id);
		if(user != null) {
			return user.getFollowing();
		}
		return null;
	}
	
	/**
	 * Retrieves registered user on the basis of username and password
	 * @param username
	 * @param password
	 * @return
	 */
	@GetMapping("/api/registereduser/credentials/{username}/{password}")
	public RegisteredUser findRegisteredUserByCredentials(@PathVariable("username") String username, @PathVariable("password") String password) {
		List<RegisteredUser> userList = (List<RegisteredUser>) registeredUserRepository.findRegisteredUserByCredentials(username, password);
		if(userList != null && userList.size() > 0) {
			return userList.get(0);
		}
		return null;
	}

}
