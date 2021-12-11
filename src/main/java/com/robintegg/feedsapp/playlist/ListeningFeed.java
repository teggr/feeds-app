package com.robintegg.feedsapp.playlist;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.robintegg.feedsapp.podcasts.Episode;
import com.robintegg.feedsapp.podcasts.Podcasts;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ListeningFeed {

	private final Podcasts podcasts;
	private final PodcastEpisodeRepository subscriptionEpisodesRepository;

	public List<Episode> fetch() {
		return subscriptionEpisodesRepository.findAllByStatus(PodcastEpisodeStatus.INTERESTED).stream()
				.map(this::toEpisode).filter(Objects::nonNull).sorted(Episode::ORDER_BY_MOST_RECENT)
				.collect(Collectors.toList());
	}

	private Episode toEpisode(PodcastEpisodeEntity se) {
		try {
			return podcasts.getEpisode(se.getEpisodeId());
		} catch (Exception e) {
			log.warn("could not load episode " + se.getEpisodeId());
			return null;
		}
	}

}
