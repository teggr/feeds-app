package com.robintegg.feedsapp.admin;

import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.robintegg.feedsapp.podcasts.PodcastCollectionEvent;
import com.robintegg.feedsapp.podcasts.PodcastUpdateEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminPodcastUpdateEmailer implements ApplicationListener<PodcastCollectionEvent> {

	private final JavaMailSender javaMailSender;
	private final AdminProperties adminProperties;

	@Override
	public void onApplicationEvent(PodcastCollectionEvent event) {

		log.info("podcast collection event");

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("teggr-feeds-app@heroku.com");
		message.setTo(adminProperties.getEmail());
		message.setSubject("Podcast Collection Summary");

		message.setText(createText(event));

		javaMailSender.send(message);

	}

	private String createText(PodcastCollectionEvent events) {
		StringBuilder builder = new StringBuilder();
		for (PodcastUpdateEvent event : events.getEvents()) {
			builder.append(String.format("%s updated with %s episodes", event.getPodcast().getFeedTitle(),
					event.getEpisodes().size()));
			builder.append("\n");
		}
		return builder.toString();
	}

}
