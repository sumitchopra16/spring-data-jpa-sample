package com.techworld.spring.data.jpa.startup.project.repositories;

import com.techworld.spring.data.jpa.startup.project.entities.Course;
import com.techworld.spring.data.jpa.startup.project.entities.CourseMaterial;
import com.techworld.spring.data.jpa.startup.project.entities.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Test
    public void printCourses(){
        List<Course> courses = courseRepository.findAll();
        System.out.println("courses => "+courses);
    }

    @Test
    public void saveCoursesWithTeacher(){
        Teacher teacher = Teacher.builder()
                .firstName("avadhut")
                .lastName("sathe")
                .build();

        Course course = Course.builder()
                .title("nodejs")
                .credit(7)
                .teacher(teacher)
                .build();

        courseRepository.save(course);
    }

    @Test
    public void saveCourses(){

        CourseMaterial courseMaterial = CourseMaterial.builder()
                .url("www.google.com")
                .build();

        Course course = Course.builder()
                .title("nodejs")
                .credit(7)
                .courseMaterial(courseMaterial)
                .build();


        courseRepository.save(course);
    }

    @Test
    public void printPaging(){
        Pageable firstPageWithThreeRecords = PageRequest.of(1, 2);
        Page<Course> courses = courseRepository.findAll(firstPageWithThreeRecords);
        System.out.println("courses => "+courses.getContent());
        System.out.println("total pages => "+courses.getTotalPages());
        System.out.println("total records in table => "+courses.getTotalElements());
        System.out.println("total records fetched => "+courses.getNumberOfElements());
        System.out.println("sort => "+courses.getSort());
        System.out.println("total number of records requested => "+courses.getSize());
        System.out.println("page number => "+courses.getNumber());
    }
}