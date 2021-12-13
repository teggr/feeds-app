package com.robintegg.feedsapp.podcasts;

import java.net.URL;
import java.time.ZonedDateTime;
import java.util.Comparator;

import lombok.Value;

@Value
public class Episode {

	String id;
	String title;
	URL linkUrl;
	ZonedDateTime publishedDate;
	Image image;
	Audio audio;

	public static Comparator<Episode> ORDER_BY_MOST_RECENT = (e1, e2) -> e2.getPublishedDate()
			.compareTo(e1.getPublishedDate());

	public boolean hasEpisodeLink() {
		return linkUrl != null;
	}

	public URL getImageUrl() {
		return image.getUrl();
	}

	public String getImageTitle() {
		return image.getTitle();
	}

	public boolean isPublishedSince(ZonedDateTime dateTime) {
		// log.info("[{}] {} for published {} and since {}", id,
		// publishedDate.isAfter(dateTime), publishedDate, dateTime);
		return publishedDate.isAfter(dateTime);
	}

}
