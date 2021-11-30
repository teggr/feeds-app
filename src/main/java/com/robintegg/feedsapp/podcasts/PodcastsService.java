package com.robintegg.feedsapp.podcasts;

import java.net.URL;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PodcastsService {
	
	private final PodcastDataService podcastDataService;
	private final PodcastRepository podcastRepository;
	
	public void addPodcastWithUrl(URL feedUrl) {
	
		PodcastMetadata podcastMetadata = podcastDataService.getPodcastMetadata(feedUrl);
		
		Podcast podcast = Podcast.forMetadata(podcastMetadata);
		podcastRepository.save(podcast);
		
	}

}
