package com.robintegg.feedsapp.podcasts;

import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.SneakyThrows;

import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import java.net.URL;

class PodcastInfoFactory {

    @SneakyThrows
    public static PodcastInfo from(String feedData) {

        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(new ByteArrayInputStream(feedData.getBytes())));

        Image image = getImage(feed);

        return new PodcastInfo(feed.getTitle(), new URL(feed.getLink()), image);

    }

    private static Image getImage(SyndFeed feed) throws MalformedURLException {
        if (feed.getImage() != null) {
            return new Image(new URL(feed.getImage().getUrl()), feed.getImage().getTitle());
        } else {
            return new Image(new URL("https://bulma.io/images/placeholders/128x128.png"), "No Image");
        }
    }

}
