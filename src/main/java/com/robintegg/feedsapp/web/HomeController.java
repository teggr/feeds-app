package com.robintegg.feedsapp.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.robintegg.feedsapp.playlist.ListeningFeed;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

	private final ListeningFeed listeningFeed;

	@GetMapping("/")
	public String get(@AuthenticationPrincipal User user, Model model) {
		model.addAttribute("episodes", listeningFeed.fetch(user));
		return "home";
	}

}
