package com.alekseenko.lms.service;

import com.alekseenko.lms.dto.CourseDto;
import java.util.List;
import org.springframework.data.domain.Page;

public interface CourseService {

  List<CourseDto> getAllCourses(String titlePrefix, String sortField, String sortDirection);

  CourseDto getCourseTemplate();

  List<CourseDto> getAllCourses(String sortField, String sortDirection);

  Page<CourseDto> findPaginated(int pageNumber, int pageSize, String titlePrefix);

  CourseDto getCourseById(Long id);

  List<CourseDto> getCoursesForUser(String username);

  void saveCourse(CourseDto courseDto);

  void deleteCourse(Long id);

  Page<CourseDto> getCoursesByTitleWithPrefix(String prefix);

  void setUserCourseConnection(Long userId, Long courseId);

  void removeUserCourseConnection(Long userId, Long courseId, String username);
}
