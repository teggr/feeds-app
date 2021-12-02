package com.robintegg.feedsapp.subscriptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.robintegg.feedsapp.podcasts.Podcast;
import com.robintegg.feedsapp.podcasts.Podcasts;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
class PodcastSubscriptionsService implements PodcastSubscriptions {

	private final Podcasts podcasts;
	private final SubscriptionRepository subscriptionRepository;

	@Override
	public List<PodcastSubscription> findAll() {
		List<PodcastSubscription> list = new ArrayList<>();
		List<SubscriptionEntity> subscriptions = subscriptionRepository.findAll();
		for (SubscriptionEntity sub : subscriptions) {
			try {
				Podcast podcast = podcasts.get(sub.getPodcastId());
				if (podcast != null) {
					list.add(new PodcastSubscription(podcast, Subscription.fromEntity(sub)));
				}
			} catch (Exception e) {
				log.warn("could not get podcast {}", sub.getPodcastId());
			}
		}
		return list;
	}

	@Override
	public PodcastSubscription getByPodcast(Long podcastId) {
		SubscriptionEntity sub = subscriptionRepository.findByPodcastId(podcastId).orElseThrow();
		try {
			Podcast podcast = podcasts.get(sub.getPodcastId());
			if (podcast != null) {
				return new PodcastSubscription(podcast, Subscription.fromEntity(sub));
			}
		} catch (Exception e) {
			log.warn("could not get podcast {}", sub.getPodcastId());

		}
		return null;
	}

	@Override
	public void startSubscribingTo(Long podcastId) {
		SubscriptionEntity subscription = SubscriptionEntity.forPodcast(podcastId);

		subscriptionRepository.save(subscription);
	}

	@Override
	public void stopSubscription(Long subscriptionId) {
		subscriptionRepository.deleteById(subscriptionId);
	}

}
