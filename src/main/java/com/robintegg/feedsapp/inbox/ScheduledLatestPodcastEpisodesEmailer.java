package com.robintegg.feedsapp.inbox;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.robintegg.feedsapp.users.FeedsAppUser;
import com.robintegg.feedsapp.users.FeedsAppUsers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Transactional
@RequiredArgsConstructor
@Slf4j
class ScheduledLatestPodcastEpisodesEmailer {

	private final FeedsAppUsers users;
	private final InboxPodcastEpisodeRepository inboxPodcastEpisodeRepository;
	private final JavaMailSender javaMailSender;

	@Scheduled(cron = "0 0 7 * * *")
	public void sendLatestPodcastEpisodesEmailToAllInterestedUsers() {

		log.info("Sending Latest Podcast Episodes to all interested users");

		List<FeedsAppUser> interestedUsers = users.whoAreInterestedInTheLatestPodcastEpisodesEmail();

		for (FeedsAppUser interestedUser : interestedUsers) {

			List<InboxPodcastEpisode> episodes = inboxPodcastEpisodeRepository
					.findAllByUsernameAndReceivedDateTimeGreaterThan(interestedUser.getUsername(),
							LocalDateTime.now().minusDays(1));

			SimpleMailMessage message = new SimpleMailMessage();
			message.setFrom("teggr-feeds-app@heroku.com");
			message.setTo(interestedUser.getEmail());
			message.setSubject("Podcast Update Summary");
			message.setText(createText(episodes));
			javaMailSender.send(message);

		}

		log.info("Completed Sending Latest Podcast Episodes to all interested users");

	}

	private String createText(List<InboxPodcastEpisode> episodes) {
		if (episodes.isEmpty()) {
			return "No new episodes";
		} else {
			StringBuilder builder = new StringBuilder();
			for (InboxPodcastEpisode episode : episodes) {
				builder.append(String.format("%s", episode.getEpisodeTitle()));
				builder.append("\n");
			}
			return builder.toString();
		}
	}

}
