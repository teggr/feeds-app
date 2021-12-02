package com.robintegg.feedsapp.subscriptions;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

interface SubscriptionRepository extends JpaRepository<SubscriptionEntity, Long> {

	Optional<SubscriptionEntity> findByPodcastId(Long podcastId);

}
