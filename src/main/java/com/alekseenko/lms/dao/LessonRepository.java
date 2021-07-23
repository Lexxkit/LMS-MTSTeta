package com.alekseenko.lms.dao;

import com.alekseenko.lms.domain.Lesson;
import com.alekseenko.lms.dto.LessonDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query("select new com.alekseenko.lms.dto.LessonDto(l.id, l.title, l.course.id) " +
            "from Lesson l where l.course.id = :id")
    List<LessonDto> findAllForLessonIdWithoutText(@Param("id") Long id);
}
