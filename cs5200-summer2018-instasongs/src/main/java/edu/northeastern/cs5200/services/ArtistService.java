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
import edu.northeastern.cs5200.entities.Critic;
import edu.northeastern.cs5200.entities.RegisteredUser;
import edu.northeastern.cs5200.entities.Song;
import edu.northeastern.cs5200.repositories.ArtistRepository;

@RestController
public class ArtistService {
	
	@Autowired
	private ArtistRepository artistRepository;
	
	@Autowired
	private SongService songService;
	
	@Autowired
	private RegisteredUserService registeredUserService;
	
	@Autowired
	private AdminUserService adminUserService;
	
	
	
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
	
	@GetMapping("/api/artist/name/{name}")
	public Artist findArtistByName(@PathVariable String name) {
		
		List<Artist> list =  (List<Artist>) artistRepository.findArtistByName(name);
		if(list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}
	
	@PutMapping("/api/artist/follow/{artistidFollower}/{artistidFollowing}")
	public void followAnotherArtist(@PathVariable("artistidFollower") int artistidFollower, @PathVariable("artistidFollowing") int artistidFollowing) {
		Artist artistFollower = findArtistById(artistidFollower);
		Artist artistFollowing = findArtistById(artistidFollowing);
		artistFollower.addArtistToFollowing(artistFollowing);
		updateArtist(artistidFollowing, artistFollowing);
		updateArtist(artistidFollower, artistFollower);
		
	}
		
	@DeleteMapping("/api/artist/follow/{artistidFollower}/{artistidFollowing}")
	public void unfollowArtist(@PathVariable("artistidFollower") int artistidFollower, @PathVariable("artistidFollowing") int artistidFollowing) {
		Artist artistFollower = findArtistById(artistidFollower);
		Artist artistFollowing = findArtistById(artistidFollowing);
		artistFollower.removeArtistFromFollowing(artistFollowing);
		updateArtist(artistidFollowing, artistFollowing);
		updateArtist(artistidFollower, artistFollower);
	}
	
	@GetMapping("/api/artist/following/{id}")
	public List<Artist> findAllFollowingArtists(@PathVariable ("id") int id){
		return findArtistById(id).getArtistsFollowing();
	}
	
	@GetMapping("/api/artist/follower/{id}")
	public List<Artist> findAllFollowerArtists(@PathVariable("id") int id){
		return findArtistById(id).getArtistFollowers();
	}
	
	@DeleteMapping("/api/artist/{id}")
	public void deleteArtist(@PathVariable("id") int id) {

		Artist artist = findArtistById(id);
		List<Song> songs = artist.getSongs();
		if(songs != null && !songs.isEmpty()) {
			for(Song song: songs) {
				song.getArtists().remove(artist);
				songService.updateSong(song.getId(), song);
			}
		}
		
		List<RegisteredUser> followers = artist.getFollowers();
		if(followers != null && !followers.isEmpty()) {
			for(RegisteredUser user: followers) {
				user.getFollowing().remove(artist);
				registeredUserService.updateRegisteredUser(user.getId(), user);
			}
		}
		
		List<AdminUser> adminFollowers = artist.getAdminFollowers();
		if(adminFollowers != null && !adminFollowers.isEmpty()) {
			for(AdminUser user: adminFollowers) {
				user.getFollowing().remove(artist);
				adminUserService.updateAdminUser(user.getId(), user);
			}
		}
		
		List<Artist> artistFollowers = artist.getArtistFollowers();
		if(artistFollowers != null && !artistFollowers.isEmpty()) {
			for(Artist artistFollower: artistFollowers) {
				artistFollower.getArtistsFollowing().remove(artist);
				updateArtist(artistFollower.getId(), artistFollower);
			}
		}
		
		List<Artist> artistFollowingList = artist.getArtistsFollowing();
		if(artistFollowingList != null && !artistFollowingList.isEmpty()) {
			for(Artist artistFollowing: artistFollowingList) {
				artistFollowing.getArtistFollowers().remove(artist);
				updateArtist(artistFollowing.getId(), artistFollowing);
			}
		}
		
		artistRepository.deleteById(artist.getId());
	}
	
	@DeleteMapping("/api/artist")
	public void deleteAllArtists() {
		List<Artist> artists = findAllArtists();
		for(Artist artist: artists) {
			deleteArtist(artist.getId());
		}
	}
	

}
