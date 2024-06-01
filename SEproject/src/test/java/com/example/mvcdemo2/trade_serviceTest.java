package com.example.mvcdemo2;

import com.example.mvcdemo2.model.goods;
import com.example.mvcdemo2.repository.trade_repository;
import com.example.mvcdemo2.service.trade_service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class trade_serviceTest {

    @Mock
    private trade_repository tradeRepository;

    @InjectMocks
    private trade_service tradeService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSearchGoods() {
        // 创建一个模拟的商品列表
        List<goods> mockGoodsList = new ArrayList<>();
        goods goods1 = new goods("Laptop", 1000, new ArrayList<>(), "Seller1", null, "A good laptop");
        goods1.setGood_id(1);
        goods1.setView(100);

        goods goods2 = new goods("Laptop Case", 20, new ArrayList<>(), "Seller2", null, "A good laptop case");
        goods2.setGood_id(2);
        goods2.setView(200);

        mockGoodsList.add(goods1);
        mockGoodsList.add(goods2);

        // 模拟tradeRepository.findByNameContaining()方法返回值
        when(tradeRepository.findByNameContaining("Lap")).thenReturn(mockGoodsList);

        // 调用service方法并验证结果
        List<goods> result = tradeService.searchGoods("Lap");

        // 调试输出
        System.out.println("搜索结果数量：" + result.size());
        for (goods g : result) {
            System.out.println("商品名称：" + g.getName() + ", 浏览数：" + g.getView());
        }

        assertEquals(2, result.size());  // 检查结果大小
        assertEquals("Laptop Case", result.get(0).getName());  // 确保按视图数降序排序
        assertEquals("Laptop", result.get(1).getName());
    }
}
