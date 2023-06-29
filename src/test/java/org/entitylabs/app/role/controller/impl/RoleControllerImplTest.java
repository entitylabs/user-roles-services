package org.entitylabs.app.role.controller.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.entitylabs.app.role.dto.RoleDTO;
import org.entitylabs.app.role.exception.RoleNotFoundException;
import org.entitylabs.app.role.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@DisplayName("Role controller test")
@WebFluxTest(controllers = RoleControllerImpl.class, excludeAutoConfiguration = ReactiveSecurityAutoConfiguration.class)
class RoleControllerImplTest {

	@Autowired
	private WebTestClient webClient;

	private List<RoleDTO> roleDTOs;

	private RoleDTO inputRoleDTO;

	private RoleDTO outputRoleDTO;

	@MockBean
	RoleService roleService;

	@BeforeEach
	public void createData() {

		roleDTOs = List.of(
				RoleDTO.builder().available(true).code("ADMIN").createdOn(ZonedDateTime.now()).description("Admin role")
						.id(UUID.randomUUID().toString()).name("Administrator").build(),
				RoleDTO.builder().available(true).code("USER").createdOn(ZonedDateTime.now()).description("User role")
						.id(UUID.randomUUID().toString()).name("User").build());

		inputRoleDTO = RoleDTO.builder().available(true).code("SU").createdOn(ZonedDateTime.now())
				.id(UUID.randomUUID().toString()).description("Super User").name("SuperUser").build();

		outputRoleDTO = RoleDTO.builder().available(true).code("SU").createdOn(ZonedDateTime.now())
				.id(UUID.randomUUID().toString()).description("Super User").name("SuperUser").build();
	}

	@Test
	@DisplayName("Fetch all success")
	void test_fetch_list_ok() {
		
		when(roleService.fetchAllRoles()).thenReturn(Flux.fromStream(roleDTOs.stream()));
		
		webClient.get().uri("/v1/user/roles").exchange().expectStatus().isOk();
	}

	@Test
	@DisplayName("Fetch role by id success")
	void test_fetch_role_by_id_success() {
		
		when(roleService.fetchRoleById(inputRoleDTO.getId())).thenReturn(Mono.just(inputRoleDTO));
		
		webClient.get().uri(uriBuilder-> uriBuilder.path("/v1/user/roles/".concat(inputRoleDTO.getId().toString()))
				.build()).exchange()
		.expectStatus().isOk()
		.expectBody(RoleDTO.class)
		.value(role-> assertEquals(role.getId(), inputRoleDTO.getId()));
	}

	@Test
	@DisplayName("Fetch role by id failure")
	void test_fetch_role_by_id_failure() {
		
		when(roleService.fetchRoleById(inputRoleDTO.getId())).thenThrow(RoleNotFoundException.class);
		
		webClient.get().uri(uriBuilder-> uriBuilder.path("/v1/user/roles/".concat(inputRoleDTO.getId().toString()))
				.build()).exchange()
		.expectStatus().isNotFound();
	}

}
