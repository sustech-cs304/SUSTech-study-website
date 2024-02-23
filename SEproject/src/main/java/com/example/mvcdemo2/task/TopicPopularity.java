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

    @GetMapping("/executeMethod1")
    @ResponseBody
    public String executeMethod1() {
        LOGGER.info("Executing Popularity metric 1");
        result = new StringBuilder("[");
        //Map<String,Integer> map = new HashMap<>();
        // 在这里执行你的方法
        String[] words = {"xml", "I/O", "lambda", "multithreading", "collections",
                "android", "spring-boot", "JavaFX", "thymeleaf",
                "tomcat", "spring"};


        Map<String, Integer> wordFrequencyMap = new HashMap<>();

        for (String word : words) {
            // 将单词转换为小写，以保证大小写不敏感
            String lowercaseWord = word.toLowerCase();
            // 获取当前单词的词频，如果不存在则默认为0
            int frequency = wordFrequencyMap.getOrDefault(lowercaseWord, 0);
            // 将词频加1并更新到Map中
            wordFrequencyMap.put(lowercaseWord, frequency+1 );

        }
        List<Student> students = studentService.getStudents();
        for (Student student : students) {
            List<String> tags = splitTags(student.getTags());
            for (String tag : tags) {
                String lowercaseTag = tag.toLowerCase();

                // 检查标签是否在映射中
                if (wordFrequencyMap.containsKey(lowercaseTag)) {
                    // 如果在，增加计数
                    int count = wordFrequencyMap.get(lowercaseTag);
                    wordFrequencyMap.put(lowercaseTag, count + 1);
                }

            }
        }

        System.out.println(wordFrequencyMap);
        int size = wordFrequencyMap.size();
        Object[][] wordFreqData = new Object[size][2];

        int index = 0;
        for (Map.Entry<String, Integer> entry : wordFrequencyMap.entrySet()) {
            wordFreqData[index][0] = "'" + entry.getKey() + "'";
            wordFreqData[index][1] = entry.getValue();
            index++;
        }

        for (Object[] row : wordFreqData) {
            result.append(Arrays.toString(row)).append(",");

        }
        if (result.length() > 1) {
            result.deleteCharAt(result.length() - 1);
        }
        result.append(']');  // 替换最后一个逗号为右括号
        //System.out.println(result.toString());
        LOGGER.info("metric 1 result:"+result.toString());

        return result.toString();
    }

    @GetMapping("/api/M1")
    @ResponseBody
    public String apiMethod1(HttpServletRequest request) throws JsonProcessingException {
        LOGGER.info("Executing Popularity metric 1");
        result = new StringBuilder("[");
        //Map<String,Integer> map = new HashMap<>();
        // 在这里执行你的方法
        String[] words = {"xml", "I/O", "lambda", "multithreading", "collections",
                "android", "spring-boot", "JavaFX", "thymeleaf",
                "tomcat", "spring"};


        Map<String, Integer> wordFrequencyMap = new HashMap<>();

        for (String word : words) {
            // 将单词转换为小写，以保证大小写不敏感
            String lowercaseWord = word.toLowerCase();
            // 获取当前单词的词频，如果不存在则默认为0
            int frequency = wordFrequencyMap.getOrDefault(lowercaseWord, 0);
            // 将词频加1并更新到Map中
            wordFrequencyMap.put(lowercaseWord, frequency+1 );

        }
        List<Student> students = studentService.getStudents();
        for (Student student : students) {
            List<String> tags = splitTags(student.getTags());
            for (String tag : tags) {
                String lowercaseTag = tag.toLowerCase();

                // 检查标签是否在映射中
                if (wordFrequencyMap.containsKey(lowercaseTag)) {
                    // 如果在，增加计数
                    int count = wordFrequencyMap.get(lowercaseTag);
                    wordFrequencyMap.put(lowercaseTag, count + 1);
                }

            }
        }

        System.out.println(wordFrequencyMap);
        int size = wordFrequencyMap.size();
        Object[][] wordFreqData = new Object[size][2];

        int index = 0;
        for (Map.Entry<String, Integer> entry : wordFrequencyMap.entrySet()) {
            wordFreqData[index][0] = "'" + entry.getKey() + "'";
            wordFreqData[index][1] = entry.getValue();
            index++;
        }

        for (Object[] row : wordFreqData) {
            result.append(Arrays.toString(row)).append(",");

        }
        if (result.length() > 1) {
            result.deleteCharAt(result.length() - 1);
        }
        result.append(']');  // 替换最后一个逗号为右括号
        //System.out.println(result.toString());
        LOGGER.info("metric 1 result:"+result.toString());

        String listIndexParam = request.getParameter("index");
        if (listIndexParam != null && !listIndexParam.isEmpty()) {
            Integer value = wordFrequencyMap.get(listIndexParam);
            wordFrequencyMap.clear();
            wordFrequencyMap.put(listIndexParam, value);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(wordFrequencyMap);
        }
        String topN = request.getParameter("topn");
        if (topN != null && !topN.isEmpty()) {
            int n = Integer.parseInt(topN);
            // 将Map中的Entry按照value排序，使用ArrayList保存Entry
            List<Map.Entry<String, Integer>> list = new ArrayList<>(wordFrequencyMap.entrySet());

            // 使用Collections.sort对List进行排序，传入自定义的比较器Comparator
            Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    // 以升序排序
                    return o2.getValue().compareTo(o1.getValue());
                }
            });
            list = list.subList(0, Math.min(n, list.size()));
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(list);
        }
        return null;
    }

