package com.robintegg.feedsapp.inbox;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.robintegg.feedsapp.podcasts.Episode;
import com.robintegg.feedsapp.podcasts.Podcast;
import com.robintegg.feedsapp.subscriptions.Subscription;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "inbox_podcast_episodes")
@NoArgsConstructor
@Getter
@Setter
public class InboxPodcastEpisode {

	public static InboxPodcastEpisode forEpisode(String username, Subscription subscription, Podcast podcast,
			Episode episode) {

		InboxPodcastEpisode inboxEpisode = new InboxPodcastEpisode();
		inboxEpisode.episodeId = episode.getId();
		inboxEpisode.subscriptionId = subscription.getId();
		inboxEpisode.receivedDateTime = LocalDateTime.now();
		inboxEpisode.username = username;

		inboxEpisode.episodeTitle = episode.getTitle();
		inboxEpisode.episodeLinkUrl = episode.getLinkUrl();
		inboxEpisode.episodePublishedDate = episode.getPublishedDate();
		inboxEpisode.episodeImageUrl = episode.getImageUrl();
		inboxEpisode.episodeImageTitle = episode.getImageTitle();
		inboxEpisode.episodeAudioUrl = episode.getAudio().getUrl();
		inboxEpisode.episodeAudioType = episode.getAudio().getType();

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

	@Column(name = "episode_title")
	private String episodeTitle;

	@Column(name = "episode_link_url")
	private URL episodeLinkUrl;

	@Column(name = "episode_published_date")
	private ZonedDateTime episodePublishedDate;

	@Column(name = "episode_image_url")
	private URL episodeImageUrl;

	@Column(name = "episode_image_title")
	private String episodeImageTitle;

	@Column(name = "episode_audio_url")
	private URL episodeAudioUrl;

	@Column(name = "episode_audio_type")
	private String episodeAudioType;

}
