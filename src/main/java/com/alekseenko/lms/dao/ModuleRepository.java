package com.alekseenko.lms.dao;

import com.alekseenko.lms.domain.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module, Long> {

}
