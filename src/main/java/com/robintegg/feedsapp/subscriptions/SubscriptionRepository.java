package com.robintegg.feedsapp.subscriptions;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByPodcastId(Long podcastId);

}
