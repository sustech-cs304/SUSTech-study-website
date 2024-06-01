package com.example.mvcdemo2;

import com.example.mvcdemo2.model.Student;
import com.example.mvcdemo2.repository.StudentRepository;
import com.example.mvcdemo2.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetStudents() {
        // 创建一个模拟的学生列表
        List<Student> mockStudents = new ArrayList<>();
        Student student1 = new Student(1, 1, 1234, 10, "tag1", "content1", "John Doe", "url1");
        Student student2 = new Student(2, 2, 5678, 20, "tag2", "content2", "Jane Doe", "url2");
        mockStudents.add(student1);
        mockStudents.add(student2);

        // 模拟studentRepository.findAll()方法返回值
        when(studentRepository.findAll()).thenReturn(mockStudents);

        // 调用service方法并验证结果
        List<Student> students = studentService.getStudents();
        assertEquals(2, students.size());
        assertEquals("John Doe", students.get(0).getUsername());
        assertEquals("Jane Doe", students.get(1).getUsername());
    }
}
