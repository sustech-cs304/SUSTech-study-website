package com.example.mvcdemo2;

import com.example.mvcdemo2.model.History;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HistoryTest {

    @Test
    public void testConstructorAndGetters() {
        History history = new History(1, "user1", "some data", 100);

        assertEquals(1, history.getId());
        assertEquals("user1", history.getUsername());
        assertEquals("some data", history.getData());
        assertEquals(100, history.getSetID());
    }

    @Test
    public void testSetters() {
        History history = new History();
        history.setId(2);
        history.setUsername("user2");
        history.setData("other data");
        history.setSetID(200);

        assertEquals(2, history.getId());
        assertEquals("user2", history.getUsername());
        assertEquals("other data", history.getData());
        assertEquals(200, history.getSetID());
    }

    @Test
    public void testToString() {
        History history = new History(3, "user3", "more data", 300);
        String expected = "History{id=3, username='user3', data='more data', setID=300}";

        assertEquals(expected, history.toString());
    }

    @Test
    public void testDefaultConstructor() {
        History history = new History();

        assertNull(history.getId());
        assertNull(history.getUsername());
        assertNull(history.getData());
        assertNull(history.getSetID());
    }
}
