package com.robintegg.feedsapp.podcasts;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class NewPodcastEvent extends ApplicationEvent {

	private final Podcast podcast;

	public NewPodcastEvent(Object source, Podcast podcast) {
		super(source);
		this.podcast = podcast;
	}

	public Podcast getPodcast() {
		return podcast;
	}

}
