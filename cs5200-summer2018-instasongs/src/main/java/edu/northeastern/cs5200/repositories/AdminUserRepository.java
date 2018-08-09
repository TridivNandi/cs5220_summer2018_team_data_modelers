package edu.northeastern.cs5200.repositories;

import org.springframework.data.repository.CrudRepository;

import edu.northeastern.cs5200.entities.AdminUser;

public interface AdminUserRepository extends CrudRepository<AdminUser, Integer>{

}
