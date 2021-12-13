package com.robintegg.feedsapp.playlist;

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

	private final UserInboxes userInboxes;

	public List<Episode> fetch(User user) {

		return userInboxes.getUserInbox(user).findAllPodcasts(PodcastEpisodeStatus.INTERESTED,
				Episode.ORDER_BY_MOST_RECENT);

	}

}
