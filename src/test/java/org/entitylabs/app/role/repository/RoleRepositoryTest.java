package org.entitylabs.app.role.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.ZonedDateTime;
import java.util.UUID;

import org.entitylabs.app.role.configuration.DatabaseConfiguration;
import org.entitylabs.app.role.domain.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@DisplayName("Role repository tests")
@DataMongoTest
@Import(DatabaseConfiguration.class)
@TestPropertySource(locations="file:/Users/sanyam/Documents/workspace/user-roles-services/src/test/resources/application.yml")
class RoleRepositoryTest {

	@Autowired
	private RoleRepository roleRepository;

	private Role toBeSavedRole;

	private Role savedRole;

	@BeforeEach
	public void createData() {

		toBeSavedRole = Role.builder().available(true).code("SU").createdOn(ZonedDateTime.now())
				.description("Super User").name("SuperUser").build();

		savedRole = Role.builder().available(true).code("SU").createdOn(ZonedDateTime.now())
				.id(UUID.randomUUID().toString()).description("Super User").name("SuperUser").build();

	}

	@Test
	@DisplayName("Role repository empty success")
	void test_role_repo_empty_success() {

		Mono<Role> role = roleRepository.findByCode(toBeSavedRole.getCode());

		Publisher<Role> pub = this.roleRepository.deleteAll().then(role);

		StepVerifier.create(pub).expectNextCount(0).verifyComplete();
	}

	@Test
	@DisplayName("Role repository data success")
	void test_role_repo_data_success() {

		Publisher<Role> role = this.roleRepository.deleteAll().then(this.roleRepository.save(savedRole));

		Mono<Role> findByCode = roleRepository.findByCode(toBeSavedRole.getCode());

		Publisher<Role> composite = Mono.from(role).then(findByCode);

		this.roleRepository.deleteAll();

		StepVerifier.create(composite).consumeNextWith(roleInRepo -> {

			assertEquals(roleInRepo.getCode(), toBeSavedRole.getCode());
			assertEquals(roleInRepo.getDescription(), toBeSavedRole.getDescription());
			assertNotNull(roleInRepo.getId());
		}).verifyComplete();
	}

}
