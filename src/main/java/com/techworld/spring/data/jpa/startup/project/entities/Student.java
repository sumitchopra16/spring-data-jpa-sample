package com.techworld.spring.data.jpa.startup.project.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(
        name = "tbl_student",  //specify table name in database
        // specify the unique contraints on table. for example we need that email id must be unique in table and no 2 students have same emailid.
        // If 2 students with same email id are added then org.springframework.dao.DataIntegrityViolationException will be thrown
        uniqueConstraints = @UniqueConstraint(
                name = "emailid_unique", // name of constraint
                columnNames = "email_address" // string of columns. The column specified here must be provided everytime a row is added. And the column must be not null.
        )
)
public class Student {

    //This annotation is used to mark a property as primary key of the table
    @Id

    // This annotation is used to tell the sequence name which will be used for this primary key. this annotation is used
    // only when SEQUENCE strategy is used for generating value of primary key.
    // If ddl-auto=update is set, then the sequence student_sequence will be created if you are using oracle or postgre
    // but in mysql sequence concept is not there. So in mysql a table will be created with name student_sequence and
    // it will increment the value in the table everytime we are doing the insert operation.
    // It issues following query before inserting a row in student table
    //  select next_val as id_val from student_sequence for update
    //  In this query select.. for update is used. so rows will be blocked in the table for read as well as write until the sequence value is updated by the following query.
    //  update student_sequence set next_val= ? where next_val=? this query will update the value of sequence and release the lock. now the sequnce read above will be used for this insert and sequence updated will be used for the next insert.
    @SequenceGenerator(name="student_sequence", sequenceName = "student_sequence", allocationSize = 1)

    // This annotation is used to specify that how the value of the primary key is generated. Here we are using the
    // SEQUENCE as a way to generate values of the primary key
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "student_sequence")
    private Long studentId;

    private String firstName;

    private String lastName;

    @Column(
            name="email_address", // specify exact column name in database
            nullable = false // everytime adding, updating or reading a row, we must be getting this value. if we dont get the value or get it as null then an exception will be thrown.
    )
    private String emailId;

    /*

    Following fields are removed and placed in a separate class Guardian. But We want below columns in the student table only.
    Only we have put the fields in a separate class.

    private String guardianName;

    private String guardianEmail;

    private String guardianMobile;

    */

    // This annotation tells that the declared field or class is embedded into the containing class which is student in this case.
    @Embedded
    private Guardian guardian;

}
