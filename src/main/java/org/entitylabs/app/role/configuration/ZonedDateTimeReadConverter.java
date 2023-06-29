package org.entitylabs.app.role.configuration;

import java.time.ZonedDateTime;
import java.util.Date;

import org.entitylabs.app.role.constant.AppConstant;
import org.springframework.core.convert.converter.Converter;

public class ZonedDateTimeReadConverter implements Converter<Date, ZonedDateTime> {

	@Override
	public ZonedDateTime convert(Date date) {
		
		return date.toInstant().atZone(AppConstant.APPLICATION_ZONE_OFFSET);
	}
}	