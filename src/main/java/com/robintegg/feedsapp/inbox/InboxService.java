package com.robintegg.feedsapp.inbox;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Component;

import com.robintegg.feedsapp.podcasts.Episode;
import com.robintegg.feedsapp.podcasts.Podcast;
import com.robintegg.feedsapp.podcasts.Podcasts;
import com.robintegg.feedsapp.subscriptions.Subscription;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Transactional
@RequiredArgsConstructor
@Slf4j
class InboxService implements Inbox {

	private final Podcasts podcasts;
	private final InboxPodcastEpisodeRepository podcastEpisodeRepository;

	@Override
	public void put(String username, Subscription subscription, Podcast podcast, Collection<Episode> episodes) {

		log.info("{} new Episodes for Podcast {} published through Subscription {} for User {}", episodes.size(),
				podcast.getFeedTitle(), subscription.getId(), username);

		podcastEpisodeRepository
				.saveAll(episodes.stream().map(e -> InboxPodcastEpisodeEntity.forEpisode(username, subscription, e))
						.collect(Collectors.toList()));

	}

	@Override
	public List<Episode> findAllPodcasts(String username, InboxPodcastEpisodeStatus status,
			Comparator<Episode> sortBy) {

		List<InboxPodcastEpisodeEntity> findAllByStatus;
		if (status == null) {
			findAllByStatus = podcastEpisodeRepository.findAllByUsernameAndStatusIsNull(username);
		} else {
			findAllByStatus = podcastEpisodeRepository.findAllByUsernameAndStatus(username, status);
		}
		return findAllByStatus.stream().map(this::toEpisode).filter(Objects::nonNull).sorted(sortBy)
				.collect(Collectors.toList());

	}

	private Episode toEpisode(InboxPodcastEpisodeEntity se) {
		try {
			return podcasts.getEpisode(se.getEpisodeId());
		} catch (Exception e) {
			log.warn("could not load episode " + se.getEpisodeId(), e);
			return null;
		}
	}

}
