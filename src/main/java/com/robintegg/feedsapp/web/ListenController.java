package com.robintegg.feedsapp.web;

import com.robintegg.feedsapp.subscriptions.ListeningFeed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ListenController {

    private final ListeningFeed listeningFeed;

    @GetMapping("/listen")
    public String get(
            @RequestHeader(value = HttpHeaders.REFERER, required = false) String refererUrl,
            @RequestParam(value = "audioUrl", required = false) String audioUrl,
            @RequestParam(value = "audioType", required = false) String audioType,
            Model model,
            HttpServletResponse response
    ) {

        log.info("audioUrl={},audioType={},refererUrl={}", audioUrl, audioType, refererUrl);

        model.addAttribute("audioUrl", audioUrl);
        model.addAttribute("audioType", audioType);
        model.addAttribute("navigateBackUrl", refererUrl);

        response.addHeader("Cache-Control", "no-cache");

        return "listen/listen";

    }

}
