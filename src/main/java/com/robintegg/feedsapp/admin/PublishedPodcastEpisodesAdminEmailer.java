package com.robintegg.feedsapp.admin;

import java.util.stream.Collectors;

import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.robintegg.feedsapp.podcasts.NewPodcastEpisodesEvent;
import com.robintegg.feedsapp.podcasts.PodcastEpisodesPublishedEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
class PublishedPodcastEpisodesAdminEmailer implements ApplicationListener<PodcastEpisodesPublishedEvent> {

	private final JavaMailSender javaMailSender;
	private final AdminProperties adminProperties;

	@Override
	public void onApplicationEvent(PodcastEpisodesPublishedEvent event) {

		log.info("Sending Podcast Episodes Published to Admin users");

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("teggr-feeds-app@heroku.com");
		message.setTo(adminProperties.getEmail());
		message.setSubject("Podcast Episodes Published Summary");

		message.setText(createText(event));

		javaMailSender.send(message);

	}

	private String createText(PodcastEpisodesPublishedEvent events) {
		StringBuilder builder = new StringBuilder();
		builder.append(String.format("Published %s Podcast Episodes for %s Podcasts", episodeCount(events),
				podcastCount(events)));
		builder.append("\n=================================\n");
		for (NewPodcastEpisodesEvent event : events.getEvents()) {
			builder.append(String.format("%s updated with %s episodes", event.getPodcast().getFeedTitle(),
					event.getEpisodes().size()));
			builder.append("\n");
		}
		return builder.toString();
	}

	private int podcastCount(PodcastEpisodesPublishedEvent events) {
		return events.getEvents().size();
	}

	private int episodeCount(PodcastEpisodesPublishedEvent events) {
		return events.getEvents().stream().collect(Collectors.summingInt(NewPodcastEpisodesEvent::getEpisodeCount));
	}

}
