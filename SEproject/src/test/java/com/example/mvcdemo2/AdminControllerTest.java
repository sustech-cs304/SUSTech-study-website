package com.example.mvcdemo2;

import com.example.mvcdemo2.controller.AdminController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class AdminControllerTest {

    private AdminController adminController;

    @BeforeEach
    public void setUp() {
        adminController = new AdminController();
    }

    @Test
    public void testAdminControllerInstance() {
        assertNotNull(adminController, "AdminController instance should be created");
    }
}
