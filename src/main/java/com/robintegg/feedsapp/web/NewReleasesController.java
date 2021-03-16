package com.robintegg.feedsapp.web;

import com.robintegg.feedsapp.subscriptions.MainFeed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class NewReleasesController {

    private final MainFeed mainFeed;

    @GetMapping("/newreleases")
    public String get(Model model) {
        model.addAttribute("episodes", mainFeed.fetch());
        return "newreleases/new-releases";
    }

}
