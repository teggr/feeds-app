package com.robintegg.feedsapp.inbox;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

interface InboxPodcastEpisodeRepository extends JpaRepository<InboxPodcastEpisodeEntity, Long> {

	Optional<InboxPodcastEpisodeEntity> findByUsernameAndEpisodeId(String username, String episodeId);

	List<InboxPodcastEpisodeEntity> findAllByUsernameAndIgnored(String username, boolean ignored);

	List<InboxPodcastEpisodeEntity> findAllByUsernameAndReceivedDateTimeGreaterThan(String username,
			LocalDateTime receivedDateTime);

	Page<InboxPodcastEpisodeEntity> findAllByUsernameAndIgnored(String username, boolean ignored, Pageable pageable);

}
