package com.techworld.spring.data.jpa.startup.project.entities;

import lombok.*;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = "course")
public class CourseMaterial {

    @Id
    @SequenceGenerator(name="course_material_sequence", sequenceName = "course_material_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_material_sequence")
    private Long courseMaterialId;

    private String url;

    /*
        This annotation tells that CourseMaterial is one to one related with course. CourseMaterial cannot exist without
        a course. And CourseMaterial is always associated with only one Course.

        Instead of populating courseMaterialId in course table, it's better to keep the course as clean. and keep the
        course id in CourseMaterial table. So tha class which contains this annotation, table of this class will contain the
        primary key of the other class or table as the foreign key. So here CourseMaterial is containing the primary key of
        Course table as foreign key in it.
     */
    @OneToOne(
            cascade = CascadeType.ALL,

            /*
                Fetch type lazy means the course class will not be loaded when we load courseMaterial class.
                Course will be loaded only when it is used.

                Fetch is used in OneToOne mapping annotation. To tell if the bean that it references using the foreign key
                is loaded eagerly or lazily.
             */
            fetch = FetchType.LAZY

    )

    /*
        This annotation will tell the columns of both tables using which there will be join between the 2 tables.

        Following query is run to create a foreign key relationship between CourseMaterial & Course.

            alter table course_material
            add constraint FKh74t4tcomcd9t9p7l32pjao1e
            foreign key (course_id_fk)
            references course (course_id)

     */
    @JoinColumn(
            name = "course_id_fk", //this is the name of foreign key column in the CourseMaterial table
            referencedColumnName = "courseId" // name of the field in the referenced type ie Course whose value will be populated in course_id_fk column.
    )
    private Course course;

}
