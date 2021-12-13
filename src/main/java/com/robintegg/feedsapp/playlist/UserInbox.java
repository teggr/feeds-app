package com.robintegg.feedsapp.playlist;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

import com.robintegg.feedsapp.podcasts.Episode;
import com.robintegg.feedsapp.podcasts.Podcast;
import com.robintegg.feedsapp.subscriptions.Subscription;

public interface UserInbox {

	void put(Subscription subscription, Podcast podcast, Collection<Episode> episodes);

	List<Episode> findAllPodcasts(PodcastEpisodeStatus status, Comparator<Episode> sortBy);

}
