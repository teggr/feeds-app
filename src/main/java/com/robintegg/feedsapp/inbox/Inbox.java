package com.robintegg.feedsapp.inbox;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import com.robintegg.feedsapp.podcasts.Episode;
import com.robintegg.feedsapp.podcasts.Podcast;
import com.robintegg.feedsapp.subscriptions.Subscription;

public interface Inbox {

	void put(String username, Subscription subscription, Podcast podcast, Collection<Episode> episodes);

	List<Episode> findAllPodcasts(String username, InboxPodcastEpisodeStatus status, Comparator<Episode> sortBy);

	void moveAllPodcastEpidodesToInbox(String username);

}
