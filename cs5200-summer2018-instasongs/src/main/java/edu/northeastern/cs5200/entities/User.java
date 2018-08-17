package edu.northeastern.cs5200.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

/**
 * Represents the User class. All types of users extends this class.
 * @author Tridiv
 *
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

	// instance variables
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private String type;
	
	/**
	 * Represents the default constructor
	 */
	public User() {
		super();
	}
	
	// Getters and Setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Updates the attributes of current reference with that of the user object
	 * passed as argument
	 * @param user
	 */
	public void set(User user) {
		this.setEmail(user.getEmail() != null ? user.getEmail() : this.getEmail());
		this.setFirstName(user.getFirstName() != null ? user.getFirstName() : this.getFirstName());
		this.setLastName(user.getLastName() != null ? user.getLastName() : this.getLastName());
		this.setPassword(user.getPassword() != null ? user.getPassword() : this.getPassword());
		this.setPhoneNumber(user.getPhoneNumber() != null ? user.getPhoneNumber() : this.getPhoneNumber());
		this.setUsername(user.getUsername() != null ? user.getUsername() : this.getUsername());
		this.setType(user.getType() != null ? user.getType() : this.getType());
	}
	
	/**
	 * Overridden version of the equals method. Two users are considered
	 * equal only if they have the same id
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof User) {
			User temp = (User) obj;
			return this.id == temp.id;
		}
		return false;
	}
	
	
	
	
}
