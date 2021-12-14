package com.robintegg.feedsapp.subscriptions;

import java.util.List;

import org.springframework.security.core.userdetails.User;

public interface PodcastSubscriptions {

	void startSubscribingTo(User user, Long podcastId);

	PodcastSubscription getByPodcast(User user, Long podcastId);

	List<PodcastSubscription> findAll(User user);

	void stopSubscription(User user, Long subscriptionId);

}
