package com.example.mvcdemo2.task;

//import com.alibaba.fastjson.JSON;
import com.example.mvcdemo2.model.Student;
import com.example.mvcdemo2.service.StudentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Controller
public class RelatedTopics {

    private final StudentService studentService;
    public RelatedTopics(StudentService studentService) {
        this.studentService = studentService;
    }
    private static final Logger LOGGER = LoggerFactory.getLogger(TopicPopularity.class);

    @GetMapping("/searchTopics")
    @ResponseBody
    public String searchTopics(Model model, @RequestParam String keyword) {
        // 获取匹配的主题，这里假设有一个方法来获取匹配的主题，你可以根据实际情况实现
        String matchedWords = getMatchedWords(keyword);
        LOGGER.info("Finding the related topic using:"+keyword);

        // 获取同义词并扩展搜索结果
        //List<Student> expandedWords = expandSearchWithSynonyms(matchedWords);

        //model.addAttribute("students", expandedWords);


        return matchedWords;
    }

    @GetMapping("/showTopics")
    @ResponseBody
    public String shoeTopics(Model model, @RequestParam String keyword) {
        // 获取匹配的主题，这里假设有一个方法来获取匹配的主题，你可以根据实际情况实现
        String matchedWords = getMatchedTopic(keyword);
        LOGGER.info("Finding the related topic using:"+keyword);

        // 获取同义词并扩展搜索结果
        //List<Student> expandedWords = expandSearchWithSynonyms(matchedWords);

        //model.addAttribute("students", expandedWords);


        return matchedWords;
    }
    public String getMatchedTopic(String keyword) {
        System.out.println(keyword);
        Map<String, Integer> wordFrequencyMap = new HashMap<>();
        List<Student> students = studentService.getStudents();
        for (Student student : students) {
            String tag = student.getTags();
            if (containsWord(tag, keyword)) {
                tag = tag.replaceAll("\"", "");
                String[] sp = tag.split(",");
                for (String xsp : sp) {
                    wordFrequencyMap.put(xsp, wordFrequencyMap.getOrDefault(xsp, 0) + 1);
                }
            }
        }
        LOGGER.info("Related word map" + wordFrequencyMap);
        wordFrequencyMap.remove("java");
        wordFrequencyMap.remove(keyword);

        System.out.println(wordFrequencyMap);

        return wordFrequencyMap.toString();
    }


    @GetMapping("/api/topics")
    @ResponseBody
    public String apiTopics(HttpServletRequest request) throws JsonProcessingException {
        String keyword = request.getParameter("input");
        System.out.println(keyword);
        Map<String, Integer> wordFrequencyMap = new HashMap<>();
        List<Student> students = studentService.getStudents();
        for(Student student : students){
            String tag = student.getTags();
            if(containsWord(tag,keyword)){
                tag = tag.replaceAll("\"", "");
                String[] sp = tag.split(",");
                for(String xsp : sp){
                    wordFrequencyMap.put(xsp, wordFrequencyMap.getOrDefault(xsp, 0) + 1);
                }
            }
        }
        LOGGER.info("Related word map"+wordFrequencyMap);
        wordFrequencyMap.remove("java");
        wordFrequencyMap.remove(keyword);
        Map<String, Integer> sortedMap = wordFrequencyMap.entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())) // 降序排序
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, HashMap::new));

        //System.out.println(sortedMap);
        StringBuilder result = new StringBuilder();
        for (String key : sortedMap.keySet()) {
            result.append(key).append(",");
        }
        if (result.length() > 0) {
            result.deleteCharAt(result.length() - 1);
        }
        System.out.println(result.toString());
        LOGGER.info("Related word"+result.toString());

        List<Map.Entry<String, Integer>> list = new ArrayList<>(sortedMap.entrySet());

        // 使用Collections.sort对List进行排序，传入自定义的比较器Comparator
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                // 以升序排序
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // 将 JSON 字符串转换为 List<Map<String, Integer>>
            //List<Map<String, Integer>> listOfMaps = objectMapper.readValue(list.toString(), ArrayList.class);

            // 提取每个字典的键
//            List<String> keysList = list.stream()
//                    .map(map -> map.keySet().iterator().next()) // 提取键
//                    .collect(Collectors.toList());
            List<String> keysList = new ArrayList<>();
            for (Map.Entry<String, Integer> entry : list) {
                keysList.add(entry.getKey());
            }
            // 输出按顺序的键列表
            //System.out.println(keysList);
            return objectMapper.writeValueAsString(keysList);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getMatchedWords(String keyword){
        System.out.println(keyword);
        Map<String, Integer> wordFrequencyMap = new HashMap<>();
        List<Student> students = studentService.getStudents();
        for(Student student : students){
            String tag = student.getTags();
            if(containsWord(tag,keyword)){
                tag = tag.replaceAll("\"", "");
                String[] sp = tag.split(",");
                for(String xsp : sp){
                    wordFrequencyMap.put(xsp, wordFrequencyMap.getOrDefault(xsp, 0) + 1);
                }
            }
        }
        LOGGER.info("Related word map"+wordFrequencyMap);
        wordFrequencyMap.remove("java");
        wordFrequencyMap.remove(keyword);
        Map<String, Integer> sortedMap = wordFrequencyMap.entrySet()
                .stream()
                .sorted((entry1, entry2) -> entry2.getValue().compareTo(entry1.getValue())) // 降序排序
                .limit(5)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, HashMap::new));

        //System.out.println(sortedMap);
        StringBuilder result = new StringBuilder();
        for (String key : sortedMap.keySet()) {
            result.append(key).append(",");
        }
        if (result.length() > 0) {
            result.deleteCharAt(result.length() - 1);
        }
        System.out.println(result.toString());
        LOGGER.info("Related word"+result.toString());
        return result.toString();

    }
    public static boolean containsWord(String input, String word) {
        // 将单词转换为正则表达式，考虑单词的边界
        String regex = "\\b" + Pattern.quote(word) + "\\b";

        // 使用正则表达式进行匹配
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(input);

        return matcher.find();
    }


}
