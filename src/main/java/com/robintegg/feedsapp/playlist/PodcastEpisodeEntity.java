package com.robintegg.feedsapp.playlist;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "subscription_episodes")
@NoArgsConstructor
@Getter
@Setter
public class PodcastEpisodeEntity {

	public static PodcastEpisodeEntity forEpisode(Subscription subscription, Episode episode) {
		PodcastEpisodeEntity subscriptionEpisode = new PodcastEpisodeEntity();
		subscriptionEpisode.episodeId = episode.getId();
		subscriptionEpisode.subscriptionId = subscription.getId();
		return subscriptionEpisode;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "subscription_id")
	private Long subscriptionId;

	@Column(name = "episode_id")
	private String episodeId;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private PodcastEpisodeStatus status;

	public void notInterested() {
		status = PodcastEpisodeStatus.NOT_INTERESTED;
	}

	public boolean isInterested() {
		return status != PodcastEpisodeStatus.NOT_INTERESTED;
	}

	public void interested() {
		status = PodcastEpisodeStatus.INTERESTED;
	}

	public boolean hasNoStatus() {
		return status == null;
	}

}
