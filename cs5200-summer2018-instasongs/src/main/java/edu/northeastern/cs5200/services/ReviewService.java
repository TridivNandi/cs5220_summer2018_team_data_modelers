package edu.northeastern.cs5200.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.northeastern.cs5200.entities.Critic;
import edu.northeastern.cs5200.entities.Review;
import edu.northeastern.cs5200.entities.Song;
import edu.northeastern.cs5200.repositories.CriticRepository;
import edu.northeastern.cs5200.repositories.ReviewRepository;
import edu.northeastern.cs5200.repositories.SongRepository;

@RestController
public class ReviewService {

	@Autowired
	ReviewRepository reviewRepository;
	
	@Autowired
	CriticService criticService;
	
	@Autowired
	SongService songService;
	
	@Autowired 
	SongRepository songRepo;
	
	@Autowired
	CriticRepository criticRepo;
	
	@PostMapping("/api/review/critic/{criticId}/song/{songId}")
	public Review createReview(@PathVariable("criticId") int criticId, @PathVariable("songId") int songId, @RequestBody Review review) {
		Critic critic = criticService.findCriticById(criticId);
		Song song = songService.findSongById(songId);
		review.setCritic(critic);
		review.setSong(song);
		review = reviewRepository.save(review);
		song.getReviewsRecieved().add(review);
		songRepo.save(song);
		criticRepo.save(critic);
		return review;
	}
	
	@GetMapping("/api/review/")
	public List<Review> findAllReviews(){
		return (List<Review>) reviewRepository.findAll();
	}
	
	@GetMapping("/api/review/{id}")
	public Review findReviewById(@PathVariable("id") int id) {
		
		return reviewRepository.findById(id).get();
	}
	
	@GetMapping("/api/review/critic/{criticId}/song/{songId}")
	public List<Review> findReviewByCriticForSong(@PathVariable("criticId") int criticId, @PathVariable("songId") int songId) {
		
		return (List<Review>) reviewRepository.findReviewByCrticSong(criticService.findCriticById(criticId), songService.findSongById(songId));
	}
	
	@PutMapping("/api/review/{id}")
	public Review updateReview(@PathVariable("id") int id, @RequestBody Review review) {
		Review prevReview = findReviewById(id);
		prevReview.set(review);
		return reviewRepository.save(prevReview);
	}
	
	@DeleteMapping("/api/review/{id}")
	public void deleteReview(@PathVariable("id") int id) {
		
		Review review = findReviewById(id);
		review.getCritic().getReviewsGiven().remove(review);
		review.getSong().getReviewsRecieved().remove(review);
		
		songRepo.save(review.getSong());
		criticRepo.save(review.getCritic());
		reviewRepository.deleteById(id);
	}
	
	@DeleteMapping("/api/review")
	public void deleteAllReviews() {
		List<Review> reviews = findAllReviews();
		for(Review review: reviews) {
			deleteReview(review.getId());
		}
	}
	
	
	
}
