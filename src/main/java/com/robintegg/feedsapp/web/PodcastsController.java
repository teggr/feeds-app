package com.robintegg.feedsapp.web;

import java.net.URL;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.robintegg.feedsapp.inbox.Inbox;
import com.robintegg.feedsapp.podcasts.Episode;
import com.robintegg.feedsapp.podcasts.Podcast;
import com.robintegg.feedsapp.podcasts.PodcastEpisodePublisher;
import com.robintegg.feedsapp.podcasts.Podcasts;
import com.robintegg.feedsapp.subscriptions.PodcastSubscription;
import com.robintegg.feedsapp.subscriptions.PodcastSubscriptions;
import com.robintegg.feedsapp.subscriptions.Subscription;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PodcastsController {

	private static final HateoasPageableHandlerMethodArgumentResolver RESOLVER = new HateoasPageableHandlerMethodArgumentResolver();

	private final Podcasts podcasts;
	private final PodcastEpisodePublisher podcastEpisodePublisher;
	private final PodcastSubscriptions podcastSubscriptions;
	private final Inbox inbox;

	@GetMapping("/podcasts")
	public String get(@AuthenticationPrincipal User user, Model model) {
		model.addAttribute("podcasts", podcasts.getAll());
		// TODO: user related
		model.addAttribute("subscriptions", new SubscriptionHelper(podcastSubscriptions.findAll(user)));
		return "podcasts/podcasts";
	}

	@PostMapping(path = "/podcasts", params = "add")
	public String postCreate(@RequestParam("feedUrl") URL feedUrl) {
		podcasts.addPodcastWithUrl(feedUrl);
		return "redirect:/";
	}

	@PostMapping(path = "/podcasts", params = "refresh")
	public String postRefreshAll() {
		podcastEpisodePublisher.publishAllLatestPodcastEpisodes();
		return "redirect:/";
	}

	@GetMapping("/podcasts/{id}")
	public String get(@AuthenticationPrincipal User user, @PathVariable("id") Long id,
			@PageableDefault(sort = "e.publishedDate", direction = Direction.DESC) Pageable pageable, Model model) {

		Podcast podcast = podcasts.get(id);
		Page<Episode> episodes = podcasts.getEpisodes(id, pageable);

		model.addAttribute("podcast", podcast);
		model.addAttribute("episodes", episodes);

		if (episodes.hasNext()) {
			Pageable nextPageable = episodes.nextPageable();
			ServletUriComponentsBuilder uriComponentsBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
			RESOLVER.enhance(uriComponentsBuilder, null, nextPageable);
			model.addAttribute("next", uriComponentsBuilder.build().toUriString());
		}

		// TODO: user related
		Subscription subscription = Optional.ofNullable(podcastSubscriptions.getByPodcast(user, id))
				.map(PodcastSubscription::getSubscription).orElse(null);
		model.addAttribute("subscribe", subscription == null);
		model.addAttribute("unsubscribe", subscription != null);
		model.addAttribute("subscription", subscription);

		model.addAttribute("inbox", SubscriptionInboxHelper.forSubscription(user.getUsername(), subscription, inbox));

		return "podcasts/podcast";
	}

	@PostMapping(path = "/podcasts/{id}", params = "refresh")
	public String postRefresh(@PathVariable("id") Long id) {
		log.info("refresh podcast {}", id);
		podcastEpisodePublisher.publishLatestPodcastsEpisodesFor(id);
		return "redirect:/podcasts/" + id;
	}

	@PostMapping("/podcasts/{id}/delete")
	public String postDelete(@PathVariable("id") Long id) {
		podcasts.removePodcast(id);
		return "redirect:/";
	}

}
