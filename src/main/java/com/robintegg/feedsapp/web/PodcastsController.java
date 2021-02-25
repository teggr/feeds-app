package com.robintegg.feedsapp.web;

import com.robintegg.feedsapp.podcasts.Podcast;
import com.robintegg.feedsapp.podcasts.PodcastRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URL;

@Controller
@RequiredArgsConstructor
public class PodcastsController {

    private final PodcastRepository podcastRepository;

    @PostMapping("/podcasts")
    public String post(@RequestParam("feedUrl") URL feedUrl) {
        Podcast podcast = Podcast.forUrl(feedUrl);
        podcastRepository.save(podcast);
        return "redirect:/";
    }

    @PostMapping("/podcasts/{id}/delete")
    public String post(@RequestParam("id") Long id) {
        podcastRepository.deleteById(id);
        return "redirect:/";
    }

}
