package com.pf.karza.model.entity.advanced.itrdata.taxdetails.schcfl;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.pf.karza.constant.ModelConstants;
import com.pf.karza.model.entity.advanced.itrdata.taxdetails.ItrDataTaxDetailsSetOffLossSchCfl;
import com.pf.karza.model.entity.base.BaseId;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EqualsAndHashCode(callSuper = true)
public abstract class ItrDataTaxDetailsSchCflLossRecord extends BaseId {
    @OneToOne
    @JoinColumn(nullable = false, name = "itr_data_tax_details_set_off_loss_sch_cfl_id")
    @JsonBackReference
    private ItrDataTaxDetailsSetOffLossSchCfl itrDataTaxDetailsSetOffLossSchCfl;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = ModelConstants.DD_MM_YYYY_FORMAT_SLASH)
    private LocalDate dtOfFiling;

    private BigDecimal hpLoss;

    private BigDecimal pti;

    private BigDecimal ttl;

    private BigDecimal brtFwdLoss;

    private BigDecimal amtAdjstd;

    private BigDecimal brtFwdLossAvlblFrSetOff;

    private BigDecimal spcltvBus;

    private BigDecimal spcfdBus;

    private BigDecimal insBus;

    private BigDecimal stclNormal;

    private BigDecimal stclPti;

    private BigDecimal stclTtl;

    private BigDecimal ltclNormal;

    private BigDecimal ltclPti;

    private BigDecimal ltclTtl;

    private BigDecimal lossFrmMntngRcHrses;

    @JsonProperty("hp")
    private void setHp(Map<String, Object> hpMap) {
        if (hpMap != null) {
            hpLoss = getBigDecimal(hpMap.get("hpLoss"));
            pti = getBigDecimal(hpMap.get("pti"));
            ttl = getBigDecimal(hpMap.get("ttl"));
        }
    }

    @JsonProperty("othThnSpcltvSpcfdInsBus")
    private void setOthThnSpcltvSpcfdInsBus(Map<String, Object> hpMap) {
        if (hpMap != null) {
            brtFwdLoss = getBigDecimal(hpMap.get("brtFwdLoss"));
            amtAdjstd = getBigDecimal(hpMap.get("amtAdjstd"));
            brtFwdLossAvlblFrSetOff = getBigDecimal(hpMap.get("brtFwdLossAvlblFrSetOff"));
        }
    }

    @JsonProperty("stcl")
    private void setStcl(Map<String, Object> hpMap) {
        if (hpMap != null) {
            stclNormal = getBigDecimal(hpMap.get("normal"));
            stclPti = getBigDecimal(hpMap.get("pti"));
            stclTtl = getBigDecimal(hpMap.get("ttl"));
        }
    }

    @JsonProperty("ltcl")
    private void setLtcl(Map<String, Object> hpMap) {
        if (hpMap != null) {
            ltclNormal = getBigDecimal(hpMap.get("normal"));
            ltclPti = getBigDecimal(hpMap.get("pti"));
            ltclTtl = getBigDecimal(hpMap.get("ttl"));
        }
    }

    private BigDecimal getBigDecimal(Object value) {
        if (value == null) {
            return null;
        } else if (value instanceof String valueStr) {
            return StringUtils.isNotBlank(valueStr) ? new BigDecimal(valueStr) : null;
        } else if (value instanceof Double) {
            return BigDecimal.valueOf((double) value);
        } else {
            return null;
        }
    }
}
