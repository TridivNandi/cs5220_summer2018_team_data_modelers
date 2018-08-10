package edu.northeastern.cs5200.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.northeastern.cs5200.entities.Artist;
import edu.northeastern.cs5200.entities.Critic;

public interface CriticRepository extends CrudRepository<Critic, Integer>{
	
	@Query("SELECT c FROM Critic c WHERE c.firstName=:firstName and c.lastName=:lastName")
	Iterable<Critic> findCriticByName(@Param("firstName") String firstName, @Param("lastName") String lastName);

}
