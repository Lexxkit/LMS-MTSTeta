package com.alekseenko.lms.dao;

import com.alekseenko.lms.domain.AvatarImage;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AvatarImageRepository extends JpaRepository<AvatarImage, Long> {

  @Query("from AvatarImage ai inner join ai.user u where u.username = :username")
  Optional<AvatarImage> findByUsername(@Param("username") String username);

  boolean existsAvatarImageByUser_Id(Long courseId);
}
