package com.bridgelabz.microservices.model;

import java.time.LocalDateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "Register")
public class User {

	@Id
	private String userid;

	@Field("Access_Token")
	private String token;

	@Field("First_Name")
	private String firstname;

	@Field("Last_Name")
	private String lastname;

	@Field("User_Name")
	private String username;

	@Field("Email_Id")
	private String email;

	@Field("User_Password")
	private String password;

	@Field("Registration_Date")
	private LocalDateTime date;

	@Field("Verified")
	private boolean isVerfied;

	@Field("Profile_Pic")
	private String profilepic;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public boolean isVerfied() {
		return isVerfied;
	}

	public void setVerfied(boolean isVerfied) {
		this.isVerfied = isVerfied;
	}

	public String getProfilepic() {
		return profilepic;
	}

	public void setProfilepic(String profilepic) {
		this.profilepic = profilepic;
	}

	@Override
	public String toString() {
		return "User [userid=" + userid + ", token=" + token + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", username=" + username + ", email=" + email + ", password=" + password + ", date=" + date
				+ ", isVerfied=" + isVerfied + ", profilepic=" + profilepic + "]";
	}

}
