package com.robintegg.feedsapp.web;

import com.robintegg.feedsapp.podcasts.PodcastRepository;
import com.robintegg.feedsapp.subscriptions.PodcastSubscriptions;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PodcastRepository podcastRepository;
    private final PodcastSubscriptions podcastSubscriptions;

    @GetMapping("/")
    public String get(Model model) {
        model.addAttribute("podcasts", podcastRepository.findAll());
        // TODO: user related
        model.addAttribute("subscriptions", new SubscriptionHelper(podcastSubscriptions.findAll()));
        return "home";
    }

}
