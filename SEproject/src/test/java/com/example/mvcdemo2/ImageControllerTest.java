package com.example.mvcdemo2;

import com.example.mvcdemo2.controller.ImageController;
import com.example.mvcdemo2.model.goods;
import com.example.mvcdemo2.repository.trade_repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ImageControllerTest {

    @Mock
    private trade_repository goodsRepository;

    @InjectMocks
    private ImageController imageController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void getImage_WhenImageExists() throws Exception {
        // Arrange
        goods g = new goods();
        byte[] imageData = new byte[]{1, 2, 3};
        g.setImage(imageData);
        when(goodsRepository.findById(1)).thenReturn(Optional.of(g));

        // Act
        ResponseEntity<InputStreamResource> response = imageController.getImage(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(org.springframework.http.MediaType.IMAGE_JPEG, response.getHeaders().getContentType());
        byte[] responseBody = response.getBody().getInputStream().readAllBytes();
        assertArrayEquals(imageData, responseBody);

        verify(goodsRepository).findById(1);
    }

    @Test
    public void getImage_WhenImageDoesNotExist() throws IOException {
        // Arrange
        when(goodsRepository.findById(1)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<InputStreamResource> response = imageController.getImage(1);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(goodsRepository).findById(1);
    }

    @Test
    public void getImage_WhenGoodsExistsButImageIsNull() throws IOException {
        // Arrange
        goods g = new goods();
        g.setImage(null);
        when(goodsRepository.findById(1)).thenReturn(Optional.of(g));

        // Act
        ResponseEntity<InputStreamResource> response = imageController.getImage(1);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        verify(goodsRepository).findById(1);
    }
}