package com.robintegg.feedsapp.podcasts;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PodcastFetchService {

    private final PodcastRepository podcastRepository;

    @Transactional(readOnly = false)
    public void fetchAllIgnoringErrors() {

        log.info("starting to fetch podcasts, ignoring errors");

        podcastRepository.findAll()
                .stream()
                .forEach(this::fetchIgnoreErrors);
        
    }

    private void fetchIgnoreErrors(Podcast podcast) {
        try {
            log.info("starting to fetch podcast {} {}", podcast.getId(), podcast.getFeedUrl());
            podcast.fetch();
            podcastRepository.save(podcast);
        } catch (Exception e) {
            log.error("failed to fetch podcast", e);
        }
    }

    @Transactional(readOnly = false)
    public void fetch(Long id) {

        Podcast podcast = podcastRepository.findById(id).orElseThrow();
        podcast.fetch();
        podcastRepository.save(podcast);

    }
}
