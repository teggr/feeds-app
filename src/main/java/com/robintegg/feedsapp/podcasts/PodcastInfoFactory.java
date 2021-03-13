package com.robintegg.feedsapp.podcasts;

import com.rometools.modules.itunes.FeedInformation;
import com.rometools.rome.feed.module.Module;
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
        String title = feed.getTitle();
        URL linkUrl = new URL(feed.getLink());

        return new PodcastInfo(title, linkUrl, image);

    }

    public static Image getImage(SyndFeed feed) throws MalformedURLException {
        if (feed.getImage() != null) {
            return new Image(new URL(feed.getImage().getUrl()), feed.getImage().getTitle());
        } else {

            Module module = feed.getModule("http://www.itunes.com/dtds/podcast-1.0.dtd");

            if (module instanceof FeedInformation feedInformation) {

                if (feedInformation.getImage() != null) {

                    return new Image(feedInformation.getImage(), "Feed Image");

                }

            }


            return new Image(new URL("https://bulma.io/images/placeholders/128x128.png"), "No Image");
        }
    }

}
