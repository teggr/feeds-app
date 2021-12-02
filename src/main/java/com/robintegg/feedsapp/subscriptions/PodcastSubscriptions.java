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
@Transactional(readOnly = true)
@Slf4j
public class PodcastSubscriptions {

	private final Podcasts podcasts;
	private final SubscriptionRepository subscriptionRepository;

	public List<PodcastSubscription> findAll() {
		List<PodcastSubscription> list = new ArrayList<>();
		List<Subscription> subscriptions = subscriptionRepository.findAll();
		for (Subscription sub : subscriptions) {
			try {
				Podcast podcast = podcasts.get(sub.getPodcastId());
				if (podcast != null) {
					list.add(new PodcastSubscription(podcast, sub));
				}
			} catch (Exception e) {
				log.warn("could not get podcast {}", sub.getPodcastId());
			}
		}
		return list;
	}

	public Subscription getByPodcast(Long podcastId) {
		return subscriptionRepository.findByPodcastId(podcastId).orElse(null);
	}

}
