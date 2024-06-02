package com.example.mvcdemo2;

import com.example.mvcdemo2.model.History;
import com.example.mvcdemo2.repository.HistoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import jakarta.transaction.Transactional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@Transactional
public class HistoryRepositoryTest {

    @Autowired
    private HistoryRepository historyRepository;

    @Test
    public void testFindAllHistory() {
        // Given
        History history1 = new History(null, "user1", "data1", 1);
        History history2 = new History(null, "user2", "data2", 2);
        historyRepository.save(history1);
        historyRepository.save(history2);

        // When
        List<History> histories = historyRepository.findAllHistory();

        // Then
        assertNotNull(histories);
        assertEquals(2, histories.size());
    }

    @Test
    public void testFindByUsername() {
        // Given
        History history = new History(null, "testuser", "data", 1);
        historyRepository.save(history);

        // When
        List<History> histories = historyRepository.findByUsername("testuser");

        // Then
        assertNotNull(histories);
        assertEquals(1, histories.size());
        assertEquals("testuser", histories.get(0).getUsername());
    }

    @Test
    public void testFindHisByNameAndID() {
        // Given
        History history = new History(null, "testuser", "data", 1);
        historyRepository.save(history);

        // When
        List<History> histories = historyRepository.findHisByNameAndID("testuser", 1);

        // Then
        assertNotNull(histories);
        assertEquals(1, histories.size());
        assertEquals("testuser", histories.get(0).getUsername());
        assertEquals(1, histories.get(0).getSetID());
    }

    @Test
    public void testSaveAnswers() {
        // Given
        String username = "newuser";
        String data = "newdata";
        Integer setId = 2;

        // When
        historyRepository.saveAnswers(username, data, setId);

        // Then
        List<History> histories = historyRepository.findHisByNameAndID(username, setId);
        assertNotNull(histories);
        assertEquals(1, histories.size());
        assertEquals(username, histories.get(0).getUsername());
        assertEquals(data, histories.get(0).getData());
        assertEquals(setId, histories.get(0).getSetID());
    }
}
