package com.robintegg.feedsapp.playlist;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class UserInboxesService implements UserInboxes {

	private final UserInbox userInbox;

	@Override
	public UserInbox getUserInbox(User user) {
		return userInbox;
	}

}
