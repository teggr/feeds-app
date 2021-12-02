package com.robintegg.feedsapp.playlist;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

interface PodcastEpisodeRepository extends JpaRepository<PodcastEpisodeEntity, Long> {

	Optional<PodcastEpisodeEntity> findByEpisodeId(String episodeId);

	List<PodcastEpisodeEntity> findAllByStatus(PodcastEpisodeStatus interested);

	List<PodcastEpisodeEntity> findAllByStatusIsNull();

}
