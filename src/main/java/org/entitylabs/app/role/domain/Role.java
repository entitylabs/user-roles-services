package org.entitylabs.app.role.domain;

import java.time.ZonedDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder=true)
@Document (collection = "roles")
public class Role {

	@Id
	private String id;
	private String code;
	private String name;
	private String description;
	private boolean available;
	private ZonedDateTime createdOn;
	private ZonedDateTime modifiedOn;
	
}
