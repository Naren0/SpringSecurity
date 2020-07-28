package com.naren.springsecurity.demo.student;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

    private static final List<Student> STUDENTS = Arrays.asList(
            new Student(1,"Naren"),
            new Student(2,"Anil"),
            new Student(3,"Ravi")
    );

    @GetMapping(path ="/{studentId}")
    public Student getStudent(@PathVariable Integer studentId){
            return  STUDENTS.stream()
                    .filter(student -> studentId.equals(student.getStudentId()))
                    .findFirst()
                    .orElseThrow(() -> new IllegalStateException("Student "+studentId+" not found in list"));
    }

    @GetMapping("/user")
    public String user(){
        return "Student and Admin role";
    }


    @GetMapping("/admin")
    public String admin(){
        return "Admin role only";
    }
}
