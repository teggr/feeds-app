package com.robintegg.feedsapp.podcasts;

import java.net.URL;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.Value;

@Value
public class Podcast {

	static Podcast fromEntity(PodcastEntity pe) {
		return new Podcast(pe.getId(), pe.getFeedUrl(), pe.getLastFetched(), pe.getFeedTitle(), pe.getFeedLinkUrl(),
				pe.getFeedImageUrl(), pe.getFeedImageTitle(), pe.getEpisodes());
	}

	Long id;
	URL feedUrl;
	ZonedDateTime lastFetched;
	String feedTitle;
	URL feedLinkUrl;
	URL feedImageUrl;
	String feedImageTitle;
	Set<EpisodeEntity> episodes;

	public List<Episode> getMostRecentEpisodes(int limit) {
		return episodes.stream().limit(limit).map(EpisodeEntity::to).collect(Collectors.toList());

	}

	public Collection<Episode> getEpisodesSince(ZonedDateTime lastUpdated) {
		return episodes.stream().filter(ee -> ee.isPublishedSince(lastUpdated)).map(EpisodeEntity::to)
				.collect(Collectors.toList());
	}

	public Collection<Episode> getEpisodes(Set<String> unseenSubscriptionEpisodeIds) {
		return episodes.stream().filter(ee -> unseenSubscriptionEpisodeIds.contains(ee.getEpisodeId()))
				.map(EpisodeEntity::to).collect(Collectors.toList());
	}

}