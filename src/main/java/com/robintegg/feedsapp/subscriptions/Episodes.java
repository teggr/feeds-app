package com.robintegg.feedsapp.subscriptions;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class Episodes {

    private final SubscriptionRepository subscriptionRepository;

    public void notInterested(String episodeId) {

        Subscription subscription = subscriptionRepository
                .findBySubscriptionEpisodesEpisodeId(episodeId).orElseThrow();

        subscription.notInterested(episodeId);

        subscriptionRepository.save(subscription);

    }

    public void interested(String episodeId) {

        Subscription subscription = subscriptionRepository
                .findBySubscriptionEpisodesEpisodeId(episodeId).orElseThrow();

        subscription.interested(episodeId);

        subscriptionRepository.save(subscription);

    }

}
