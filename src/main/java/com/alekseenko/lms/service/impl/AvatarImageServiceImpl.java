package com.alekseenko.lms.service.impl;

import static com.alekseenko.lms.util.DataUtil.getContentType;
import static com.alekseenko.lms.util.DataUtil.getInputStream;
import static com.alekseenko.lms.util.DataUtil.printFileProperties;
import static com.alekseenko.lms.util.DataUtil.writeFile;

import com.alekseenko.lms.dao.AvatarImageRepository;
import com.alekseenko.lms.dao.UserRepository;
import com.alekseenko.lms.domain.AvatarImage;
import com.alekseenko.lms.domain.User;
import com.alekseenko.lms.exception.NotFoundException;
import com.alekseenko.lms.service.AvatarImageService;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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
import org.springframework.web.multipart.MultipartFile;

@Service
public class AvatarImageServiceImpl implements AvatarImageService {

  private static final Logger logger = LoggerFactory.getLogger(AvatarImageServiceImpl.class);
  private final AvatarImageRepository avatarImageRepository;
  private final UserRepository userRepository;

  @Value("${file.storage.avatar.path}")
  private String path;

  @Autowired
  public AvatarImageServiceImpl(AvatarImageRepository avatarImageRepository,
      UserRepository userRepository) {
    this.avatarImageRepository = avatarImageRepository;
    this.userRepository = userRepository;
  }

  @Override
  public String getContentTypeByUser(String username) {
    return avatarImageRepository.findByUsername(username)
        .map(AvatarImage::getContentType)
        .orElseThrow(
            () -> new NotFoundException(String.format("Avatar for user %s not found", username)));
  }

  @Override
  public Optional<byte[]> getAvatarImageByUser(String username) {
    return avatarImageRepository.findByUsername(username)
        .map(AvatarImage::getFilename)
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
  public void saveAvatarImage(String username, MultipartFile inputImage) {
    if (!inputImage.isEmpty()) {
      printFileProperties(inputImage);
      String contentType = getContentType(inputImage);
      InputStream is = getInputStream(inputImage);
      Optional<AvatarImage> opt = avatarImageRepository.findByUsername(username);
      AvatarImage avatarImage;
      String filename;

      if (Files.notExists(Path.of(path))) {
        new File(path).mkdir();
      }

      if (opt.isEmpty()) {
        filename = UUID.randomUUID().toString();
        User user = userRepository.findUserByUsername(username)
            .orElseThrow(IllegalArgumentException::new);
        avatarImage = new AvatarImage(null, contentType, filename, user);
      } else {
        avatarImage = opt.get();
        filename = avatarImage.getFilename();
        avatarImage.setContentType(contentType);
      }
      avatarImageRepository.save(avatarImage);
      writeFile(is, filename, path);
    }
  }
}
