package com.robintegg.feedsapp.podcasts;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
class PodcastUpdateCollectionScheduler {

    private final PodcastUpdateCollector podcastUpdateCollector;

    @Scheduled(cron = "0 0 0 * * *")
    public void fetchPodcasts() {
    	
    	log.info("Starting podcast update collection");

        podcastUpdateCollector.collect();
        
        log.info("Completed podcast update collection");

    }

}
