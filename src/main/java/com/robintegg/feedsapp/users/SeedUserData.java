package com.robintegg.feedsapp.users;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SeedUserData implements ApplicationRunner {

	private final UserDetailsManager userDetailsManager;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		if (!userDetailsManager.userExists("rtegg")) {
			UserDetails user = User.withDefaultPasswordEncoder().username("rtegg").password("password")
					.roles("ADMIN", "USER").build();
			userDetailsManager.createUser(user);
		}

	}

}
