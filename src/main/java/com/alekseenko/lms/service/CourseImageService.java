package com.alekseenko.lms.service;

import java.io.InputStream;
import java.util.Optional;

public interface CourseImageService {

  String getContentTypeByCourse(Long courseId);

  Optional<byte[]> getCourseImageByCourse(Long courseId);

  void saveCourseImage(Long courseId, String contentType, InputStream is);
}
