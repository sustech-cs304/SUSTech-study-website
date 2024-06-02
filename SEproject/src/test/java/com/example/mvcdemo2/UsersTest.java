package com.example.mvcdemo2;

import com.example.mvcdemo2.model.Users;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UsersTest {

    @Test
    public void testConstructorAndGetters() {
        Users user = new Users(1, "user1", "password1");

        assertEquals(1, user.getId());
        assertEquals("user1", user.getName());
        assertEquals("password1", user.getPassword());
    }

    @Test
    public void testSetters() {
        Users user = new Users();
        user.setId(2);
        user.setName("user2");
        user.setPassword("password2");

        assertEquals(2, user.getId());
        assertEquals("user2", user.getName());
        assertEquals("password2", user.getPassword());
    }

    @Test
    public void testToString() {
        Users user = new Users(3, "user3", "password3");
        String expected = "Users{id=3, name='user3', password='password3'}";

        assertEquals(expected, user.toString());
    }

    @Test
    public void testDefaultConstructor() {
        Users user = new Users();

        assertNull(user.getId());
        assertNull(user.getName());
        assertNull(user.getPassword());
    }
}
