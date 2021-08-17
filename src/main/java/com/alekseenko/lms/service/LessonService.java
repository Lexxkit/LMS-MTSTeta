package com.alekseenko.lms.service;

import com.alekseenko.lms.dto.LessonDto;
import java.util.List;

public interface LessonService {

  LessonDto getLessonById(Long id);

  List<LessonDto> getAllForLessonIdWithoutText(Long id);

  void saveLesson(LessonDto lessonDto);

  void deleteLesson(Long id);
}
