package com.robintegg.feedsapp.inbox;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.robintegg.feedsapp.podcasts.Episode;
import com.robintegg.feedsapp.podcasts.Podcast;
import com.robintegg.feedsapp.subscriptions.Subscription;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Transactional
@RequiredArgsConstructor
@Slf4j
class InboxService implements Inbox {

	private final InboxPodcastEpisodeRepository podcastEpisodeRepository;

	@Override
	public void put(String username, Subscription subscription, Podcast podcast, Collection<Episode> episodes) {

		log.info("{} new Episodes for Podcast {} published through Subscription {} for User {}", episodes.size(),
				podcast.getFeedTitle(), subscription != null ? subscription.getId() : null, username);

		podcastEpisodeRepository
				.saveAll(episodes.stream().map(e -> InboxPodcastEpisode.forEpisode(username, subscription, podcast, e))
						.collect(Collectors.toList()));

	}

	@Override
	public void moveAllPodcastEpidodesToInbox(String username) {

		List<InboxPodcastEpisode> podcastEpisodes = podcastEpisodeRepository.findAll();
		podcastEpisodes.forEach(pe -> pe.setUsername(username));
		podcastEpisodeRepository.saveAll(podcastEpisodes);

	}

	@Override
	public Page<InboxPodcastEpisode> getItems(String username, boolean saved, Pageable pageable) {

		return podcastEpisodeRepository.findAllByUsernameAndSaved(username, saved, pageable);

	}

	@Override
	public void deleteItem(String username, String episodeId) {

		InboxPodcastEpisode episode = podcastEpisodeRepository.findByUsernameAndEpisodeId(username, episodeId)
				.orElseThrow();

		podcastEpisodeRepository.delete(episode);

	}

	@Override
	public InboxPodcastEpisode getItem(String username, String episodeId) {

		return podcastEpisodeRepository.findByUsernameAndEpisodeId(username, episodeId).orElseThrow();

	}

	@Override
	public InboxPodcastEpisode getTopItem(String username) {

		return podcastEpisodeRepository.findAll().stream().findFirst().orElseThrow();

	}

	@Override
	public List<InboxPodcastEpisode> getItemsForSubscription(String username, Long subscriptionId) {
		return podcastEpisodeRepository.findAllByUsernameAndSubscriptionId(username, subscriptionId);
	}

	@Override
	public void saveItem(String episodeId, String username) {

		InboxPodcastEpisode episode = podcastEpisodeRepository.findByUsernameAndEpisodeId(username, episodeId)
				.orElseThrow();

		episode.setSaved(true);

		podcastEpisodeRepository.save(episode);

	}

}
