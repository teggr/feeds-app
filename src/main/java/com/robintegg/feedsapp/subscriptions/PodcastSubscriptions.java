package com.robintegg.feedsapp.subscriptions;

import com.robintegg.feedsapp.podcasts.Podcast;
import com.robintegg.feedsapp.podcasts.PodcastRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PodcastSubscriptions {

    private final PodcastRepository podcastRepository;
    private final SubscriptionRepository subscriptionRepository;

    @Transactional(readOnly = true)
    public List<PodcastSubscription> findAll() {
        List<PodcastSubscription> list = new ArrayList<>();
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        for (Subscription sub : subscriptions) {
            Podcast podcast = podcastRepository.findById(sub.getPodcastId()).orElse(null);
            if (podcast != null) {
                list.add(new PodcastSubscription(podcast, sub));
            }
        }
        return list;
    }
}
