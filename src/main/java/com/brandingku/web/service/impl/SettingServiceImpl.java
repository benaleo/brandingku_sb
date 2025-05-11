package com.brandingku.web.service.impl;

import com.brandingku.web.entity.Setting;
import com.brandingku.web.model.CompilerPagination;
import com.brandingku.web.model.SettingModel;
import com.brandingku.web.model.search.ListOfFilterPagination;
import com.brandingku.web.model.search.SavedKeywordAndPageable;
import com.brandingku.web.repository.SettingRepository;
import com.brandingku.web.repository.UserRepository;
import com.brandingku.web.response.PageCreateReturn;
import com.brandingku.web.response.ResultPageResponseDTO;
import com.brandingku.web.service.SettingService;
import com.brandingku.web.util.GlobalConverter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SettingServiceImpl implements SettingService {

    private final SettingRepository settingRepository;
    private final UserRepository userRepository;

    @Override
    public ResultPageResponseDTO<SettingModel.ListSettingResponse> getAllSetting(CompilerPagination f) {
        ListOfFilterPagination filter = new ListOfFilterPagination(f.keyword());
        SavedKeywordAndPageable set = GlobalConverter.appsCreatePageable(f.pages(), f.limit(), f.sortBy(), f.direction(), f.keyword(), filter);

        // First page result (get total count)
        Page<Setting> firstResult = settingRepository.findDataByKeyword(set.keyword(), set.pageable());

        // Use a correct Pageable for fetching the next page
        Pageable pageable = GlobalConverter.oldSetPageable(f.pages(), f.limit(), f.sortBy(), f.direction(), firstResult, null);
        Page<Setting> pageResult = settingRepository.findDataByKeyword(set.keyword(), pageable);

        // Map the data to the DTOs
        List<SettingModel.ListSettingResponse> dtos = pageResult.stream().map(
                (c) -> {
                    SettingModel.ListSettingResponse dto = new SettingModel.ListSettingResponse();
                    dto.setName(c.getName());
                    dto.setIdentity(c.getIdentity());
                    dto.setType(c.getType());
                    dto.setValue(c.getValue());

                    GlobalConverter.CmsIDTimeStampResponseAndId(dto, c, userRepository);
                    return dto;
                }
        ).collect(Collectors.toList());

        return PageCreateReturn.create(
                pageResult,
                dtos
        );
    }

    @Override
    public SettingModel.DetailSettingResponse getDetailSetting(String identity) {
        Setting setting = settingRepository.findByIdentity(identity).orElse(null);
        return new SettingModel.DetailSettingResponse(
                setting != null ? setting.getName() : null,
                setting != null ? setting.getIdentity() : null,
                setting != null ? setting.getType() : null,
                setting != null ? setting.getValue() : null,
                setting != null ? setting.getIsActive() : false
        );
    }

    @Override
    public void updateSetting(String id, SettingModel.@Valid UpdateSettingRequest req) {
        Setting setting = settingRepository.findByIdentity(id).orElse(null);
        if (setting != null) {
            setting.setValue(req.value());
            setting.setIsActive(req.isActive());
            settingRepository.save(setting);
        } else {
            throw new IllegalArgumentException("Setting not found");
        }
    }


}
