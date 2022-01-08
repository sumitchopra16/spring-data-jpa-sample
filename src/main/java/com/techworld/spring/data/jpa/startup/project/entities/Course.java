package com.techworld.spring.data.jpa.startup.project.entities;

import lombok.*;

import javax.persistence.*;

/*
    In this class we will show Bidirectional @OneToOne relationship with CourseMaterial class
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course {

    @Id
    @SequenceGenerator(name="course_sequence", sequenceName = "course_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_sequence")
    private Long courseId;

    private String title;

    private Integer credit;

    /*
        By adding OneToOne annotation in Course class as well, we have made this OneToOne relationship as bi-directional.
        In this annotation we don't give the join column, as it is already defined in the class which contains the foreign
        key. Here we will tell the annotation that the mapping is done by which field of the CourseMaterial class.

        So now when we fetch courses from the database, then we also get related courseMaterial. hibernate will hit the
        query in courseMaterial table for the course id which is fetched.

        select
        course0_.course_id as course_i1_0_,
        course0_.credit as credit2_0_,
        course0_.title as title3_0_
        from
            course course0_

        select
            coursemate0_.course_material_id as course_m1_1_0_,
            coursemate0_.course_id_fk as course_i3_1_0_,
            coursemate0_.url as url2_1_0_
        from
            course_material coursemate0_
        where
            coursemate0_.course_id_fk=?

        CourseMaterial will always be eagerly fetched. Even if you specify FetchType.LAZY

     */
    @OneToOne(
            cascade = CascadeType.ALL,
            mappedBy = "course",

            /*
                this property tells that you cannot save one entity without another. So when saving course, you wwill
                have to save the CourseMaterial as well.
             */
            optional = false
    )
    private CourseMaterial courseMaterial;

    /*
        Spring recommends to use @ManyToOne annotation instead of @OneToMany.
        As @ManyToOne is more readable and is easy to understand.

        Course table will have teacher_id_fk as foreign key which will refer to teacherId in teacher class.
    */
    @ManyToOne(
        cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "teacher_id_fk",
            referencedColumnName = "teacherId"
    )
    private Teacher teacher;

}
