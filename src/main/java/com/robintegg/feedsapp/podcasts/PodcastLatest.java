package com.robintegg.feedsapp.podcasts;

import java.time.ZonedDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PodcastLatest {

	PodcastMetadata podcastMetadata;
	List<Episode> episodes;
	@Builder.Default
	ZonedDateTime timestamp = ZonedDateTime.now();
	
}
