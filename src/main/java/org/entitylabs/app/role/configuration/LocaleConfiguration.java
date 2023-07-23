package org.entitylabs.app.role.configuration;

import java.util.TimeZone;

import org.entitylabs.app.commons.AppConstant;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LocaleConfiguration {

	@PostConstruct
	public void seTimezone() {

		log.info("LocaleConfiguration::seTimezone::called");

		TimeZone.setDefault(TimeZone.getTimeZone(AppConstant.APPLICATION_ZONE_OFFSET));
	}
}
