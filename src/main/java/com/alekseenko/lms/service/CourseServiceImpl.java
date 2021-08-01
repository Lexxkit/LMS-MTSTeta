package com.alekseenko.lms.service;

import com.alekseenko.lms.controller.AccessDeniedException;
import com.alekseenko.lms.controller.NotFoundException;
import com.alekseenko.lms.dao.CourseRepository;
import com.alekseenko.lms.dao.UserRepository;
import com.alekseenko.lms.domain.Course;
import com.alekseenko.lms.dto.CourseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository,
                             UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public CourseDto getCourseTemplate() {
        return new CourseDto();
    }

    @Override
    public List<CourseDto> getAllCourses() {
        return  courseRepository.findAll(Sort.by(Sort.Direction.ASC, "id")).stream()
                .map(course -> new CourseDto(course.getId(), course.getAuthor(), course.getTitle()))
                .collect(Collectors.toList());
    }

    @Override
    public CourseDto getCourseById(Long id) {
        return courseRepository.findById(id)
                .map(course -> new CourseDto(course.getId(), course.getAuthor(), course.getTitle(), course.getUsers()))
                .orElseThrow(NotFoundException::new);
    }

    @Override
    public void saveCourse(CourseDto courseDto) {
        Course course = new Course(
                courseDto.getId(),
                courseDto.getAuthor(),
                courseDto.getTitle()
        );
        courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public List<CourseDto> getCoursesByTitleWithPrefix(String prefix) {
        return courseRepository.findByTitleLike(prefix).stream()
                .map(course -> new CourseDto(course.getId(), course.getAuthor(), course.getTitle()))
                .collect(Collectors.toList());
    }

    @Override
    public void setUserCourseConnection(Long userId, Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(NotFoundException::new);
        course.getUsers().add(userRepository.getById(userId));
        courseRepository.save(course);
    }

    @Override
    public void removeUserCourseConnection(Long userId, Long courseId, String username, boolean isAdmin) {

        if (userRepository.getById(userId).getUsername().equals(username) || isAdmin) {
            Course course = courseRepository.findById(courseId).orElseThrow(NotFoundException::new);
            course.getUsers().remove(userRepository.getById(userId));
            courseRepository.save(course);
        } else {
            throw new AccessDeniedException();
        }

    }
}
