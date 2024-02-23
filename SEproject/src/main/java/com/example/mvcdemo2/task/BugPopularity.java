package com.example.mvcdemo2.task;

import com.example.mvcdemo2.model.Student;
import com.example.mvcdemo2.service.StudentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class BugPopularity {
    @Autowired
    private StudentService studentService;
    private static final Logger LOGGER = LoggerFactory.getLogger(TopicPopularity.class);

    @GetMapping("/Syntax")
    public String showSyntax(Model model){
        LOGGER.info("Enter Syntax page");
        return "Syntax";
    }

    @GetMapping("/Fatal")
    public String showFatal(Model model){
        LOGGER.info("Enter Fatal page");
        return "Fatal";
    }

    @GetMapping("/Exceptions")
    public String showException(Model model){
        LOGGER.info("Enter Exceptions page");
        return "Exceptions";
    }

    @GetMapping("/between")
    public String showBetween(){
        return "between";
    }

    @GetMapping("/calSynList")
    @ResponseBody
    public String calSynList() throws JsonProcessingException {
        List<Student> errorList = studentService.getStudents();
        String[] errorKey = {"Syntax error on token", "Syntax error insert to solve", "Cannot find symbol", "Unclosed character literal", "cannot resolve symbol", "SQL syntax", "other"};
        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < errorKey.length; i++){
            Map<String, Object> item1 = new HashMap<>();
            item1.put("label", errorKey[i]);
            item1.put("value", 0);
            data.add(item1);
        }
        for (Student thread: errorList){
            String content = thread.getContent();
            Matcher matcher = match(content,"(syntax error|syntax mistake|syntax errors|syntax mistakes)");
            if (matcher.find()) {
                int check = 0;
                Matcher mToken = match(content, "error on token");
                if (mToken.find()){
                    data.get(0).put("value", thread.getView_count() + (int)data.get(0).get("value"));
                    check += 1;
                }
                Matcher iToken = match(content, "error, insert");
                if (iToken.find()){
                    data.get(1).put("value", thread.getView_count() + (int)data.get(1).get("value"));
                    check += 1;
                }
                Matcher sToken = match(content, "Cannot find symbol");
                if (sToken.find()){
                    data.get(2).put("value", thread.getView_count() + (int)data.get(2).get("value"));
                    check += 1;
                }
                Matcher uToken = match(content, "Unclosed character literal");
                if (uToken.find()){
                    data.get(3).put("value", thread.getView_count() + (int)data.get(3).get("value"));
                    check += 1;
                }
                Matcher rToken = match(content, "cannot resolve symbol");
                if (rToken.find()){
                    data.get(4).put("value", thread.getView_count() + (int)data.get(4).get("value"));
                    check += 1;
                }
                Matcher sqlToken = match(content, "error in your SQL syntax");
                if (sqlToken.find()){
                    data.get(5).put("value", thread.getView_count() + (int)data.get(5).get("value"));
                    check += 1;
                }

                if (check == 0){
                    data.get(6).put("value", thread.getView_count() + (int)data.get(6).get("value"));
                }
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        LOGGER.info("Syntax page outcome:"+data);
        return objectMapper.writeValueAsString(data);
    }

    @GetMapping("/calFalList")
    @ResponseBody
    public String calFalList() throws JsonProcessingException {
        List<Student> errorList = studentService.getStudents();
        String[] errorKey = {"NullPointerException","NoClassDefFoundError","OutOfMemoryError","StackOverflowError","LinkageError","InternalError","VirtualMachineError","other"};
        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < errorKey.length; i++){
            Map<String, Object> item1 = new HashMap<>();
            item1.put("label", errorKey[i]);
            item1.put("value", 0);
            data.add(item1);
        }
        for (Student thread: errorList) {
            String content = thread.getContent();
            Matcher matcher = match(content, "fatal");
            if (matcher.find()) {
                int check = 0;
                for (int i = 0; i < errorKey.length - 1; i++){
                    Matcher m = match(content, errorKey[i]);
                    if (m.find()){
                        int temp = thread.getView_count();
                        if (temp > 10000){
                            temp = 10000;
                        }
                        data.get(i).put("value", temp + (int)data.get(i).get("value"));
                        check += 1;
                    }
                }
                if (check == 0){
                    int temp = thread.getView_count();
                    if (temp > 10000){
                        temp = 10000;
                    }
                    data.get(7).put("value", temp + (int)data.get(7).get("value"));
                }
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        LOGGER.info("Fatal page outcome:"+data);
        return objectMapper.writeValueAsString(data);
    }

    @GetMapping("/calExpList")
    @ResponseBody
    public String calExpList() throws JsonProcessingException {
        List<Student> errorList = studentService.getStudents();
        String[] errorKey = {"bean exception","IllegalArgumentException","IllegalStateException","ConcurrentModificationException","ClassCastException","UnsupportedOperationException","other"};
        String[] regex = {"(BeanInstantiationException|BeanCreationException)","IllegalArgumentException","IllegalStateException","ConcurrentModificationException","ClassCastException","UnsupportedOperationException"};
        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < errorKey.length; i++){
            Map<String, Object> item1 = new HashMap<>();
            item1.put("label", errorKey[i]);
            item1.put("value", 0);
            data.add(item1);
        }
        for (Student thread: errorList) {
            String content = thread.getContent();
            Matcher matcher = match(content, "exception:");
            if (matcher.find()) {
                int check = 0;
                for (int i = 0; i < errorKey.length - 1; i++){
                    Matcher m = match(content, regex[i]);
                    if (m.find()){
                        int temp = thread.getView_count();
                        if (temp > 10000){
                            temp = 10000;
                        }
                        data.get(i).put("value", temp + (int)data.get(i).get("value"));
                        check += 1;
                    }
                }
                if (check == 0){
                    int temp = thread.getView_count();
                    if (temp > 10000){
                        temp = 10000;
                    }
                    data.get(errorKey.length - 1).put("value", temp + (int)data.get(errorKey.length - 1).get("value"));
                }
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        LOGGER.info("Exception page outcome:"+data);
        return objectMapper.writeValueAsString(data);
    }

    @GetMapping("/calList")
    @ResponseBody
    public String calList() throws JsonProcessingException {
        List<Student> errorList = studentService.getStudents();
        String[] errorKey = {"Syntax errors", "Fatal errors", "Exceptions"};
        String[] regex = {"(syntax error|syntax mistake|syntax errors|syntax mistakes)", "fatal", "exception:"};
        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < errorKey.length; i++){
            Map<String, Object> item1 = new HashMap<>();
            item1.put("label", errorKey[i]);
            item1.put("value", 0);
            data.add(item1);
        }
        for (Student thread: errorList) {
            String content = thread.getContent();
            for (int i = 0; i < errorKey.length; i++){
                Matcher m = match(content, regex[i]);
                if (m.find()){
                    int temp = thread.getView_count();
                    if (temp > 10000){
                        temp = 10000;
                    }
                    data.get(i).put("value", temp + (int)data.get(i).get("value"));
                    //check += 1;
                }
            }
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(data);
    }

    public Matcher match(String content, String regex){
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);
        return matcher;
    }

    // 新增REST服务的方法，并使用 @ResponseBody 注解将返回值转换为 JSON 格式
    @GetMapping("/api/SynList")
    @ResponseBody
    public String SynListAPI(HttpServletRequest request) throws JsonProcessingException {
        LOGGER.info("API call Syntax error");
        List<Student> errorList = studentService.getStudents();
        String[] errorKey = {"Syntax error on token", "Syntax error insert to solve", "Cannot find symbol", "Unclosed character literal", "cannot resolve symbol", "SQL syntax", "other"};
        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < errorKey.length; i++){
            Map<String, Object> item1 = new HashMap<>();
            item1.put("label", errorKey[i]);
            item1.put("value", 0);
            data.add(item1);
        }
        for (Student thread: errorList){
            String content = thread.getContent();
            Matcher matcher = match(content,"(syntax error|syntax mistake|syntax errors|syntax mistakes)");
            if (matcher.find()) {
                int check = 0;
                Matcher mToken = match(content, "error on token");
                if (mToken.find()){
                    data.get(0).put("value", thread.getView_count() + (int)data.get(0).get("value"));
                    check += 1;
                }
                Matcher iToken = match(content, "error, insert");
                if (iToken.find()){
                    data.get(1).put("value", thread.getView_count() + (int)data.get(1).get("value"));
                    check += 1;
                }
                Matcher sToken = match(content, "Cannot find symbol");
                if (sToken.find()){
                    data.get(2).put("value", thread.getView_count() + (int)data.get(2).get("value"));
                    check += 1;
                }
                Matcher uToken = match(content, "Unclosed character literal");
                if (uToken.find()){
                    data.get(3).put("value", thread.getView_count() + (int)data.get(3).get("value"));
                    check += 1;
                }
                Matcher rToken = match(content, "cannot resolve symbol");
                if (rToken.find()){
                    data.get(4).put("value", thread.getView_count() + (int)data.get(4).get("value"));
                    check += 1;
                }
                Matcher sqlToken = match(content, "error in your SQL syntax");
                if (sqlToken.find()){
                    data.get(5).put("value", thread.getView_count() + (int)data.get(5).get("value"));
                    check += 1;
                }

                if (check == 0){
                    data.get(6).put("value", thread.getView_count() + (int)data.get(6).get("value"));
                }
            }
        }
        String listIndexParam = request.getParameter("index");
        if (listIndexParam != null && !listIndexParam.isEmpty()) {
            LOGGER.error("API call Syntax error index: "+listIndexParam);
            for (Map<String, Object> d: data){
                String label = (String) d.get("label");
                Map<String, Object> temp = d;
                if (label.equals(listIndexParam)){
                    data.clear();
                    data.add(temp);
                    break;
                }
            }
        }
        String topN = request.getParameter("topn");
        if (topN != null && !topN.isEmpty()) {
            LOGGER.error("API call Syntax error Top n: "+topN);
            int n = Integer.parseInt(topN);
            Collections.sort(data, new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    // 获取两个 Map 中的 value 值进行比较
                    Integer value1 = (Integer) o1.get("value");
                    Integer value2 = (Integer) o2.get("value");

                    // 从大到小排序
                    return value2.compareTo(value1);
                }
            });
            data = data.subList(0, Math.min(n, data.size()));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        LOGGER.info("API call Syntax Syntax outcome: "+data);
        return objectMapper.writeValueAsString(data);
    }

    @GetMapping("/api/FalList")
    @ResponseBody
    public String FalListAPI(HttpServletRequest request) throws JsonProcessingException {
        LOGGER.info("API call Fatal error");
        List<Student> errorList = studentService.getStudents();
        String[] errorKey = {"NullPointerException","NoClassDefFoundError","OutOfMemoryError","StackOverflowError","LinkageError","InternalError","VirtualMachineError","other"};
        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < errorKey.length; i++){
            Map<String, Object> item1 = new HashMap<>();
            item1.put("label", errorKey[i]);
            item1.put("value", 0);
            data.add(item1);
        }
        for (Student thread: errorList) {
            String content = thread.getContent();
            Matcher matcher = match(content, "fatal");
            if (matcher.find()) {
                int check = 0;
                for (int i = 0; i < errorKey.length - 1; i++){
                    Matcher m = match(content, errorKey[i]);
                    if (m.find()){
                        int temp = thread.getView_count();
                        if (temp > 10000){
                            temp = 10000;
                        }
                        data.get(i).put("value", temp + (int)data.get(i).get("value"));
                        check += 1;
                    }
                }
                if (check == 0){
                    int temp = thread.getView_count();
                    if (temp > 10000){
                        temp = 10000;
                    }
                    data.get(7).put("value", temp + (int)data.get(7).get("value"));
                }
            }
        }
        String listIndexParam = request.getParameter("index");
        if (listIndexParam != null && !listIndexParam.isEmpty()) {
            LOGGER.error("API call Fatal error index: "+listIndexParam);
            for (Map<String, Object> d: data){
                String label = (String) d.get("label");
                Map<String, Object> temp = d;
                if (label.equals(listIndexParam)){
                    data.clear();
                    data.add(temp);
                    break;
                }
            }
        }
        String topN = request.getParameter("topn");
        if (topN != null && !topN.isEmpty()) {
            LOGGER.error("API call Fatal error Top n: "+topN);
            int n = Integer.parseInt(topN);
            Collections.sort(data, new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    // 获取两个 Map 中的 value 值进行比较
                    Integer value1 = (Integer) o1.get("value");
                    Integer value2 = (Integer) o2.get("value");

                    // 从大到小排序
                    return value2.compareTo(value1);
                }
            });
            data = data.subList(0, Math.min(n, data.size()));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        LOGGER.info("API call Fatal Syntax outcome: "+data);
        return objectMapper.writeValueAsString(data);
    }

    @GetMapping("/api/ExpList")
    @ResponseBody
    public String ExpListAPI(HttpServletRequest request) throws JsonProcessingException {
        LOGGER.info("API call Exception error");
        List<Student> errorList = studentService.getStudents();
        String[] errorKey = {"bean exception","IllegalArgumentException","IllegalStateException","ConcurrentModificationException","ClassCastException","UnsupportedOperationException","other"};
        String[] regex = {"(BeanInstantiationException|BeanCreationException)","IllegalArgumentException","IllegalStateException","ConcurrentModificationException","ClassCastException","UnsupportedOperationException"};
        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < errorKey.length; i++){
            Map<String, Object> item1 = new HashMap<>();
            item1.put("label", errorKey[i]);
            item1.put("value", 0);
            data.add(item1);
        }
        for (Student thread: errorList) {
            String content = thread.getContent();
            Matcher matcher = match(content, "exception:");
            if (matcher.find()) {
                int check = 0;
                for (int i = 0; i < errorKey.length - 1; i++){
                    Matcher m = match(content, regex[i]);
                    if (m.find()){
                        int temp = thread.getView_count();
                        if (temp > 10000){
                            temp = 10000;
                        }
                        data.get(i).put("value", temp + (int)data.get(i).get("value"));
                        check += 1;
                    }
                }
                if (check == 0){
                    int temp = thread.getView_count();
                    if (temp > 10000){
                        temp = 10000;
                    }
                    data.get(errorKey.length - 1).put("value", temp + (int)data.get(errorKey.length - 1).get("value"));
                }
            }
        }
        String listIndexParam = request.getParameter("index");
        if (listIndexParam != null && !listIndexParam.isEmpty()) {
            LOGGER.error("API call Exception error index: "+listIndexParam);
            for (Map<String, Object> d: data){
                String label = (String) d.get("label");
                Map<String, Object> temp = d;
                if (label.equals(listIndexParam)){
                    data.clear();
                    data.add(temp);
                    break;
                }
            }
        }
        String topN = request.getParameter("topn");
        if (topN != null && !topN.isEmpty()) {
            LOGGER.error("API call Exception error Top n: "+topN);
            int n = Integer.parseInt(topN);
            Collections.sort(data, new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    // 获取两个 Map 中的 value 值进行比较
                    Integer value1 = (Integer) o1.get("value");
                    Integer value2 = (Integer) o2.get("value");

                    // 从大到小排序
                    return value2.compareTo(value1);
                }
            });
            data = data.subList(0, Math.min(n, data.size()));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        LOGGER.info("API call Exception Syntax outcome: "+data);
        return objectMapper.writeValueAsString(data);
    }

    @GetMapping("/api/List")
    @ResponseBody
    public String ListAPI(HttpServletRequest request) throws JsonProcessingException{
        List<Student> errorList = studentService.getStudents();
        String[] errorKey = {"Syntax errors", "Fatal errors", "Exceptions"};
        String[] regex = {"(syntax error|syntax mistake|syntax errors|syntax mistakes)", "fatal", "exception:"};
        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < errorKey.length; i++){
            Map<String, Object> item1 = new HashMap<>();
            item1.put("label", errorKey[i]);
            item1.put("value", 0);
            data.add(item1);
        }
        for (Student thread: errorList) {
            String content = thread.getContent();
//            Matcher matcher = match(content, "exception:");
//            if (matcher.find()) {
            for (int i = 0; i < errorKey.length; i++){
                Matcher m = match(content, regex[i]);
                if (m.find()){
                    int temp = thread.getView_count();
                    if (temp > 10000){
                        temp = 10000;
                    }
                    data.get(i).put("value", temp + (int)data.get(i).get("value"));
                }
            }
            //}
        }
        String listIndexParam = request.getParameter("index");
        if (listIndexParam != null && !listIndexParam.isEmpty()) {
            for (Map<String, Object> d: data){
                String label = (String) d.get("label");
                Map<String, Object> temp = d;
                if (label.equals(listIndexParam)){
                    data.clear();
                    data.add(temp);
                    break;
                }
            }
        }
        String topN = request.getParameter("topn");
        if (topN != null && !topN.isEmpty()) {
            int n = Integer.parseInt(topN);
            Collections.sort(data, new Comparator<Map<String, Object>>() {
                @Override
                public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                    // 获取两个 Map 中的 value 值进行比较
                    Integer value1 = (Integer) o1.get("value");
                    Integer value2 = (Integer) o2.get("value");

                    // 从大到小排序
                    return value2.compareTo(value1);
                }
            });
            data = data.subList(0, Math.min(n, data.size()));
        }
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(data);
    }
}
