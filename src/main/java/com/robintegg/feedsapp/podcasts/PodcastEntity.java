package com.robintegg.feedsapp.podcasts;

import java.net.URL;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Entity
@Table(name = "podcasts")
@NoArgsConstructor
@Getter
@Setter
@Slf4j
class PodcastEntity {

	public static PodcastEntity forMetadata(PodcastFeedMetadata podcastMetadata) {
		PodcastEntity podcast = new PodcastEntity();
		podcast.feedUrl = podcastMetadata.getFeedUrl();
		podcast.feedTitle = podcastMetadata.getTitle();
		podcast.feedLinkUrl = podcastMetadata.getLinkUrl();
		podcast.feedImageUrl = podcastMetadata.getImage().getUrl();
		podcast.feedImageTitle = podcastMetadata.getImage().getTitle();
		podcast.lastFetched = ZonedDateTime.now().minusMonths(1);
		return podcast;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "feed_url")
	private URL feedUrl;

	@Column(name = "last_fetched")
	private ZonedDateTime lastFetched;

	// feed derived values
	@Column(name = "feed_title")
	private String feedTitle;

	@Column(name = "feed_link_url")
	private URL feedLinkUrl;

	@Column(name = "feed_image_url")
	private URL feedImageUrl;

	@Column(name = "feed_image_title")
	private String feedImageTitle;

	@OneToMany(mappedBy = "podcast", cascade = CascadeType.ALL)
	@OrderBy("publishedDate DESC")
	private Set<PodcastEpisodeEntity> episodes = new HashSet<>();

	public void onPodcastFeedUpdate(PodcastFeedUpdate podcastFeedUpdate) {

		PodcastFeedMetadata metadata = podcastFeedUpdate.getMetadata();

		feedTitle = metadata.getTitle();
		feedLinkUrl = metadata.getLinkUrl();
		feedImageUrl = metadata.getImage().getUrl();
		feedImageTitle = metadata.getImage().getTitle();

		saveEpisodes(podcastFeedUpdate.getEpisodes());

		lastFetched = podcastFeedUpdate.getTimestamp();

	}

	private void saveEpisodes(List<Episode> episodes) {

		episodes.stream().map(e -> PodcastEpisodeEntity.from(e)).forEach(ee -> saveEpisode(ee));

	}

	private void saveEpisode(PodcastEpisodeEntity ee) {
		// log.info("adding episode {} to {}", ee.getEpisodeId(), feedTitle);
		episodes.add(ee);
		ee.setPodcast(this);
	}

}