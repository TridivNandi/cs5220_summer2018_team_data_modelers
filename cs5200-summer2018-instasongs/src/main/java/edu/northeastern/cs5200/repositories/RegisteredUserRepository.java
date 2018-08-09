package edu.northeastern.cs5200.repositories;

import org.springframework.data.repository.CrudRepository;

import edu.northeastern.cs5200.entities.RegisteredUser;

public interface RegisteredUserRepository extends CrudRepository<RegisteredUser, Integer>{

}
