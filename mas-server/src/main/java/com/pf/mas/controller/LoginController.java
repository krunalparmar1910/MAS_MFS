package com.pf.mas.controller;

import com.pf.mas.dto.user.GetGST3BReportRequestHistoryResponseDTO;
import com.pf.mas.exception.MasJSONException;
import com.pf.mas.service.user.UserInfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final UserInfoService userInfoService;
    @Value("${default-username}")
    private String defaultUsername;

    public LoginController(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @GetMapping("/login-info")
    public ResponseEntity<Object> loginInfo() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get-gst-3b-report-request-history")
    public ResponseEntity<GetGST3BReportRequestHistoryResponseDTO> getGST3BReportRequestHistory() throws MasJSONException {
        return ResponseEntity.ok().body(userInfoService.getGST3BReportRequestHistory(defaultUsername));
    }
}
