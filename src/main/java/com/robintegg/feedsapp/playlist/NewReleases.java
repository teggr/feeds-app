package com.robintegg.feedsapp.playlist;

import java.util.List;
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
public class NewReleases {

	private final Podcasts podcasts;
	private final PodcastEpisodeRepository subscriptionEpisodesRepository;

	public List<Episode> fetch() {

		return subscriptionEpisodesRepository.findAllByStatusIsNull().stream().map(this::toEpisode)
				.sorted(Episode::ORDER_BY_MOST_RECENT).collect(Collectors.toList());
	}

	private Episode toEpisode(PodcastEpisodeEntity se) {
		return podcasts.getEpisode(se.getEpisodeId());
	}

}
