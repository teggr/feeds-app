package com.robintegg.feedsapp.users;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import com.robintegg.feedsapp.inbox.Inbox;
import com.robintegg.feedsapp.subscriptions.PodcastSubscriptions;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeedUserData implements ApplicationRunner {

	private final UserDetailsManager userDetailsManager;
	private final UserProperties userProperties;
	private final PodcastSubscriptions podcastSubscriptions;
	private final Inbox inbox;

	@Override
	public void run(ApplicationArguments args) throws Exception {

		if (!userDetailsManager.userExists(userProperties.getEmail())) {

			UserDetails user = User.withDefaultPasswordEncoder().username(userProperties.getEmail())
					.password("password").roles("ADMIN", "USER").build();
			userDetailsManager.createUser(user);

			log.info("Created the seed user {} with password {}", user.getUsername(), user.getPassword());

			podcastSubscriptions.transferAllSubscriptionsToUser(user.getUsername());

			inbox.moveAllPodcastEpidodesToInbox(user.getUsername());

		}

	}

}
