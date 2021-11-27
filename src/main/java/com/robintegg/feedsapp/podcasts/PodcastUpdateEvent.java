package com.robintegg.feedsapp.podcasts;

import java.util.Collection;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class PodcastUpdateEvent extends ApplicationEvent {

	private Collection<Episode> episodes;
	private Podcast podcast;

	public PodcastUpdateEvent(Object source, Podcast podcast, Collection<Episode> episodes) {
		super(source);
		this.podcast = podcast;
		this.episodes = episodes;
	}
	
	public Podcast getPodcast() {
		return podcast;
	}
	
	public Collection<Episode> getEpisodes() {
		return episodes;
	}

}
