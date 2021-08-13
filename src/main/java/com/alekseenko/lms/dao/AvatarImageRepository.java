package com.alekseenko.lms.dao;

import com.alekseenko.lms.domain.AvatarImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AvatarImageRepository extends JpaRepository<AvatarImage, Long> {
    @Query("from AvatarImage ai inner join ai.user u where u.username = :username")
    Optional<AvatarImage> findByUsername(@Param("username") String username);
}
