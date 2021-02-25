package com.robintegg.feedsapp.web;

import com.robintegg.feedsapp.podcasts.PodcastRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final PodcastRepository podcastRepository;

    @GetMapping("/")
    public String get(Model model) {
        model.addAttribute("podcasts", podcastRepository.findAll());
        return "home";
    }

}