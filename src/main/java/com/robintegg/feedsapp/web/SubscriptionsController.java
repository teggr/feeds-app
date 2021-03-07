package com.robintegg.feedsapp.web;

import com.robintegg.feedsapp.subscriptions.PodcastSubscriptions;
import com.robintegg.feedsapp.subscriptions.Subscription;
import com.robintegg.feedsapp.subscriptions.SubscriptionSetup;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class SubscriptionsController {

    private final SubscriptionSetup subscriptionSetup;
    private final PodcastSubscriptions podcastSubscriptions;

    @PostMapping(path = "/subscriptions")
    public String postSubscribe(@RequestHeader("Referer") String referer, @RequestParam("podcastId") Long podcastId) {
        Subscription subscription = subscriptionSetup.subscribeToPodcast(podcastId);
        return "redirect:" + referer;
    }

    @GetMapping(path = "/subscriptions")
    public String getPodcastSubscriptions(Model model) {
        model.addAttribute("podcastSubscriptions", podcastSubscriptions.findAll());
        return "subscriptions/subscriptions";
    }

}
