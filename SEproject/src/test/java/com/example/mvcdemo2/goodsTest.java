package com.example.mvcdemo2;

import com.example.mvcdemo2.model.goods;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class goodsTest {

    @Test
    public void testGoodsConstructorAndGetters() {
        List<String> tags = Arrays.asList("tag1", "tag2");
        byte[] image = new byte[]{1, 2, 3};

        goods goods = new goods("Test Product", 100, tags, "test_seller", image, "Test Description");
        goods.setView(10);
        goods.setContact("test_contact");

        assertEquals("Test Product", goods.getName());
        assertEquals(100, goods.getPrice());
        assertEquals(tags, goods.getTags());
        assertEquals("test_seller", goods.getSeller());
        assertArrayEquals(image, goods.getImage());
        assertEquals("Test Description", goods.getDescription());
        assertEquals(10, goods.getView());
        assertEquals("test_contact", goods.getContact());
    }

    @Test
    public void testGoodsSetters() {
        goods goods = new goods();

        List<String> tags = Arrays.asList("tag1", "tag2");
        byte[] image = new byte[]{1, 2, 3};

        goods.setName("Test Product");
        goods.setPrice(100);
        goods.setTags(tags);
        goods.setSeller("test_seller");
        goods.setImage(image);
        goods.setDescription("Test Description");
        goods.setView(10);
        goods.setContact("test_contact");

        assertEquals("Test Product", goods.getName());
        assertEquals(100, goods.getPrice());
        assertEquals(tags, goods.getTags());
        assertEquals("test_seller", goods.getSeller());
        assertArrayEquals(image, goods.getImage());
        assertEquals("Test Description", goods.getDescription());
        assertEquals(10, goods.getView());
        assertEquals("test_contact", goods.getContact());
    }
}
