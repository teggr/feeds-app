package com.robintegg.feedsapp.web;

import com.robintegg.feedsapp.subscriptions.PodcastSubscription;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SubscriptionHelper {

    private final Map<Long, Long> subscriptionsByPodcast;

    public SubscriptionHelper(List<PodcastSubscription> subscriptions) {
        subscriptionsByPodcast = subscriptions.stream()
                .collect(Collectors.toMap(
                        s -> s.getPodcast().getId(),
                        s -> s.getSubscription().getId()));
    }

    public boolean canSubscribe(Long podcastId) {
        return !subscriptionsByPodcast.containsKey(podcastId);
    }

}
