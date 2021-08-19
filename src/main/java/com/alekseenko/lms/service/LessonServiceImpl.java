package com.alekseenko.lms.service;

import com.alekseenko.lms.controller.NotFoundException;
import com.alekseenko.lms.dao.LessonRepository;
import com.alekseenko.lms.dao.ModuleRepository;
import com.alekseenko.lms.domain.Lesson;
import com.alekseenko.lms.domain.Module;
import com.alekseenko.lms.dto.LessonDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LessonServiceImpl implements LessonService {

  private final LessonRepository lessonRepository;
  private final ModuleRepository moduleRepository;

  @Autowired
  public LessonServiceImpl(LessonRepository lessonRepository, ModuleRepository moduleRepository) {
    this.lessonRepository = lessonRepository;
    this.moduleRepository = moduleRepository;
  }

  @Override
  public LessonDto getLessonById(Long id) {
    return lessonRepository.findById(id)
        .map(lsn -> new LessonDto(lsn.getId(), lsn.getTitle(), lsn.getDescription(),
            lsn.getModule().getId()))
        .orElseThrow(NotFoundException::new);
  }

  @Override
  public List<LessonDto> getAllForLessonIdWithoutText(Long id) {
    return lessonRepository.findAllForLessonIdWithoutText(id);
  }

  @Override
  public void saveLesson(LessonDto lessonDto) {
    Module currentModule = moduleRepository.findById(lessonDto.getModuleId())
        .orElseThrow(NotFoundException::new);
    Lesson lesson = new Lesson(
        lessonDto.getId(),
        lessonDto.getTitle(),
        lessonDto.getContent(),
        currentModule
    );
    lessonRepository.save(lesson);
  }

  @Override
  public void deleteLesson(Long id) {
    lessonRepository.deleteById(id);
  }
}
