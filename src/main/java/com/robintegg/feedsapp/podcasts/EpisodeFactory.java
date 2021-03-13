package com.robintegg.feedsapp.podcasts;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
class EpisodeFactory {

    @SneakyThrows
    public static List<Episode> get(String feedData) {

        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(new ByteArrayInputStream(feedData.getBytes())));

        log.info("uri={},title={},link={}", feed.getUri(), feed.getTitle(), feed.getLink());

        Image podcastImage = PodcastInfoFactory.getImage(feed);

        List<Episode> episodes = new ArrayList<>();

        List<SyndEntry> entries = feed.getEntries();
        for (SyndEntry entry : entries) {

            log.info("uri={},title={},link={}", entry.getUri(), entry.getTitle(), entry.getLink());

            if (entry.getPublishedDate() != null) {

                Audio audio = getAudio(entry);
                URL linkUrl = getLinkUrl(entry);
                ZonedDateTime publishedDate = getPublishedDate(entry);
                String title = entry.getTitle();
                Image image = podcastImage;

                episodes.add(
                        new Episode(
                                title,
                                linkUrl,
                                publishedDate,
                                image,
                                audio
                        ));

            }

        }

        return episodes;

    }

    private static ZonedDateTime getPublishedDate(SyndEntry entry) {
        return entry.getPublishedDate().toInstant().atZone(ZoneId.systemDefault());
    }

    private static URL getLinkUrl(SyndEntry entry) throws MalformedURLException {
        URL linkUrl = null;
        if (entry.getLink() != null) {
            linkUrl = new URL(entry.getLink());
        }
        return linkUrl;
    }

    private static Audio getAudio(SyndEntry entry) throws MalformedURLException {
        Audio audio;
        if (entry.getEnclosures().isEmpty()) {
            log.info("NO ENCLOSURES");
            audio = new Audio(null, null);
        } else {
            audio = new Audio(new URL(entry.getEnclosures().get(0).getUrl()), entry.getEnclosures().get(0).getType());
        }
        return audio;
    }

}
