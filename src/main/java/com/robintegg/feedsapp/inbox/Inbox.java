package com.robintegg.feedsapp.inbox;

import java.util.Collection;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.robintegg.feedsapp.podcasts.Episode;
import com.robintegg.feedsapp.podcasts.Podcast;
import com.robintegg.feedsapp.subscriptions.Subscription;

public interface Inbox {

	void put(String username, Subscription subscription, Podcast podcast, Collection<Episode> episodes);

	void moveAllPodcastEpidodesToInbox(String username);

	Page<InboxPodcastEpisode> getItems(String username, Pageable pageable);

	void deleteItem(String username, String episodeId);

	InboxPodcastEpisode getTopItem(String username);

	InboxPodcastEpisode getItem(String username, String episodeId);

	List<InboxPodcastEpisode> getItemsForSubscription(String username, Long subscriptionId);

}
