package ar.edu.itba.cleancode.resilientbackend.usermanager;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    // You can add custom query methods here if needed
}
