package com.robintegg.feedsapp.podcasts;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface PodcastRepository extends JpaRepository<PodcastEntity, Long> {

	@Query("select e from PodcastEntity p join p.episodes e where e.episodeId = :episodeId")
	Optional<EpisodeEntity> findEpisodeById(String episodeId);

}
