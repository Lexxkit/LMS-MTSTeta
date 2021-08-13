package com.alekseenko.lms.service;

import java.io.InputStream;
import java.util.Optional;

public interface AvatarImageService {

    String getContentTypeByUser(String username);

    Optional<byte[]> getAvatarImageByUser(String username);

    void saveAvatarImage(String username, String contentType, InputStream is);
}
