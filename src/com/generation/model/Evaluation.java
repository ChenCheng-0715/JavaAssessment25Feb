package com.generation.model;

import java.util.List;
import java.util.Map;

public interface Evaluation {

    Map<Course, Float> findPassedCourses();

    boolean getEnrolledCourses();
}
