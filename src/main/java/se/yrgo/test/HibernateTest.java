package se.yrgo.test;

import jakarta.persistence.*;

import se.yrgo.domain.Subject;
import se.yrgo.domain.Tutor;

import java.util.List;

public class HibernateTest {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("databaseConfig");
        EntityManager em = emf.createEntityManager();
        EntityTransaction etx = em.getTransaction();
        etx.begin();

        String queryTemplate;

        /*|-----|-----|-----|Adding Tutors and Students|-----|-----|-----|*/
        Tutor t1 = new Tutor("ABC123", "Johan Smith", 290000);
        em.persist(t1);

        t1.createStudentAndAddtoTeachingGroup("Eva  Sands", "1-SAN-2019",
                "street-1", "London", "4455");
        t1.createStudentAndAddtoTeachingGroup("Sam Everest", "2-EVE-2018",
                "street-2", "Paris", "6767");
        t1.createStudentAndAddtoTeachingGroup("Sam Luff", "1-LUF-2018",
                "street-3", "city 2", "8792");
        t1.createStudentAndAddtoTeachingGroup("Jimi Hendriks", "1-HEN-2017",
                "street-4", "city 2", "1212");

        Subject programming = new Subject("programming", 2);
        em.persist(programming);
        t1.addSubjectsToTeach(programming);
        programming.addTutorToSubject(t1);

        Tutor t2 = new Tutor("XYZ789", "Taylor Evergreen", 290001);
        em.persist(t2);

        t2.createStudentAndAddtoTeachingGroup("Emma Watts", "1-WAT-2018",
                "street-5", "city 1", "1758");
        t2.createStudentAndAddtoTeachingGroup("Groose Gustaffson", "2-GUS-2018",
                "street-6", "city 1", "8971");
        t2.createStudentAndAddtoTeachingGroup("Matilda", "0-ERR-9999",
                "stree-7", "Supply Closet", "1234");

        Subject science = new Subject("science", 2);
        em.persist(science);
        t2.addSubjectsToTeach(science);
        science.addTutorToSubject(t2);

        Tutor t3 = new Tutor("PET502", "Findus", 9592);
        em.persist(t3);

        //System.out.println("Science id: " + science.getSubjectId());

        System.out.println();
        /*|-----|-----|-----|U1: Navigera över relationer(med member of)|-----|-----|-----|*/
        Subject scienceSubject = em.find(Subject.class, 4);
        queryTemplate = "FROM Tutor AS tutor JOIN tutor.teachingGroup AS students JOIN tutor.subjectsToTeach WHERE :scienceSubject MEMBER OF tutor.subjectsToTeach";
        TypedQuery<Object[]> query1 = em.createQuery(queryTemplate, Object[].class);
        query1.setParameter("scienceSubject", scienceSubject);
        List<Object[]> studentsForScience = query1.getResultList();
        for(Object[] obj : studentsForScience) {
            System.out.println(obj[1]);
        }

        System.out.println();
        /*|-----|-----|-----|U2: Report Query - Multiple fields (med join)|-----|-----|-----|*/
        queryTemplate = "SELECT tutor.name, student.name FROM Tutor tutor JOIN tutor.teachingGroup AS student";
        List<Object[]> results2 = em.createQuery(queryTemplate).getResultList();

        for(Object[] obj : results2) {
            String studentName = (String) obj[1];
            String tutorName = (String) obj[0];
            System.out.println("Student: " + studentName + " -||- Tutor: " + tutorName);
        }

        System.out.println();
        /*|-----|-----|-----|U3: Report Query - Aggregation|-----|-----|-----|*/
        queryTemplate = "SELECT AVG(subject.numberOfSemesters) FROM Subject subject";
        double averageSemesterLength = (Double) em.createQuery(queryTemplate).getSingleResult();
        System.out.println("The average length of the term is " + averageSemesterLength);

        System.out.println();
        /*|-----|-----|-----|U4: Query med Aggregation|-----|-----|-----|*/
        queryTemplate = "SELECT MAX(tutor.salary) FROM Tutor tutor";
        int tutorMaxSalary = (Integer) em.createQuery(queryTemplate).getSingleResult();
        System.out.println("The MAX Salary from Tutor is: " + tutorMaxSalary + "kr");

        System.out.println();
        /*|-----|-----|-----|U5: NamedQuery|-----|-----|-----|*/
        List<Tutor> results5 = em.createNamedQuery("tutorFindSalaryGreaterThan10000", Tutor.class).getResultList();
        for (Tutor tutor : results5) {
            System.out.println(tutor + " has a Salary greater than 10'000kr");
        }

        etx.commit();
        em.close();
    }

}

