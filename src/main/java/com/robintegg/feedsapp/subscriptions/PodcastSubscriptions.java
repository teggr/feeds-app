package com.robintegg.feedsapp.subscriptions;

import java.util.List;

public interface PodcastSubscriptions {

	void startSubscribingTo(Long podcastId);

	PodcastSubscription getByPodcast(Long podcastId);

	List<PodcastSubscription> findAll();

	void stopSubscription(Long subscriptionId);

}
