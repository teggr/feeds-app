package com.robintegg.feedsapp.podcasts;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
class ScheduledPodcastEpisodePublisher {

	private final PodcastEpisodePublisher podcastEpisodePublisher;

	@Scheduled(cron = "0 0 0 * * *")
	public void publishPodcastEpisodes() {

		log.info("Starting scheduled Podcast Episode publish");

		podcastEpisodePublisher.publishAllLatestPodcastEpisodes();

		log.info("Completed scheduled Podcast Episode publish");

	}

}
