package com.generation.service;

import com.generation.model.Course;
import com.generation.model.Student;

import java.util.*;

public class StudentService {
    private final Map<String, Student> students = new HashMap<>();

    public void subscribeStudent( Student student )
    {
        students.put( student.getId(), student );
    }

    public Student findStudent( String studentId ) {
        //find a student with given id, return null if not found
        if (students.containsKey(studentId)) {
            return students.get(studentId);
        }
        return null;
    }

    public boolean showSummary() {
        //check if any student has been registered or not
        if (checkRegisteredStudent()) {
            // if there is registered student, loop and printout each item
            for (String studentId : students.keySet()) {
                System.out.println(students.get(studentId));
                System.out.println("Enrolled Courses:");
                students.get(studentId).getEnrolledCourses();
            }
            return true;
        }
        // if no student registered, return false
        return false;
    }

    public boolean enrollToCourse( String studentId, Course course ) {
        //return true if course enrolled successful, false if duplicate course
        return students.get(studentId).enrollToCourse(course);
    }

    public boolean checkRegisteredStudent() {
        return students.size() != 0;
    }
}
