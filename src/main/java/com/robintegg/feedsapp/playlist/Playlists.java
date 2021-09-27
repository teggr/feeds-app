package com.robintegg.feedsapp.playlist;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.robintegg.feedsapp.podcasts.Episode;
import com.robintegg.feedsapp.subscriptions.ListeningFeed;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class Playlists {

	private final ListeningFeed feed;
	private final PlaylistRepository playlistRepository;

	public Episode getCurrent(String episodeId) {

		Playlist playlist = playlistRepository.findAll().stream()
				.findFirst()
				.orElse(new Playlist());

		if (episodeId == null) {
			episodeId = playlist.getEpisodeId();
			if (episodeId == null) {
				episodeId = findNextEpisodeInFeed(episodeId).getId();
			}
		}

		Episode episode = findNextEpisodeInFeed(episodeId);

		playlist.setEpisodeId(episode.getId());
		
		playlistRepository.save(playlist);

		return episode;
	}

	private Episode findNextEpisodeInFeed(String episode) {
		return feed.fetch().stream()
				.filter(e -> e.getId().equals(episode))
				.findFirst().get();
	}

}
