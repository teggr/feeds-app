package com.robintegg.feedsapp.podcasts;

import lombok.Value;

import java.net.URL;

@Value
public class PodcastMetadata {

	URL feedUrl;
    String title;
    URL linkUrl;
    Image image;

}
