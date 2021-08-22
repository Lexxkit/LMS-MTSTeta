package com.alekseenko.lms.dao;

import com.alekseenko.lms.domain.Course;
import com.alekseenko.lms.domain.Module;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module, Long> {

  List<Module> findAllByCourse(Course course);
}
