package com.brandingku.web.service;

import com.brandingku.web.model.UserModel;
import com.brandingku.web.response.ResultPageResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UserService {
    ResultPageResponseDTO<UserModel.IndexResponse> findDataIndex(Integer pages, Integer limit, String sortBy, String direction, String keyword);

    UserModel.DetailResponse findDataById(String id);

    void saveData(UserModel.CreateRequest item);

    void updateData(String id, UserModel.UpdateRequest item);

    void updateAvatar(String id, MultipartFile avatar) throws IOException;

    void deleteData(String id);

}
