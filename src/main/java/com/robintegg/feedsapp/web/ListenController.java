package com.robintegg.feedsapp.web;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.robintegg.feedsapp.playlist.Playlists;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class ListenController {

    private final Playlists playlist;

    @GetMapping("/listen")
    public String get(
            @RequestHeader(value = HttpHeaders.REFERER, required = false) String refererUrl,
            @RequestParam(value = "episodeId", required = false) String episodeId,
            Model model,
            HttpServletResponse response
    ) {

        log.info("episodeId={},refererUrl={}", episodeId, refererUrl);

        model.addAttribute("episode", playlist.getCurrent(episodeId) );
        model.addAttribute("navigateBackUrl", refererUrl);

        // response.addHeader("Cache-Control", "no-cache");

        return "listen/listen";

    }

}
