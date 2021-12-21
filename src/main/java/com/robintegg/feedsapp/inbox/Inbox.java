package com.robintegg.feedsapp.inbox;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.robintegg.feedsapp.podcasts.Episode;
import com.robintegg.feedsapp.podcasts.Podcast;
import com.robintegg.feedsapp.subscriptions.Subscription;

public interface Inbox {

	void put(String username, Subscription subscription, Podcast podcast, Collection<Episode> episodes);

	List<Episode> findAllPodcasts(String username, boolean played, Comparator<Episode> sortBy);

	void moveAllPodcastEpidodesToInbox(String username);

	Page<Episode> getItems(String username, Pageable pageable);

	void ignore(String username, String episodeId);

}
