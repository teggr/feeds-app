package com.robintegg.feedsapp.playlist;

import org.springframework.security.core.userdetails.User;

public interface UserInboxes {

	UserInbox getUserInbox(User user);

}
