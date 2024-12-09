package com.pf.mas.model.entity.commercial;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.pf.mas.model.constant.Constants;
import com.pf.mas.model.entity.base.BaseID;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "application_data_commercial", schema = Constants.CIBIL_DB_SCHEMA)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class ApplicationDataCommercial extends BaseID {
    private Long amount;

    private String litigationReport;

    private String formattedReport;

    private String msmeRank;
    private String commercialCir;

    private String consumerCir;

    private String gstStateCode;

    private String executionType;

    private String inputValReasonCodes;

    private Long testCountAppList;

    private String executeParallel;

    private Long purpose;

    private String customerConsent;

    private String companyPanCheck;
    private Long noOfDirector;

    private String combStartTime;

    private String combEndTime;
}