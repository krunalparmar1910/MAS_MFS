package com.pf.mas.service.report;

import com.pf.mas.exception.MasReportSheetReaderException;
import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.ClientOrderReportDetails;
import com.pf.mas.model.entity.SheetTypeName;
import com.pf.mas.repository.ClientOrderReportDetailsRepository;
import com.pf.mas.service.report.sheet.SheetReaderUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@Service
public class ExcelReportDetailsReadingService implements ReportDetailsReadingService {
    private final ClientOrderReportDetailsRepository clientOrderReportDetailsRepository;

    public ExcelReportDetailsReadingService(ClientOrderReportDetailsRepository clientOrderReportDetailsRepository) {
        this.clientOrderReportDetailsRepository = clientOrderReportDetailsRepository;
    }

    @Override
    public void readAndStoreReportDetails(String reportFileName, Map<SheetTypeName, Sheet> sheetMap, ClientOrder clientOrder) throws MasReportSheetReaderException {
        log.info("Reading report header and storing data for client order {}", clientOrder);

        Sheet summaryAnalysisSheet = sheetMap.get(SheetTypeName.SUMMARY_ANALYSIS);
        if (summaryAnalysisSheet != null) {
            ClientOrderReportDetails clientOrderReportDetails = new ClientOrderReportDetails();
            DataFormatter dataFormatter = new DataFormatter();
            Iterator<Row> rowIterator = summaryAnalysisSheet.rowIterator();

            if (rowIterator.hasNext()) {
                Row firstRow = rowIterator.next();
                Cell firstCell = firstRow.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                String firstCellValue = SheetReaderUtils.sanitizeStringValue(dataFormatter.formatCellValue(firstCell));
                clientOrderReportDetails.setReportCompanyName(firstCellValue);
                clientOrderReportDetails.setReportFileName(reportFileName);
                log.trace("Company name found: {}", firstCellValue);

                if (rowIterator.hasNext()) {
                    Row secondRow = rowIterator.next();
                    Cell detailsCell = secondRow.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK);
                    String detailsCellValue = SheetReaderUtils.sanitizeStringValue(dataFormatter.formatCellValue(detailsCell));

                    clientOrderReportDetails.setReportDetailsString(detailsCellValue);

                    Pair<LocalDate, LocalDate> fromToDatePair = SheetReaderUtils.getFromDateToDatePair(detailsCellValue);
                    clientOrderReportDetails.setPeriodCoveredFrom(fromToDatePair.getLeft());
                    clientOrderReportDetails.setPeriodCoveredTo(fromToDatePair.getRight());

                    clientOrderReportDetails.setReportPan(SheetReaderUtils.getPANValueFromReportHeader(detailsCellValue));
                    clientOrderReportDetails.setReportGstn(SheetReaderUtils.getGSTNValueFromReportHeader(detailsCellValue));
                } else {
                    log.warn("Did not find second row to read report header details");
                }
            } else {
                log.warn("Did not find row to read report header details");
            }

            clientOrderReportDetails.setClientOrder(clientOrder);
            clientOrderReportDetailsRepository.save(clientOrderReportDetails);
            log.info("Completed report header and storing data for client order {}", clientOrder);
        } else {
            log.warn("Summary analysis sheet was not found in sheetMap, skipping saving of client order report details");
        }
    }
}
