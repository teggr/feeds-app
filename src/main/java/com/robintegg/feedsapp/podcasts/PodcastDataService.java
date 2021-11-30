package com.robintegg.feedsapp.podcasts;

import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import com.rometools.modules.itunes.FeedInformation;
import com.rometools.rome.feed.module.Module;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;

import lombok.SneakyThrows;

/**
 * Responsible for gathering Podcast data and mapping into a standard data model
 *
 */
@Component
public class PodcastDataService {

	@SneakyThrows
	public PodcastLatest getPodcastLatest(URL feedUrl, ZonedDateTime since) {

		String feedData = StreamUtils.copyToString(feedUrl.openStream(), StandardCharsets.UTF_8);

		SyndFeedInput input = new SyndFeedInput();
		SyndFeed feed = input.build(new XmlReader(new ByteArrayInputStream(feedData.getBytes())));

		Image image = getImage(feed);
		String title = feed.getTitle();
		URL linkUrl = new URL(feed.getLink());

		List<Episode> episodes = EpisodeFactory.get(feed, image).stream().filter(e -> e.isPublishedSince(since))
				.collect(Collectors.toList());

		return PodcastLatest.builder().podcastMetadata(new PodcastMetadata(feedUrl, title, linkUrl, image))
				.episodes(episodes).build();

	}

	@SneakyThrows
	public PodcastMetadata getPodcastMetadata(URL feedUrl) {

		String feedData = StreamUtils.copyToString(feedUrl.openStream(), StandardCharsets.UTF_8);

		SyndFeedInput input = new SyndFeedInput();
		SyndFeed feed = input.build(new XmlReader(new ByteArrayInputStream(feedData.getBytes())));

		Image image = getImage(feed);
		String title = feed.getTitle();
		URL linkUrl = new URL(feed.getLink());

		return new PodcastMetadata(feedUrl, title, linkUrl, image);

	}

	private static Image getImage(SyndFeed feed) throws MalformedURLException {
		if (feed.getImage() != null) {
			return new Image(new URL(feed.getImage().getUrl()), feed.getImage().getTitle());
		} else {

			Module module = feed.getModule("http://www.itunes.com/dtds/podcast-1.0.dtd");

			if (module instanceof FeedInformation feedInformation) {

				if (feedInformation.getImage() != null) {

					return new Image(feedInformation.getImage(), "Feed Image");

				}

			}

			return new Image(new URL("https://bulma.io/images/placeholders/128x128.png"), "No Image");
		}
	}

}
