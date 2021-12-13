package com.robintegg.feedsapp.web;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.robintegg.feedsapp.inbox.Episodes;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class EpisodesController {

	private final Episodes episodes;

	@PostMapping(path = "/episodes/not-interested/{episodeId}")
	public ResponseEntity postNotInterested(@PathVariable("episodeId") String episodeId) {
		episodes.notInterested(episodeId);
		return ResponseEntity.accepted().build();
	}

	@PostMapping(path = "/episodes/interested/{episodeId}")
	public ResponseEntity postInterested(@PathVariable("episodeId") String episodeId) {
		episodes.interested(episodeId);
		return ResponseEntity.accepted().build();
	}

}
