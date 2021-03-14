package com.robintegg.feedsapp.subscriptions;

import com.robintegg.feedsapp.podcasts.Episode;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "subscription_episodes")
@Getter
public class SubscriptionEpisode {

    public static SubscriptionEpisode forEpisode(Subscription subscription, Episode episode) {
        SubscriptionEpisode subscriptionEpisode = new SubscriptionEpisode();
        subscriptionEpisode.episodeId = episode.getId();
        subscriptionEpisode.subscription = subscription;
        return subscriptionEpisode;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subscription_id")
    private Subscription subscription;

    @Column(name = "episode_id")
    private String episodeId;

    protected SubscriptionEpisode() {
    } // for persistence

}
