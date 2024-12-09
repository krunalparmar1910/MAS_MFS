package com.pf.mas.controller;

import com.pf.mas.model.dto.cibil.EMIMasterDto;
import com.pf.mas.service.emiMaster.EMIMasterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/emiMaster")
@Slf4j
@RequiredArgsConstructor
public class EMIMasterController {

    @Autowired
    EMIMasterService emiMasterService;

    @PostMapping(value = "/save")
    public ResponseEntity<EMIMasterDto> addLoans(@RequestBody EMIMasterDto emiMasterDto){
        return ResponseEntity.ok(emiMasterService.addLoans(emiMasterDto));
    }

    @GetMapping(value = "/get/{id}")
    public ResponseEntity<EMIMasterDto> getLoans(@PathVariable Long id){
        return ResponseEntity.ok(emiMasterService.getLoans(id));
    }

    @GetMapping(value = "/getall")
    public ResponseEntity<List<EMIMasterDto>> getAllLoans(){
        return ResponseEntity.ok(emiMasterService.getAllLoans());
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<String> deleteLoans(@PathVariable Long id){
        emiMasterService.deleteLoan(id);
        return ResponseEntity.ok("Record Deleted");
    }
}
