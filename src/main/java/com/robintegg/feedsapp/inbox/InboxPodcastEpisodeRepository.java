package com.robintegg.feedsapp.inbox;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

interface InboxPodcastEpisodeRepository extends JpaRepository<InboxPodcastEpisode, Long> {

	Optional<InboxPodcastEpisode> findByUsernameAndEpisodeId(String username, String episodeId);

	List<InboxPodcastEpisode> findAllByUsernameAndIgnored(String username, boolean ignored);

	List<InboxPodcastEpisode> findAllByUsernameAndReceivedDateTimeGreaterThan(String username,
			LocalDateTime receivedDateTime);

	Page<InboxPodcastEpisode> findAllByUsernameAndIgnored(String username, boolean ignored, Pageable pageable);

	List<InboxPodcastEpisode> findAllByUsernameAndSubscriptionId(String username, Long subscriptionId);

}
