package com.alekseenko.lms.service;

import com.alekseenko.lms.domain.Course;

import java.util.List;
import java.util.Optional;

public interface CourseService {

    Course getCourseTemplate();

    List<Course> getAllCourses();

    Optional<Course> getCourseById(Long id);

    void saveCourse(Course course);

    void deleteCourse(Long id);

    List<Course> getCoursesByTitleWithPrefix(String prefix);
}
