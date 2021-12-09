package com.robintegg.feedsapp.podcasts;

import java.util.Collection;

import org.springframework.context.ApplicationEvent;

@SuppressWarnings("serial")
public class PodcastCollectionEvent extends ApplicationEvent {

	private final Collection<PodcastUpdateEvent> events;

	public PodcastCollectionEvent(Object source, Collection<PodcastUpdateEvent> events) {
		super(source);
		this.events = events;
	}

	public Collection<PodcastUpdateEvent> getEvents() {
		return events;
	}

}
