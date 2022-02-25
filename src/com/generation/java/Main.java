package com.generation.java;

import com.generation.model.Course;
import com.generation.model.Student;
import com.generation.service.CourseService;
import com.generation.service.StudentService;
import com.generation.utils.PrinterHelper;

import java.text.ParseException;
import java.util.InvalidPropertiesFormatException;
import java.util.Scanner;

public class Main {

    public static void main( String[] args ) throws ParseException {
        StudentService studentService = new StudentService();
        CourseService courseService = new CourseService();
        Scanner scanner = new Scanner( System.in );
        int option;
        do {
            PrinterHelper.showMainMenu();
            option = scanner.nextInt();
            switch ( option ) {
                case 1:
                    registerStudent( studentService, scanner );
                    break;
                case 2:
                    findStudent( studentService, scanner );
                    break;
                case 3:
                    gradeStudent( studentService, scanner );
                    break;
                case 4:
                    enrollCourse( studentService, courseService, scanner );
                    break;
                case 5:
                    showStudentsSummary( studentService, scanner );
                    break;
                case 6:
                    showCoursesSummary( courseService, scanner );
                    break;
                case 7:
                    showPassedCourses( studentService, scanner );
                    break;
            }
        } while ( option != 8 );
    }

    private static void enrollCourse( StudentService studentService, CourseService courseService, Scanner scanner ) {
        System.out.println( "Insert student ID" );
        String studentId = scanner.next();
        Student student = studentService.findStudent( studentId );
        if ( student == null ) {
            System.out.println( "Invalid Student ID" );
            return;
        }
        System.out.println( student );
        System.out.println( "Insert course ID" );
        String courseId = scanner.next();
        Course course = courseService.getCourse( courseId );
        if ( course == null ) {
            System.out.println( "Invalid Course ID" );
            return;
        }
        System.out.println( course );
        if (studentService.enrollToCourse( studentId, course )) {
            System.out.println("Student with ID: " + studentId + " enrolled successfully to " + courseId);
        } else {
            System.out.println("Student with ID: " + studentId + " is already enrolled to " + courseId);
        }
    }

    private static void showCoursesSummary( CourseService courseService, Scanner scanner ) {
        courseService.showSummary();
    }

    private static void showStudentsSummary( StudentService studentService, Scanner scanner ) {
        if (!studentService.showSummary()) {
            System.out.println("No Student Yet");
        }
    }

    private static void gradeStudent( StudentService studentService, Scanner scanner ) {

        // if not even one student is registered, return to main menu
        if (studentService.checkRegisteredStudent()) {
            Student student;
            do {
                student = getStudentInformation(studentService, scanner);
            } while (student == null);

            // if no course enrolled for this student, return to main menu
            System.out.println("Enrolled course:");
            if (student.getEnrolledCourses()) {

                boolean invalidCourseID = true;
                do {
                    // check if insert valid course ID to be graded
                    System.out.println("Insert course ID to be graded");
                    String courseId = scanner.next();
                    if (student.findCourseById(courseId) != null) {
                        invalidCourseID = false;
                        float grade = 0f;
                        boolean invalidGrade = true;
                        do {
                            // check if insert valid grade
                            System.out.println("Insert course grade for: " + student.findCourseById(courseId).getName());
                            try {
                                // check if user enter a float number
                                grade = Float.parseFloat(scanner.next());

                                // check if the number is within grade range
                                if (grade > 6 || grade < 1) {
                                    System.out.println("Invalid grade range (Please grade from 1 to 6)");
                                } else {
                                    student.getEnrolledCourse().put(student.findCourseById(courseId), grade);
                                    invalidGrade = false;
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Invalid grade");
                            }
                        } while (invalidGrade);
                    } else {
                        System.out.println("Course ID: '" + courseId + "' not found!");
                    }
                } while (invalidCourseID);
            }
        } else {
            System.out.println("There is no student registered yet!");
        }

    }

    private static Student getStudentInformation( StudentService studentService, Scanner scanner ) {
        System.out.println( "Enter student ID: " );
        String studentId = scanner.next();
        Student student = studentService.findStudent( studentId );
        if ( student == null ) {
            System.out.println( "Student not found" );
        }
        return student;
    }

    private static void findStudent( StudentService studentService, Scanner scanner ) {
        Student student = getStudentInformation( studentService, scanner );
        if ( student != null ) {
            System.out.println( "Student Found: " );
            System.out.println( student );
        }
    }

    private static void registerStudent( StudentService studentService, Scanner scanner ) throws ParseException {
       Student student = PrinterHelper.createStudentMenu( scanner );
       studentService.subscribeStudent( student );
    }

    private static void showPassedCourses(StudentService studentService, Scanner scanner ) {
        System.out.println("Enter student ID: ");
        String studentId = scanner.next();
        Student student = studentService.findStudent(studentId);
        if (student == null) {
            System.out.println("Student not found");
        } else {
            if (student.findPassedCourses().size() == 0) {
                System.out.println("No passed courses available");
            } else {
                for (Course course : student.findPassedCourses().keySet()) {
                    System.out.println(course + " Grade: " + student.findPassedCourses().get(course));
                }
            }
        }
    }

}

