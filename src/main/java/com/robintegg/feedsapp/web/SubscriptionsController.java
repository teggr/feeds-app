package com.robintegg.feedsapp.web;

import com.robintegg.feedsapp.subscriptions.Subscription;
import com.robintegg.feedsapp.subscriptions.SubscriptionSetup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class SubscriptionsController {

    private final SubscriptionSetup subscriptionSetup;

    @PostMapping(path = "/subscriptions")
    public String postSubscribe(@RequestHeader("Referer") String referer, @RequestParam("podcastId") Long podcastId) {
        Subscription subscription = subscriptionSetup.subscribeToPodcast(podcastId);
        return "redirect:" + referer;
    }

}
