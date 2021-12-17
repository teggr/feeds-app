package com.robintegg.feedsapp.inbox;

import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.robintegg.feedsapp.podcasts.Episode;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class NewReleases {

	private final Inbox userInbox;

	public List<Episode> fetch(User user) {

		return userInbox.findAllPodcasts(user.getUsername(), null, Episode.ORDER_BY_MOST_RECENT);

	}

}
