package com.robintegg.feedsapp.web;

import com.robintegg.feedsapp.subscriptions.MainFeed;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MainFeed mainFeed;

    @GetMapping("/")
    public String get(Model model) {
        model.addAttribute("episodes", mainFeed.fetch());
        return "home";
    }

}
