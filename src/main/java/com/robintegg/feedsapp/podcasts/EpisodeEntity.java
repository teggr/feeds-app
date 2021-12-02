package com.robintegg.feedsapp.podcasts;

import java.net.URL;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "episodes")
@NoArgsConstructor
@Getter
@Setter
class EpisodeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "episode_id")
	private String episodeId;

	@Column(name = "title")
	private String title;

	@Column(name = "link_url")
	private URL linkUrl;

	@Column(name = "published_date")
	private ZonedDateTime publishedDate;

	@Column(name = "image_url")
	private URL imageUrl;

	@Column(name = "image_title")
	private String imageTitle;

	@Column(name = "audio_url")
	private URL audioUrl;

	@Column(name = "audio_type")
	private String audioType;

	@ManyToOne
	@JoinColumn(name = "podcast_id")
	private PodcastEntity podcast;

	public boolean isPublishedSince(ZonedDateTime dateTime) {
		return publishedDate.isAfter(dateTime);
	}

	public static EpisodeEntity from(Episode e) {
		EpisodeEntity entity = new EpisodeEntity();
		entity.episodeId = e.getId();
		entity.title = e.getTitle();
		entity.linkUrl = e.getLinkUrl();
		entity.publishedDate = e.getPublishedDate();
		entity.imageUrl = e.getImage().getUrl();
		entity.imageTitle = e.getImage().getTitle();
		entity.audioUrl = e.getAudio().getUrl();
		entity.audioType = e.getAudio().getType();
		return entity;
	}

	public static Episode to(EpisodeEntity entity) {
		return new Episode(entity.episodeId, entity.title, entity.linkUrl, entity.publishedDate,
				new Image(entity.imageUrl, entity.imageTitle), new Audio(entity.audioUrl, entity.audioType));
	}

}
