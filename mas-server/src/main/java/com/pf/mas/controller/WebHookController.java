package com.pf.mas.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pf.common.response.CommonResponse;
import com.pf.mas.model.entity.encryptor.HypthAdditionReqDobj;
import com.pf.mas.model.entity.encryptor.RequestDto;
import com.pf.mas.model.util.encryptor.Encryptor;
import com.pf.perfios.model.dto.TransactionCallBackDTO;
import com.pf.perfios.service.PerfiosService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
@RequiredArgsConstructor
@Slf4j
public class WebHookController {

    private final PerfiosService perfiosService;

    private final AsyncTaskExecutor taskExecutor;

    public static final String URL = "https://vahan.parivahan.gov.in/vahanHypothecationWS/v1/addition";
    public static final String userId = "masuser";
    public static final String userPwd = "dL@12345";
    public static final String key = "R+WQ8VJry/sEOJ9NukwAohCeqxakkKHNRVgEMxI56q0=";

    public static final Logger LOG = LoggerFactory.getLogger(WebHookController.class);

    @PostMapping(value = "/transaction-callback", consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CommonResponse> transactionCallback(@RequestBody TransactionCallBackDTO transactionCallBackDTO) {
        taskExecutor.execute(() -> {
            perfiosService.transactionComplete(transactionCallBackDTO);
        });
        return ResponseEntity.ok(new CommonResponse("Data received successfully", null));
    }

    //temporary API for testing mas-webhook calls
    @PostMapping(value = "/mas-webhook-temp", consumes = {MediaType.APPLICATION_JSON_VALUE,MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<CommonResponse> masWebhook(@RequestBody TransactionCallBackDTO transactionCallBackDTO) throws JsonProcessingException {
        log.info("MAS webhook call back received with data: "+new ObjectMapper().writeValueAsString(transactionCallBackDTO));
        return ResponseEntity.ok(new CommonResponse("Data received successfully", null));
    }

    @GetMapping({"/"})
    public String encryptService() {
        return "Encrypt Service 1.0 is running...";
    }

    @PostMapping({"/encrypt"})
    public String encryptRequestBody1(@RequestBody String body, @RequestHeader String key) {
        try {
            LOG.info("Encrypting request body");
            return Encryptor.encrypt(body.getBytes(), key);
        } catch (Exception var4) {
            LOG.error("ERROR", var4.fillInStackTrace());
            return "";
        }
    }

    @PostMapping({"/decrypt"})
    public String decryptRequestBody(@RequestBody String encryptedString, @RequestHeader String key) {
        try {
            LOG.info("decrypting string: {}", encryptedString);
            return Encryptor.decrypt(encryptedString, key);
        } catch (Exception var4) {
            LOG.error("ERROR", var4.fillInStackTrace());
            return "";
        }
    }

    public static String convertToJson(HypthAdditionReqDobj vaTac) {
        String jsonString = "";
        ObjectMapper mapper = new ObjectMapper();

        try {
            jsonString = mapper.writeValueAsString(vaTac);
        } catch (Exception var4) {
            LOG.error("ERROR", var4.fillInStackTrace());
        }

        return jsonString;
    }

    public static String convertToJson(RequestDto vaTac) {
        String jsonString = "";
        ObjectMapper mapper = new ObjectMapper();

        try {
            jsonString = mapper.writeValueAsString(vaTac);
        } catch (Exception var4) {
            System.out.printf(var4.getMessage());
        }

        return jsonString;
    }
}
