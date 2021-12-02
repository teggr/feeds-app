package com.robintegg.feedsapp.podcasts;

import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class PodcastsService implements Podcasts {

	private final PodcastDataService podcastDataService;
	private final PodcastRepository podcastRepository;

	@Override
	public void addPodcastWithUrl(URL feedUrl) {

		PodcastMetadata podcastMetadata = podcastDataService.getPodcastMetadata(feedUrl);

		PodcastEntity podcast = PodcastEntity.forMetadata(podcastMetadata);
		podcastRepository.save(podcast);

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
		return podcastRepository.findEpisodeById(episodeId).map(EpisodeEntity::to).orElseThrow();
	}

}
