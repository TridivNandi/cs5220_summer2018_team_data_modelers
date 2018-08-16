package edu.northeastern.cs5200.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.northeastern.cs5200.entities.AdminUser;

public interface AdminUserRepository extends CrudRepository<AdminUser, Integer>{
	
	@Query("SELECT u FROM AdminUser u WHERE u.username=:username and u.password=:password")
	Iterable<AdminUser> findAdminUserByCredentials(@Param("username") String username, @Param("password") String password);

}
