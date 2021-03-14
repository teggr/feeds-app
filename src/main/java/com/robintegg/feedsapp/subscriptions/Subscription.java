package com.robintegg.feedsapp.subscriptions;

import com.robintegg.feedsapp.podcasts.Episode;
import com.robintegg.feedsapp.podcasts.Podcast;
import lombok.Getter;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "subscriptions")
@Getter
public class Subscription {

    public static Subscription forPodcast(Long podcastId) {
        Subscription subscription = new Subscription();
        subscription.podcastId = podcastId;
        subscription.startDate = ZonedDateTime.now();
        return subscription;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "podcast_id", nullable = false)
    private Long podcastId;

    @Column(name = "start_date")
    private ZonedDateTime startDate;

    @Column(name = "last_updated")
    private ZonedDateTime lastUpdated = null;

    @OneToMany(mappedBy = "subscription", cascade = CascadeType.ALL)
    private List<SubscriptionEpisode> subscriptionEpisodes = new ArrayList<>();

    protected Subscription() {
    } // for persistence

    public Collection<Episode> updateEpisodes(Podcast podcast) {
        if (lastUpdated == null) {
            lastUpdated = startDate.minusMonths(1);
        }

        Collection<Episode> newEpisodes =
                podcast.getEpisodesSince(lastUpdated);

        newEpisodes.forEach(this::addEpisodeSubscription);

        Collection<Episode> allEpisodes = podcast.getEpisodes(getAllSubscriptionEpisodeIds());

        lastUpdated = ZonedDateTime.now();

        return allEpisodes;
    }

    private Set<String> getAllSubscriptionEpisodeIds() {
        return subscriptionEpisodes.stream()
                .map(SubscriptionEpisode::getEpisodeId)
                .collect(Collectors.toSet());
    }

    private void addEpisodeSubscription(Episode episode) {
        subscriptionEpisodes.add(SubscriptionEpisode.forEpisode(this, episode));
    }

}
