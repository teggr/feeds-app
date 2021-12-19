package com.robintegg.feedsapp.podcasts;

import java.time.ZonedDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
class PodcastFeedUpdate {

	PodcastFeedMetadata metadata;
	List<Episode> episodes;
	@Builder.Default
	ZonedDateTime timestamp = ZonedDateTime.now();

}
