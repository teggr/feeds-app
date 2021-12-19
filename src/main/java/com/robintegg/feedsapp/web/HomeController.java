package com.robintegg.feedsapp.web;

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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.robintegg.feedsapp.inbox.Inbox;
import com.robintegg.feedsapp.podcasts.Episode;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {

	private static final HateoasPageableHandlerMethodArgumentResolver RESOLVER = new HateoasPageableHandlerMethodArgumentResolver();

	private final Inbox inbox;

	@GetMapping("/")
	public String get(@AuthenticationPrincipal User user,
			@PageableDefault(sort = "receivedDateTime", direction = Direction.ASC) Pageable pageable, Model model) {
		Page<Episode> items = inbox.getItems(user.getUsername(), pageable);
		model.addAttribute("episodes", items);
		if (items.hasNext()) {
			Pageable nextPageable = items.nextPageable();
			ServletUriComponentsBuilder uriComponentsBuilder = ServletUriComponentsBuilder.fromCurrentRequest();
			RESOLVER.enhance(uriComponentsBuilder, null, nextPageable);
			model.addAttribute("next", uriComponentsBuilder.build().toUriString());
		}
		return "inbox";
	}

}
