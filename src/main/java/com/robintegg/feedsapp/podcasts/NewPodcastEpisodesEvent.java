package com.robintegg.feedsapp.podcasts;

import java.util.Collection;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class NewPodcastEpisodesEvent extends ApplicationEvent {

	private final Collection<Episode> episodes;
	private final Podcast podcast;

	public NewPodcastEpisodesEvent(Object source, Podcast podcast, Collection<Episode> episodes) {
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

	public int getEpisodeCount() {
		return episodes.size();
	}

}
