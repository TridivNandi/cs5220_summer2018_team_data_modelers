package edu.northeastern.cs5200.repositories;

import org.springframework.data.repository.CrudRepository;

import edu.northeastern.cs5200.entities.Artist;

public interface ArtistRepository extends CrudRepository<Artist, Integer>{

}
