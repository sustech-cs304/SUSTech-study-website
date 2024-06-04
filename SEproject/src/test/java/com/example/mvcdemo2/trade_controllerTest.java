package com.example.mvcdemo2;

import com.example.mvcdemo2.controller.trade_controller;
import com.example.mvcdemo2.model.goods;
import com.example.mvcdemo2.repository.trade_repository;
import com.example.mvcdemo2.service.trade_service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class trade_controllerTest {

    @Mock
    private trade_repository tr;

    @Mock
    private trade_service ts;

    @Mock
    private Model model;

    @Mock
    private MultipartFile imageFile;

    @InjectMocks
    private trade_controller controller;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testBegin() throws IOException {
        // Mocking repository method
        List<goods> goodsList = new ArrayList<>();
        when(tr.findAll()).thenReturn(goodsList);

        String view = controller.begin(model, "testUser");

        // Asserting the view name
        assertEquals("frontpage", view);

        // Verifying model attributes and repository calls
        verify(model).addAttribute("recommendedProducts", goodsList);
        verify(tr).findAll();
    }

    @Test
    public void testGetProductInfo() {
        goods g = new goods();
        g.setView(0);
        Optional<goods> optionalGoods = Optional.of(g);
        when(tr.findById(anyInt())).thenReturn(optionalGoods);

        String view = controller.getProductInfo(1, model);

        assertEquals("productInfo", view);
        verify(model).addAttribute("product", g);
        verify(tr).save(g);
    }

    @Test
    public void testPostProduct() {
        String view = controller.postProduct();
        assertEquals("postPage", view);
    }

    @Test
    public void testUpdateGoods() throws IOException {
        goods g = new goods();
        byte[] imageData = new byte[10];
        when(imageFile.getBytes()).thenReturn(imageData);

        String view = controller.updateGoods("testUser", g, imageFile);

        assertEquals("redirect:/tradeFrontPage", view);
        assertEquals("testUser", g.getSeller());
        assertEquals(0, g.getView());
        assertEquals(imageData, g.getImage());

        verify(tr).save(g);
    }

    @Test
    public void testSearch() {
        List<goods> searchResults = new ArrayList<>();
        when(ts.searchGoods(anyString())).thenReturn(searchResults);

        String view = controller.search("query", model);

        assertEquals("frontpage", view);
        verify(model).addAttribute("recommendedProducts", searchResults);
        verify(ts).searchGoods("query");
    }

    @Test
    public void testFetchProducts() {
        List<goods> products = new ArrayList<>();
        when(tr.findBySeller(anyString())).thenReturn(products);

        String view = controller.fetchProducts("testUser", model);

        assertEquals("myAccount", view);
        verify(model).addAttribute("products", products);
        verify(tr).findBySeller("testUser");
    }
}