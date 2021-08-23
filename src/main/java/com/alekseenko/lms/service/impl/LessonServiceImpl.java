package com.alekseenko.lms.service.impl;

import com.alekseenko.lms.dao.LessonRepository;
import com.alekseenko.lms.dao.ModuleRepository;
import com.alekseenko.lms.domain.Lesson;
import com.alekseenko.lms.domain.Module;
import com.alekseenko.lms.dto.LessonDto;
import com.alekseenko.lms.exception.NotFoundException;
import com.alekseenko.lms.service.LessonService;
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
        .map(lsn -> new LessonDto(lsn.getId(), lsn.getTitle(), lsn.getContent(),
            lsn.getModule().getId()))
        .orElseThrow(() -> new NotFoundException(String.format("Lesson with id#%d not found", id)));
  }

  @Override
  public List<LessonDto> getAllForLessonIdWithoutText(Long id) {
    return lessonRepository.findAllLessonDtoByModuleId(id);
  }

  @Override
  public void saveLesson(LessonDto lessonDto) {
    Module currentModule = moduleRepository.findById(lessonDto.getModuleId())
        .orElseThrow(() -> new NotFoundException(
            String.format("Lesson with id#%d not found", lessonDto.getModuleId())));
    Lesson lesson = new Lesson(
        lessonDto.getId(),
        lessonDto.getTitle(),
        lessonDto.getContent(),
        currentModule,
        currentModule.getCourse().getId()
    );
    lessonRepository.save(lesson);
  }

  @Override
  public void deleteLesson(Long id) {
    lessonRepository.deleteById(id);
  }
}
