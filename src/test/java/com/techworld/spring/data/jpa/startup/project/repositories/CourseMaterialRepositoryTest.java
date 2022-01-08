package com.techworld.spring.data.jpa.startup.project.repositories;

import com.techworld.spring.data.jpa.startup.project.entities.Course;
import com.techworld.spring.data.jpa.startup.project.entities.CourseMaterial;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseMaterialRepositoryTest {

    @Autowired
    private CourseMaterialRepository courseMaterialRepository;


    /*
        This method will throw
        Caused by: org.hibernate.TransientPropertyValueException: object references an unsaved transient instance - save the transient instance before flushing : com.techworld.spring.data.jpa.startup.project.entities.CourseMaterial.course -> com.techworld.spring.data.jpa.startup.project.entities.Course
        if we dont specify cascade type in oneToOne mapping annotation.

        Because we are trying to save the course_material without saving the course first. as courseMaterial needs id of course
        to pupulate into its foreign key column so this will fail.

        After specifying the option of cascade in OneToOne annotation, course bean which needed to be saved in database before
        saving CourseMaterial will be saved automatically. Cascade option tells to the spring data jpa that it insert record
        into the course table if that is needed to save the course material. So any dependency which comes in the way of
        saving the course material class will be added to the database automatically. This is called cascading effect.
     */
    @Test
    public void saveCourseMaterial(){

        Course course = Course.builder()
                .title("DSA")
                .credit(6)
                .build();

        CourseMaterial courseMaterial = CourseMaterial.builder()
                .course(course)
                .url("www.google.com")
                .build();

        courseMaterialRepository.save(courseMaterial);

    }

    /*
        This method is giving following error :
        org.hibernate.LazyInitializationException: could not initialize proxy [com.techworld.spring.data.jpa.startup.project.entities.Course#3] - no Session

        This is because, in the toString method of courseMaterial, course object is getting accessed to print its details.
        As the course class is not initialized, therefore this error. And for initialization now, it needs to connect
        with database using a session. But the session is not available now after the courseMaterial class has been fetched
        the session is closed.

        If were in same transaction like in a service method marked with transactional annotation. then the session would
        have existed even after fetching the courseMaterial so that later access to course class within the same service method
        would have hit a query to database to get the course object.

        The error is resolved if we exclude the course class from toString method of courseMaterial.
        This is done in lombok toString annotation. And after that this method works fine and print only courseMaterial
        objects from database.
     */
    @Test
    public void getCourseMaterial(){
        List<CourseMaterial> courseMaterials = courseMaterialRepository.findAll();
        System.out.println("courseMaterials  ==>  "+courseMaterials);
    }

}