package com.example.mvcdemo2;

import com.example.mvcdemo2.model.Admin;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AdminTest {

    @Test
    public void testConstructorAndGetters() {
        Admin admin = new Admin(1, "adminUser");

        assertEquals(1, admin.getId());
        assertEquals("adminUser", admin.getUsername());
    }

    @Test
    public void testSetters() {
        Admin admin = new Admin();
        admin.setId(2);
        admin.setUsername("newAdminUser");

        assertEquals(2, admin.getId());
        assertEquals("newAdminUser", admin.getUsername());
    }

    @Test
    public void testToString() {
        Admin admin = new Admin(3, "toStringUser");
        String expected = "Admin{id=3, username='toStringUser'}";

        assertEquals(expected, admin.toString());
    }

    @Test
    public void testDefaultConstructor() {
        Admin admin = new Admin();

        assertNull(admin.getId());
        assertNull(admin.getUsername());
    }
}
