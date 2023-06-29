package org.entitylabs.app.role.util;

import java.time.ZonedDateTime;

import org.entitylabs.app.role.domain.Role;
import org.entitylabs.app.role.dto.RoleDTO;
import org.springframework.stereotype.Component;

/**
 * 
 * @author sanyam
 *
 */
@Component
public class DomainMapper {

	/**
	 * 
	 * @param role
	 * @param isAvailable
	 * @param modifiedOn
	 * @param createdOn
	 * @return
	 */
//	public Role roleToDomain(final RoleDTO role, final boolean isAvailable, final ZonedDateTime modifiedOn,
//			final ZonedDateTime createdOn) {
//
//		return Role.builder().available(isAvailable).modifiedOn(modifiedOn).createdOn(createdOn)
//				.description(role.getDescription()).name(role.getName()).code(role.getCode()).build();
//	}

	/**
	 * 
	 * @param role
	 * @param isAvailable
	 * @return
	 */
	public Role roleToDomain(final RoleDTO role, final boolean isAvailable) {

		var timeNow = ZonedDateTime.now();
		return Role.builder().available(isAvailable).modifiedOn(timeNow).createdOn(timeNow)
				.description(role.getDescription()).name(role.getName()).code(role.getCode()).build();
	}

	/**
	 * 
	 * @param role
	 * @return
	 */
	public RoleDTO roleDomainToDTO(final Role role) {

		return RoleDTO.builder().available(role.isAvailable()).modifiedOn(role.getModifiedOn()).id(role.getId())
				.createdOn(role.getCreatedOn()).description(role.getDescription()).name(role.getName())
				.code(role.getCode()).build();
	}

}
