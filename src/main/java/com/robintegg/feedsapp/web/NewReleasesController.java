package com.robintegg.feedsapp.web;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.robintegg.feedsapp.playlist.NewReleases;

@Controller
@RequiredArgsConstructor
public class NewReleasesController {

    private final NewReleases newReleases;

    @GetMapping("/newreleases")
    public String get(Model model) {
        model.addAttribute("episodes", newReleases.fetch());
        return "newreleases/new-releases";
    }

}
