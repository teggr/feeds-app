package com.robintegg.feedsapp.podcasts;

import java.net.URL;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface Podcasts {

	void addPodcastWithUrl(URL feedUrl);

	void removePodcast(Long id);

	Podcast get(Long id);

	List<Podcast> getAll();

	Episode getEpisode(String episodeId);

	Podcast getPodcastForEpisode(String episodeId);

	Page<Episode> getEpisodes(Long id, Pageable pageable);

}
