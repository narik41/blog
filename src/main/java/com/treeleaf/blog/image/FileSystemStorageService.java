package com.treeleaf.blog.image;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileSystemStorageService {

    void init();

    String saveFile(MultipartFile file);

    Resource loadFile(String fileName);

}
