package com.robintegg.feedsapp.inbox;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import org.springframework.security.core.userdetails.User;

import com.robintegg.feedsapp.podcasts.Episode;
import com.robintegg.feedsapp.podcasts.Podcast;
import com.robintegg.feedsapp.subscriptions.Subscription;

public interface UserInbox {

	void put(User user, Subscription subscription, Podcast podcast, Collection<Episode> episodes);

	List<Episode> findAllPodcasts(User user, PodcastEpisodeStatus status, Comparator<Episode> sortBy);

}
