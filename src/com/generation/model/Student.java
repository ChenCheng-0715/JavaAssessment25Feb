package com.generation.model;

import java.util.*;

public class Student extends Person implements Evaluation {

    float PASS_MIN_GRADE = 3.0f;
    //creating HashMap to store student enrolled courses, course as key, grade as value
    private final Map<Course, Float> enrolledCourse;

    public Student( String id, String name, String email, Date birthDate ) {
        super( id, name, email, birthDate );
        this.enrolledCourse = new HashMap<>();
    }

    public Map<Course, Float> getEnrolledCourse() { return this.enrolledCourse; }

    public boolean enrollToCourse( Course course ) {
        //check if new course is same with existing course
        if (!this.enrolledCourse.containsKey(course)) {
            this.enrolledCourse.put(course, 0f);
            return true;
        }
        return false;
    }

    @Override
    public Map<Course, Float> findPassedCourses() {
        //create new HashMap to store passed courses with grade
        Map<Course, Float> passedCourses = new HashMap<>();
        //loop thru each item in enrolled course map to compare the grade with passing grade
        for (Course course: enrolledCourse.keySet()) {
            if (enrolledCourse.get(course) >= PASS_MIN_GRADE) {
                passedCourses.put(course, enrolledCourse.get(course));
            }
        }
            return passedCourses;
    }

    public Course findCourseById( String courseId ) {
        //loop thru each item in enrolled course map to check course code with given courseID
        for (Course course: enrolledCourse.keySet()) {
            if (course.getCode().equals(courseId)) {
                return course;
            }
        }
        return null;
    }


    @Override
    public boolean getEnrolledCourses() {
        //check student has enrolled any course or not
        if (enrolledCourse.size() != 0) {
            //print out each enrolled course with grade
            for (Course course: enrolledCourse.keySet()) {
                System.out.println(course + " Grade: " + enrolledCourse.get(course));
            }
            return true;
        }
        System.out.println("No Course enrolled");
        return false;
    }

    @Override
    public String toString()
    {
        return "Student {" + super.toString() + "}";
    }

}
