package com.robintegg.feedsapp.users;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.provisioning.JdbcUserDetailsManager;

@Configuration
public class UserConfiguration {

	@Bean
	public JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {

		return new JdbcUserDetailsManager(dataSource);

	}

}
