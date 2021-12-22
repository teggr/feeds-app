package com.robintegg.feedsapp.subscriptions;

import java.util.List;

import org.springframework.stereotype.Service;

import com.robintegg.feedsapp.inbox.Inbox;
import com.robintegg.feedsapp.podcasts.Episode;
import com.robintegg.feedsapp.podcasts.Podcast;
import com.robintegg.feedsapp.podcasts.Podcasts;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
class BackCatalogueService implements BackCatalogue {

	private final Inbox inbox;
	private final Podcasts podcasts;

	@Override
	public void distributeEpisodeTo(String episodeId, String username) {

		Episode episode = podcasts.getEpisode(episodeId);
		if (episode != null) {
			Podcast podcast = podcasts.getPodcastForEpisode(episodeId);
			inbox.put(username, null, podcast, List.of(episode));
		}
	}

}
