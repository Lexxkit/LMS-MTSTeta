package com.alekseenko.lms.service;

import com.alekseenko.lms.dao.CourseRepository;
import com.alekseenko.lms.domain.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course getCourseTemplate() {
        return new Course();
    }

    @Override
    public List<Course> getAllCourses() {
        return  courseRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
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
        courseRepository.deleteById(id);
    }

    @Override
    public List<Course> getCoursesByTitleWithPrefix(String prefix) {
        return courseRepository.findByTitleLike(prefix);
    }
}
