package com.robintegg.feedsapp.web;

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
	public String postSubscribe(@RequestHeader("Referer") String referer, @RequestParam("podcastId") Long podcastId) {
		podcastSubscriptions.startSubscribingTo(podcastId);
		return "redirect:" + referer;
	}

	@GetMapping(path = "/subscriptions")
	public String getPodcastSubscriptions(Model model) {
		model.addAttribute("podcastSubscriptions", podcastSubscriptions.findAll());
		return "subscriptions/subscriptions";
	}

	@PostMapping(path = "/subscriptions/{id}/unsubscribe")
	public String postSubscribe(@PathVariable("id") Long subscriptionId) {
		podcastSubscriptions.stopSubscription(subscriptionId);
		return "redirect:/subscriptions";
	}

}
