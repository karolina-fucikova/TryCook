package cz.upce.trycook;

import cz.upce.trycook.entity.User;
import cz.upce.trycook.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.security.crypto.password.PasswordEncoder;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {
            User testUser = new User();
            testUser.setUsername("karolina_studentka");
            testUser.setEmail("karolina@student.upce.cz");
            testUser.setPassword(passwordEncoder.encode("tajneheslo123"));
            testUser.setRole("ROLE_USER");

            userRepository.save(testUser);
            System.out.println("Testovací uživatel byl vytvořen se zašifrovaným heslem.");
        }
    }
}