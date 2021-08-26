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
public class AvatarImageServiceImpl implements AvatarImageService {

  private final AvatarImageRepository avatarImageRepository;
  private final UserRepository userRepository;

  @Value("${file.storage.avatar.path}")
  private String path;

  @Value("${file.storage.img.path.default}")
  private String defaultImgPath;

  @Autowired
  public AvatarImageServiceImpl(AvatarImageRepository avatarImageRepository,
      UserRepository userRepository) {
    this.avatarImageRepository = avatarImageRepository;
    this.userRepository = userRepository;
  }

  @Override
  public boolean checkAvatarImage(Long userId) {
    return !avatarImageRepository.existsAvatarImageByUser_Id(userId);
  }

  public byte[] getDefaultAvatar() {
    final String DEFAULT_IMAGE_FILENAME = "default-user-avatar.png";
    return DataUtil.readData(DEFAULT_IMAGE_FILENAME, defaultImgPath);
  }

  public Optional<byte[]> getDataAvatar(String username) {
    Long userId;
    Optional<byte[]> data;
    userId = getIdByUsername(username);
    if (userId == null || checkAvatarImage(userId)) {
      data = Optional.ofNullable(getDefaultAvatar());
    } else {
      data = getAvatarImageByUser(username);
    }
    return data;
  }

  private Long getIdByUsername(String username) {
    Long userId;
    try {
      userId = userRepository.findUserByUsername(username)
          .orElseThrow(() -> new NotFoundException("user_not_found")).getId();
    } catch (NotFoundException e) {
      userId = null; //если юзер не имеет id, например, взят из inMemoryAuthentication
    }
    return userId;
  }

  public Optional<String> getContentTypeAvatarByUser(String username) {
    Long userId;
    userId = getIdByUsername(username);
    Optional<String> contentType;

    if (userId == null || checkAvatarImage(userId)) {
      String DEFAULT_CONTENT_TYPE = "image/png";
      contentType = Optional.of(DEFAULT_CONTENT_TYPE);
    } else {
      contentType = Optional.ofNullable(getContentTypeByUser(username));
    }
    return contentType;
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
        .map((String filename) -> DataUtil.readData(filename, path));
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