//    public String washingmachine(String input){
//        //String input = "[['spring', 52],['lambda', 2],['collections', 2],['tomcat', 6],['xml', 12],['android', 51],['i/o', 1],['spring-boot', 73],['multithreading', 6],['javafx', 10],['thymeleaf', 3]]";
//
//        // 去除字符串中的单引号，因为 JSON 使用双引号
//        input = input.replace("'", "\"");
//
//        // 使用 ObjectMapper 将字符串转换为 List<List<Object>>
//        ObjectMapper objectMapper = new ObjectMapper();
//        List<List<Object>> listOfLists = new ArrayList<>();
//        try {
//            listOfLists = objectMapper.readValue(input, ArrayList.class);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        // 转换后的 Java 对象
//        System.out.println(listOfLists);
//
//        // 将 Java 对象转换为 JSON 字符串
//        String jsonOutput = "";
//        try {
//            jsonOutput = objectMapper.writeValueAsString(listOfLists);
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//
//        // 输出 JSON 格式的字符串
//        return (jsonOutput);
//    }
    @GetMapping("/executeMethod2")
    @ResponseBody
    public String executeMethod2(){
        LOGGER.info("Executing Popularity metric 2");
        result2 = new StringBuilder("[");
        // 在这里执行你的方法
        String[] words = {"xml", "I/O", "lambda", "multithreading", "collections",
                "android", "spring-boot", "JavaFX", "thymeleaf",
                "tomcat", "spring"};

        Map<String, Integer> wordFrequencyMap = new HashMap<>();

        for (String word : words) {
            // 将单词转换为小写，以保证大小写不敏感
            String lowercaseWord = word.toLowerCase();
            // 获取当前单词的词频，如果不存在则默认为0
            int frequency = wordFrequencyMap.getOrDefault(lowercaseWord, 0);
            // 将词频加1并更新到Map中
            wordFrequencyMap.put(lowercaseWord, frequency+1 );
        }
        List<Student> students = studentService.getStudents();
        for (Student student : students) {
            List<String> tags = splitTags(student.getTags());
            for (String tag : tags) {
                String lowercaseTag = tag.toLowerCase();

                // 检查标签是否在映射中
                if (wordFrequencyMap.containsKey(lowercaseTag)) {
                    // 如果在，增加计数
                    int count = wordFrequencyMap.get(lowercaseTag);
                    wordFrequencyMap.put(lowercaseTag, count + student.getView_count());
                }

            }
        }

        System.out.println(wordFrequencyMap);
        int size = wordFrequencyMap.size();
        Object[][] wordFreqData = new Object[size][2];

        int index = 0;
        for (Map.Entry<String, Integer> entry : wordFrequencyMap.entrySet()) {
            wordFreqData[index][0] = "'" + entry.getKey() + "'";
            wordFreqData[index][1] = 2*Math.sqrt(Math.sqrt(entry.getValue()));
            index++;
        }

        for (Object[] row : wordFreqData) {
            result2.append(Arrays.toString(row)).append(",");
        }
        if (result2.length() > 1) {
            result2.deleteCharAt(result2.length() - 1);
        }
        result2.append(']');  // 替换最后一个逗号为右括号
        //System.out.println(result.toString());
        LOGGER.info("metric 2 result:"+result.toString());
        return result2.toString();
    }

    @GetMapping("/api/M2")
    @ResponseBody
    public String apiMethod2(HttpServletRequest request) throws JsonProcessingException{
        LOGGER.info("Executing Popularity metric 2");
        result2 = new StringBuilder("[");
        // 在这里执行你的方法
        String[] words = {"xml", "I/O", "lambda", "multithreading", "collections",
                "android", "spring-boot", "JavaFX", "thymeleaf",
                "tomcat", "spring"};

        Map<String, Integer> wordFrequencyMap = new HashMap<>();

        for (String word : words) {
            // 将单词转换为小写，以保证大小写不敏感
            String lowercaseWord = word.toLowerCase();
            // 获取当前单词的词频，如果不存在则默认为0
            int frequency = wordFrequencyMap.getOrDefault(lowercaseWord, 0);
            // 将词频加1并更新到Map中
            wordFrequencyMap.put(lowercaseWord, frequency+1 );
        }
        List<Student> students = studentService.getStudents();
        for (Student student : students) {
            List<String> tags = splitTags(student.getTags());
            for (String tag : tags) {
                String lowercaseTag = tag.toLowerCase();

                // 检查标签是否在映射中
                if (wordFrequencyMap.containsKey(lowercaseTag)) {
                    // 如果在，增加计数
                    int count = wordFrequencyMap.get(lowercaseTag);
                    wordFrequencyMap.put(lowercaseTag, count + student.getView_count());
                }

            }
        }

        System.out.println(wordFrequencyMap);
        int size = wordFrequencyMap.size();
        Object[][] wordFreqData = new Object[size][2];

        int index = 0;
        for (Map.Entry<String, Integer> entry : wordFrequencyMap.entrySet()) {
            wordFreqData[index][0] = "'" + entry.getKey() + "'";
            wordFreqData[index][1] = 2*Math.sqrt(Math.sqrt(entry.getValue()));
            index++;
        }

        for (Object[] row : wordFreqData) {
            result2.append(Arrays.toString(row)).append(",");
        }
        if (result2.length() > 1) {
            result2.deleteCharAt(result2.length() - 1);
        }
        result2.append(']');  // 替换最后一个逗号为右括号
        //System.out.println(result.toString());
        LOGGER.info("metric 2 result:"+result.toString());

        String listIndexParam = request.getParameter("index");
        if (listIndexParam != null && !listIndexParam.isEmpty()) {
            Integer value = wordFrequencyMap.get(listIndexParam);
            wordFrequencyMap.clear();
            wordFrequencyMap.put(listIndexParam, value);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(wordFrequencyMap);
        }
        String topN = request.getParameter("topn");
        if (topN != null && !topN.isEmpty()) {
            int n = Integer.parseInt(topN);
            // 将Map中的Entry按照value排序，使用ArrayList保存Entry
            List<Map.Entry<String, Integer>> list = new ArrayList<>(wordFrequencyMap.entrySet());

            // 使用Collections.sort对List进行排序，传入自定义的比较器Comparator
            Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    // 以升序排序
                    return o2.getValue().compareTo(o1.getValue());
                }
            });
            list = list.subList(0, Math.min(n, list.size()));
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(list);
        }
        return null;
    }

    @GetMapping("/executeMethod3")
    @ResponseBody
    public String executeMethod3(){
        LOGGER.info("Executing Popularity metric 3");
        result3 = new StringBuilder("[");
        // 在这里执行你的方法
        String[] words = {"xml", "I/O", "lambda", "multithreading", "collections",
                "android", "spring-boot", "JavaFX", "thymeleaf",
                "tomcat", "spring"};

        Map<String, Integer> wordFrequencyMap = new HashMap<>();

        for (String word : words) {
            // 将单词转换为小写，以保证大小写不敏感
            String lowercaseWord = word.toLowerCase();
            // 获取当前单词的词频，如果不存在则默认为0
            int frequency = wordFrequencyMap.getOrDefault(lowercaseWord, 0);
            // 将词频加1并更新到Map中
            wordFrequencyMap.put(lowercaseWord, frequency+1 );
        }
        List<Student> students = studentService.getStudents();
        for (Student student : students) {
            List<String> tags = splitTags(student.getTags());
            for (String tag : tags) {
                String lowercaseTag = tag.toLowerCase();

                // 检查标签是否在映射中
                if (wordFrequencyMap.containsKey(lowercaseTag)) {
                    // 如果在，增加计数
                    int count = wordFrequencyMap.get(lowercaseTag);
                    wordFrequencyMap.put(lowercaseTag, count + student.getAnswer_count());
                }

            }
        }

        System.out.println(wordFrequencyMap);
        int size = wordFrequencyMap.size();
        Object[][] wordFreqData = new Object[size][2];

        int index = 0;
        for (Map.Entry<String, Integer> entry : wordFrequencyMap.entrySet()) {
            wordFreqData[index][0] = "'" + entry.getKey() + "'";
            wordFreqData[index][1] = 5*Math.sqrt(entry.getValue());
            index++;
        }

        for (Object[] row : wordFreqData) {
            result3.append(Arrays.toString(row)).append(",");
        }
        if (result3.length() > 1) {
            result3.deleteCharAt(result3.length() - 1);
        }
        result3.append(']');  // 替换最后一个逗号为右括号
        //System.out.println(result.toString());
        LOGGER.info("metric 3 result:"+result.toString());
        return result3.toString();
    }

    @GetMapping("/api/M3")
    @ResponseBody
    public String apiMethod3(HttpServletRequest request) throws JsonProcessingException {
        LOGGER.info("Executing Popularity metric 3");
        result3 = new StringBuilder("[");
        // 在这里执行你的方法
        String[] words = {"xml", "I/O", "lambda", "multithreading", "collections",
                "android", "spring-boot", "JavaFX", "thymeleaf",
                "tomcat", "spring"};

        Map<String, Integer> wordFrequencyMap = new HashMap<>();

        for (String word : words) {
            // 将单词转换为小写，以保证大小写不敏感
            String lowercaseWord = word.toLowerCase();
            // 获取当前单词的词频，如果不存在则默认为0
            int frequency = wordFrequencyMap.getOrDefault(lowercaseWord, 0);
            // 将词频加1并更新到Map中
            wordFrequencyMap.put(lowercaseWord, frequency + 1);
        }
        List<Student> students = studentService.getStudents();
        for (Student student : students) {
            List<String> tags = splitTags(student.getTags());
            for (String tag : tags) {
                String lowercaseTag = tag.toLowerCase();

                // 检查标签是否在映射中
                if (wordFrequencyMap.containsKey(lowercaseTag)) {
                    // 如果在，增加计数
                    int count = wordFrequencyMap.get(lowercaseTag);
                    wordFrequencyMap.put(lowercaseTag, count + student.getAnswer_count());
                }

            }
        }

        System.out.println(wordFrequencyMap);
        int size = wordFrequencyMap.size();
        Object[][] wordFreqData = new Object[size][2];

        int index = 0;
        for (Map.Entry<String, Integer> entry : wordFrequencyMap.entrySet()) {
            wordFreqData[index][0] = "'" + entry.getKey() + "'";
            wordFreqData[index][1] = 5 * Math.sqrt(entry.getValue());
            index++;
        }

        for (Object[] row : wordFreqData) {
            result3.append(Arrays.toString(row)).append(",");
        }
        if (result3.length() > 1) {
            result3.deleteCharAt(result3.length() - 1);
        }
        result3.append(']');  // 替换最后一个逗号为右括号
        //System.out.println(result.toString());
        LOGGER.info("metric 3 result:" + result.toString());

        String listIndexParam = request.getParameter("index");
        if (listIndexParam != null && !listIndexParam.isEmpty()) {
            Integer value = wordFrequencyMap.get(listIndexParam);
            wordFrequencyMap.clear();
            wordFrequencyMap.put(listIndexParam, value);
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(wordFrequencyMap);
        }
        String topN = request.getParameter("topn");
        if (topN != null && !topN.isEmpty()) {
            int n = Integer.parseInt(topN);
            // 将Map中的Entry按照value排序，使用ArrayList保存Entry
            List<Map.Entry<String, Integer>> list = new ArrayList<>(wordFrequencyMap.entrySet());

            // 使用Collections.sort对List进行排序，传入自定义的比较器Comparator
            Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
                @Override
                public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                    // 以升序排序
                    return o2.getValue().compareTo(o1.getValue());
                }
            });
            list = list.subList(0, Math.min(n, list.size()));
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(list);
        }
        return null;
    }

    public void Check(){
//        List<Student> students = studentService.getStudents();
//        for (Student student : students) {
//            List<String> tags = splitTags(student.getTags());
//            for (String tag : tags) {
//                String lowercaseTag = tag.toLowerCase();
//
//                // 检查标签是否在映射中
//                if (wordFrequencyMap.containsKey(lowercaseTag)) {
//                    // 如果在，增加计数
//                    int count = wordFrequencyMap.get(lowercaseTag);
//                    wordFrequencyMap.put(lowercaseTag, count + 1);
//                }
//
//            }
//        }
//
//            // 在这里可以使用 tags，例如打印每个学生的标签
//            System.out.println("Student " + student.getQuestion_id() + " tags: " + tags);
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
}

