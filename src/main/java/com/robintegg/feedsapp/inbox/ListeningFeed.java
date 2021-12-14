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
public class ListeningFeed {

	private final UserInbox userInbox;

	public List<Episode> fetch(User user) {

		return userInbox.findAllPodcasts(user, PodcastEpisodeStatus.INTERESTED, Episode.ORDER_BY_MOST_RECENT);

	}

}