package pl.javastart.mailapp.user;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface UserRepository extends ListCrudRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByToken(String token);
}
