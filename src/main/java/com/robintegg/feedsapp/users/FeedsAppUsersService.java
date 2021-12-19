package com.robintegg.feedsapp.users;

import java.util.List;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class FeedsAppUsersService implements FeedsAppUsers {

	private final FeedAppUsersRepository feedAppUsersRepository;

	@Override
	public List<FeedsAppUser> whoAreInterestedInTheLatestPodcastEpisodesEmail() {
		return feedAppUsersRepository.findAll();
	}

}
