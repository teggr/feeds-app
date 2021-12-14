package com.robintegg.feedsapp.podcasts;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PodcastEpisodePublisher {

	private final AsyncPodcastEpisodePublisher asyncPodcastEpisodePublisher;

	public void publishAllLatestPodcastEpisodes() {

		log.info("Publishing all latest podcast episodes");

		asyncPodcastEpisodePublisher.asyncPublishAllLatestPodcastEpisodes();

	}

	public void publishLatestPodcastsEpisodesFor(Long id) {

		log.info("Publishing latest podcast episodes for {}", id);

		asyncPodcastEpisodePublisher.asyncPublishLatestPodcastsEpisodesFor(id);

	}

}
