package com.robintegg.feedsapp.playlist;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.robintegg.feedsapp.inbox.Inbox;
import com.robintegg.feedsapp.inbox.InboxPodcastEpisode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class Playlists {

	private final Inbox inbox;
	private final PlaylistRepository playlistRepository;

	public InboxPodcastEpisode getCurrent(User user, String episodeId) {

		Playlist playlist = playlistRepository.findAll().stream().findFirst().orElse(new Playlist());

		if (episodeId == null) {
			episodeId = playlist.getEpisodeId();
		}

		InboxPodcastEpisode episode = null;
		if (episodeId == null) {
			episode = inbox.getTopItem(user.getUsername());
		} else {
			episode = inbox.getItem(user.getUsername(), episodeId);
		}

		playlist.setEpisodeId(episode.getEpisodeId());

		playlistRepository.save(playlist);

		return episode;
	}

}
