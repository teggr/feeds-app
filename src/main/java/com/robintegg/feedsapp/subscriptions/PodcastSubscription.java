package com.robintegg.feedsapp.subscriptions;

import com.robintegg.feedsapp.podcasts.Podcast;
import lombok.Value;

@Value
public class PodcastSubscription {

    Podcast podcast;
    Subscription subscription;

}
