package org.entitylabs.app.role.controller;

import org.entitylabs.app.role.dto.RoleDTO;

import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 
 * @author sanyam
 * @version 1.0
 * 
 *          This controller end points for performing CRUD operations on Roles
 *          reactively
 *
 */
public interface RoleController {

	/**
	 * 
	 * @param id
	 */
	Mono<RoleDTO> fetchRoleById(@Parameter(description = "Role id") @NotEmpty final String id);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	Mono<RoleDTO> fetchRoleByCode(@Parameter(description = "Role code") @NotEmpty final String code);

	/**
	 * 
	 */
	Flux<RoleDTO> fetchAllRoles();

	/**
	 * 
	 * @param role
	 */
	Mono<Void> addRole(@Parameter(description = "Role object") @NotNull @Valid final RoleDTO role);

	/**
	 * 
	 * @param id
	 */
	Mono<Void> deleteRole(@Parameter(description = "Role id") @NotEmpty final String id);

	/**
	 * 
	 * @param role
	 */
	Mono<RoleDTO> updateRole(@Parameter(description = "Role object") @Valid @NotNull final RoleDTO role,
			@Parameter(description = "Role id") @NotEmpty final String id);
}
