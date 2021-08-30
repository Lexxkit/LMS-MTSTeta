package com.alekseenko.lms.service;

import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

public interface CourseImageService {

  String getContentTypeByCourse(Long courseId);

  Optional<byte[]> getCourseImageByCourse(Long courseId);

  void saveCourseImage(Long courseId, MultipartFile courseImage);

  Optional<byte[]> getDataImagedByCourseId(Long courseId);
}
