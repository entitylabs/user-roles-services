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

	private String id;
	
	@NotEmpty
	private String code;
	
	private String name;
	
	@NotEmpty
	private String description;
	
	private Boolean available;
	
	private ZonedDateTime createdOn;
	
	private ZonedDateTime modifiedOn;
	
}
