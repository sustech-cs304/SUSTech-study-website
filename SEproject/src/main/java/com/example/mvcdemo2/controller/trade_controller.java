package com.example.mvcdemo2.controller;

import com.example.mvcdemo2.model.goods;
import com.example.mvcdemo2.repository.trade_repository;
import com.example.mvcdemo2.service.trade_service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
@ComponentScan(basePackages = {"com.example.mvcdemo2.service"})
public class trade_controller {
    @Autowired
    private trade_repository tr;
    @Autowired
    private trade_service ts;

    private String usr;

    public trade_controller(trade_repository tr) {
        this.tr = tr;
    }

    @GetMapping("/tradeFrontPage")
    public String begin(Model model, String username) throws IOException {
//        List<String> l = new ArrayList<>();
//        l.add("animation");
//        l.add("hand-painting");
//        l.add("personally-made");
//        File imageFile = ResourceUtils.getFile("E:\\好图\\d1689ca80dbb3b24e4aedf93f9c4a0821657184802962.jpeg");
//        byte[] imageData = StreamUtils.copyToByteArray(new FileInputStream(imageFile));
//        File imageFile1 = ResourceUtils.getFile("C:\\Users\\lyc\\Desktop\\pixiv\\115662672_p0_master1200.jpg");
//        byte[] imageData1 = StreamUtils.copyToByteArray(new FileInputStream(imageFile1));
//
//        insertData("drawing", 100, l, imageData, "seller", "this is just a test good");
//        insertData("drawing", 100, l, imageData1, "seller", "this is just a test good");
//        insertData("drawing", 100, l, imageData1, "seller", "this is just a test good");
//        insertData("drawing", 100, l, imageData1, "seller", "this is just a test good");
//        insertData("drawing", 100, l, imageData1, "seller", "this is just a test good");
//        insertData("drawing", 100, l, imageData1, "seller", "this is just a test good");
        model.addAttribute("recommendedProducts", tr.findAll());
        this.usr = username;
        return "frontpage";
    }

    public void insertData(String name, int price, List<String> tag, byte[] image, String seller, String description) {
        goods entity = new goods();
        entity.setName(name);
        entity.setPrice(price);
        entity.setSeller(seller);
        entity.setTags(tag);
        entity.setImage(image);
        entity.setDescription(description);

        tr.save(entity);
    }

    @GetMapping("/productInfo")
    public String getProductInfo(@RequestParam("id") int productId, Model model) {
        // 根据商品ID查询商品信息，这里假设使用 ProductService 来处理
        Optional<goods> g = tr.findById(productId);
        if (g.isPresent()) {
            System.out.println("add view!");
            goods goods = g.get();
            goods.setView(goods.getView() + 1);  // 调用 goods 类中的方法
            tr.save(goods);
        } else {
            // 处理找不到 productId 的情况
            System.out.println("could not find product according to the productID");
        }
        // 将查询到的商品信息放入Model中，以便在前端页面渲染
        model.addAttribute("product", g.orElse(null));

        // 返回商品信息页面的模板文件名，比如productInfo.html
        return "productInfo";
    }

    @GetMapping("/postGoods")
    public String postProduct() {
        return "postPage";
    }

    @PostMapping("/submit-product")
    public String updateGoods(String username, goods g, @RequestParam("imageFromNet") MultipartFile imageFile) {
        g.setSeller(username);
        g.setView(0);
        try {
            byte[] imageData = imageFile.getBytes();
            System.out.println("Image data length: " + imageData.length);
            g.setImage(imageData); // 将上传的图片文件转换为字节数组并保存
        } catch (IOException e) {
            // 处理文件上传异常
            e.printStackTrace();
        }
        tr.save(g);
        return "redirect:/tradeFrontPage";
    }

    @GetMapping("/search")
    public String search(@RequestParam("searchQuery") String searchQuery, Model model) {
        System.out.println("into search");
        List<goods> searchResults = ts.searchGoods(searchQuery);
        model.addAttribute("recommendedProducts", searchResults);
        return "frontpage";
    }

    @GetMapping("/fetchProducts")
    public String fetchProducts(String username, Model model) {
        System.out.println("this is usr:" + username);
        List<goods> products = tr.findBySeller(username);
        model.addAttribute("products", products);
        return "myAccount";
    }
}