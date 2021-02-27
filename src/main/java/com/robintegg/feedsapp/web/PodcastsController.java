package com.robintegg.feedsapp.web;

import com.robintegg.feedsapp.podcasts.Podcast;
import com.robintegg.feedsapp.podcasts.PodcastRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping("/podcasts/{id}")
    public String post(@PathVariable("id") Long id, Model model) {
        model.addAttribute("podcast", podcastRepository.findById(id)
                .orElseThrow());
        return "podcasts/podcast";
    }

    @PostMapping("/podcasts/{id}/delete")
    public String post(@PathVariable("id") Long id) {
        podcastRepository.deleteById(id);
        return "redirect:/";
    }

}
