package com.robintegg.feedsapp.inbox;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class Episodes {

	private final PodcastEpisodeRepository podcastEpisodeRepository;

	public void notInterested(User user, String episodeId) {

		PodcastEpisodeEntity episode = podcastEpisodeRepository.findByEpisodeId(episodeId).orElseThrow();

		episode.notInterested();

		podcastEpisodeRepository.save(episode);

	}

	public void interested(User user, String episodeId) {

		PodcastEpisodeEntity episode = podcastEpisodeRepository.findByEpisodeId(episodeId).orElseThrow();

		episode.interested();

		podcastEpisodeRepository.save(episode);

	}

}
