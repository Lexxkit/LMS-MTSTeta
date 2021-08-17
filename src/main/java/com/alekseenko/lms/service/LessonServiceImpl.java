package com.alekseenko.lms.service;

import com.alekseenko.lms.controller.NotFoundException;
import com.alekseenko.lms.dao.CourseRepository;
import com.alekseenko.lms.dao.LessonRepository;
import com.alekseenko.lms.domain.Course;
import com.alekseenko.lms.domain.Lesson;
import com.alekseenko.lms.dto.LessonDto;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LessonServiceImpl implements LessonService {

  private final LessonRepository lessonRepository;
  private final CourseRepository courseRepository;

  @Autowired
  public LessonServiceImpl(LessonRepository lessonRepository, CourseRepository courseRepository) {
    this.lessonRepository = lessonRepository;
    this.courseRepository = courseRepository;
  }

  @Override
  public LessonDto getLessonById(Long id) {
    return lessonRepository.findById(id)
        .map(lsn -> new LessonDto(lsn.getId(), lsn.getTitle(), lsn.getText(),
            lsn.getCourse().getId()))
        .orElseThrow(NotFoundException::new);
  }

  @Override
  public List<LessonDto> getAllForLessonIdWithoutText(Long id) {
    return lessonRepository.findAllForLessonIdWithoutText(id);
  }

  @Override
  public void saveLesson(LessonDto lessonDto) {
    Course currentCourse = courseRepository.findById(lessonDto.getCourseId())
        .orElseThrow(NotFoundException::new);
    Lesson lesson = new Lesson(
        lessonDto.getId(),
        lessonDto.getTitle(),
        lessonDto.getText(),
        currentCourse
    );
    lessonRepository.save(lesson);
  }

  @Override
  public void deleteLesson(Long id) {
    lessonRepository.deleteById(id);
  }
}
