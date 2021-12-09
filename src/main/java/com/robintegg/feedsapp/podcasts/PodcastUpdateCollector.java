package com.robintegg.feedsapp.podcasts;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PodcastUpdateCollector {

	private final AsyncPodcastUpdateCollector asyncPodcastUpdateCollector;

	public void getUpdatesForAllPodcasts() {

		log.info("get updates for all podcasts");

		asyncPodcastUpdateCollector.collect();

	}

	public void getUpdatesForPodcast(Long id) {

		log.info("get updates for podcast {}", id);

		asyncPodcastUpdateCollector.fetch(id);

	}

}
