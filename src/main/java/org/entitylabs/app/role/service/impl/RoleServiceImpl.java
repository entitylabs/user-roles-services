package org.entitylabs.app.role.service.impl;

import java.time.ZonedDateTime;

import org.entitylabs.app.role.constant.ErrorMessage;
import org.entitylabs.app.role.domain.Role;
import org.entitylabs.app.role.dto.RoleDTO;
import org.entitylabs.app.role.exception.RoleAlreadyExistsException;
import org.entitylabs.app.role.exception.RoleNotFoundException;
import org.entitylabs.app.role.repository.RoleRepository;
import org.entitylabs.app.role.service.RoleService;
import org.entitylabs.app.role.util.DomainMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {

	private final RoleRepository roleRepository;

	private final DomainMapper domainMapper;

	@Override
	public Mono<RoleDTO> fetchRoleById(String id) {

		return roleRepository.findById(id)
				.filter(role-> role.isAvailable())
				.map(role -> domainMapper.roleDomainToDTO(role)).switchIfEmpty(
				Mono.error(new RoleNotFoundException(String.format(ErrorMessage.ROLE_WITH_ID_NOT_FOUND, id))));
	}

	@Override
	public Flux<RoleDTO> fetchAllRoles() {

		return roleRepository.findAll()
				.filter(role-> role.isAvailable())
				.map(role -> domainMapper.roleDomainToDTO(role));

	}

	@Override
	public Mono<Void> addRole(RoleDTO role) {

		return this.roleRepository.findByCode(role.getCode())
				.flatMap(foundRole -> Mono.error(new RoleAlreadyExistsException(
						String.format(ErrorMessage.ROLE_WITH_CODE_ALREADY_EXISTS, role.getCode()))))
				.switchIfEmpty(Mono.defer(() -> Mono.just(role))).log()
				.flatMap(roleDTO -> Mono.just(domainMapper.roleToDomain(role, true)))
				.flatMap(roleUpdated -> this.roleRepository.save(roleUpdated))
				.then();

	}

	@Override
	public Mono<Void> deleteRole(String id) {

		return this.roleRepository.findById(id)
				.switchIfEmpty(
						Mono.error(new RoleNotFoundException(String.format(ErrorMessage.ROLE_WITH_ID_NOT_FOUND, id))))
				.map(role -> role.toBuilder().available(false).build())
				.flatMap(role -> this.roleRepository.save(role))
				.log()
				.then();
	}

	@Override
	public Mono<RoleDTO> updateRole(RoleDTO role) {

		return this.roleRepository.findById(role.getId()).map(savedRole -> updateRoleData(savedRole, role))
				.flatMap(savedRole -> this.roleRepository.save(savedRole))
				.map(roleUpdated -> domainMapper.roleDomainToDTO(roleUpdated)).switchIfEmpty(Mono.error(
						new RoleNotFoundException(String.format(ErrorMessage.ROLE_WITH_ID_NOT_FOUND, role.getId()))));

	}

	@Override
	public Mono<RoleDTO> fetchRoleByCode(String code) {

		return this.roleRepository.findByCode(code).filter(role-> role.isAvailable())
				.map(roleUpdated -> domainMapper.roleDomainToDTO(roleUpdated)).switchIfEmpty(Mono
						.error(new RoleNotFoundException(String.format(ErrorMessage.ROLE_WITH_CODE_NOT_FOUND, code))));
	}

	private Role updateRoleData(Role savedRole, RoleDTO receivedRole) {

		if (StringUtils.hasText(receivedRole.getDescription())) {
			
			savedRole.setDescription(receivedRole.getDescription());
		}

		if (StringUtils.hasText(receivedRole.getName())) {
			savedRole.setName(receivedRole.getName());
		}

		savedRole.setModifiedOn(ZonedDateTime.now());

		return savedRole;
	}

}
