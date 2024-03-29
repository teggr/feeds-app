package com.robintegg.feedsapp.podcasts;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

interface PodcastRepository extends JpaRepository<PodcastEntity, Long> {

	@Query("select e from PodcastEntity p join p.episodes e where e.episodeId = :episodeId")
	Optional<PodcastEpisodeEntity> findEpisodeById(String episodeId);

	@Query("select e from PodcastEntity p join p.episodes e where p.id = :id")
	Page<PodcastEpisodeEntity> findAllEpisodesById(Long id, Pageable pageable);

}
