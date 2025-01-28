package com.kopibery.pos.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

public class UserModel {

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static class IndexResponse extends AdminModelBaseDTOResponse {

        private String name;
        private String email;
        private String avatar;
        private String roleName;
        private String companyName;

    }

    @Data
    @AllArgsConstructor
    public static class DetailResponse {

        private String name;
        private String email;
        private String avatar;
        private String avatarName;
        private String roleId;
        private String roleName;
        private String companyId;
        private String companyName;

    }

    @Data
    public static class CreateRequest {

        private String name;
        private String email;
        private String password;

        private String roleId;

    }

    @Data
    public static class UpdateRequest {

        private String name;
        private String password;

        private String roleId;

    }
}
