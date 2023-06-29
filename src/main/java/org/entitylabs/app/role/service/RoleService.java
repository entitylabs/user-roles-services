package org.entitylabs.app.role.service;

import org.entitylabs.app.role.dto.RoleDTO;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoleService {

	Mono<RoleDTO> fetchRoleById(final String id);
	
	Mono<RoleDTO> fetchRoleByCode(final String code);

	Flux<RoleDTO>  fetchAllRoles();

	Mono<RoleDTO>  addRole(final RoleDTO role);

	Mono<RoleDTO> deleteRole(final String id);

	Mono<RoleDTO> updateRole(final RoleDTO role);
}
