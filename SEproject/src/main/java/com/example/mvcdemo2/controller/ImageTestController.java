package com.example.mvcdemo2.controller;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageTestController {

    private final ResourceLoader resourceLoader;

    public ImageTestController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @GetMapping("/test-image")
    public ResponseEntity<Resource> testImage() {
        Resource resource = resourceLoader.getResource("classpath:/static/img/keli.png");
        if (resource.exists()) {
            // 如果找到资源，返回OK响应
            return ResponseEntity.ok().body(resource);
        } else {
            // 如果资源不存在，返回Not Found响应
            return ResponseEntity.notFound().build();
        }
    }
}
