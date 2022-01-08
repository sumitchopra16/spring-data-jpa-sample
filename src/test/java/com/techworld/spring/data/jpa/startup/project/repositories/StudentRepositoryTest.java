package com.techworld.spring.data.jpa.startup.project.repositories;

import com.techworld.spring.data.jpa.startup.project.entities.Guardian;
import com.techworld.spring.data.jpa.startup.project.entities.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// You should not be using this annotation for testing your repositories layer. We are just using it to interact with our repository class
// as we dont have service or controllers in this application. We are going to write method to learn how the spring data works.
//  How it does crud operations on entities. We are going to learn that what is happening when you are using the repository.
@SpringBootTest

/*
Use this annotation to test your repositories layer. It will help to test your repository layer and once the testing is completed, it
will flush the data, so the database will not be impacted. But for this application we want thar the data gets inserted into
the database so we are using the @SpringBootTest annotation.
 */
//@DataJpaTest

// alt + insert on StudentRepository gives an option of creating a test class
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;


    @Test
    public void saveStudent(){
        Student student = Student.builder()
                .emailId("sumitchopra16@gmail.com")
                .firstName("sumit")
                .lastName("chopra")
                //.guardianName("deepa")
                //.guardianEmail("deepamanga@gmail.com")
                //.guardianMobile("32787827823")
                .build();

        studentRepository.save(student);
    }

    @Test
    public void saveStudentWithGuardian(){

        Guardian guardian = Guardian.builder()
                .email("deepamanga@gmail.com")
                .name("deepa")
                .mobile("32787827823")
                .build();

        Student student = Student.builder()
                .emailId("sumitchopra16@gmail.com")
                .firstName("sumit")
                .lastName("chopra")
                .guardian(guardian)
                .build();

        studentRepository.save(student);
    }

    @Test
    public void printAllStudents(){
        List<Student> studentList = studentRepository.findAll();

        System.out.println("studentList ==>  "+studentList);
    }

    @Test
    public void printStudentByFirstName(){
        List<Student> students = studentRepository.findByFirstName("sumit");

        System.out.println("students :: "+students);
    }

    @Test
    public void printStudentByFirstNameContaining(){
        List<Student> students = studentRepository.findByFirstNameContaining("it");

        System.out.println("students :: "+students);
    }

    @Test
    public void printStudentByLastNameNotNull(){
        List<Student> students = studentRepository.findByLastNameNotNull();

        System.out.println("students :: "+students);
    }

    @Test
    public void printStudentByGuardianName(){
        List<Student> students = studentRepository.findByGuardianName("deepa");

        System.out.println("students :: "+students);
    }

    @Test
    public void printStudentByFirstNameAndLastName(){
        Student student = studentRepository.findByFirstNameAndLastName("sumit", "chopra");

        System.out.println("student :: "+student);
    }

    @Test
    public void printStudentByEmailAddress(){
        Student student = studentRepository.getStudentByEmailAddress("sumitchopra16@gmail.com");

        System.out.println("student :: "+student);
    }

    @Test
    public void printStudentFirstNameByEmailAddressWithException(){
        Student student = studentRepository.getStudentFirstNameByEmailAddressWithException("sumitchopra16@gmail.com");

        System.out.println("student :: "+student);
    }

    @Test
    public void printStudentFirstNameByEmailAddress(){
        String firstName = studentRepository.getStudentFirstNameByEmailAddress("sumitchopra16@gmail.com");

        System.out.println("student firstName :: "+firstName);
    }

    @Test
    public void printStudentByEmailAddressNative(){
        Student student = studentRepository.getStudentByEmailAddressNative("sumitchopra16@gmail.com");

        System.out.println("student :: "+student);
    }

    @Test
    public void updateStudentFirstNameByEmailAddress(){
        int updatedRecords = studentRepository.updateStudentFirstNameByEmailAddress("sumittt", "sumitchopra16@gmail.com");
        List<Student> students = studentRepository.findByFirstName("sumittt");
        System.out.println("students :: "+students);
    }
}