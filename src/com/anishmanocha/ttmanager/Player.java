package com.anishmanocha.ttmanager;

public class Player {
	
	private String firstName;
	
	private String lastName;
	
	private int rating;
	
	private String email;
	
	public Player(String firstName, String lastName, int rating, String email) {
		
		this.firstName= firstName;
		
		this.lastName=lastName;
		
		this.rating= rating;
		
		this.email=email;
	}
	
	
	//Getters and Setters

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

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}


	@Override
	public String toString() {
		return "Player [firstName=" + firstName + ", lastName=" + lastName + ", rating=" + rating + ", email=" + email
				+ "]";
	}
	
	
	
}
