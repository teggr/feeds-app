package com.robintegg.feedsapp.podcasts;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.SneakyThrows;
import org.springframework.util.StreamUtils;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

class EpisodeFactory {

    @SneakyThrows
    public static List<Episode> get(URL feedUrl) {

        String feedData = StreamUtils.copyToString(feedUrl.openStream(), StandardCharsets.UTF_8);
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(new ByteArrayInputStream(feedData.getBytes())));

        // System.out.println(feed);

        System.out.println(feed.getUri());
        System.out.println(feed.getTitle());

        System.out.println(feed.getPublishedDate());

     //   System.out.println("Episodes since " + modifiedSince);

        List<Episode> episodes = new ArrayList<>();

        List<SyndEntry> entries = feed.getEntries();
        for (SyndEntry entry : entries) {

            // System.out.println(entry.getTitle());

            System.out.println(entry.getPublishedDate());

            if (entry.getPublishedDate() != null) {

             //   Instant pd = entry.getPublishedDate().toInstant();
               // Instant mod = modifiedSince.toInstant(ZoneOffset.UTC);
               // System.out.println(pd.getEpochSecond() + " : " + mod.getEpochSecond());
               // if (pd.isAfter(mod)) {
                    episodes.add(
                            new Episode(
                                    entry.getTitle(),
                                    entry.getPublishedDate().toInstant().atZone(ZoneId.systemDefault())
                                    ));
               // }
            }

        }

        // System.out.println(episodes.size());

        return episodes;

    }
}
