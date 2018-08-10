package edu.northeastern.cs5200.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.northeastern.cs5200.entities.Song;

public interface SongRepository extends CrudRepository<Song, Integer> {

	@Query("SELECT s FROM Song s WHERE s.name=:name")
	Iterable<Song> findSongByName(@Param("name") String name);

}
