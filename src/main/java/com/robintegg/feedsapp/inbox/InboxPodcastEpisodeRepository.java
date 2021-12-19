package com.robintegg.feedsapp.inbox;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

interface InboxPodcastEpisodeRepository extends JpaRepository<InboxPodcastEpisodeEntity, Long> {

	Optional<InboxPodcastEpisodeEntity> findByUsernameAndEpisodeId(String username, String episodeId);

	List<InboxPodcastEpisodeEntity> findAllByUsernameAndStatus(String username, InboxPodcastEpisodeStatus interested);

	List<InboxPodcastEpisodeEntity> findAllByUsernameAndStatusIsNull(String username);

	List<InboxPodcastEpisodeEntity> findAllByUsernameAndReceivedDateTimeGreaterThan(String username,
			LocalDateTime receivedDateTime);

	Page<InboxPodcastEpisodeEntity> findAllByUsernameAndStatusIsNull(String username, Pageable pageable);

	Page<InboxPodcastEpisodeEntity> findAllByUsernameAndStatus(String username, InboxPodcastEpisodeStatus status,
			Pageable pageable);

}
