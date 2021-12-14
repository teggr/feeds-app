package com.robintegg.feedsapp.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.robintegg.feedsapp.subscriptions.PodcastSubscriptions;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SubscriptionsController {

	private final PodcastSubscriptions podcastSubscriptions;

	@PostMapping(path = "/subscriptions/subscribe")
	public String postSubscribe(@AuthenticationPrincipal User user, @RequestHeader("Referer") String referer,
			@RequestParam("podcastId") Long podcastId) {
		podcastSubscriptions.startSubscribingTo(user, podcastId);
		return "redirect:" + referer;
	}

	@GetMapping(path = "/subscriptions")
	public String getPodcastSubscriptions(@AuthenticationPrincipal User user, Model model) {
		model.addAttribute("podcastSubscriptions", podcastSubscriptions.findAll(user));
		return "subscriptions/subscriptions";
	}

	@PostMapping(path = "/subscriptions/{id}/unsubscribe")
	public String postSubscribe(@AuthenticationPrincipal User user, @PathVariable("id") Long subscriptionId) {
		podcastSubscriptions.stopSubscription(user, subscriptionId);
		return "redirect:/subscriptions";
	}

}
