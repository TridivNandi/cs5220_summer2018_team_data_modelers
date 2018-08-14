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
import edu.northeastern.cs5200.entities.Review;
import edu.northeastern.cs5200.repositories.CriticRepository;

@RestController
public class CriticService {

	@Autowired
	private CriticRepository criticRepository;
	
	@Autowired
	private ReviewService reviewService;
	
	/**
	 * Creates a new entry for critic
	 * @param critic
	 * @return
	 */
	@PostMapping("/api/critic")
	public Critic createCritic(@RequestBody Critic critic) {
		return criticRepository.save(critic);
		
	}
	
	/**
	 * Retrieves critic by id
	 * @param id
	 * @return
	 */
	@GetMapping("/api/critic/{id}")
	public Critic findCriticById(@PathVariable("id") int id) {
		Optional<Critic> critic =  criticRepository.findById(id);
		if(critic != null) {
			return critic.get();
		}
		else {
			return null;
		}
	}
	
	
	/**
	 * Retrieves all the critics
	 * @return
	 */
	@GetMapping("/api/critic")
	public List<Critic> findAllCritics(){
		return (List<Critic>) criticRepository.findAll();
	}
	
	
	/**
	 * Updates the attributes of a critic
	 * @param id
	 * @param critic
	 * @return
	 */
	@PutMapping("/api/critic/{id}")
	public Critic updateCritic(@PathVariable("id") int id, @RequestBody Critic critic) {
		Critic prevCritic = findCriticById(id);
		prevCritic.set(critic);
		return criticRepository.save(prevCritic);
	}
	
	/**
	 * Retrieve a critic by his/her first and last name
	 * @param firstName
	 * @param lastName
	 * @return
	 */
	@GetMapping("/api/critic/name/{firstName}/{lastName}")
	public Critic findCriticByName(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
		List<Critic> criticList = (List<Critic>) criticRepository.findCriticByName(firstName, lastName);
		if(criticList != null && !criticList.isEmpty()) {
			return criticList.get(0);
		}
		return null;
	}
	
	
	/**
	 * Delete a particular critic by his/her id
	 * @param id
	 */
	@DeleteMapping("/api/critic/{id}")
	public void deleteCritic(@PathVariable("id") int id) {
		Critic critic = findCriticById(id);
		List<Review> reviewsGiven = critic.getReviewsGiven();
		if(reviewsGiven != null && !reviewsGiven.isEmpty()) {
			for(Review reviewGiven: reviewsGiven) {
				reviewGiven.setCritic(null);
				reviewService.updateReview(reviewGiven.getId(), reviewGiven);
			}
		}
		criticRepository.deleteById(id);
	}
	
	/**
	 * Deletes all the critics
	 */
	@DeleteMapping("/api/critic")
	public void deleteAllCritics() {
		List<Critic> critics = findAllCritics();
		for(Critic critic: critics) {
			deleteCritic(critic.getId());
		}
	}
}
