package edu.northeastern.cs5200.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.northeastern.cs5200.entities.RegisteredUser;

public interface RegisteredUserRepository extends CrudRepository<RegisteredUser, Integer>{

	@Query("SELECT u FROM RegisteredUser u WHERE u.username=:username and u.password=:password")
	Iterable<RegisteredUser> findRegisteredUserByCredentials(@Param("username") String username, @Param("password") String password);
}
