package com.robintegg.feedsapp.podcasts;

import org.springframework.data.jpa.repository.JpaRepository;

interface PodcastRepository extends JpaRepository<PodcastEntity, Long> {
}
