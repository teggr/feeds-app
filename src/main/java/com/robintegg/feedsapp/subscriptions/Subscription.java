package com.robintegg.feedsapp.subscriptions;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "subscriptions")
@Getter
public class Subscription {

    public static Subscription forPodcast(Long podcastId) {
        Subscription subscription = new Subscription();
        subscription.podcastId = podcastId;
        return subscription;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "podcast_id", nullable = false)
    private Long podcastId;

    protected Subscription() {
    } // for persistence

}
