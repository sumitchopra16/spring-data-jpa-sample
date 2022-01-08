package com.techworld.spring.data.jpa.startup.project.repositories;

import com.techworld.spring.data.jpa.startup.project.entities.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    public void printTeachers(){
        List<Teacher> teachers = teacherRepository.findAll();
        System.out.println("teachers =>  "+teachers);
    }

}