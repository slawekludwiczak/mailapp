package pl.javastart.mailapp.user;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.javastart.mailapp.mail.MailService;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private static final String USER_ROLE = "USER";
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    public UserService(UserRepository userRepository,
                       UserRoleRepository userRoleRepository,
                       PasswordEncoder passwordEncoder, MailService mailService) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailService = mailService;
    }

    public void register(UserRegistrationDto registration) {
        User user = new User();
        user.setFirstName(registration.getFirstName());
        user.setLastName(registration.getLastName());
        user.setEmail(registration.getEmail());
        String passwordHash = passwordEncoder.encode(registration.getPassword());
        user.setPassword(passwordHash);
        Optional<UserRole> userRole = userRoleRepository.findByName(USER_ROLE);
        userRole.ifPresentOrElse(
                role -> user.getRoles().add(role),
                () -> {
                    throw new NoSuchElementException();
                }
        );
        user.setToken(UUID.randomUUID().toString());
        userRepository.save(user);
        mailService.sendConfirmationEmail(user);
    }

    @Transactional
    public boolean confirmRegistration(String token) {
        Optional<User> userByToken = userRepository.findByToken(token);
        if (userByToken.isPresent()) {
            userByToken.get().activate();
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public void resetPassword(String email) {
        userRepository.findByEmail(email)
                .filter(User::isActive)
                .ifPresent(user -> {
                    user.setToken(UUID.randomUUID().toString());
                    mailService.sendPasswordResetLink(user);
                });
    }

    public Optional<User> findUserByToken(String token) {
        return userRepository.findByToken(token);
    }

    @Transactional
    public void changePassword(String token, String password) {
        userRepository.findByToken(token)
                .ifPresent(user -> {
                    String passwordHash = passwordEncoder.encode(password);
                    user.setPassword(passwordHash);
                    user.setToken(null);
                });
    }
}