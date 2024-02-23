package com.example.mvcdemo2.controller;

import com.example.mvcdemo2.model.Student;
import com.example.mvcdemo2.service.StudentService;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/students")
public class StudentRestController {
    private final StudentService studentService;

    public StudentRestController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudentsByQuestion(@RequestParam(value = "question_id")
    Optional<Integer> qid) {
//        if (qid.isPresent()){
//            return studentService.findByEmailLike(qid.get());
//        }
        return studentService.getStudents();
    }

    @PutMapping(path = "{questionId}")
    public void updateStudent(@PathVariable("questionId") int studentId,
        @RequestParam(required = false) int name,
        @RequestParam(required = false) int email) {
        //studentService.updateStudent(studentId, name, email);
    }

}