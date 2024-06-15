package site.mutopia.server.domain.user.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import site.mutopia.server.domain.user.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void withdraw() {
        userRepository.findById("bab198e8-5151-4c37-8788-b9463088156d").ifPresent(user -> {
            userService.withdrawUser(user);
        });
    }
}