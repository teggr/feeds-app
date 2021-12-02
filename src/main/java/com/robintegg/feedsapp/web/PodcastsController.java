package com.robintegg.feedsapp.web;

import java.net.URL;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.robintegg.feedsapp.podcasts.Podcast;
import com.robintegg.feedsapp.podcasts.PodcastUpdateCollector;
import com.robintegg.feedsapp.podcasts.Podcasts;
import com.robintegg.feedsapp.subscriptions.PodcastSubscriptions;
import com.robintegg.feedsapp.subscriptions.Subscription;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class PodcastsController {

	private final Podcasts podcasts;
	private final PodcastUpdateCollector podcastFetchService;
	private final PodcastSubscriptions podcastSubscriptions;

	@GetMapping("/podcasts")
	public String get(Model model) {
		model.addAttribute("podcasts", podcasts.getAll());
		// TODO: user related
		model.addAttribute("subscriptions", new SubscriptionHelper(podcastSubscriptions.findAll()));
		return "podcasts/podcasts";
	}

	@PostMapping(path = "/podcasts", params = "add")
	public String postCreate(@RequestParam("feedUrl") URL feedUrl) {
		podcasts.addPodcastWithUrl(feedUrl);
		return "redirect:/";
	}

	@PostMapping(path = "/podcasts", params = "refresh")
	public String postRefreshAll() {
		podcastFetchService.collect();
		return "redirect:/";
	}

	@GetMapping("/podcasts/{id}")
	public String get(@PathVariable("id") Long id, Model model) {
		Podcast podcast = podcasts.get(id);
		model.addAttribute("podcast", podcast);
		model.addAttribute("episodes", podcast.getMostRecentEpisodes(10));
		// TODO: user related
		Subscription subscription = podcastSubscriptions.getByPodcast(id);
		model.addAttribute("subscribe", subscription == null);
		model.addAttribute("unsubscribe", subscription != null);
		model.addAttribute("subscription", subscription);
		return "podcasts/podcast";
	}

	@PostMapping("/podcasts/{id}/delete")
	public String postDelete(@PathVariable("id") Long id) {
		podcasts.removePodcast(id);
		return "redirect:/";
	}

	@PostMapping("/podcasts/{id}/refresh")
	public String postRefresh(@PathVariable("id") Long id) {
		podcastFetchService.fetch(id);
		return "redirect:/podcasts/" + id;
	}

}
