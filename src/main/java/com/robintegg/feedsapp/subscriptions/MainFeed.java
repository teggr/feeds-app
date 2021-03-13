package com.robintegg.feedsapp.subscriptions;

import com.robintegg.feedsapp.podcasts.Episode;
import com.robintegg.feedsapp.podcasts.Podcast;
import com.robintegg.feedsapp.podcasts.PodcastRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MainFeed {

    private final PodcastRepository podcastRepository;
    private final SubscriptionRepository subscriptionRepository;

    public List<Episode> get() {

        List<Episode> list = new ArrayList<>();
        List<Subscription> subscriptions = subscriptionRepository.findAll();
        for (Subscription sub : subscriptions) {
            Podcast podcast = podcastRepository.findById(sub.getPodcastId()).orElse(null);
            if (podcast != null) {
                list.addAll(podcast.getMostRecentEpisodes(3));
            }
        }
        list.sort(Episode::ORDER_BY_MOST_RECENT);
        return list;

    }

}
