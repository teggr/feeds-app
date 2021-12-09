package com.robintegg.feedsapp.playlist;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
class DailyPodcastUpdateEmailerScheduler {

	private final PodcastEpisodeRepository podcastEpisodeRepository;
	private final JavaMailSender javaMailSender;
	private final UserProperties userProperties;

	@Scheduled(cron = "0 0 7 * * *")
	public void sendUserEmails() {

		log.info("Starting daily email");

		List<PodcastEpisodeEntity> episodes = podcastEpisodeRepository
				.findAllByReceivedDateTimeGreaterThan(LocalDateTime.now().minusDays(1));

		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom("teggr-feeds-app@heroku.com");
		message.setTo(userProperties.getEmail());
		message.setSubject("Podcast Update Summary");
		message.setText(createText(episodes));
		javaMailSender.send(message);

		log.info("Completed daily email");

	}

	private String createText(List<PodcastEpisodeEntity> episodes) {
		if (episodes.isEmpty()) {
			return "No new episodes";
		} else {
			StringBuilder builder = new StringBuilder();
			for (PodcastEpisodeEntity episode : episodes) {
				builder.append(String.format("%s", episode.getEpisodeId()));
				builder.append("\n");
			}
			return builder.toString();
		}
	}

}
