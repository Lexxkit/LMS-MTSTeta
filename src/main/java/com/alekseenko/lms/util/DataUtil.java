package com.alekseenko.lms.util;

import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.TRUNCATE_EXISTING;
import static java.nio.file.StandardOpenOption.WRITE;

import com.alekseenko.lms.exception.InternalServerException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class DataUtil {

  private static final Logger logger = LoggerFactory.getLogger(DataUtil.class);

  public static byte[] readData(String filename, String path) {
    try {
      return Files.readAllBytes(Path.of(path, filename));
    } catch (IOException ex) {
      logger.error("Can't read file {}", filename, ex);
      throw new IllegalStateException(ex);
    }
  }

  public static void writeFile(InputStream is, String filename, String path) {
    try (OutputStream os = Files
        .newOutputStream(Path.of(path, filename), CREATE, WRITE, TRUNCATE_EXISTING)) {
      is.transferTo(os);
    } catch (Exception ex) {
      logger.error("Can't write to file {}", filename, ex);
      throw new IllegalStateException(ex);
    }
  }

  public static InputStream getInputStream(MultipartFile inputImage) {
    InputStream is = null;
    try {
      is = inputImage.getInputStream();
    } catch (IOException e) {
      logger.info("Read file error", e);
      throw new InternalServerException("Internal Server Error");
    }
    return is;
  }

  public static String getContentType(MultipartFile inputImage) {
    String contentType = inputImage.getContentType();
    return contentType;
  }

  public static void printFileProperties(MultipartFile inputImage) {
    logger.info("File name {}, file content type {}, file size {}",
        inputImage.getOriginalFilename(),
        inputImage.getContentType(), inputImage.getSize());
  }
}
