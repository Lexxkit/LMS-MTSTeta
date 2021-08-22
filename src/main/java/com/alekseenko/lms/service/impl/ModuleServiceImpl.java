package com.alekseenko.lms.service.impl;

import com.alekseenko.lms.dao.CourseRepository;
import com.alekseenko.lms.dao.ModuleRepository;
import com.alekseenko.lms.domain.Course;
import com.alekseenko.lms.domain.Module;
import com.alekseenko.lms.dto.CourseDto;
import com.alekseenko.lms.dto.ModuleDto;
import com.alekseenko.lms.exception.NotFoundException;
import com.alekseenko.lms.service.ModuleService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ModuleServiceImpl implements ModuleService {

  private final ModuleRepository moduleRepository;
  private final CourseRepository courseRepository;



  @Autowired
  public ModuleServiceImpl(ModuleRepository repository,
      CourseRepository courseRepository) {
    this.moduleRepository = repository;
    this.courseRepository = courseRepository;

  }

  @Override
  public ModuleDto findById(Long id) {
    return moduleRepository.findById(id).map(ModuleDto::new)
        .orElseThrow(() -> new NotFoundException("Module not found"));
  }

  @Override
  public List<ModuleDto> findAllByCourse(CourseDto course) {
    return moduleRepository.findAllByCourse(new Course(course)).stream()
        .map(ModuleDto::new).collect(
            Collectors.toList());
  }

  @Override
  public List<ModuleDto> findAll() {
    return moduleRepository.findAll().stream()
        .map(ModuleDto::new).collect(
            Collectors.toList());
  }

  @Override
  public void save(ModuleDto moduleDto) {
    Course currentCourse = courseRepository.findById(moduleDto.getCourseId())
        .orElseThrow(() -> new NotFoundException(
            String.format("Course with id#%d not found", moduleDto.getId())));
    Module module = new Module(moduleDto, currentCourse);
    moduleRepository.save(module);

  }


  @Override
  public void deleteModule(Long id) {
    moduleRepository.deleteById(id);

  }
}
