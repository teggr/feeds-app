package com.robintegg.feedsapp.subscriptions;

import lombok.Value;

@Value
public class Subscription {

	static Subscription fromEntity(SubscriptionEntity se) {
		return new Subscription(se.getId());
	}

	Long id;

}
