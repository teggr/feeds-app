package com.robintegg.feedsapp.playlist;

import java.util.Collection;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.robintegg.feedsapp.podcasts.Episode;
import com.robintegg.feedsapp.podcasts.Podcast;
import com.robintegg.feedsapp.subscriptions.Subscription;

import lombok.RequiredArgsConstructor;

@Component
@Transactional
@RequiredArgsConstructor
public class UserInboxService implements UserInbox {

	private final PodcastEpisodeRepository podcastEpisodeRepository;

	@Override
	public void put(Subscription subscription, Podcast podcast, Collection<Episode> episodes) {

		podcastEpisodeRepository.saveAll(episodes.stream().map(e -> PodcastEpisodeEntity.forEpisode(subscription, e))
				.collect(Collectors.toList()));

	}

}
