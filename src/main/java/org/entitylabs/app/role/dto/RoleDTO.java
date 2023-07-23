package org.entitylabs.app.role.dto;

import java.time.ZonedDateTime;

import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;
import lombok.Data;

/**
 * 
 * @author sanyam
 *
 */
@Data
@Builder
public class RoleDTO {

	private final String id;
	
	@NotEmpty
	private final String code;
	
	private final String name;
	
	@NotEmpty
	private final String description;
	
	private final Boolean available;
	
	private final ZonedDateTime createdOn;
	
	private final ZonedDateTime modifiedOn;
	
}
