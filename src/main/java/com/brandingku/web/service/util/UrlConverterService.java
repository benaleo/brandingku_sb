package com.brandingku.web.service.util;

import com.brandingku.web.util.FileUploadHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UrlConverterService {

    @Value("${app.base.url}")
    private String baseUrl;

    @Value("${app.upload.dir}")
    private String UPLOAD_DIR;

    public String saveUrlImageProduct(MultipartFile file) throws IOException {
        return FileUploadHelper.urlSaveFile(file, UPLOAD_DIR + "/images/product", baseUrl);
    }

    public String saveUrlImageProductCategory(MultipartFile file) throws IOException {
        return FileUploadHelper.urlSaveFile(file, UPLOAD_DIR + "/images/product-category", baseUrl);
    }

}
