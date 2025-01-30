package org.burgas.identityservice.repository;

import org.burgas.identityservice.entity.Identity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdentityRepository extends JpaRepository<Identity, Long> {

    Optional<Identity> findIdentityByEmail(String email);
}
