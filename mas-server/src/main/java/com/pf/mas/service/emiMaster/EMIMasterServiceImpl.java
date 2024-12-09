package com.pf.mas.service.emiMaster;

import com.pf.mas.mapper.CibilConsumerMapper;
import com.pf.mas.model.dto.cibil.EMIMasterDto;
import com.pf.mas.model.entity.cibil.EMIMaster;
import com.pf.mas.repository.emiMaster.EMIMasterRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EMIMasterServiceImpl implements EMIMasterService{

    @Autowired
    private EMIMasterRepository emiMasterRepository;
    @Autowired
    private CibilConsumerMapper cibilConsumerMapper;
    @Override
    public EMIMasterDto addLoans(EMIMasterDto emiMasterDto) {

        EMIMaster emiMaster = cibilConsumerMapper.toEmiMaster(emiMasterDto);
        return cibilConsumerMapper.toEmiMasterDto(emiMasterRepository.save(emiMaster));
    }

    @Override
    public EMIMasterDto getLoans(Long id) {
        return cibilConsumerMapper.toEmiMasterDto(emiMasterRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Loan with ID " + id + " not found.")));
    }

    @Override
    public List<EMIMasterDto> getAllLoans() {
        List<EMIMaster> all = emiMasterRepository.findAll();
        return all.stream().map(a -> cibilConsumerMapper.toEmiMasterDto(a)).toList();
    }

    @Override
    public void deleteLoan(Long id) {
        emiMasterRepository.deleteById(id);
    }
}
