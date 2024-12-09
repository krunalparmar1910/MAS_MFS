package com.pf.perfios.service;

import com.pf.perfios.model.dto.RuleQueryContext;
import org.junit.jupiter.api.Test;
import org.springframework.expression.EvaluationException;
import org.springframework.expression.ParseException;

import static org.junit.jupiter.api.Assertions.*;

class MasterRuleServiceTest {

    private static RuleQueryContext getRuleQueryContext(boolean transactionType, boolean category, boolean parties, boolean identificationValue) {
        RuleQueryContext data =
                RuleQueryContext.builder()
                        .transactionType(transactionType)
                        .category(category)
                        .parties(parties)
                        .identificationValue(identificationValue)
                        .build();
        return data;
    }


    @Test
    public void testRuleEvaluation(){

        assertFalse(
                MasterRuleService.evaluateRule("((transactionType and category) or (parties and identificationValue))",
                getRuleQueryContext(false, true, false, true))
        );

        assertTrue(
                MasterRuleService.evaluateRule("((transactionType and category) or (parties and identificationValue))",
                        getRuleQueryContext(true, true, false, true))
        );
    }

    @Test
    public void testRuleEvaluationWithDifferentAndOR(){

        assertFalse(
                MasterRuleService.evaluateRule("((transactionType AND category) Or (parties And identificationValue))",
                        getRuleQueryContext(false, true, false, true))
        );

        assertTrue(
                MasterRuleService.evaluateRule("((transactionType && category) || (parties && identificationValue))",
                        getRuleQueryContext(true, true, false, true))
        );
    }

    @Test
    public void testRuleEvaluationFailure(){

        assertThrows(EvaluationException.class, ()-> assertFalse(
                MasterRuleService.evaluateRule("((transactionType1 AND category2) Or (parties2 And identificationValue1))",
                        getRuleQueryContext(false, true, false, true))
        ));

        assertThrows(ParseException.class, ()->
                MasterRuleService.evaluateRule("((true && (false) || (true && (false))",
                        getRuleQueryContext(true, true, false, true))
        );
    }


}