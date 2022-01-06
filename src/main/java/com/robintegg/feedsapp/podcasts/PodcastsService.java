package com.robintegg.feedsapp.podcasts;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class PodcastsService implements Podcasts {

	private final PodcastDataService podcastDataService;
	private final PodcastRepository podcastRepository;
	private final ApplicationEventPublisher applicationEventPublisher;

	@Override
	public void addPodcastWithUrl(URL feedUrl) {

		PodcastFeedMetadata podcastMetadata = podcastDataService.getPodcastFeedMetadata(feedUrl);

		PodcastEntity podcast = PodcastEntity.forMetadata(podcastMetadata);
		podcast = podcastRepository.save(podcast);

		applicationEventPublisher.publishEvent(new NewPodcastEvent(this, Podcast.fromEntity(podcast)));

	}

	@Override
	public void removePodcast(Long id) {
		podcastRepository.deleteById(id);
	}

	@Override
	public Podcast get(Long id) {
		return podcastRepository.findById(id).map(Podcast::fromEntity).orElseThrow();
	}

	@Override
	public List<Podcast> getAll() {
		return podcastRepository.findAll().stream().map(Podcast::fromEntity).collect(Collectors.toList());
	}

	@Override
	public Episode getEpisode(String episodeId) {
		return podcastRepository.findEpisodeById(episodeId).map(PodcastEpisodeEntity::to).orElseThrow();
	}

	@Override
	public Podcast getPodcastForEpisode(String episodeId) {
		return Podcast.fromEntity(podcastRepository.findEpisodeById(episodeId).get().getPodcast());
	}

	@Override
	public Page<Episode> getEpisodes(Long id, Pageable pageable) {
		return podcastRepository.findAllEpisodesById(id, pageable).map(PodcastEpisodeEntity::to);
	}

}
