package com.robintegg.feedsapp.podcasts;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
class PodcastFetchScheduler {

    private final PodcastFetchService podcastFetchService;

    @Scheduled(cron = "0 0 * * * *")
    public void fetchPodcasts() {
    	
    	log.info("Starting podcast fetch");

        podcastFetchService.fetchAllIgnoringErrors();
        
        log.info("Completed podcast fetch");

    }

}
