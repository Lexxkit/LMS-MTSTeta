package com.alekseenko.lms.dao;

import com.alekseenko.lms.domain.Lesson;
import com.alekseenko.lms.dto.LessonDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

  @Query("select new com.alekseenko.lms.dto.LessonDto(l.id, l.title, l.module.id) " +
      "from Lesson l where l.module.id = :id")
  List<LessonDto> findAllLessonDtoByModuleId(@Param("id") Long id);
}
