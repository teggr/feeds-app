package com.robintegg.feedsapp.podcasts;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
class NewPodcastEpisodesPublisher implements ApplicationListener<NewPodcastEvent> {

	private final PodcastEpisodePublisher podcastEpisodePublisher;

	@Override
	public void onApplicationEvent(NewPodcastEvent event) {

		log.info("New Podcast added. Publishing latest podcast episodes for {}", event.getPodcast().getId());

		podcastEpisodePublisher.publishLatestPodcastsEpisodesFor(event.getPodcast().getId());

	}

}
