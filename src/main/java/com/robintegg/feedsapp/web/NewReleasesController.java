package com.robintegg.feedsapp.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.robintegg.feedsapp.playlist.NewReleases;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class NewReleasesController {

	private final NewReleases newReleases;

	@GetMapping("/newreleases")
	public String get(@AuthenticationPrincipal User user, Model model) {
		model.addAttribute("episodes", newReleases.fetch(user));
		return "newreleases/new-releases";
	}

}
