package org.entitylabs.app.role.service.impl;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.entitylabs.app.role.domain.Role;
import org.entitylabs.app.role.dto.RoleDTO;
import org.entitylabs.app.role.exception.RoleAlreadyExistsException;
import org.entitylabs.app.role.exception.RoleNotFoundException;
import org.entitylabs.app.role.repository.RoleRepository;
import org.entitylabs.app.role.util.DomainMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DisplayName("Test for user roles")
@ExtendWith(SpringExtension.class)
class RoleServiceImplTest {

	@InjectMocks
	private RoleServiceImpl roleServiceImpl;

	@Mock
	private RoleRepository roleRepository;

	@Mock
	private DomainMapper domainMapper;

	private List<Role> roles;

	private List<RoleDTO> roleDTOs;

	private RoleDTO inputRoleDTO;

	private RoleDTO outputRoleDTO;

	private Role toBeSavedRole;

	private Role savedRole;

	@BeforeEach
	public void createData() {

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

		inputRoleDTO = RoleDTO.builder().available(true).code("SU").createdOn(ZonedDateTime.now())
				.description("Super User").name("SuperUser")
				.id(UUID.randomUUID().toString())
				.build();

		toBeSavedRole = Role.builder().available(true).code("SU").createdOn(ZonedDateTime.now())
				.description("Super User").name("SuperUser").build();

		savedRole = Role.builder().available(true).code("SU").createdOn(ZonedDateTime.now())
				.id(UUID.randomUUID().toString()).description("Super User").name("SuperUser").build();

		outputRoleDTO = RoleDTO.builder().available(true).code("SU").createdOn(ZonedDateTime.now())
				.id(savedRole.getId()).description("Super User").name("SuperUser").build();
	}

	@Test
	@DisplayName("Fetch user roles success")
	void test_fetchAll_roles_success() {

		when(this.roleRepository.findAll()).thenReturn(Flux.fromStream(roles.stream()));
		
		when(domainMapper.roleDomainToDTO(roles.get(0))).thenReturn(roleDTOs.get(0));
		when(domainMapper.roleDomainToDTO(roles.get(1))).thenReturn(roleDTOs.get(1));
		
		StepVerifier.create(roleServiceImpl.fetchAllRoles())
		.expectNextMatches(role-> role.getId().equals(roles.get(0).getId()))
		.expectNextMatches(role-> role.getId().equals(roles.get(1).getId()))
		.verifyComplete();
		
	}

	@Test
	@DisplayName("Add user role success")
	void test_add_user_role_success() {

		when(this.roleRepository.save(toBeSavedRole)).thenReturn(Mono.just(savedRole));
		
		when(this.roleRepository.findByCode(inputRoleDTO.getCode())).thenReturn(Mono.empty());
		
		when(domainMapper.roleToDomain(inputRoleDTO, true)).thenReturn(toBeSavedRole);
		
		when(domainMapper.roleDomainToDTO(savedRole)).thenReturn(outputRoleDTO);
		
		StepVerifier.create(roleServiceImpl.addRole(inputRoleDTO))
		.verifyComplete();
		
	}

	@Test
	@DisplayName("Added user already exists")
	void test_add_user_role_failure() {

		when(this.roleRepository.findByCode(inputRoleDTO.getCode())).thenReturn(Mono.just(savedRole));
		
		when(domainMapper.roleDomainToDTO(savedRole)).thenReturn(outputRoleDTO);
		
		when(this.roleRepository.save(savedRole)).thenReturn(Mono.just(savedRole));
		
		StepVerifier.create(roleServiceImpl.addRole(inputRoleDTO))
		.expectError(RoleAlreadyExistsException.class);
		
	}
	
	
	@Test
	@DisplayName("Delete user success")
	void test_delete_user_role_success() {

		when(this.roleRepository.findById(inputRoleDTO.getId())).thenReturn(Mono.just(savedRole));
		
		when(this.roleRepository.save(any())).thenReturn(Mono.just(savedRole));
		
		StepVerifier.create(roleServiceImpl.deleteRole(inputRoleDTO.getId()))
		.expectNextCount(0)
		.verifyComplete();
		
	}
	
	@Test
	@DisplayName("Delete user role does not existing")
	void test_delete_user_role_failure() {

		when(this.roleRepository.findById(inputRoleDTO.getId())).thenReturn(Mono.empty());
		
		StepVerifier.create(roleServiceImpl.deleteRole(inputRoleDTO.getId()))
		.expectError(RoleNotFoundException.class);
		
	}
	
	
	@Test
	@DisplayName("Fetch user by role success")
	void test_fetch_user_role_by_code_success() {

		when(this.roleRepository.findByCode(inputRoleDTO.getCode())).thenReturn(Mono.just(savedRole));
		
		when(domainMapper.roleDomainToDTO(savedRole)).thenReturn(outputRoleDTO);
		
		StepVerifier.create(roleServiceImpl.fetchRoleByCode(inputRoleDTO.getCode()))
		.expectNextMatches(role-> role.getId().equals(savedRole.getId()))
		.verifyComplete();
		
	}
	
	@Test
	@DisplayName("Fetch user by role failure")
	void test_fetch_user_role_by_code_failure() {

		when(this.roleRepository.findByCode(inputRoleDTO.getCode())).thenReturn(Mono.empty());
		
		StepVerifier.create(roleServiceImpl.fetchRoleByCode(inputRoleDTO.getCode()))
		.expectError(RoleNotFoundException.class);
		
	}
	
	@Test
	@DisplayName("Update user by id success")
	void test_update_user_role_by_id_success() {

		when(this.roleRepository.findById(inputRoleDTO.getId())).thenReturn(Mono.just(savedRole));
		
		when(this.roleRepository.save(savedRole)).thenReturn(Mono.just(savedRole));
		
		when(domainMapper.roleDomainToDTO(savedRole)).thenReturn(outputRoleDTO);
		
		StepVerifier.create(roleServiceImpl.updateRole(inputRoleDTO))
		.expectNextMatches(role-> role.getId().equals(savedRole.getId()))
		.verifyComplete();
		
	}
	
	@Test
	@DisplayName("Update user by id failure")
	void test_update_user_role_by_id_failure() {

		when(this.roleRepository.findById(inputRoleDTO.getId())).thenReturn(Mono.empty());
		
		StepVerifier.create(roleServiceImpl.updateRole(inputRoleDTO))
		.expectError(RoleNotFoundException.class);
		
	}
	

}
