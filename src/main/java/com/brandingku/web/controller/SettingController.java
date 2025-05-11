package com.brandingku.web.controller;

import com.brandingku.web.model.CompilerPagination;
import com.brandingku.web.model.SettingModel;
import com.brandingku.web.response.ApiResponse;
import com.brandingku.web.response.ResultPageResponseDTO;
import com.brandingku.web.service.SettingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping(SettingController.urlRoute)
@Tag(name = "setting API")
@Slf4j
@SecurityRequirement(name = "Authorization")
public class SettingController {

    static final String urlRoute = "/cms/v1/setting";

    private final SettingService settingService;

    @Operation(description = "Get all setting")
    @GetMapping
    public ResponseEntity<?> getAllSetting(
            @RequestParam(name = "pages", required = false, defaultValue = "0") Integer pages,
            @RequestParam(name = "limit", required = false, defaultValue = "10") Integer limit,
            @RequestParam(name = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(name = "direction", required = false, defaultValue = "asc") String direction,
            @RequestParam(name = "keyword", required = false) String keyword
    ) {
        try {
            CompilerPagination f = new CompilerPagination(pages, limit, sortBy, direction, keyword);
            ResultPageResponseDTO<SettingModel.ListSettingResponse> response = settingService.getAllSetting(f);
            return ResponseEntity.ok().body(new ApiResponse(true, "Success get all setting", response));
        } catch (Exception e) {
            log.error("Error get all setting : {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @Operation(description = "Get detail setting")
    @GetMapping("{identity}")
    public ResponseEntity<?> getDetailSetting(@PathVariable("identity") String identity) {
        try {
            SettingModel.DetailSettingResponse response = settingService.getDetailSetting(identity);
            return ResponseEntity.ok().body(new ApiResponse(true, "Success get detail setting", response));
        } catch (Exception e) {
            log.error("Error get detail setting : {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }

    @Operation(description = "Update setting")
    @PutMapping("{id}")
    public ResponseEntity<ApiResponse> updateSetting(@PathVariable("id") String id, @Valid @RequestBody SettingModel.UpdateSettingRequest req) {
        try {
            settingService.updateSetting(id, req);
            return ResponseEntity.ok().body(new ApiResponse(true, "Success update setting", null));
        } catch (Exception e) {
            log.error("Error update setting : {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body(new ApiResponse(false, e.getMessage(), null));
        }
    }

}
