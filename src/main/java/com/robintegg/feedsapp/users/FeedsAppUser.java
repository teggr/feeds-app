package com.robintegg.feedsapp.users;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class FeedsAppUser {

	@Id
	@Column(name = "username")
	private String username;

	@Column(name = "enabled")
	private boolean enabled;

	public String getUsername() {
		return username;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public String getEmail() {
		return username;
	}

}
