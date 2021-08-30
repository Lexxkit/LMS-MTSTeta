package com.alekseenko.lms.service.impl;

import static com.alekseenko.lms.util.DataUtil.getContentType;
import static com.alekseenko.lms.util.DataUtil.getInputStream;
import static com.alekseenko.lms.util.DataUtil.printFileProperties;
import static com.alekseenko.lms.util.DataUtil.writeFile;

import com.alekseenko.lms.dao.CourseImageRepository;
import com.alekseenko.lms.dao.CourseRepository;
import com.alekseenko.lms.domain.Course;
import com.alekseenko.lms.domain.CourseImage;
import com.alekseenko.lms.exception.NotFoundException;
import com.alekseenko.lms.service.CourseImageService;
import com.alekseenko.lms.util.DataUtil;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CourseImageServiceImpl implements CourseImageService {

  private final CourseImageRepository courseImageRepository;
  private final CourseRepository courseRepository;

  @Value("${file.storage.logo.path}")
  private String path;

  @Value("${file.storage.img.path.default}")
  private String defaultImgPath;

  @Autowired
  public CourseImageServiceImpl(CourseImageRepository courseImageRepository,
      CourseRepository courseRepository) {
    this.courseImageRepository = courseImageRepository;
    this.courseRepository = courseRepository;
  }

  private boolean checkCourseImage(Long courseId) {
    return courseImageRepository.existsCourseImageByCourse_Id(courseId);
  }

  public byte[] getDefaultCourseImageData() {
    String DEFAULT_IMAGE_FILENAME = "course-default.png";
    return DataUtil.readData(DEFAULT_IMAGE_FILENAME, defaultImgPath);
  }

  @Override
  public String getContentTypeByCourse(Long courseId) {
    String contentType;
    if (checkCourseImage(courseId)) {
      contentType = getCourseImagedByCourseId(courseId)
          .map(CourseImage::getContentType)
          .orElseThrow(() -> new NotFoundException("image mot found"));
    } else {
      contentType = "image/png";
    }
    return contentType;
  }

  private Optional<CourseImage> getCourseImagedByCourseId(Long courseId) {
    Course course = courseRepository.findById(courseId)
        .orElseThrow(() -> new NotFoundException("User not found"));
    return Optional.ofNullable(course.getCourseImage());
  }

  public Optional<byte[]> getCourseImageByCourse(Long courseId) {
    return getCourseImagedByCourseId(courseId)
        .map(CourseImage::getFilename)
        .map((String filename) -> DataUtil.readData(filename, path));
  }

  public Optional<byte[]> getDataImagedByCourseId(Long courseId) {
    Optional<byte[]> data;
    if (checkCourseImage(courseId)) {
      data = getCourseImageByCourse(courseId);
    } else {
      data = Optional.ofNullable(getDefaultCourseImageData());
    }
    return data;
  }

  @Override
  @Transactional
  public void saveCourseImage(Long courseId, MultipartFile inputImage) {
    if (!inputImage.isEmpty()) {
      printFileProperties(inputImage);
      String contentType = getContentType(inputImage);
      InputStream is = getInputStream(inputImage);
      Optional<CourseImage> opt = courseImageRepository.findByCourseId(courseId);
      CourseImage courseImage;
      String filename;

      if (Files.notExists(Path.of(path))) {
        new File(path).mkdir();
      }

      if (opt.isEmpty()) {
        filename = UUID.randomUUID().toString();
        Course course = courseRepository.findById(courseId)
            .orElseThrow(
                () -> new NotFoundException(
                    String.format("Course with id#%d not found", courseId)));
        courseImage = new CourseImage(null, contentType, filename, course);
      } else {
        courseImage = opt.get();
        filename = courseImage.getFilename();
        courseImage.setContentType(contentType);
      }
      courseImageRepository.save(courseImage);
      writeFile(is, filename, path);
    }
  }
}
