package org.entitylabs.app.role.configuration;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableMongoAuditing
public class DatabaseConfiguration {

	@Bean
	MongoCustomConversions mongoCustomConversions() {

		log.info("DatabaseConfiguration::mongoCustomConversions::called");

		return new MongoCustomConversions(
				Arrays.asList(new ZonedDateTimeReadConverter(), new ZonedDateTimeWriteConverter()));
	}
}