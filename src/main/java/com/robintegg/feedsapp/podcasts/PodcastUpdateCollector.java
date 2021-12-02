package com.robintegg.feedsapp.podcasts;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PodcastUpdateCollector {

	private final PodcastDataService podcastDataService;
	private final PodcastRepository podcastRepository;
	private final ApplicationEventPublisher applicationEventPublisher;

	@Transactional(readOnly = false)
	public void collect() {

		log.info("starting to collect podcast updates");

		List<PodcastUpdateEvent> fetchAllResults = podcastRepository.findAll().stream().map(this::fetchIgnoreErrors)
				.collect(Collectors.toList());

		publishFetchResults(fetchAllResults);

	}

	private void publishFetchResults(List<PodcastUpdateEvent> fetchAllResults) {

		fetchAllResults.stream().forEach(
				e -> log.info("{} published {} new episodes", e.getPodcast().getFeedTitle(), e.getEpisodes().size()));

	}

	private PodcastUpdateEvent fetchIgnoreErrors(PodcastEntity podcast) {
		try {
			log.info("starting to fetch podcast {} {}", podcast.getId(), podcast.getFeedUrl());

			PodcastLatest podcastLatest = podcastDataService.getPodcastLatest(podcast.getFeedUrl(),
					podcast.getLastFetched());

			podcast.onFetch(podcastLatest);

			podcastRepository.save(podcast);

			log.info("publishing updates");

			PodcastUpdateEvent event = new PodcastUpdateEvent(this, Podcast.fromEntity(podcast),
					podcastLatest.getEpisodes());
			applicationEventPublisher.publishEvent(event);

			return event;
		} catch (Exception e) {
			log.error("failed to fetch podcast", e);
			return new PodcastUpdateEvent(this, Podcast.fromEntity(podcast), Collections.emptyList());
		}
	}

	@Transactional(readOnly = false)
	public void fetch(Long id) {

		PodcastEntity podcast = podcastRepository.findById(id).orElseThrow();

		log.info("refreshing podcast {}[{}]", podcast.getFeedTitle(), podcast.getFeedUrl());

		PodcastLatest podcastLatest = podcastDataService.getPodcastLatest(podcast.getFeedUrl(),
				podcast.getLastFetched());

		log.info("lastest podcast episodes {}", podcastLatest.getEpisodes().size());

		podcast.onFetch(podcastLatest);

		podcastRepository.save(podcast);

		log.info("publishing updates");

		PodcastUpdateEvent event = new PodcastUpdateEvent(this, Podcast.fromEntity(podcast),
				podcastLatest.getEpisodes());
		applicationEventPublisher.publishEvent(event);

	}

}
