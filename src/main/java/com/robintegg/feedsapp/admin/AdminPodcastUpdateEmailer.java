package com.robintegg.feedsapp.admin;

import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.robintegg.feedsapp.podcasts.PodcastUpdateEvent;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminPodcastUpdateEmailer implements ApplicationListener<PodcastUpdateEvent> {

	private final JavaMailSender javaMailSender;
	private final AdminProperties adminProperties;

	@Override
	public void onApplicationEvent(PodcastUpdateEvent event) {

		log.info("new podcast data published for {}", event.getPodcast().getFeedTitle());

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("teggr-feeds-app@heroku.com");
		message.setTo(adminProperties.getEmail());
		message.setSubject("Podcast Update Summary");
		message.setText(String.format("Podcast %s updated with %s episodes", event.getPodcast().getFeedTitle(),
				event.getEpisodes().size()));
		javaMailSender.send(message);

	}

}
