package edu.northeastern.cs5200.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.northeastern.cs5200.entities.User;

public interface UserRepository extends CrudRepository<User,Integer> {
	
	@Query("SELECT u FROM User u WHERE u.username=:username and u.password=:password")
	Iterable<User> findUserByCredentials(@Param("username") String username, @Param("password") String password);

}
