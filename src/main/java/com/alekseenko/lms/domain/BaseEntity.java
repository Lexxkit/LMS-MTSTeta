package com.alekseenko.lms.domain;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
public abstract class BaseEntity {

  @Column(updatable = false)
  @CreationTimestamp
  private LocalDateTime createdAt;

  @ManyToOne
  private User createdByUser;

  @Column
  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @ManyToOne
  private User updatedByUser;

  public BaseEntity() {
  }

  public BaseEntity(LocalDateTime createdAt, User createdByUser, LocalDateTime updatedAt,
      User updatedByUser) {
    this.createdAt = createdAt;
    this.createdByUser = createdByUser;
    this.updatedAt = updatedAt;
    this.updatedByUser = updatedByUser;
  }

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public User getCreatedByUser() {
    return createdByUser;
  }

  public void setCreatedByUser(User createdByUser) {
    this.createdByUser = createdByUser;
  }

  public LocalDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(LocalDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public User getUpdatedByUser() {
    return updatedByUser;
  }

  public void setUpdatedByUser(User updatedByUser) {
    this.updatedByUser = updatedByUser;
  }
}
