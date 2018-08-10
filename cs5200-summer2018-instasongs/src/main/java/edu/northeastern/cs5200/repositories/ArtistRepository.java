package edu.northeastern.cs5200.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.northeastern.cs5200.entities.Artist;
import edu.northeastern.cs5200.entities.Song;

public interface ArtistRepository extends CrudRepository<Artist, Integer>{
	
	@Query("SELECT a FROM Artist a WHERE a.firstName=:firstName and a.lastName=:lastName")
	Iterable<Artist> findArtistByName(@Param("firstName") String firstName, @Param("lastName") String lastName);

}
