package com.robintegg.feedsapp.subscriptions;

import java.time.ZonedDateTime;

import org.springframework.context.ApplicationListener;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.robintegg.feedsapp.playlist.UserInboxes;
import com.robintegg.feedsapp.podcasts.PodcastUpdateEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PodcastEpisodeDistributor implements ApplicationListener<PodcastUpdateEvent> {

	private final SubscriptionRepository subscriptionRepository;
	private final UserInboxes userInboxes;

	@Override
	public void onApplicationEvent(PodcastUpdateEvent event) {

		log.info("new podcast data published for {}", event.getPodcast().getFeedTitle());

		SubscriptionEntity subscriptionEntity = subscriptionRepository.findByPodcastId(event.getPodcast().getId())
				.orElse(null);

		if (subscriptionEntity != null) {

			log.info("subscription {} for podcast found", subscriptionEntity.getId());

			// TODO: need to assign a user id / details to subscription
			User user = (User) User.withUsername("anon").build();

			userInboxes.getUserInbox(user).put(Subscription.fromEntity(subscriptionEntity), event.getPodcast(),
					event.getEpisodes());

			subscriptionEntity.setLastUpdated(ZonedDateTime.now());

		}

	}

}
