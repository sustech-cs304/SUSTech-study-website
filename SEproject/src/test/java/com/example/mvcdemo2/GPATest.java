package com.example.mvcdemo2;

import com.example.mvcdemo2.model.GPA;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GPATest {

    private GPA gpa;

    @BeforeEach
    public void setUp() {
        gpa = new GPA();
        gpa.setName("Test username");
        gpa.setD1S("D1S Value");
        gpa.setD1X("D1X Value");
        gpa.setD2S("D2S Value");
        gpa.setD2X("D2X Value");
        gpa.setD3S("D3S Value");
        gpa.setD3X("D3X Value");
        gpa.setD4S("D4S Value");
        gpa.setD4X("D4X Value");
    }

    @Test
    public void testGPAAttributes() {
        assertEquals("Test username", gpa.getName());
        assertEquals("D1S Value", gpa.getD1S());
        assertEquals("D1X Value", gpa.getD1X());
        assertEquals("D2S Value", gpa.getD2S());
        assertEquals("D2X Value", gpa.getD2X());
        assertEquals("D3S Value", gpa.getD3S());
        assertEquals("D3X Value", gpa.getD3X());
        assertEquals("D4S Value", gpa.getD4S());
        assertEquals("D4X Value", gpa.getD4X());
    }

    @Test
    public void testSettersAndGetters() {
        gpa.setName("New username");
        assertEquals("New username", gpa.getName());

        gpa.setD1S("New D1S");
        assertEquals("New D1S", gpa.getD1S());

        gpa.setD1X("New D1X");
        assertEquals("New D1X", gpa.getD1X());

        gpa.setD2S("New D2S");
        assertEquals("New D2S", gpa.getD2S());

        gpa.setD2X("New D2X");
        assertEquals("New D2X", gpa.getD2X());

        gpa.setD3S("New D3S");
        assertEquals("New D3S", gpa.getD3S());

        gpa.setD3X("New D3X");
        assertEquals("New D3X", gpa.getD3X());

        gpa.setD4S("New D4S");
        assertEquals("New D4S", gpa.getD4S());

        gpa.setD4X("New D4X");
        assertEquals("New D4X", gpa.getD4X());
    }

    @Test
    public void testToString() {
        String toString = gpa.toString();
        assertTrue(toString.contains("id=" + gpa.getId()));
        assertTrue(toString.contains("username='" + gpa.getName() + "'"));
        assertTrue(toString.contains("D1S='" + gpa.getD1S() + "'"));
        assertTrue(toString.contains("D1X='" + gpa.getD1X() + "'"));
        assertTrue(toString.contains("D2S='" + gpa.getD2S() + "'"));
        assertTrue(toString.contains("D2X='" + gpa.getD2X() + "'"));
        assertTrue(toString.contains("D3S='" + gpa.getD3S() + "'"));
        assertTrue(toString.contains("D3X='" + gpa.getD3X() + "'"));
        assertTrue(toString.contains("D4S='" + gpa.getD4S() + "'"));
        assertTrue(toString.contains("D4X='" + gpa.getD4X() + "'"));
    }
}
