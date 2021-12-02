package com.robintegg.feedsapp.podcasts;

import java.net.URL;
import java.util.List;

public interface Podcasts {

	void addPodcastWithUrl(URL feedUrl);

	void removePodcast(Long id);

	Podcast get(Long id);

	List<Podcast> getAll();

	Episode getEpisode(String episodeId);

}
