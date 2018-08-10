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

import edu.northeastern.cs5200.entities.Artist;
import edu.northeastern.cs5200.entities.Critic;
import edu.northeastern.cs5200.entities.RegisteredUser;
import edu.northeastern.cs5200.repositories.CriticRepository;

@RestController
public class CriticService {

	@Autowired
	private CriticRepository criticRepository;
	
	@PostMapping("/api/critic")
	public Critic createCritic(@RequestBody Critic critic) {
		return criticRepository.save(critic);
		
	}
	
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
	
	@GetMapping("/api/critic")
	public List<Critic> findAllCritics(){
		return (List<Critic>) criticRepository.findAll();
	}
	
	@PutMapping("/api/critic/{id}")
	public Critic updateCritic(@PathVariable("id") int id, @RequestBody Critic critic) {
		Critic prevCritic = findCriticById(id);
		prevCritic.set(critic);
		return criticRepository.save(prevCritic);
	}
	
	@GetMapping("/api/critic/name/{firstName}/{lastName}")
	public Critic findCriticByName(@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName) {
		List<Critic> criticList = (List<Critic>) criticRepository.findCriticByName(firstName, lastName);
		if(criticList != null && !criticList.isEmpty()) {
			return criticList.get(0);
		}
		return null;
	}
}
