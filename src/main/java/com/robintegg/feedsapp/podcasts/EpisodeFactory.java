package com.robintegg.feedsapp.podcasts;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.DigestUtils;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class EpisodeFactory {

	@SneakyThrows
	public static List<Episode> get(SyndFeed feed, Image podcastImage) throws MalformedURLException {

		log.debug("uri={},title={},link={}", feed.getUri(), feed.getTitle(), feed.getLink());

		List<Episode> episodes = new ArrayList<>();

		List<SyndEntry> entries = feed.getEntries();
		for (SyndEntry entry : entries) {

			String idAsUriHash = DigestUtils.md5DigestAsHex(entry.getUri().getBytes(StandardCharsets.UTF_8));

			log.debug("id={},uri={},title={},link={},published={}", idAsUriHash, entry.getUri(), entry.getTitle(),
					entry.getLink(), entry.getPublishedDate());

			if (entry.getPublishedDate() != null) {

				Audio audio = getAudio(entry);
				URL linkUrl = getLinkUrl(entry);
				ZonedDateTime publishedDate = getPublishedDate(entry);
				String title = entry.getTitle();
				Image image = podcastImage;
				String id = idAsUriHash;

				episodes.add(new Episode(id, title, linkUrl, publishedDate, image, audio));

			}

		}

		return episodes;
	}

	private static ZonedDateTime getPublishedDate(SyndEntry entry) {
		return entry.getPublishedDate().toInstant().atZone(ZoneId.systemDefault());
	}

	private static URL getLinkUrl(SyndEntry entry) throws MalformedURLException {
		URL linkUrl = null;
		if (entry.getLink() != null) {
			linkUrl = new URL(entry.getLink());
		}
		return linkUrl;
	}

	private static Audio getAudio(SyndEntry entry) throws MalformedURLException {
		Audio audio;
		if (entry.getEnclosures().isEmpty()) {
			log.info("NO ENCLOSURES");
			audio = new Audio(null, null);
		} else {
			audio = new Audio(new URL(entry.getEnclosures().get(0).getUrl()), entry.getEnclosures().get(0).getType());
		}
		return audio;
	}

}
