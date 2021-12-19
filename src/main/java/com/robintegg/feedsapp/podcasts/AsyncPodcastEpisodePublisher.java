package com.robintegg.feedsapp.podcasts;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Transactional
@Slf4j
@RequiredArgsConstructor
class AsyncPodcastEpisodePublisher {

	private final PodcastDataService podcastDataService;
	private final PodcastRepository podcastRepository;
	private final ApplicationEventPublisher applicationEventPublisher;

	@Async
	public void asyncPublishAllLatestPodcastEpisodes() {

		log.info("Async publishing all latest podcast episodes");

		List<PodcastEntity> podcasts = podcastRepository.findAll();

		publishPodcastEpisodes(podcasts);

	}

	@Async
	public void asyncPublishLatestPodcastsEpisodesFor(Long id) {

		log.info("Async publishing latest podcast episodes for {}", id);

		PodcastEntity podcast = podcastRepository.findById(id).orElseThrow();

		publishPodcastEpisodes(List.of(podcast));

	}

	private void publishPodcastEpisodes(List<PodcastEntity> podcasts) {

		long startTime = System.currentTimeMillis();

		List<NewPodcastEpisodesEvent> allNewPodcastEpisodeEvents = podcasts.stream().map(this::publishLatestEpisodes)
				.collect(Collectors.toList());

		long endTime = System.currentTimeMillis();

		publishFetchResults(allNewPodcastEpisodeEvents, endTime - startTime);

	}

	private void publishFetchResults(List<NewPodcastEpisodesEvent> events, long timeTakenMs) {

		events.stream().forEach(
				e -> log.info("{} published {} new episodes", e.getPodcast().getFeedTitle(), e.getEpisodes().size()));

		PodcastEpisodesPublishedEvent event = new PodcastEpisodesPublishedEvent(this, events, timeTakenMs);
		applicationEventPublisher.publishEvent(event);

	}

	private NewPodcastEpisodesEvent publishLatestEpisodes(PodcastEntity podcast) {

		try {

			log.info("starting to fetch podcast {} {}", podcast.getId(), podcast.getFeedUrl());

			PodcastFeedUpdate podcastFeedUpdate = podcastDataService.getPodcastFeedUpdate(podcast.getFeedUrl(),
					podcast.getLastFetched());

			podcast.onPodcastFeedUpdate(podcastFeedUpdate);

			podcast = podcastRepository.save(podcast);

			log.info("publishing updates");

			NewPodcastEpisodesEvent event = new NewPodcastEpisodesEvent(this, Podcast.fromEntity(podcast),
					podcastFeedUpdate.getEpisodes());
			applicationEventPublisher.publishEvent(event);

			return event;
		} catch (Exception e) {
			log.error("failed to fetch podcast", e);
			return new NewPodcastEpisodesEvent(this, Podcast.fromEntity(podcast), Collections.emptyList());
		}

	}

}
