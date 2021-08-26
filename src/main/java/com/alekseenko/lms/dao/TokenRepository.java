package com.alekseenko.lms.dao;

import com.alekseenko.lms.domain.User;
import com.alekseenko.lms.domain.VerificationToken;
import java.sql.Date;
import java.util.stream.Stream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<VerificationToken, Long> {

  VerificationToken findByToken(String token);

  VerificationToken findByUser(User user);

  Stream<VerificationToken> findAllByExpiryDateLessThan(Date now);

  void deleteByExpiryDateLessThan(Date now);

  @Modifying
  @Query("delete from VerificationToken t where t.expiryDate <= ?1")
  void deleteAllExpiredSince(Date now);
}
