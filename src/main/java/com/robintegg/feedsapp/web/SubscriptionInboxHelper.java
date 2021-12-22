package com.robintegg.feedsapp.web;

import java.util.List;
import java.util.Optional;

import com.robintegg.feedsapp.inbox.Inbox;
import com.robintegg.feedsapp.inbox.InboxPodcastEpisode;
import com.robintegg.feedsapp.subscriptions.Subscription;

class SubscriptionInboxHelper {

	private List<InboxPodcastEpisode> itemsForSubscription;

	private SubscriptionInboxHelper(List<InboxPodcastEpisode> itemsForSubscription) {
		this.itemsForSubscription = itemsForSubscription;
	}

	public boolean canAdd(String episodeId) {
		return findEpisode(episodeId).map(e -> false).orElse(true);

	}

	private Optional<InboxPodcastEpisode> findEpisode(String episodeId) {
		return itemsForSubscription.stream().filter(e -> e.getEpisodeId().equals(episodeId)).findFirst();
	}

	public static SubscriptionInboxHelper forSubscription(String username, Subscription subscription, Inbox inbox) {
		return new SubscriptionInboxHelper(inbox.getItemsForSubscription(username, subscription.getId()));
	}

}
