package com.example.mvcdemo2;

import com.example.mvcdemo2.model.GPA;
import com.example.mvcdemo2.repository.GPARepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
public class GPARepositoryTest {

    @Autowired
    private GPARepository gpaRepository;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void testSaveGPA() {
        // Given
        String username = "testuser";
        String d3x = "defaultD3X";
        GPA gpa = new GPA();
        gpa.setName(username);
        gpa.setD1S("defaultD1S");
        gpa.setD1X("defaultD1X");
        gpa.setD2S("defaultD2S");
        gpa.setD2X("defaultD2X");
        gpa.setD3S("testD3S");
        gpa.setD3X("defaultD3X");
        gpa.setD4S("defaultD4S");
        gpa.setD4X("defaultD4X");
        gpaRepository.save(gpa);

        // When
        gpaRepository.saveGPA(username, d3x);
        entityManager.flush();  // Ensure updates are flushed to the database

        // Then
        GPA retrievedGpa = gpaRepository.findByUsername(username);
        assertNotNull(retrievedGpa);
        assertEquals(d3x, retrievedGpa.getD3X());
    }

    @Test
    public void testFindByUsername() {
        // Given
        GPA gpa = new GPA();
        gpa.setName("testuser");
        gpa.setD1S("defaultD1S");
        gpa.setD1X("defaultD1X");
        gpa.setD2S("defaultD2S");
        gpa.setD2X("defaultD2X");
        gpa.setD3S("testD3S");
        gpa.setD3X("defaultD3X");
        gpa.setD4S("defaultD4S");
        gpa.setD4X("defaultD4X");
        gpaRepository.save(gpa);

        // When
        GPA retrievedGpa = gpaRepository.findByUsername("testuser");

        // Then
        assertNotNull(retrievedGpa);
        assertEquals("testuser", retrievedGpa.getName());
    }
}
