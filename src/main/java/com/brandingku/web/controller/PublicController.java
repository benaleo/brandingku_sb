package com.brandingku.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(PublicController.urlRoute)
@Tag(name = "landing page API")
@Slf4j
public class PublicController {

    static final String urlRoute = "/api/v1";


}
