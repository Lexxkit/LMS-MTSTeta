package com.alekseenko.lms.service;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;

import com.alekseenko.lms.controller.NotFoundException;
import com.alekseenko.lms.dao.CourseImageRepository;
import com.alekseenko.lms.dao.CourseRepository;
import com.alekseenko.lms.domain.Course;
import com.alekseenko.lms.domain.CourseImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CourseImageServiceImpl implements CourseImageService {

  private static final Logger logger = LoggerFactory.getLogger(AvatarImageServiceImpl.class);
  private final CourseImageRepository courseImageRepository;
  private final CourseRepository courseRepository;

  @Autowired
  public CourseImageServiceImpl(CourseImageRepository courseImageRepository,
      CourseRepository courseRepository) {
    this.courseImageRepository = courseImageRepository;
    this.courseRepository = courseRepository;
  }

  @Value("${file.storage.path}")
  private String path;

  @Override
  public String getContentTypeByCourse(Long courseId) {
    return courseImageRepository.findByCourseId(courseId)
        .map(CourseImage::getContentType)
        .orElseThrow(NotFoundException::new);
  }

  @Override
  public Optional<byte[]> getCourseImageByCourse(Long courseId) {
    return courseImageRepository.findByCourseId(courseId)
        .map(CourseImage::getFilename)
        .map(filename -> {
          try {
            return Files.readAllBytes(Path.of(path, filename));
          } catch (IOException ex) {
            logger.error("Can't read file {}", filename, ex);
            throw new IllegalStateException(ex);
          }
        });
  }

  @Override
  @Transactional
  public void saveCourseImage(Long courseId, String contentType, InputStream is) {
    Optional<CourseImage> opt = courseImageRepository.findByCourseId(courseId);
    CourseImage courseImage;
    String filename;
    if (opt.isEmpty()) {
      filename = UUID.randomUUID().toString();
      Course course = courseRepository.findById(courseId)
          .orElseThrow(NotFoundException::new);
      courseImage = new CourseImage(null, contentType, filename, course);
    } else {
      courseImage = opt.get();
      filename = courseImage.getFilename();
      courseImage.setContentType(contentType);
    }
    courseImageRepository.save(courseImage);

    try (OutputStream os = Files
        .newOutputStream(Path.of(path, filename), CREATE, WRITE, TRUNCATE_EXISTING)) {
      is.transferTo(os);
    } catch (Exception ex) {
      logger.error("Can't write to file {}", filename, ex);
      throw new IllegalStateException(ex);
    }
  }
}
