package ng.ourChemo.data.repositories;

import ng.ourChemo.data.models.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest {

    @Test
    void newRepository() {
        UserRepository userRepository = new UserRepositoryImpl();
            assertEquals(0, userRepository.count());

    }

    @Test
    void saveNewUserCountIsOneTest() {
        UserRepository userRepository = new UserRepositoryImpl();
        User user = new User();
        User result = userRepository.save(user);
        assertEquals(1, userRepository.count());
    }

    @Test
    void saveTwoNewUsersAndDeleteOneCountIsOneTest() {
        UserRepository userRepository = new UserRepositoryImpl();
        User user = new User();
        User result = userRepository.save(user);
        User result2 = userRepository.save(user);
        assertEquals(2, userRepository.count());
        userRepository.delete(result);
        assertEquals(1, userRepository.count());
    }

}