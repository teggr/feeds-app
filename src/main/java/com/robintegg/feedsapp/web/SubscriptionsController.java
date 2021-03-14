package com.robintegg.feedsapp.web;

import com.robintegg.feedsapp.subscriptions.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class SubscriptionsController {

    private final SubscriptionSetup subscriptionSetup;
    private final PodcastSubscriptions podcastSubscriptions;
    private final SubscriptionRepository subscriptionRepository;
    private final MainFeed mainFeed;

    @PostMapping(path = "/subscriptions/subscribe")
    public String postSubscribe(@RequestHeader("Referer") String referer, @RequestParam("podcastId") Long podcastId) {
        Subscription subscription = subscriptionSetup.subscribeToPodcast(podcastId);
        return "redirect:" + referer;
    }

    @PostMapping(path = "/subscriptions/not-interested/{episodeId}")
    public ResponseEntity postNotInterested(@PathVariable("episodeId") String episodeId) {
        mainFeed.notInterested(episodeId);
        return ResponseEntity.accepted().build();
    }

    @GetMapping(path = "/subscriptions")
    public String getPodcastSubscriptions(Model model) {
        model.addAttribute("podcastSubscriptions", podcastSubscriptions.findAll());
        return "subscriptions/subscriptions";
    }

    @PostMapping(path = "/subscriptions/{id}/unsubscribe")
    public String postSubscribe(@PathVariable("id") Long subscriptionId) {
        subscriptionRepository.deleteById(subscriptionId);
        return "redirect:/subscriptions";
    }

}
