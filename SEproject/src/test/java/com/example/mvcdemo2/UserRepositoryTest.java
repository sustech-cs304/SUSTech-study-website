package com.example.mvcdemo2;

import com.example.mvcdemo2.model.Users;
import com.example.mvcdemo2.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void testFindAllUsers() {
        // Given
        Users user1 = new Users(null, "user1", "password1");
        Users user2 = new Users(null, "user2", "password2");
        userRepository.save(user1);
        userRepository.save(user2);

        // When
        List<Users> users = userRepository.findAllUsers();

        // Then
        assertNotNull(users);
        assertEquals(2, users.size());
    }

    @Test
    public void testFindByUsername() {
        // Given
        Users user = new Users(null, "testuser", "password");
        userRepository.save(user);

        // When
        Users foundUser = userRepository.findByUsername("testuser");

        // Then
        assertNotNull(foundUser);
        assertEquals("testuser", foundUser.getName());
    }

    @Test
    public void testSaveUser() {
        // Given
        String username = "newuser";
        String password = "newpassword";

        // When
        userRepository.saveUser(username, password);

        // Then
        Users savedUser = userRepository.findByUsername(username);
        assertNotNull(savedUser);
        assertEquals(username, savedUser.getName());
        assertEquals(password, savedUser.getPassword());
    }

    @Test
    public void testUpdatePassword() {
        // Given
        Users user = new Users(null, "userToUpdate", "oldPassword");
        userRepository.save(user);
        String newPassword = "newPassword";

        // When
        userRepository.updatePassword("userToUpdate", newPassword);

        // Ensure the update is applied by clearing the persistence context and fetching fresh data
        entityManager.flush();
        entityManager.clear();

        // Then
        Users updatedUser = userRepository.findByUsername("userToUpdate");
        assertNotNull(updatedUser);
        assertEquals(newPassword, updatedUser.getPassword());
    }
}
