package com.alekseenko.lms.service;

import com.alekseenko.lms.domain.Course;
import com.alekseenko.lms.dto.CourseDto;

import java.util.List;

public interface CourseService {

    CourseDto getCourseTemplate();

    List<CourseDto> getAllCourses();

    CourseDto getCourseById(Long id);

    void saveCourse(CourseDto courseDto);

    void deleteCourse(Long id);

    List<CourseDto> getCoursesByTitleWithPrefix(String prefix);

    void setUserCourseConnection(Long userId, Long courseId);

    void removeUserCourseConnection(Long userId, Long courseId, String username);
}
