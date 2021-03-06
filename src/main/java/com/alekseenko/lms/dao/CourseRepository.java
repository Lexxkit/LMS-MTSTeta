package com.alekseenko.lms.dao;

import com.alekseenko.lms.domain.Course;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

  Page<Course> findByTitleLike(String title, Pageable pageable);

  Page<Course> findByTitleContainingIgnoreCase(String title, Pageable pageable);

  @Query("from Course c inner join c.users u where u.username = :username")
  List<Course> findByUsername(@Param("username") String username);

}
