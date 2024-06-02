package com.example.mvcdemo2;

import com.example.mvcdemo2.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    @Test
    public void testSetId() {
        User user = new User(1, "testName", "testPassword");
        user.setId(2);
        assertEquals(2, user.getId());
    }

    @Test
    public void testSetName() {
        User user = new User(1, "testName", "testPassword");
        user.setName("newName");
        assertEquals("newName", user.getName());
    }

    @Test
    public void testSetPassword() {
        User user = new User(1, "testName", "testPassword");
        user.setPassword("newPassword");
        assertEquals("newPassword", user.getPassword());
    }

    @Test
    public void testConstructor() {
        User user = new User(1, "testName", "testPassword");
        assertEquals(1, user.getId());
        assertEquals("testName", user.getName());
        assertEquals("testPassword", user.getPassword());
    }
}
