package com.robintegg.feedsapp.subscriptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.robintegg.feedsapp.podcasts.Episode;
import com.robintegg.feedsapp.podcasts.Podcast;
import com.robintegg.feedsapp.podcasts.Podcasts;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class NewReleases {

	private final Podcasts podcasts;
	private final SubscriptionRepository subscriptionRepository;

	public List<Episode> fetch() {

		List<Episode> list = new ArrayList<>();
		List<Subscription> subscriptions = subscriptionRepository.findAll();
		for (Subscription sub : subscriptions) {
			try {
				Podcast podcast = podcasts.get(sub.getPodcastId());
				if (podcast != null) {
					list.addAll(sub.updateEpisodes(podcast));
					sub = subscriptionRepository.save(sub);
				}
			} catch (Exception e) {
				log.warn("could not get podcast {}", sub.getPodcastId());
			}
		}
		list.sort(Episode::ORDER_BY_MOST_RECENT);
		return list;

	}

}
