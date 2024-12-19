package africa.semicolon.election_management_system.data.repositories;

import africa.semicolon.election_management_system.data.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {
    boolean existsByUsername(String username);

    Optional<Admin> findByUsername(String username);
}
