package com.robintegg.feedsapp.podcasts;

import java.util.Collection;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@SuppressWarnings("serial")
@Getter
public class PodcastEpisodesPublishedEvent extends ApplicationEvent {

	private final Collection<NewPodcastEpisodesEvent> events;
	private long timeTakenMs;

	public PodcastEpisodesPublishedEvent(Object source, Collection<NewPodcastEpisodesEvent> events, long timeTakenMs) {
		super(source);
		this.events = events;
		this.timeTakenMs = timeTakenMs;
	}

}
