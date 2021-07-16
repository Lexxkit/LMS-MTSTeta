package com.alekseenko.lms.service;

import com.alekseenko.lms.dao.CourseRepository;
import com.alekseenko.lms.domain.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImplementation implements CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseServiceImplementation(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course createCourse() {
        return new Course();
    }

    @Override
    public Optional<Course> getCourseById(Long id) {
        return courseRepository.findById(id);
    }

    @Override
    public void saveCourse(Course course) {
        courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.delete(id);
    }

    @Override
    public List<Course> getCoursesByTitleWithPrefix(String prefix) {
        return courseRepository.getByTitleWithPrefix(prefix);
    }
}
