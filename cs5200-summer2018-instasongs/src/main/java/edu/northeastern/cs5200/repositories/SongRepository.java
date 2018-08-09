package edu.northeastern.cs5200.repositories;

import org.springframework.data.repository.CrudRepository;

import edu.northeastern.cs5200.entities.Song;

public interface SongRepository extends CrudRepository<Song, Integer> {

}
