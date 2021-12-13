package com.robintegg.feedsapp.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

import com.robintegg.feedsapp.inbox.Episodes;
import com.robintegg.feedsapp.inbox.NewReleases;
import com.robintegg.feedsapp.subscriptions.PodcastSubscriptions;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SubscriptionsController {

	private final PodcastSubscriptions podcastSubscriptions;
	private final NewReleases newReleases;
	private final Episodes episodes;

	@PostMapping(path = "/subscriptions/subscribe")
	public String postSubscribe(@RequestHeader("Referer") String referer, @RequestParam("podcastId") Long podcastId) {
		podcastSubscriptions.startSubscribingTo(podcastId);
		return "redirect:" + referer;
	}

	@PostMapping(path = "/subscriptions/not-interested/{episodeId}")
	public ResponseEntity postNotInterested(@PathVariable("episodeId") String episodeId) {
		episodes.notInterested(episodeId);
		return ResponseEntity.accepted().build();
	}

	@PostMapping(path = "/subscriptions/interested/{episodeId}")
	public ResponseEntity postInterested(@PathVariable("episodeId") String episodeId) {
		episodes.interested(episodeId);
		return ResponseEntity.accepted().build();
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
