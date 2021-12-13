package com.robintegg.feedsapp.inbox;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class Episodes {

	private final PodcastEpisodeRepository subscriptionEpisodesRepository;

	public void notInterested(String episodeId) {

		PodcastEpisodeEntity subscription = subscriptionEpisodesRepository.findByEpisodeId(episodeId).orElseThrow();

		subscription.notInterested();

		subscriptionEpisodesRepository.save(subscription);

	}

	public void interested(String episodeId) {

		PodcastEpisodeEntity subscription = subscriptionEpisodesRepository.findByEpisodeId(episodeId).orElseThrow();

		subscription.interested();

		subscriptionEpisodesRepository.save(subscription);

	}

}
