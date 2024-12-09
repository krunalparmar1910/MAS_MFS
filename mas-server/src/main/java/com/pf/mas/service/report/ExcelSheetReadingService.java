package com.pf.mas.service.report;

import com.pf.mas.exception.MasReportSheetReaderException;
import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.SheetType;
import com.pf.mas.model.entity.SheetTypeName;
import com.pf.mas.service.report.sheet.BifurcationStateAmountSheetReader;
import com.pf.mas.service.report.sheet.CustomerSupplierAnalysisSheetReader;
import com.pf.mas.service.report.sheet.CustomerSupplierWiseSheetReader;
import com.pf.mas.service.report.sheet.FilingDetailsSheetReader;
import com.pf.mas.service.report.sheet.IntraGroupSheetReader;
import com.pf.mas.service.report.sheet.SeasonalityAnalysisSheetReader;
import com.pf.mas.service.report.sheet.SheetReader;
import com.pf.mas.service.report.sheet.SummaryGSTYearlyTaxSheetReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class ExcelSheetReadingService implements SheetReadingService {
    private static final Set<SheetTypeName> SUMMARY_GST_TAX_YEARLY_TAX_SHEETS = Set.of(
            SheetTypeName.SUMMARY_ANALYSIS,
            SheetTypeName.SUMMARY,
            SheetTypeName.GSTR_3B,
            SheetTypeName.TAX,
            SheetTypeName.YEARLY_TAX
    );
    private static final Set<SheetTypeName> CUSTOMER_SUPPLIER_ANALYSIS_SHEETS = Set.of(
            SheetTypeName.CUSTOMERS_ANALYSIS,
            SheetTypeName.SUPPLIERS_ANALYSIS,
            SheetTypeName.HSN_CHAPTER_ANALYSIS,
            SheetTypeName.DETAILS_OF_CUSTOMERS_AND_SUPP
    );
    private static final Set<SheetTypeName> CUSTOMER_SUPPLIER_PRODUCT_WISE_SHEETS = Set.of(
            SheetTypeName.CIRCULAR_TRANSACTIONS,
            SheetTypeName.CUSTOMER_WISE,
            SheetTypeName.SUPPLIER_WISE,
            SheetTypeName.PRODUCT_WISE
    );
    private static final Set<SheetTypeName> BIFURCATION_STATE_AMOUNT_RETURN_SHEETS = Set.of(
            SheetTypeName.BIFURCATION,
            SheetTypeName.ADJUSTED_AMOUNTS,
            SheetTypeName.STATE_WISE,
            SheetTypeName.YEARLY_RETURN_SUMMARY
    );
    private static final Set<SheetTypeName> SKIP_SHEETS = Set.of(SheetTypeName.INDEX, SheetTypeName.DISCLAIMER, SheetTypeName.TREND_CHARTS);
    private final AsyncTaskExecutor taskExecutor;
    private final SummaryGSTYearlyTaxSheetReader summaryGSTYearlyTaxSheetReader;
    private final CustomerSupplierAnalysisSheetReader customerSupplierAnalysisSheetReader;
    private final CustomerSupplierWiseSheetReader customerSupplierWiseSheetReader;
    private final BifurcationStateAmountSheetReader bifurcationStateAmountSheetReader;
    private final SeasonalityAnalysisSheetReader seasonalityAnalysisSheetReader;
    private final IntraGroupSheetReader intraGroupSheetReader;
    private final FilingDetailsSheetReader filingDetailsSheetReader;

    @SuppressWarnings("java:S107")
    public ExcelSheetReadingService(
            AsyncTaskExecutor taskExecutor,
            SummaryGSTYearlyTaxSheetReader summaryGSTYearlyTaxSheetReader,
            CustomerSupplierAnalysisSheetReader customerSupplierAnalysisSheetReader,
            CustomerSupplierWiseSheetReader customerSupplierWiseSheetReader,
            BifurcationStateAmountSheetReader bifurcationStateAmountSheetReader,
            SeasonalityAnalysisSheetReader seasonalityAnalysisSheetReader,
            IntraGroupSheetReader intraGroupSheetReader,
            FilingDetailsSheetReader filingDetailsSheetReader) {
        this.taskExecutor = taskExecutor;
        this.summaryGSTYearlyTaxSheetReader = summaryGSTYearlyTaxSheetReader;
        this.customerSupplierAnalysisSheetReader = customerSupplierAnalysisSheetReader;
        this.customerSupplierWiseSheetReader = customerSupplierWiseSheetReader;
        this.bifurcationStateAmountSheetReader = bifurcationStateAmountSheetReader;
        this.seasonalityAnalysisSheetReader = seasonalityAnalysisSheetReader;
        this.intraGroupSheetReader = intraGroupSheetReader;
        this.filingDetailsSheetReader = filingDetailsSheetReader;
    }

    @Override
    public void readAllSheetsAndStoreData(
            Map<SheetTypeName, Sheet> sheetMap,
            Map<SheetTypeName, SheetType> sheetTypeMap,
            ClientOrder clientOrder) throws MasReportSheetReaderException {
        log.info("Reading all sheets and storing data for client order {}", clientOrder);
        List<Future<Void>> futureList = new ArrayList<>();
        // counters to log progress of number of sheets read
        AtomicInteger sheetCounter = new AtomicInteger(0);
        int totalSheets = sheetMap.size();

        try {
            for (Map.Entry<SheetTypeName, Sheet> entry : sheetMap.entrySet()) {
                if (SKIP_SHEETS.contains(entry.getKey())) {
                    log.debug("Skipping reading of sheet {}, sheets read {} / {}", entry.getKey().getName(), sheetCounter.incrementAndGet(), totalSheets);
                } else if (SUMMARY_GST_TAX_YEARLY_TAX_SHEETS.contains(entry.getKey())) {
                    futureList.add(executeTask(summaryGSTYearlyTaxSheetReader,
                            entry.getKey(), entry.getValue(), sheetTypeMap.get(entry.getKey()), clientOrder, sheetCounter, totalSheets));
                } else if (CUSTOMER_SUPPLIER_ANALYSIS_SHEETS.contains(entry.getKey())) {
                    futureList.add(executeTask(customerSupplierAnalysisSheetReader,
                            entry.getKey(), entry.getValue(), sheetTypeMap.get(entry.getKey()), clientOrder, sheetCounter, totalSheets));
                } else if (CUSTOMER_SUPPLIER_PRODUCT_WISE_SHEETS.contains(entry.getKey())) {
                    futureList.add(executeTask(customerSupplierWiseSheetReader,
                            entry.getKey(), entry.getValue(), sheetTypeMap.get(entry.getKey()), clientOrder, sheetCounter, totalSheets));
                } else if (BIFURCATION_STATE_AMOUNT_RETURN_SHEETS.contains(entry.getKey())) {
                    futureList.add(executeTask(bifurcationStateAmountSheetReader,
                            entry.getKey(), entry.getValue(), sheetTypeMap.get(entry.getKey()), clientOrder, sheetCounter, totalSheets));
                } else if (SheetTypeName.SEASONALITY_ANALYSIS == entry.getKey()) {
                    futureList.add(executeTask(seasonalityAnalysisSheetReader,
                            entry.getKey(), entry.getValue(), sheetTypeMap.get(entry.getKey()), clientOrder, sheetCounter, totalSheets));
                } else if (SheetTypeName.INTRA_GROUP == entry.getKey()) {
                    futureList.add(executeTask(intraGroupSheetReader,
                            entry.getKey(), entry.getValue(), sheetTypeMap.get(entry.getKey()), clientOrder, sheetCounter, totalSheets));
                } else if (SheetTypeName.PROFILE_FILING_TABLE == entry.getKey()) {
                    futureList.add(executeTask(filingDetailsSheetReader,
                            entry.getKey(), entry.getValue(), sheetTypeMap.get(entry.getKey()), clientOrder, sheetCounter, totalSheets));
                } else {
                    sheetCounter.incrementAndGet();
                    log.error("Unexpected sheet type encountered {}, skipping reading", entry.getKey());
                }
            }

            for (Future<Void> voidFuture : futureList) {
                voidFuture.get();
            }
            log.info("Completed reading all sheets and storing data for client order {}", clientOrder);
        } catch (Exception e) {
            try {
                Thread.currentThread().interrupt();
                for (Future<Void> voidFuture : futureList) {
                    voidFuture.cancel(true);
                }
            } catch (Exception e1) {
                log.error("Unexpected error while cancelling futures", e1);
                throw new MasReportSheetReaderException(e1);
            }
            throw e instanceof MasReportSheetReaderException ex ? ex : new MasReportSheetReaderException(e);
        }
    }

    private Future<Void> executeTask(
            SheetReader sheetReader,
            SheetTypeName sheetTypeName,
            Sheet sheet,
            SheetType sheetType,
            ClientOrder clientOrder,
            AtomicInteger sheetCounter,
            int totalSheets) {
        return taskExecutor.submit(() -> {
            sheetReader.readSheetAndStoreData(sheetTypeName, sheet, sheetType, clientOrder);
            log.debug("Sheets read {} / {}", sheetCounter.incrementAndGet(), totalSheets);
            return null;
        });
    }
}
