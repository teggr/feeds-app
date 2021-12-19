package com.robintegg.feedsapp.podcasts;

import java.net.URL;

import lombok.Value;

@Value
class PodcastFeedMetadata {

	URL feedUrl;
	String title;
	URL linkUrl;
	Image image;

}
