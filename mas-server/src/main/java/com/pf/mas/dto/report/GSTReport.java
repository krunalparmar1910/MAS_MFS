package com.pf.mas.dto.report;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.Map;

@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode
@Jacksonized
public class GSTReport {
    private final List<GSTReportProfileDetail> gstReportProfileDetails;

    private final Map<String, GST3BSalesReport> gst3BSalesReports;

    private final GST3BSalesReport gst3BConsolidatedSalesReport;
}
