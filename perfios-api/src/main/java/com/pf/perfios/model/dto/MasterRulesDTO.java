package com.pf.perfios.model.dto;

import com.pf.perfios.model.entity.DebitCredit;
import com.pf.perfios.model.entity.MasterRule;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class MasterRulesDTO {

    private Long id;

    private List<MasterIdentifiersDTO> transactionTypeList;


    private List<MasterIdentifiersDTO> categoryList;


    private List<MasterIdentifiersDTO> partiesOrMerchantList;

    private String identificationValue;

    private DebitCredit debitOrCredit;

    private String transactionFlag;

    private String ruleQuery;

    private boolean deletable;
    private boolean completed;

    public static MasterRulesDTO from(MasterRule masterRule) {
        return MasterRulesDTO.builder()
                .id(masterRule.getId())
                .transactionTypeList(
                        masterRule.getTransactionTypeList().stream().map(MasterIdentifiersDTO::nameOnlyDTOFrom).toList()
                )
                .categoryList(
                        masterRule.getCategoryList().stream().map(MasterIdentifiersDTO::nameOnlyDTOFrom).toList()
                )
                .partiesOrMerchantList(
                        masterRule.getPartiesMerchantList().stream().map(MasterIdentifiersDTO::nameOnlyDTOFrom).toList()
                )
                .identificationValue(masterRule.getIdentificationValue())
                .debitOrCredit(masterRule.getDebitOrCredit())
                .transactionFlag(masterRule.getTransactionFlag())
                .ruleQuery(masterRule.getRuleQuery())
                .deletable(masterRule.isDeletable())
                .completed(masterRule.isCompleted())
                .build();
    }
}
