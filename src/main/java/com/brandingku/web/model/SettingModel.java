package com.brandingku.web.model;

import com.brandingku.web.enums.SettingType;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class SettingModel {

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class ListSettingResponse extends AdminModelBaseDTOResponse {
        private String name;
        private String identity;
        private SettingType type;
        private String value;
    }


    public record DetailSettingResponse(
            String name,
            String identity,
            SettingType type,
            String value,
            Boolean isActive
    ) {
    }

    public record UpdateSettingRequest(
            String value,
            Boolean isActive
    ) {
    }
}
