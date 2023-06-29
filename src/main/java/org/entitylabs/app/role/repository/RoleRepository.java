package org.entitylabs.app.role.repository;

import org.entitylabs.app.role.domain.Role;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import reactor.core.publisher.Mono;

public interface RoleRepository extends ReactiveMongoRepository<Role, String> {

	Mono<Role> findByCode(final String code);
}
