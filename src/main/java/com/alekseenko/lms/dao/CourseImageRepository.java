package com.alekseenko.lms.dao;

import com.alekseenko.lms.domain.CourseImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourseImageRepository extends JpaRepository<CourseImage, Long> {

    Optional<CourseImage> findByCourseId(Long courseId);
}
