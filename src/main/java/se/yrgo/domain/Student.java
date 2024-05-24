package se.yrgo.domain;


import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class Student
{
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int id;
    @Column(unique=true, nullable=false)
    private String enrollmentID;
    private String name;

  //  @ManyToOne
    //@JoinColumn(name="TUTOR_FK")
    //private Tutor tutor;

    @Column(name="NUM_COURSES")
    private Integer numberOfCourses;

    @Embedded
    private Address address;

    public Student() {}
    public Student(String name, String enrollmentID, String street, String city, String zipcode){
        this.name = name;
        this.enrollmentID= enrollmentID;
        this.address = new Address(street, city, zipcode);
    }

    public String getEnrollmentID() {
        return enrollmentID;
    }

    public Student(String name, String enrollmentID) {
        this.name = name;
         this.enrollmentID= enrollmentID;
    }

    //public void allocateTutor(Tutor tutor) {
      //  this.tutor=tutor;
    //}

    //public String getTutorName() {
      //  return this.tutor.getName();
    //}
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(getEnrollmentID(), student.getEnrollmentID());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, getEnrollmentID());
    }



}
