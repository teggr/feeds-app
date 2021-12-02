package com.robintegg.feedsapp.subscriptions;

import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "subscriptions")
@NoArgsConstructor
@Getter
@Setter
class SubscriptionEntity {

	public static SubscriptionEntity forPodcast(Long podcastId) {
		SubscriptionEntity subscription = new SubscriptionEntity();
		subscription.podcastId = podcastId;
		subscription.startDate = ZonedDateTime.now();
		return subscription;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;

	@Column(name = "podcast_id", nullable = false)
	private Long podcastId;

	@Column(name = "start_date")
	private ZonedDateTime startDate;

	@Column(name = "last_updated")
	private ZonedDateTime lastUpdated = null;

}
