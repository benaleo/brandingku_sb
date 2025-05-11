package com.brandingku.web.service;

import com.brandingku.web.model.CompilerPagination;
import com.brandingku.web.model.SettingModel;
import com.brandingku.web.response.ResultPageResponseDTO;
import jakarta.validation.Valid;

public interface SettingService {
    ResultPageResponseDTO<SettingModel.ListSettingResponse> getAllSetting(CompilerPagination f);

    SettingModel.DetailSettingResponse getDetailSetting(String identity);

    void updateSetting(String id, SettingModel.@Valid UpdateSettingRequest req);
}
