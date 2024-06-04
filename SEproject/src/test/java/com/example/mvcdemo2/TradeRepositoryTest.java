package com.example.mvcdemo2;

import com.example.mvcdemo2.model.goods;
import com.example.mvcdemo2.repository.trade_repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class TradeRepositoryTest {

    @Autowired
    private trade_repository tradeRepository;

    @BeforeEach
    void setUp() {
        goods good1 = new goods("Apple", 10, Arrays.asList("fruit"), "SellerA", null, "Fresh apples");
        good1.setView(100);
        goods good2 = new goods("Banana", 5, Arrays.asList("fruit"), "SellerB", null, "Fresh bananas");
        good2.setView(150);
        goods good3 = new goods("Orange", 8, Arrays.asList("fruit"), "SellerA", null, "Fresh oranges");
        good3.setView(50);

        tradeRepository.save(good1);
        tradeRepository.save(good2);
        tradeRepository.save(good3);
    }

    @Test
    void testFindByNameContaining() {
        List<goods> result = tradeRepository.findByNameContaining("a");
        assertEquals(3, result.size());
        assertEquals("Apple", result.get(0).getName());
        assertEquals("Banana", result.get(1).getName());
        assertEquals("Orange", result.get(2).getName());
    }

    @Test
    void testFindBySeller() {
        List<goods> result = tradeRepository.findBySeller("SellerA");
        assertEquals(2, result.size());
        assertEquals("Apple", result.get(0).getName());
        assertEquals("Orange", result.get(1).getName());
    }
}
