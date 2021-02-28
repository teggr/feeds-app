package com.robintegg.feedsapp.podcasts;

import lombok.Getter;

import javax.persistence.*;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "podcasts")
@Getter
public class Podcast {

    public static Podcast forUrl(URL feedUrl) {
        Podcast podcast = new Podcast();
        podcast.feedUrl = feedUrl;
        return podcast;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "feedUrl")
    private URL feedUrl;

    protected Podcast() {
    } // for persistence

    public List<Episode> getMostRecentEpisodes(int maxEpisodes) {

        return EpisodeFactory.get(feedUrl).stream()
                .sorted(Episode::ORDER_BY_MOST_RECENT)
                .limit(maxEpisodes)
                .collect(Collectors.toList());

    }
}