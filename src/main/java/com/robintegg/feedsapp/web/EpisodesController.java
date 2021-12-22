package com.robintegg.feedsapp.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import com.robintegg.feedsapp.inbox.Inbox;
import com.robintegg.feedsapp.subscriptions.BackCatalogue;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EpisodesController {

	private final Inbox inbox;
	private final BackCatalogue backCatalogue;

	@PutMapping(path = "/episodes/{episodeId}")
	public ResponseEntity putAddToMyInbox(@AuthenticationPrincipal User user,
			@PathVariable("episodeId") String episodeId) {

		backCatalogue.distributeEpisodeTo(episodeId, user.getUsername());

		return ResponseEntity.accepted().build();
	}

	@DeleteMapping(path = "/episodes/{episodeId}")
	public ResponseEntity postDelete(@AuthenticationPrincipal User user, @PathVariable("episodeId") String episodeId) {
		inbox.deleteItem(user.getUsername(), episodeId);
		return ResponseEntity.accepted().build();
	}

}
