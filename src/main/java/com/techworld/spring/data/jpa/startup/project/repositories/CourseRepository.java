package com.techworld.spring.data.jpa.startup.project.repositories;

import com.techworld.spring.data.jpa.startup.project.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
}
