package com.robintegg.feedsapp.playlist;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.robintegg.feedsapp.podcasts.Episode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class Playlists {

	private final ListeningFeed feed;
	private final PlaylistRepository playlistRepository;

	public Episode getCurrent(User user, String episodeId) {

		Playlist playlist = playlistRepository.findAll().stream().findFirst().orElse(new Playlist());

		if (episodeId == null) {
			episodeId = playlist.getEpisodeId();
			if (episodeId == null) {
				episodeId = findNextEpisodeInFeed(user, episodeId).getId();
			}
		}

		Episode episode = findNextEpisodeInFeed(user, episodeId);

		playlist.setEpisodeId(episode.getId());

		playlistRepository.save(playlist);

		return episode;
	}

	private Episode findNextEpisodeInFeed(User user, String episode) {
		return feed.fetch(user).stream().filter(e -> e.getId().equals(episode)).findFirst().get();
	}

}
