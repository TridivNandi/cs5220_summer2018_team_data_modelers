package edu.northeastern.cs5200.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.northeastern.cs5200.entities.AdminUser;
import edu.northeastern.cs5200.repositories.AdminUserRepository;

@RestController
public class AdminUserService {
	
	@Autowired
	AdminUserRepository adminUserRepository;
	
	@PostMapping("/api/adminuser")
	public AdminUser createAdminUser(@RequestBody AdminUser admin) {
		return adminUserRepository.save(admin);
	}
	
	@GetMapping("/api/adminuser/{id}")
	public AdminUser findAdminUserById(@PathVariable("id") int id) {
		Optional<AdminUser> adminUser =  adminUserRepository.findById(id);
		if(adminUser != null) {
			return adminUser.get();
		}
		else {
			return null;
		}
	}
	
	@GetMapping("/api/adminuser")
	public List<AdminUser> findAllAdminUsers(){
		return (List<AdminUser>) adminUserRepository.findAll();
	}
	
	
	
	
	

}
