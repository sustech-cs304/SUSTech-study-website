package com.example.mvcdemo2;

import com.example.mvcdemo2.model.Admin;
import com.example.mvcdemo2.repository.AdminRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class AdminRepositoryTest {

    @Autowired
    private AdminRepository adminRepository;

    @Test
    public void testFindAdmin() {
        // Given
        Admin admin = new Admin(null, "testuser");
        adminRepository.save(admin);

        // When
        Admin foundAdmin = adminRepository.findAdmin("testuser");

        // Then
        assertNotNull(foundAdmin);
        assertEquals("testuser", foundAdmin.getUsername());
    }

    @Test
    public void testSaveAdmin() {
        // Given
        Admin admin = new Admin(null, "newuser");

        // When
        Admin savedAdmin = adminRepository.save(admin);

        // Then
        assertNotNull(savedAdmin.getId());
        assertEquals("newuser", savedAdmin.getUsername());
    }
}
