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

import edu.northeastern.cs5200.entities.AdminUser;
import edu.northeastern.cs5200.entities.Artist;
import edu.northeastern.cs5200.entities.RegisteredUser;
import edu.northeastern.cs5200.entities.User;
import edu.northeastern.cs5200.repositories.AdminUserRepository;

@RestController
public class AdminUserService {
	
	@Autowired
	AdminUserRepository adminUserRepository;
	
	@Autowired
	ArtistService artistService;
	
	
	/**
	 * Creates a new entry of admin in the database
	 * @param admin
	 * @return
	 */
	@PostMapping("/api/adminuser")
	public AdminUser createAdminUser(@RequestBody AdminUser admin) {
		return adminUserRepository.save(admin);
	}
	
	/**
	 * Retrieves details of an admin by id
	 * @param id
	 * @return
	 */
	@GetMapping("/api/adminuser/{id}")
	public AdminUser findAdminUserById(@PathVariable("id") int id) {
		Optional<AdminUser> adminUser =  adminUserRepository.findById(id);
		if(adminUser != null) {
			return adminUser.get();
		}
		else {
			return null;
		}
	}
	
	/**
	 * Retrieves all the admins
	 * @return
	 */
	@GetMapping("/api/adminuser")
	public List<AdminUser> findAllAdminUsers(){
		return (List<AdminUser>) adminUserRepository.findAll();
	}
	
	/**
	 * Updates the attributes of an admin
	 * @param id
	 * @param user
	 * @return
	 */
	@PutMapping("/api/adminuser/{id}")
	public AdminUser updateAdminUser(@PathVariable("id") int id, @RequestBody AdminUser user) {
		AdminUser prevUser = findAdminUserById(id);
		prevUser.set(user);
		return adminUserRepository.save(prevUser);
	}
	
	/**
	 * Makes a particular admin follow a particular artist
	 * @param userid
	 * @param artistid
	 * @return
	 */
	@PutMapping("/api/adminuser/follow/{userid}/{artistid}")
	public AdminUser followArtist(@PathVariable("userid") int userid, @PathVariable("artistid") int artistid) {
		AdminUser user = findAdminUserById(userid);
		Artist artist = artistService.findArtistById(artistid);
		user.addArtistToFollowing(artist);
		artistService.updateArtist(artistid, artist);
		return updateAdminUser(userid, user);
		
		
	}
	
	/**
	 * Makes a particular admin un-follow a particular artist
	 * @param userid
	 * @param artistid
	 * @return
	 */
	@DeleteMapping("/api/adminuser/follow/{userid}/{artistid}")
	public AdminUser unfollowArtist(@PathVariable("userid") int userid, @PathVariable("artistid") int artistid) {
		AdminUser user = findAdminUserById(userid);
		Artist artist = artistService.findArtistById(artistid);
		user.removeArtistFromFollowing(artist);
		artistService.updateArtist(artistid, artist);
		return updateAdminUser(userid, user);
	}
	
	/**
	 * Deletes an admin
	 * @param id
	 */
	@DeleteMapping("/api/adminuser/{id}")
	public void deleteAdminUser(@PathVariable ("id") int id) {
		AdminUser user = findAdminUserById(id);
		if(!user.getFollowing().isEmpty()) {
			for(Artist artist: user.getFollowing()) {
				artist.getAdminFollowers().remove(this);
				artistService.updateArtist(artist.getId(), artist);
			}
		}
		adminUserRepository.deleteById(id);
	}
	
	/**
	 * Deletes all the admins
	 */
	@DeleteMapping("/api/adminuser")
	public void deleteAll() {
		List<AdminUser> adminUserList = findAllAdminUsers();
		for(AdminUser user: adminUserList) {
			deleteAdminUser(user.getId());
		}
	}
	

	/**
	 * Retrieves admin user on the basis of username and password
	 * @param username
	 * @param password
	 * @return
	 */
	@GetMapping("/api/adminuser/credentials/{username}/{password}")
	public AdminUser findAdminUserByCredentials(@PathVariable("username") String username, @PathVariable("password") String password) {
		List<AdminUser> userList = (List<AdminUser>) adminUserRepository.findAdminUserByCredentials(username, password);
		if(userList != null && userList.size() > 0) {
			return userList.get(0);
		}
		return null;
	}
}
