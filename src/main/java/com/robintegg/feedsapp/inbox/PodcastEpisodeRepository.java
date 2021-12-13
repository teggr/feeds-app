package com.robintegg.feedsapp.inbox;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

interface PodcastEpisodeRepository extends JpaRepository<PodcastEpisodeEntity, Long> {

	Optional<PodcastEpisodeEntity> findByEpisodeId(String episodeId);

	List<PodcastEpisodeEntity> findAllByStatus(PodcastEpisodeStatus interested);

	List<PodcastEpisodeEntity> findAllByStatusIsNull();

	List<PodcastEpisodeEntity> findAllByReceivedDateTimeGreaterThan(LocalDateTime receivedDateTime);

}
