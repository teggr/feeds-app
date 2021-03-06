package com.robintegg.feedsapp.subscriptions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SubscriptionSetup {

    private final SubscriptionRepository subscriptionRepository;

    @Transactional(readOnly = false)
    public Subscription subscribeToPodcast(Long podcastId) {

        Subscription subscription = Subscription.forPodcast(podcastId);

        return subscriptionRepository.save(subscription);

    }

}
