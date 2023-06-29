package org.entitylabs.app.role.controller.impl;

import org.entitylabs.app.role.controller.RoleController;
import org.entitylabs.app.role.dto.RoleDTO;
import org.entitylabs.app.role.service.RoleService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RestController
@RequestMapping("v1/user/roles")
@AllArgsConstructor
public class RoleControllerImpl implements RoleController {

	private final RoleService roleService;

	@GetMapping(path = "/{id}")
	@Override
	public Mono<RoleDTO> fetchRoleById(@PathVariable final String id) {

		log.info("RoleControllerImpl.fetchRoleById.called");

		return this.roleService.fetchRoleById(id);
	}

	@GetMapping
	@Override
	public Flux<RoleDTO> fetchAllRoles() {

		log.info("RoleControllerImpl.fetchALlRoles.called");

		return this.roleService.fetchAllRoles();
	}

	@PostMapping
	@Override
	public Mono<RoleDTO> addRole(@RequestBody final RoleDTO role) {

		System.out.println(role);

		log.info("RoleControllerImpl.addRole.called");

		return this.roleService.addRole(role);
	}

	@DeleteMapping(path = "/{id}")
	@Override
	public Mono<RoleDTO> deleteRole(@PathVariable final String id) {

		log.info("RoleControllerImpl.deleteRole.called");

		return this.roleService.deleteRole(id);
	}

	@PatchMapping(path = "/{id}")
	@Override
	public Mono<RoleDTO> updateRome(@RequestBody final RoleDTO role, @PathVariable final String id) {

		log.info("RoleControllerImpl.updateRome.called");

		return this.roleService.updateRole(role);
	}

	@GetMapping(value = "/role")
	@Override
	public Mono<RoleDTO> fetchRoleByCode(@RequestParam String code) {
		
		log.info("RoleControllerImpl.fetchRoleByCode.called");

		return this.roleService.fetchRoleByCode(code);
	}

}
