package com.robintegg.feedsapp.subscriptions;

import java.time.ZonedDateTime;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.robintegg.feedsapp.inbox.Inbox;
import com.robintegg.feedsapp.podcasts.NewPodcastEpisodesEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PodcastEpisodeDistributor implements ApplicationListener<NewPodcastEpisodesEvent> {

	private final SubscriptionRepository subscriptionRepository;
	private final Inbox userInbox;

	@Override
	public void onApplicationEvent(NewPodcastEpisodesEvent event) {

		log.info("New Podcast Episodes published for {}", event.getPodcast().getFeedTitle());

		SubscriptionEntity subscriptionEntity = subscriptionRepository.findByPodcastId(event.getPodcast().getId())
				.orElse(null);

		if (subscriptionEntity != null) {

			log.info("Podcast Subscription {} for found for User {}", subscriptionEntity.getId(),
					subscriptionEntity.getUsername());

			userInbox.put(subscriptionEntity.getUsername(), Subscription.fromEntity(subscriptionEntity),
					event.getPodcast(), event.getEpisodes());

			subscriptionEntity.setLastUpdated(ZonedDateTime.now());

		}

	}

}
