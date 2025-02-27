package pl.javastart.mailapp.user;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface UserRoleRepository extends ListCrudRepository<UserRole, Long> {
    Optional<UserRole> findByName(String userRole);
}
