package com.alekseenko.lms.mapper;

import com.alekseenko.lms.domain.Course;
import com.alekseenko.lms.dto.CourseDto;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

@Service
public class CourseMapper {

  public CourseDto mapToCourseDtoWithoutUser(Course course) {
    CourseDto courseDto = new CourseDto();

    courseDto.setId(course.getId());
    courseDto.setAuthor(course.getAuthor());
    courseDto.setTitle(course.getTitle());
    courseDto.setDescription(course.getDescription());
    courseDto.setDurationWeeks(course.getDurationWeeks());
    courseDto.setTag(course.getTag());
    courseDto.setAvgRating(course.getAvgRating());
    courseDto.setCourseImage(course.getCourseImage());
    courseDto.setCreatedAt((course.getCreatedAt() != null) ? course.getCreatedAt()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : LocalDate.now().toString());

    return courseDto;
  }

  public CourseDto mapToCourseDtoWithoutImage(Course course) {
    CourseDto courseDto = new CourseDto();

    courseDto.setId(course.getId());
    courseDto.setAuthor(course.getAuthor());
    courseDto.setTitle(course.getTitle());
    courseDto.setDescription(course.getDescription());
    courseDto.setDurationWeeks(course.getDurationWeeks());
    courseDto.setTag(course.getTag());
    courseDto.setAvgRating(course.getAvgRating());
    courseDto.setUsers(course.getUsers());

    return courseDto;
  }

  public Course mapToCourseWithoutUser(CourseDto courseDto) {
    return new Course(
        courseDto.getId(),
        courseDto.getAuthor(),
        courseDto.getTitle(),
        courseDto.getDescription(),
        courseDto.getDurationWeeks(),
        courseDto.getTag(),
        courseDto.getCourseImage()
    );
  }
}
