package com.captcha.model;

import java.util.ArrayList;
import java.util.List;

public class User {
	
    private Long id;
        
    private String username;
        
    private String password;
    
    private List<String> roles = new ArrayList<>();

    
	/**
	 * @param id
	 * @param username
	 * @param password
	 */
	public User(Long id, String username, String password) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;		
	}
    
	/**
	 * @param id
	 * @param username
	 * @param password
	 * @param roles
	 */
	public User(Long id, String username, String password, List<String> roles) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.roles = roles;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @return the roles
	 */
	public List<String> getRoles() {
		return roles;
	}

	/**
	 * @param roles the roles to set
	 */
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User [username=" + username + "]";
	}
	
	
    
    
    
}
