package com.alekseenko.lms.service;

import com.alekseenko.lms.domain.Lesson;
import com.alekseenko.lms.dto.LessonDto;

import java.util.List;
import java.util.Optional;

public interface LessonService {

    Optional<Lesson> getLessonById(Long id);

    List<LessonDto> getAllForLessonIdWithoutText(Long id);

    void saveLesson(Lesson lesson);

    void deleteLesson(Long id);
}
