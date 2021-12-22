package com.robintegg.feedsapp.web;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.robintegg.feedsapp.inbox.Inbox;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EpisodesController {

	private final Inbox inbox;

	@DeleteMapping(path = "/episodes/{episodeId}")
	public ResponseEntity postDelete(@AuthenticationPrincipal User user, @PathVariable("episodeId") String episodeId) {
		inbox.deleteItem(user.getUsername(), episodeId);
		return ResponseEntity.accepted().build();
	}

}
