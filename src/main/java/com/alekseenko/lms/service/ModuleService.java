package com.alekseenko.lms.service;

import com.alekseenko.lms.dto.CourseDto;
import com.alekseenko.lms.dto.ModuleDto;
import java.util.List;

public interface ModuleService {

  ModuleDto findById(Long id);

  List<ModuleDto> findAllByCourse(CourseDto course);

  List<ModuleDto> findAll();

  void save(ModuleDto moduleDto);

  void deleteModule(Long id);
}
