package edu.northeastern.cs5200.repositories;

import org.springframework.data.repository.CrudRepository;

import edu.northeastern.cs5200.entities.Playlist;

public interface PlaylistRepository extends CrudRepository<Playlist, Integer> {
	

}
