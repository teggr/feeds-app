package com.robintegg.feedsapp.inbox;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

interface InboxPodcastEpisodeRepository extends JpaRepository<InboxPodcastEpisodeEntity, Long> {

	Optional<InboxPodcastEpisodeEntity> findByUsernameAndEpisodeId(String username, String episodeId);

	List<InboxPodcastEpisodeEntity> findAllByUsernameAndStatus(String username, InboxPodcastEpisodeStatus interested);

	List<InboxPodcastEpisodeEntity> findAllByUsernameAndStatusIsNull(String username);

	List<InboxPodcastEpisodeEntity> findAllByUsernameAndReceivedDateTimeGreaterThan(String username,
			LocalDateTime receivedDateTime);

}
