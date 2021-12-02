package com.robintegg.feedsapp.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.robintegg.feedsapp.playlist.ListeningFeed;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ListeningFeed listeningFeed;

    @GetMapping("/")
    public String get(Model model) {
        model.addAttribute("episodes", listeningFeed.fetch());
        return "home";
    }

}
