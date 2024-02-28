package pl.edu.pw.zpoif.exchangeit.repositorytest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import pl.edu.pw.zpoif.exchangeit.model.user.User;
import pl.edu.pw.zpoif.exchangeit.repository.UserRepository;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setEmail("ravikumar@gmail.com");
        user.setPassword("ravi2020");
        user.setFirstName("Ravi");
        user.setLastName("Kumar");

        User savedUser = userRepository.save(user);
        entityManager.flush();

        assertNotNull(savedUser.getUserId());
        User existUser = entityManager.find(User.class, savedUser.getUserId());
        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());
        assertThat(user.getPassword()).isEqualTo(existUser.getPassword());
        assertThat(user.getFirstName()).isEqualTo(existUser.getFirstName());
        assertThat(user.getLastName()).isEqualTo(existUser.getLastName());
    }

    @Test
    public void testFindByEmailUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setFirstName("John");
        user.setLastName("Doe");
        userRepository.save(user);

        User foundUser = userRepository.findByEmail("test@example.com");

        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getEmail()).isEqualTo("test@example.com");
        assertThat(foundUser.getPassword()).isEqualTo("password");
        assertThat(foundUser.getFirstName()).isEqualTo("John");
        assertThat(foundUser.getLastName()).isEqualTo("Doe");
    }

    @Test
    public void testUpdateUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setFirstName("John");
        user.setLastName("Doe");
        userRepository.save(user);

        user.setFirstName("Jane");
        User updatedUser = userRepository.save(user);

        assertThat(updatedUser.getFirstName()).isEqualTo("Jane");
    }

    @Test
    public void testDeleteUser() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setFirstName("John");
        user.setLastName("Doe");
        userRepository.save(user);

        userRepository.deleteById(user.getUserId());

        assertThat(userRepository.findById(user.getUserId())).isEmpty();
    }

}
