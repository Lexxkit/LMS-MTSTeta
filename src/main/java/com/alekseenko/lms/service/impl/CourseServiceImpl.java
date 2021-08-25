package com.alekseenko.lms.service.impl;

import com.alekseenko.lms.dao.CourseRepository;
import com.alekseenko.lms.dao.UserRepository;
import com.alekseenko.lms.domain.Course;
import com.alekseenko.lms.dto.CourseDto;
import com.alekseenko.lms.exception.AccessDeniedException;
import com.alekseenko.lms.exception.NotFoundException;
import com.alekseenko.lms.mapper.CourseMapper;
import com.alekseenko.lms.service.CourseService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

  private final CourseRepository courseRepository;
  private final CourseMapper courseMapper;
  private final UserRepository userRepository;

  @Autowired
  public CourseServiceImpl(CourseRepository courseRepository,
      CourseMapper courseMapper,
      UserRepository userRepository) {
    this.courseRepository = courseRepository;
    this.courseMapper = courseMapper;
    this.userRepository = userRepository;
  }

  @Override
  public List<CourseDto> getAllCourses(String titlePrefix, String sortField, String sortDirection) {
    if (titlePrefix == null) {
      return getAllCourses(sortField, sortDirection);
    } else {
      return getCoursesByTitleWithPrefix(titlePrefix).getContent();
    }
  }

  @Override
  public CourseDto getCourseTemplate() {
    return new CourseDto();
  }

  @Override
  public List<CourseDto> getAllCourses(String sortField, String sortDirection) {
    Sort sort = sortContent(sortField, sortDirection);
    return courseRepository.findAll(sort).stream()
        .map(courseMapper::mapToCourseDtoWithoutUser)
        .collect(Collectors.toList());
  }

  @Override
  public Page<CourseDto> findPaginated(int pageNumber, int pageSize, String titlePrefix) {
    if (titlePrefix != null) {
      return getCoursesByTitleWithPrefix(titlePrefix);
    } else {
      Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
      return courseRepository.findAll(pageable).map(courseMapper::mapToCourseDtoWithoutUser);
    }
  }

  @Override
  public CourseDto getCourseById(Long id) {
    return courseRepository.findById(id)
        .map(courseMapper::mapToCourseDtoWithoutImage)
        .orElseThrow(() -> new NotFoundException(String.format("Course with id#%d not found", id)));
  }

  @Override
  public List<CourseDto> getCoursesForUser(String username) {
    return courseRepository.findByUsername(username).stream()
        .map(courseMapper::mapToCourseDtoWithoutUser)
        .collect(Collectors.toList());
  }

  @Override
  public void saveCourse(CourseDto courseDto) {
    courseRepository.save(courseMapper.mapToCourseWithoutUser(courseDto));
  }

  @Override
  public void deleteCourse(Long id) {
    courseRepository.deleteById(id);
  }

  @Override
  public Page<CourseDto> getCoursesByTitleWithPrefix(String prefix) {
    return courseRepository.findByTitleContainingIgnoreCase(prefix, Pageable.unpaged())
        .map(courseMapper::mapToCourseDtoWithoutUser);
  }

  @Override
  public void setUserCourseConnection(Long userId, Long courseId) {
    Course course = courseRepository.findById(courseId).orElseThrow(
        () -> new NotFoundException(String.format("Course with id#%d not found", courseId)));
    course.getUsers().add(userRepository.getById(userId));
    courseRepository.save(course);
  }

  @Override
  public void removeUserCourseConnection(Long userId, Long courseId, String username) {

    if (userRepository.getById(userId).getUsername().equals(username)) {
      Course course = courseRepository.findById(courseId).orElseThrow(
          () -> new NotFoundException(String.format("Course with id#%d not found", courseId)));
      course.getUsers().remove(userRepository.getById(userId));
      courseRepository.save(course);
    } else {
      throw new AccessDeniedException("Access Denied");
    }
  }

  private Sort sortContent(String sortField, String sortDirection) {
    return sortDirection.equalsIgnoreCase(Direction.ASC.name()) ? Sort.by(sortField).ascending() :
        Sort.by(sortField).descending();
  }
}
