package com.robintegg.feedsapp.podcasts;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.SneakyThrows;

import java.io.ByteArrayInputStream;
import java.net.URL;

class PodcastInfoFactory {

    @SneakyThrows
    public static PodcastInfo from(String feedData) {

        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(new ByteArrayInputStream(feedData.getBytes())));

        return new PodcastInfo(feed.getTitle(), new URL(feed.getLink()));

    }
}
