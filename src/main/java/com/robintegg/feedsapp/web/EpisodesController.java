package com.robintegg.feedsapp.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.robintegg.feedsapp.inbox.Inbox;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EpisodesController {

	private final Inbox inbox;

	@PostMapping(path = "/episodes/{episodeId}/ignore")
	public ResponseEntity postNotInterested(@AuthenticationPrincipal User user,
			@PathVariable("episodeId") String episodeId) {
		inbox.ignore(user.getUsername(), episodeId);
		return ResponseEntity.accepted().build();
	}

}
