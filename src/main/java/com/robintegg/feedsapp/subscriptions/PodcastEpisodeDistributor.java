package com.robintegg.feedsapp.subscriptions;

import java.time.ZonedDateTime;

import org.springframework.context.ApplicationListener;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.robintegg.feedsapp.inbox.UserInbox;
import com.robintegg.feedsapp.podcasts.NewPodcastEpisodesEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class PodcastEpisodeDistributor implements ApplicationListener<NewPodcastEpisodesEvent> {

	private final SubscriptionRepository subscriptionRepository;
	private final UserInbox userInbox;

	@Override
	public void onApplicationEvent(NewPodcastEpisodesEvent event) {

		log.info("new podcast data published for {}", event.getPodcast().getFeedTitle());

		SubscriptionEntity subscriptionEntity = subscriptionRepository.findByPodcastId(event.getPodcast().getId())
				.orElse(null);

		if (subscriptionEntity != null) {

			log.info("subscription {} for podcast found", subscriptionEntity.getId());

			// TODO: need to assign a user id / details to subscription
			User user = (User) User.withUsername("anon").build();

			userInbox.put(user, Subscription.fromEntity(subscriptionEntity), event.getPodcast(), event.getEpisodes());

			subscriptionEntity.setLastUpdated(ZonedDateTime.now());

		}

	}

}
