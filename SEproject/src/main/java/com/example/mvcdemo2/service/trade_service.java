package com.example.mvcdemo2.service;

import com.example.mvcdemo2.model.goods;
import com.example.mvcdemo2.repository.trade_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@ComponentScan(basePackages = {"com.example.mvcdemo2.repository"})
public class trade_service{
    @Autowired
    private trade_repository trade_repository;


    public trade_service(trade_repository trade_repository){
        this.trade_repository = trade_repository;
    }

    public List<goods> searchGoods(String searchQuery) {
        // 通过正则表达式进行宽松匹配
        Pattern pattern = Pattern.compile(".*" + searchQuery + ".*", Pattern.CASE_INSENSITIVE);

        List<goods> goodsList = trade_repository.findByNameContaining(searchQuery);

        // 筛选并排序
        return goodsList.stream()
                .filter(goods -> {
                    boolean matches = pattern.matcher(goods.getName()).matches();
                    System.out.println("匹配结果：" + goods.getName() + " - " + matches);  // 调试输出
                    return matches;
                })
                .sorted((g1, g2) -> Integer.compare(g2.getView(), g1.getView()))
                .collect(Collectors.toList());
    }

//    @Transactional
//    public void saveImageToDatabase(int goodsId) throws IOException {
//        Optional<goods> optionalGoods = trade_repository.findById(goodsId);
//        if (optionalGoods.isPresent()) {
//            goods goods = optionalGoods.get();
//            File imageFile = ResourceUtils.getFile("C:\\Users\\lyc\\Desktop\\pixiv\\115662672_p0_master1200.jpg");
//            byte[] imageData = StreamUtils.copyToByteArray(new FileInputStream(imageFile));
//            goods.setImage(imageData);
//            trade_repository.save(goods);
//        } else {
//            throw new RuntimeException("Goods not found with ID: " + goodsId);
//        }
//    }
}