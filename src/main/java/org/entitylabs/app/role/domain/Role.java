package org.entitylabs.app.role.domain;

import java.time.ZonedDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
@Document(collection = "roles")
public class Role {

	@Id
	private String id;

	@NotBlank
	private String code;

	@NotBlank
	private String name;
	private String description;

	private boolean available;

	@CreatedDate
	private ZonedDateTime createdOn;

	@LastModifiedDate
	private ZonedDateTime modifiedOn;

}
