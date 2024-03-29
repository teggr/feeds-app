package com.robintegg.feedsapp.users;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties("user")
@Data
public class UserProperties {

	private String email;

}
