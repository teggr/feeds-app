package com.robintegg.feedsapp.web;

import com.robintegg.feedsapp.subscriptions.ListeningFeed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ListenController {

    private final ListeningFeed listeningFeed;

    @GetMapping("/listen")
    public String get(
            @RequestHeader(value = HttpHeaders.REFERER, required = false) String refererUrl,
            @RequestParam(value = "audioUrl", required = false) String audioUrl,
            Model model
    ) {

        System.out.println(refererUrl);

        // model
        // playing url
        // next url
        // navigate url
        model.addAttribute("audioUrl", audioUrl);
        model.addAttribute("navigateBackUrl", refererUrl);

        return "listen/listen";

    }

}
