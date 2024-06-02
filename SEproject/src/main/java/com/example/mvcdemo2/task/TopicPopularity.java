package com.example.mvcdemo2.task;

import com.example.mvcdemo2.model.Student;
import com.example.mvcdemo2.service.StudentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;
import org.slf4j.Logger;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TopicPopularity {
    @Autowired
    private final StudentService studentService;
    private static final Logger LOGGER = LoggerFactory.getLogger(TopicPopularity.class);

    public static StringBuilder result = new StringBuilder("[");
    public static StringBuilder result2 = new StringBuilder("[");
    public static StringBuilder result3 = new StringBuilder("[");

    public TopicPopularity(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/word")
    public String yourPage(Model model) {
        String yourString = result.toString(); // 从其他类获取字符串
        LOGGER.info("Enter the Topic Popularity page");
        model.addAttribute("yourString", yourString); // 将字符串添加到模型
        return "wordreal"; // 返回模板名称
    }


    private static List<String> splitTags(String tagsString) {
        List<String> tags = new ArrayList<>();

        if (tagsString != null && !tagsString.isEmpty()) {
            // 以逗号分隔，并去除每个标签的双引号
            String[] tagArray = tagsString.split(",");
            for (String tag : tagArray) {
                tags.add(tag.trim().replaceAll("^\"|\"$", ""));
            }
        }

        return tags;
    }

    public static void main(String[] args) {


    }
    private static Object[][] convertArray(String[][] inputArray) {//转换数组为词云接受的对象
        int rows = inputArray.length;
        int cols = inputArray[0].length;
        Object[][] outputArray = new Object[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (j == 1) {
                    outputArray[i][j] = Integer.parseInt(inputArray[i][j]);
                } else {
                    outputArray[i][j] = inputArray[i][j];
                }
            }
        }

        return outputArray;
    }
    public List<String> publicSplitTags(String tagsString) {
        return splitTags(tagsString);
    }

    public Object[][] publicConvertArray(String[][] inputArray) {
        return convertArray(inputArray);
    }
}

