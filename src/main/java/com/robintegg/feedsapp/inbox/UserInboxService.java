package com.robintegg.feedsapp.inbox;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.security.core.userdetails.User;
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
class UserInboxService implements UserInbox {

	private final Podcasts podcasts;
	private final PodcastEpisodeRepository podcastEpisodeRepository;

	@Override
	public void put(User user, Subscription subscription, Podcast podcast, Collection<Episode> episodes) {

		log.info("subscription {} sent {} new episodes for podcast {}", subscription.getId(), episodes.size(),
				podcast.getFeedTitle());

		podcastEpisodeRepository.saveAll(episodes.stream().map(e -> PodcastEpisodeEntity.forEpisode(subscription, e))
				.collect(Collectors.toList()));

	}

	@Override
	public List<Episode> findAllPodcasts(User user, PodcastEpisodeStatus status, Comparator<Episode> sortBy) {

		List<PodcastEpisodeEntity> findAllByStatus;
		if (status == null) {
			findAllByStatus = podcastEpisodeRepository.findAllByStatusIsNull();
		} else {
			findAllByStatus = podcastEpisodeRepository.findAllByStatus(status);
		}
		return findAllByStatus.stream().map(this::toEpisode).filter(Objects::nonNull).sorted(sortBy)
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
