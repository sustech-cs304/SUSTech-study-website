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
import java.util.Optional;

@Service
@ComponentScan(basePackages = {"com.example.mvcdemo2.repository"})
public class trade_service{
    @Autowired
    private trade_repository trade_repository;


    public trade_service(trade_repository trade_repository){
        this.trade_repository = trade_repository;
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
