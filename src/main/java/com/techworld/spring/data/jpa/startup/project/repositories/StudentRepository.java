package com.techworld.spring.data.jpa.startup.project.repositories;

import com.techworld.spring.data.jpa.startup.project.entities.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

// This annotation tells jpa at startup that this is a repository class. jpa will create a proxy for this and will use it
// communicate with the student table in the database.
@Repository

/* We have following Repository interfaces available in spring data jpa.
    Repository, CrudRepository, PagingAndSortingRepository, JpaRepository
    All these interfaces uses generics to specify the entity to be managed by the repository and its primary key type
*/

// We have crated StudentRepositoryTest class to run and test this class as we dont have service or controllers in this
// application. For looking all the query methods that are available in spring data jpa,
// see reference documentation for spring data jpa. There you will find all the available ways for creating query methods.
//  You will also see there how the jpql query is created for each method.
public interface StudentRepository extends JpaRepository<Student, Long> {

    public List<Student> findByFirstName(String firstName);

    // This works same as 'select * from student where first_name like %name%'
    public List<Student> findByFirstNameContaining(String name);

    /*
        select
        student0_.student_id as student_1_0_,
        student0_.email_address as email_ad2_0_,
        student0_.first_name as first_na3_0_,
        student0_.guardian_email as guardian4_0_,
        student0_.guardian_mobile as guardian5_0_,
        student0_.guardian_name as guardian6_0_,
        student0_.last_name as last_nam7_0_
        from
            tbl_student student0_
        where
            student0_.last_name is not null
     */
    public List<Student> findByLastNameNotNull();

    // select
    //        student0_.student_id as student_1_0_,
    //        student0_.email_address as email_ad2_0_,
    //        student0_.first_name as first_na3_0_,
    //        student0_.guardian_email as guardian4_0_,
    //        student0_.guardian_mobile as guardian5_0_,
    //        student0_.guardian_name as guardian6_0_,
    //        student0_.last_name as last_nam7_0_
    //    from
    //        tbl_student student0_
    //    where
    //        student0_.guardian_name=?
    public List<Student> findByGuardianName(String guardianName);

    public Student findByFirstNameAndLastName(String firstName, String lastName);

    // This annotation allows us to write a jpql query instead of using a method query. We can give any name to such method
    // which used jpql query. We need not provide standard method name as above. for example: findByEmailId is the standard
    // method name to be used if we were to use query method. but as we are using jpql we have give name getStudentByEmailAddress
    // to our method. We can give it a standard name also ie findByEmailId, and still write jpql query using @Query annotation
    // jpql query will take precedence over the query method. jpql query is based on the class that you have defined and not
    // on the basis of the table and columns that you have defined in the database.
    @Query("select s from Student s where s.emailId = :emailAddress")
    public Student getStudentByEmailAddress(String emailAddress);


    // As we are returning only firstname here, so the return type cannot be Student. If return type you have given as Student
    //  Then there will be a classcast exception.
    // java.lang.ClassCastException: class java.lang.String cannot be cast to class com.techworld.spring.data.jpa.startup.project.entities.Student
    @Query("select s.firstName from Student s where s.emailId = :emailAddress")
    public Student getStudentFirstNameByEmailAddressWithException(String emailAddress);

    @Query("select s.firstName from Student s where s.emailId = :emailAddress")
    public String getStudentFirstNameByEmailAddress(String emailAddress);

    /*
        Suppose, you have a very complex query which you cannot write in jpql, then you can write a native query.
        Disadvantage is that this query will not be database platform independent.ie if you are using mysql database then
        you will be writing a mysql compatible query which might not run on oracle database if in future you have to
        change to oracle. But you can change only such queries according to your database if you have to change in future.
     */
    //Native
    @Query(value = "select * from tbl_student s where s.email_address = :emailAddress", nativeQuery = true)
    public Student getStudentByEmailAddressNative(String emailAddress);

    /*  As we are modifying the values or deleting the values in the database, so we will use this annotation
        With help of this annotation we tell the hibernate that we are executing a query for which we dont expect a
        resultset. If we dont provide this annotation for update query then hibernate will give an exception that it is
        trying to extract a resultset but could not find. Because hibernate will always try to get a resultset when it
        execute a query. So the query will not be executed.

        Caused by: java.sql.SQLException : Statement.executeQuery() cannot issue statements that do not produce result sets.
     */
    @Modifying

    /*  When we do any writing in database like create, update or delete, we need a transaction. So that a transaction started
        then the query is executed and then the transaction is committed. By using this annotation we made our query as
        transactional.
        Ideally or generally, we will use this transaction on service methods. So that when we call a service method, then
        the whole method is run inside a transaction. Which helps if we have lets say 3 queries or repository methods
        to call from a service method and we want all to run in a single transaction. So that if any one of the 3 gets failed
        then the whole transaction gets rolled back. This annotation can be used on method as well ass at class level.

        If we don't specify this annotation then we will get the following error
        org.springframework.dao.InvalidDataAccessApiUsageException: Executing an update/delete query; nested exception is javax.persistence.TransactionRequiredException: Executing an update/delete query

        As hibernate requires a transaction whenever it is updating or deleting a record.
    */
    @Transactional
    @Query(value = "update tbl_student set first_name = :firstName where email_address = :emailAddress", nativeQuery = true)
    public int updateStudentFirstNameByEmailAddress(String firstName, String emailAddress);

}
