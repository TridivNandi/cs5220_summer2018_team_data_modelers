package edu.northeastern.cs5200.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import edu.northeastern.cs5200.entities.User;
import edu.northeastern.cs5200.repositories.UserRepository;

@RestController
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/api/user/credentials/{username}/{password}")
	public User findUserByCredentials(@PathVariable("username") String username, @PathVariable("password") String password) {
		List<User> userList = (List<User>) userRepository.findUserByCredentials(username, password);
		if(userList != null && userList.size() > 0) {
			return userList.get(0);
		}
		return null;
	}
}
