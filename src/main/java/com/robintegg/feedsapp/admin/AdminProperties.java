package com.robintegg.feedsapp.admin;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties("admin")
@Data
public class AdminProperties {

	private String email;

}
