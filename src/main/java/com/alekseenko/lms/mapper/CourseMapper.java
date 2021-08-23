package com.alekseenko.lms.mapper;

import com.alekseenko.lms.domain.Course;
import com.alekseenko.lms.dto.CourseDto;
import org.springframework.stereotype.Service;

@Service
public class CourseMapper {

  public CourseDto mapToCourseDtoWithoutUser(Course course) {
    CourseDto courseDto = new CourseDto();

    courseDto.setId(course.getId());
    courseDto.setAuthor(course.getAuthor());
    courseDto.setTitle(course.getTitle());
    courseDto.setCourseImage(course.getCourseImage());

    return courseDto;
  }

  public CourseDto mapToCourseDtoWithoutImage(Course course) {
    CourseDto courseDto = new CourseDto();

    courseDto.setId(course.getId());
    courseDto.setAuthor(course.getAuthor());
    courseDto.setTitle(course.getTitle());
    courseDto.setUsers(course.getUsers());

    return courseDto;
  }
}
