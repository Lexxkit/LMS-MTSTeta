package com.alekseenko.lms.service;

import com.alekseenko.lms.dao.CourseRepository;
import com.alekseenko.lms.domain.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseLister {
    private final CourseRepository repository;

    @Autowired
    public CourseLister(CourseRepository repository) {
        this.repository = repository;
    }


    public Optional<Course> findById(Long id) {
        return repository.findById(id);
    }

    public void save(Course course) {
        repository.save(course);
    }

    public void delete(Long id) {
        repository.delete(id);
    }

    public List<Course> findCourseByTitleWithPrefix(String prefix) {
        return repository.findAll()
                .stream().filter(course -> course.getTitle().toLowerCase().startsWith(prefix.toLowerCase()))
                .collect(Collectors.toList());
    }
}
