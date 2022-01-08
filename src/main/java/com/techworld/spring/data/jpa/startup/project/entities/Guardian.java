package com.techworld.spring.data.jpa.startup.project.entities;




import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;

// This annotation tells that this class is embeddable into another class.
@Embeddable
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

/*
    In student class we had defined fields guardianName, guardianEmail, guardianMobile. Resulting columns in database were
    guardian_name, guardian_email, guardian_mobile. But now in this class we have properties as name, email, mobile.
    If we run it as same then the columns in table will be name, email and mobile. but we want to keep column name as
    guardian_name, guardian_email, guardian_mobile so that we know that these name, email and mobile are the details of guardian.
    So to do this we have to use @AttributeOverrides annotation as below.
 */
@AttributeOverrides({
        @AttributeOverride(
                name = "name",
                column = @Column(name = "guardian_name")
        ),
        @AttributeOverride(
                name = "email",
                column = @Column(name = "guardian_email")
        ),
        @AttributeOverride(
                name = "mobile",
                column = @Column(name = "guardian_mobile")
        )
})


// This class must not be an entity as we dont want to create a seperate table. We want these properties as columns in
// student table only. So we will embedd this class in student entity.
public class Guardian {

    private String name;

    private String email;

    private String mobile;

}
