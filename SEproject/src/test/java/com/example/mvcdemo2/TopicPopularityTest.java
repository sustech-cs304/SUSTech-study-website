package com.example.mvcdemo2;

import com.example.mvcdemo2.service.StudentService;
import com.example.mvcdemo2.task.TopicPopularity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TopicPopularity.class)
public class TopicPopularityTest {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private StudentService studentService;

    @InjectMocks
    private TopicPopularity topicPopularity;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

//    @Test
//    public void testYourPage() throws Exception {
//        // Set up the static result variable before performing the request
//        TopicPopularity.result.append("{\"word\":\"example\",\"count\":1},]");
//
//        mockMvc.perform(get("/word"))
//                .andExpect(status().isOk())
//                .andExpect(view().name("wordreal"))
//                .andExpect(model().attributeExists("yourString"))
//                .andExpect(model().attribute("yourString", "[{\"word\":\"example\",\"count\":1},]"));
//
//        // Clear the result for other tests
//        TopicPopularity.result.setLength(1); // Reset to "[" only
//    }

    @Test
    public void testSplitTags() {
        List<String> expectedTags = List.of("tag1", "tag2", "tag3");
        List<String> actualTags = topicPopularity.publicSplitTags("\"tag1\", \"tag2\", \"tag3\"");

        assertEquals(expectedTags, actualTags);
    }

    @Test
    public void testConvertArray() {
        String[][] inputArray = {
                {"word1", "10"},
                {"word2", "20"}
        };

        Object[][] expectedArray = {
                {"word1", 10},
                {"word2", 20}
        };

        Object[][] actualArray = topicPopularity.publicConvertArray(inputArray);

        assertArrayEquals(expectedArray, actualArray);
    }
}
