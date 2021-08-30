package com.alekseenko.lms.dao;

import com.alekseenko.lms.domain.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  @Query("from User u " +
      "where u.id not in ( " +
      "select u.id " +
      "from User u " +
      "inner join u.courses c " +
      "where c.id = :courseId)")
  List<User> findUsersNotAssignedToCourse(@Param("courseId") Long courseId);

  Optional<User> findUserByUsername(String username);

  User findUserByEmail(String username);
}
