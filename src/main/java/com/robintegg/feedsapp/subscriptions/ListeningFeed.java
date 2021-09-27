package com.robintegg.feedsapp.subscriptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.robintegg.feedsapp.podcasts.Episode;
import com.robintegg.feedsapp.podcasts.Podcast;
import com.robintegg.feedsapp.podcasts.PodcastRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ListeningFeed {

	private final PodcastRepository podcastRepository;
	private final SubscriptionRepository subscriptionRepository;

	public List<Episode> fetch() {

		List<Episode> list = new ArrayList<>();
		List<Subscription> subscriptions = subscriptionRepository.findAll();
		for (Subscription sub : subscriptions) {
			Podcast podcast = podcastRepository.findById(sub.getPodcastId()).orElse(null);
			if (podcast != null) {
				list.addAll(sub.getInterestedEpisodes(podcast));
			}
		}
		list.sort(Episode::ORDER_BY_MOST_RECENT);
		return list;

	}

}
