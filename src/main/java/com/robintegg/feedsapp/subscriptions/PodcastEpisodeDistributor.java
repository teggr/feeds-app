package com.robintegg.feedsapp.subscriptions;

import java.time.ZonedDateTime;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.robintegg.feedsapp.playlist.UserInboxes;
import com.robintegg.feedsapp.podcasts.PodcastUpdateEvent;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PodcastEpisodeDistributor implements ApplicationListener<PodcastUpdateEvent> {

	private final SubscriptionRepository subscriptionRepository;
	private final UserInboxes userInboxes;

	@Override
	public void onApplicationEvent(PodcastUpdateEvent event) {

		SubscriptionEntity subscriptionEntity = subscriptionRepository.findByPodcastId(event.getPodcast().getId())
				.orElse(null);

		if (subscriptionEntity != null) {

			userInboxes.getUserInbox().put(Subscription.fromEntity(subscriptionEntity), event.getPodcast(),
					event.getEpisodes());

			subscriptionEntity.setLastUpdated(ZonedDateTime.now());

		}

	}

}
