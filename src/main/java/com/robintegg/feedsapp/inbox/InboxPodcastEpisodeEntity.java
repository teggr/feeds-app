package com.robintegg.feedsapp.inbox;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.robintegg.feedsapp.podcasts.Episode;
import com.robintegg.feedsapp.subscriptions.Subscription;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "inbox_podcast_episodes")
@NoArgsConstructor
@Getter
@Setter
public class InboxPodcastEpisodeEntity {

	public static InboxPodcastEpisodeEntity forEpisode(String username, Subscription subscription, Episode episode) {
		InboxPodcastEpisodeEntity inboxEpisode = new InboxPodcastEpisodeEntity();
		inboxEpisode.episodeId = episode.getId();
		inboxEpisode.subscriptionId = subscription.getId();
		inboxEpisode.receivedDateTime = LocalDateTime.now();
		inboxEpisode.username = username;
		return inboxEpisode;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "subscription_id")
	private Long subscriptionId;

	@Column(name = "episode_id")
	private String episodeId;

	@Column(name = "username")
	private String username;

	@Column(name = "ignored")
	private boolean ignored = false;

	@Column(name = "received_date_time")
	private LocalDateTime receivedDateTime;

}
