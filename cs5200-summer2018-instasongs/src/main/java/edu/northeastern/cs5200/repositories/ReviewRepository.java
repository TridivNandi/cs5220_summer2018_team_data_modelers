package edu.northeastern.cs5200.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.northeastern.cs5200.entities.Critic;
import edu.northeastern.cs5200.entities.Review;
import edu.northeastern.cs5200.entities.Song;
import edu.northeastern.cs5200.entities.User;

public interface ReviewRepository extends CrudRepository<Review, Integer> {
	
	@Query("SELECT r FROM Review r WHERE r.critic=:critic and r.song=:song")
	Iterable<Review> findReviewByCrticSong(@Param("critic") Critic critic, @Param("song") Song song);

}
