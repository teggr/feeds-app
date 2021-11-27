package com.robintegg.feedsapp.podcasts;

import java.util.Collection;
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
public class PodcastFetchService {

	private final PodcastRepository podcastRepository;
	private final ApplicationEventPublisher applicationEventPublisher;

	@Transactional(readOnly = false)
	public void fetchAllIgnoringErrors() {

		log.info("starting to fetch podcasts, ignoring errors");

		List<PodcastUpdateEvent> fetchAllResults = podcastRepository.findAll().stream().map(this::fetchIgnoreErrors)
				.collect(Collectors.toList());

		publishFetchResults(fetchAllResults);

	}

	private void publishFetchResults(List<PodcastUpdateEvent> fetchAllResults) {

		fetchAllResults.stream().forEach(
				e -> log.info("{} published {} new episodes", e.getPodcast().getFeedTitle(), e.getEpisodes().size()));

	}

	private PodcastUpdateEvent fetchIgnoreErrors(Podcast podcast) {
		try {
			log.info("starting to fetch podcast {} {}", podcast.getId(), podcast.getFeedUrl());
			Collection<Episode> newEpisodes = podcast.fetch();
			podcastRepository.save(podcast);
			PodcastUpdateEvent event = new PodcastUpdateEvent(this, podcast, newEpisodes);
			applicationEventPublisher.publishEvent(event);
			return event;
		} catch (Exception e) {
			log.error("failed to fetch podcast", e);
			return new PodcastUpdateEvent(this, podcast, Collections.emptyList());
		}
	}

	@Transactional(readOnly = false)
	public void fetch(Long id) {

		Podcast podcast = podcastRepository.findById(id).orElseThrow();
		podcast.fetch();
		podcastRepository.save(podcast);

	}
}
