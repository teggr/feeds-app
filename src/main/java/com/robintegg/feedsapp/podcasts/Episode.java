package com.robintegg.feedsapp.podcasts;

import lombok.Value;

import java.time.ZonedDateTime;

@Value
public class Episode {

    String title;
    ZonedDateTime publishedDate;

    public static int ORDER_BY_MOST_RECENT(Episode e1, Episode e2) {
        return e2.getPublishedDate().compareTo(e1.getPublishedDate());
    }

}
