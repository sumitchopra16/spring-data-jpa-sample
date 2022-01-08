package com.techworld.spring.data.jpa.startup.project.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

/*
    In this class we will show @OneToMany relationship with Course class. ie one teacher teach multiple courses.
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Teacher {

    @Id
    @SequenceGenerator(name="teacher_sequence", sequenceName = "teacher_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teacher_sequence")
    private Long teacherId;

    private String firstName;

    private String lastName;

    /*
        Spring recommends to use @ManyToOne annotation instead of @OneToMany.
        As @ManyToOne is more readable and is easy to understand.

        As we have used @OneToMany here, it will create same table structure as @ManyToOne but less readable. As the
        teacher is not going to keep the foreign key. Course table will be having the foreign key.

        In join column here we have given foreign key column name and also referenced column name of teacher itself.
        We are referring here to Course table but in referenceColumnName we are giving the column of Teacher.

        This is not good for readability purpose.
     */
    /*@OneToMany(
            cascade = CascadeType.ALL
    )
    private List<Course> courses;*/

}
