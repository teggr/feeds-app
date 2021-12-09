package com.robintegg.feedsapp.podcasts;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class NewPodcastRefreshListener implements ApplicationListener<NewPodcastEvent> {

	private final PodcastUpdateCollector podcastUpdateCollector;

	@Override
	public void onApplicationEvent(NewPodcastEvent event) {

		log.info("Refreshing Podcast feed for new podcast");

		podcastUpdateCollector.getUpdatesForPodcast(event.getPodcast().getId());

	}

}
