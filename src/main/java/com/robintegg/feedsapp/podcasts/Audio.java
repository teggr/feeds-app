package com.robintegg.feedsapp.podcasts;

import lombok.Value;

import java.net.URL;

@Value
public class Audio {
    URL url;
    String type;
}
