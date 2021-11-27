package com.robintegg.feedsapp.podcasts;

import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.util.StreamUtils;

import javax.persistence.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "podcasts")
@Getter
public class Podcast {

	public static Podcast forUrl(URL feedUrl) {
		Podcast podcast = new Podcast();
		podcast.feedUrl = feedUrl;
		return podcast;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "feed_url")
	private URL feedUrl;

	@Lob
	@Column(name = "feed_data")
	private String feedData;

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

	protected Podcast() {
	} // for persistence

	public List<Episode> getMostRecentEpisodes(int maxEpisodes) {

		if (feedData == null) {
			return Collections.emptyList();
		}

		return EpisodeFactory.get(feedData).stream().sorted(Episode::ORDER_BY_MOST_RECENT).limit(maxEpisodes)
				.collect(Collectors.toList());

	}

	@SneakyThrows
	public Collection<Episode> fetch() {

		feedData = StreamUtils.copyToString(feedUrl.openStream(), StandardCharsets.UTF_8);

		// update podcast information
		PodcastInfo podcastInfo = PodcastInfoFactory.from(feedData);

		feedTitle = podcastInfo.getTitle();
		feedLinkUrl = podcastInfo.getLinkUrl();
		feedImageUrl = podcastInfo.getImage().getUrl();
		feedImageTitle = podcastInfo.getImage().getTitle();

		Collection<Episode> newEpisodes = getEpisodesSince(lastFetched == null ? ZonedDateTime.now() : lastFetched);

		lastFetched = ZonedDateTime.now();

		return newEpisodes;

	}

	public Collection<Episode> getEpisodesSince(ZonedDateTime dateTime) {

		if (feedData == null) {
			return Collections.emptyList();
		}

		return EpisodeFactory.get(feedData).stream().filter(e -> e.isPublishedSince(dateTime))
				.collect(Collectors.toList());

	}

	public Collection<Episode> getEpisodes(Set<String> ids) {

		if (feedData == null) {
			return Collections.emptyList();
		}

		return EpisodeFactory.get(feedData).stream().filter(e -> ids.contains(e.getId())).collect(Collectors.toList());

	}

}