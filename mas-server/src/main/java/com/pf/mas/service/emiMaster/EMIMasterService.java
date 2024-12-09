package com.pf.mas.service.emiMaster;

import com.pf.mas.model.dto.cibil.EMIMasterDto;

import java.util.List;


public interface EMIMasterService {

    EMIMasterDto addLoans(EMIMasterDto emiMasterDto);

    EMIMasterDto getLoans(Long id);

    List<EMIMasterDto> getAllLoans();

    void deleteLoan(Long id);
}
