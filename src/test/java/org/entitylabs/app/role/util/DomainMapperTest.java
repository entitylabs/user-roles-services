package org.entitylabs.app.role.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.entitylabs.app.role.domain.Role;
import org.entitylabs.app.role.dto.RoleDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("Domain mapper test")
class DomainMapperTest {

	private DomainMapper domainMapper;

	private List<Role> roles;

	private List<RoleDTO> roleDTOs;

	@BeforeEach
	public void createData() {

		this.domainMapper = new DomainMapper();

		roles = List.of(
				Role.builder().available(true).code("ADMIN").createdOn(ZonedDateTime.now()).description("Admin role")
						.id(UUID.randomUUID().toString()).name("Administrator").build(),
				Role.builder().available(true).code("USER").createdOn(ZonedDateTime.now()).description("User role")
						.id(UUID.randomUUID().toString()).name("User").build());

		roleDTOs = List.of(
				RoleDTO.builder().available(true).code("ADMIN").createdOn(ZonedDateTime.now()).description("Admin role")
						.id(roles.get(0).getId()).name("Administrator").build(),
				RoleDTO.builder().available(true).code("USER").createdOn(ZonedDateTime.now()).description("User role")
						.id(roles.get(1).getId()).name("User").build());

	}

	@DisplayName("Domain mapper to dto success")
	@Test
	public void test_domainMapper_toDTO() {

		assertEquals(domainMapper.roleDomainToDTO(roles.get(0)).getCode(), roles.get(0).getCode());
		assertEquals(domainMapper.roleDomainToDTO(roles.get(0)).getDescription(), roles.get(0).getDescription());

	}

	@DisplayName("Domain mapper to domain success")
	@Test
	public void test_domainMapper_toDomain() {

		assertEquals(domainMapper.roleToDomain(roleDTOs.get(0), true).getCode(), roles.get(0).getCode());
		assertEquals(domainMapper.roleToDomain(roleDTOs.get(0), true).getDescription(), roles.get(0).getDescription());

	}

}
