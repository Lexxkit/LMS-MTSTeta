package com.alekseenko.lms.service;

import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

public interface AvatarImageService {

  String getContentTypeByUser(String username);

  Optional<byte[]> getAvatarImageByUser(String username);

  void saveAvatarImage(String username, MultipartFile avatar);

  boolean checkAvatarImage(Long userId);

  Optional<String> getContentTypeAvatarByUser(String username);

  Optional<byte[]> getDataAvatar(String username);
}
