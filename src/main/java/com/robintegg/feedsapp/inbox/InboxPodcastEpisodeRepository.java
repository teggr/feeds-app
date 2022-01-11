package com.robintegg.feedsapp.inbox;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

interface InboxPodcastEpisodeRepository extends JpaRepository<InboxPodcastEpisode, Long> {

	Optional<InboxPodcastEpisode> findByUsernameAndEpisodeId(String username, String episodeId);

	List<InboxPodcastEpisode> findAllByUsernameAndReceivedDateTimeGreaterThan(String username,
			LocalDateTime receivedDateTime);

	List<InboxPodcastEpisode> findAllByUsernameAndSubscriptionId(String username, Long subscriptionId);

	Page<InboxPodcastEpisode> findAllByUsernameAndSaved(String username, boolean saved, Pageable pageable);

}
