package com.example.mvcdemo2.controller;

import com.example.mvcdemo2.model.goods;
import com.example.mvcdemo2.repository.trade_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@Controller
@ComponentScan(basePackages = {"com.example.mvcdemo2.service"})
public class ImageController {

    @Autowired
    private trade_repository goodsRepository;

    @GetMapping("/getImage/{id}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable int id) throws IOException {
        goods goods = goodsRepository.findById(id).orElse(null);
        if (goods != null && goods.getImage() != null) {
            byte[] imageData = goods.getImage();
            return ResponseEntity.ok()
                    .contentType(org.springframework.http.MediaType.IMAGE_JPEG)
                    .body(new InputStreamResource(new ByteArrayInputStream(imageData)));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
