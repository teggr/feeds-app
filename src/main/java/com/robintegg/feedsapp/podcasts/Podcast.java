package com.robintegg.feedsapp.podcasts;

import lombok.Getter;

import javax.persistence.*;
import java.net.URL;

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

}