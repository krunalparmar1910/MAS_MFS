package com.pf.mas.service.gst;

import com.pf.mas.dto.report.GST3BCustomers;
import com.pf.mas.dto.report.GST3BCustomersDetails;
import com.pf.mas.dto.report.GST3BSales;
import com.pf.mas.dto.report.GST3BSalesDetail;
import com.pf.mas.dto.report.GST3BSalesReport;
import com.pf.mas.dto.report.GST3BSuppliers;
import com.pf.mas.dto.report.GST3BSuppliersDetails;
import com.pf.mas.dto.report.GSTReport;
import com.pf.mas.dto.report.GSTReportProfileDetail;
import com.pf.mas.dto.report.UpdateGST3BReportManualEntryRequest;
import com.pf.mas.exception.MasGSTNoEntityFoundException;
import com.pf.mas.exception.MasGetGST3BReportException;
import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.GSTReportManualBankingEntry;
import com.pf.mas.model.entity.GSTReportManualEntry;
import com.pf.mas.model.entity.sheet.CustomerSupplierAnalysisTotal;
import com.pf.mas.model.entity.sheet.CustomersAnalysis;
import com.pf.mas.model.entity.sheet.FilingDetail;
import com.pf.mas.model.entity.sheet.GSTR3B;
import com.pf.mas.model.entity.sheet.GSTR3BValue;
import com.pf.mas.model.entity.sheet.IntraGroupPurchasesAndExpensesValue;
import com.pf.mas.model.entity.sheet.IntraGroupRevenueValue;
import com.pf.mas.model.entity.sheet.ProfileDetail;
import com.pf.mas.model.entity.sheet.Summary;
import com.pf.mas.model.entity.sheet.SummaryValue;
import com.pf.mas.model.entity.sheet.SuppliersAnalysis;
import com.pf.mas.repository.ClientOrderRepository;
import com.pf.mas.repository.GSTReportManualBankingEntryRepository;
import com.pf.mas.repository.GSTReportManualEntryRepository;
import com.pf.mas.repository.sheet.CustomerSupplierAnalysisTotalRepository;
import com.pf.mas.repository.sheet.CustomersAnalysisRepository;
import com.pf.mas.repository.sheet.FilingDetailRepository;
import com.pf.mas.repository.sheet.GSTR3BRepository;
import com.pf.mas.repository.sheet.IntraGroupPurchasesAndExpensesValueRepository;
import com.pf.mas.repository.sheet.IntraGroupRevenueValueRepository;
import com.pf.mas.repository.sheet.ProfileDetailRepository;
import com.pf.mas.repository.sheet.SummaryRepository;
import com.pf.mas.repository.sheet.SuppliersAnalysisRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DefaultGSTReportService implements GSTReportService {
    private static final String GSTR_3B_REVENUE_GROUP = "Revenue as per GSTR 3B";
    private static final String ADJUSTED_PURCHASES_EXPENSES = "Adjusted Purchase and Expenses (Total)";
    private static final String MONTHLY_SUMMARY = "Monthly Summary";
    private static final String GSTR3B = "GSTR3B";
    private static final String TOTAL = "TOTAL";
    private static final String TTM = "TTM";
    private static final String OTHERS = "Others";
    private static final String FULL_TOTAL = "(Total)";
    private static final String CUSTOMERS = "CUSTOMERS";
    private static final String SUPPLIERS = "SUPPLIERS";
    private static final String PERCENT = "%";
    private static final int MAX_CUSTOMERS_SUPPLIERS_RECORDS = 10;
    private static final int NUMBER_OF_MONTHS = 12;
    private static final BigDecimal HUNDRED = BigDecimal.valueOf(100L);
    private final ClientOrderRepository clientOrderRepository;
    private final ProfileDetailRepository profileDetailRepository;
    private final GSTR3BRepository gstr3BRepository;
    private final SummaryRepository summaryRepository;
    private final FilingDetailRepository filingDetailRepository;
    private final CustomersAnalysisRepository customersAnalysisRepository;
    private final SuppliersAnalysisRepository suppliersAnalysisRepository;
    private final CustomerSupplierAnalysisTotalRepository customerSupplierAnalysisTotalRepository;
    private final IntraGroupRevenueValueRepository intraGroupRevenueValueRepository;
    private final IntraGroupPurchasesAndExpensesValueRepository intraGroupPurchasesAndExpensesValueRepository;
    private final GSTReportManualEntryRepository gstReportManualEntryRepository;
    private final GSTReportManualBankingEntryRepository gstReportManualBankingEntryRepository;

    @SuppressWarnings("java:S107")
    public DefaultGSTReportService(
            ClientOrderRepository clientOrderRepository,
            ProfileDetailRepository profileDetailRepository,
            GSTR3BRepository gstr3BRepository,
            SummaryRepository summaryRepository,
            FilingDetailRepository filingDetailRepository,
            CustomersAnalysisRepository customersAnalysisRepository,
            SuppliersAnalysisRepository suppliersAnalysisRepository,
            CustomerSupplierAnalysisTotalRepository customerSupplierAnalysisTotalRepository,
            IntraGroupRevenueValueRepository intraGroupRevenueValueRepository,
            IntraGroupPurchasesAndExpensesValueRepository intraGroupPurchasesAndExpensesValueRepository,
            GSTReportManualEntryRepository gstReportManualEntryRepository,
            GSTReportManualBankingEntryRepository gstReportManualBankingEntryRepository) {
        this.clientOrderRepository = clientOrderRepository;
        this.profileDetailRepository = profileDetailRepository;
        this.gstr3BRepository = gstr3BRepository;
        this.summaryRepository = summaryRepository;
        this.filingDetailRepository = filingDetailRepository;
        this.customersAnalysisRepository = customersAnalysisRepository;
        this.suppliersAnalysisRepository = suppliersAnalysisRepository;
        this.customerSupplierAnalysisTotalRepository = customerSupplierAnalysisTotalRepository;
        this.intraGroupRevenueValueRepository = intraGroupRevenueValueRepository;
        this.intraGroupPurchasesAndExpensesValueRepository = intraGroupPurchasesAndExpensesValueRepository;
        this.gstReportManualEntryRepository = gstReportManualEntryRepository;
        this.gstReportManualBankingEntryRepository = gstReportManualBankingEntryRepository;
    }

    @Override
    public GSTReport getGSTReport(String entityId, List<String> clientOrderIds) throws MasGSTNoEntityFoundException, MasGetGST3BReportException {
        log.debug("Requesting GST3B Report for entity id {}, client order id {}", entityId, clientOrderIds);

        GST3BConsolidatedSalesReportRecord gst3BConsolidatedSalesReportRecord = getGST3BConsolidatedSalesReports(entityId);
        GST3BSingleSalesReportRecord gst3BSingleSalesReportRecord = getGST3BSingleSalesReports(entityId, clientOrderIds);

        verifyPANDetails(entityId, clientOrderIds, gst3BConsolidatedSalesReportRecord, gst3BSingleSalesReportRecord);

        log.debug("Returning GST3B Report for entity id {}, client order ids {}, ids from single report {}",
                entityId, clientOrderIds, gst3BSingleSalesReportRecord.gst3BSalesReports().values().stream().map(GST3BSalesReport::getClientOrderId).toList());
        return GSTReport.builder()
                .gstReportProfileDetails(StringUtils.isNotBlank(entityId)
                        ? gst3BConsolidatedSalesReportRecord.gstReportProfileDetails()
                        : gst3BSingleSalesReportRecord.gstReportProfileDetails())
                .gst3BSalesReports(gst3BSingleSalesReportRecord.gst3BSalesReports())
                .gst3BConsolidatedSalesReport(gst3BConsolidatedSalesReportRecord.gst3BConsolidatedSalesReports().orElse(null))
                .build();
    }

    @Override
    public GSTReport updateManualEntriesInGST3BReport(UpdateGST3BReportManualEntryRequest updateRequest) throws MasGSTNoEntityFoundException, MasGetGST3BReportException {
        if (StringUtils.isBlank(updateRequest.getEntityId()) && StringUtils.isBlank(updateRequest.getClientOrderId())) {
            throw new MasGSTNoEntityFoundException("Either entity id or client order id is required");
        }

        ClientOrder clientOrder = clientOrderRepository.findByEntityIdAndClientOrderId(updateRequest.getEntityId(), updateRequest.getClientOrderId());
        if (clientOrder == null) {
            throw new MasGSTNoEntityFoundException("No client order found with entity id " + updateRequest.getEntityId()
                    + " or client order id " + updateRequest.getClientOrderId());
        }

        GSTReportManualEntry existingGSTReportManualEntry = gstReportManualEntryRepository.findByClientOrderId(clientOrder.getId());
        GSTReportManualEntry gstReportManualEntry;
        if (existingGSTReportManualEntry == null) {
            gstReportManualEntry = new GSTReportManualEntry();
            gstReportManualEntry.setClientOrder(clientOrder);
        } else {
            gstReportManualEntry = existingGSTReportManualEntry;
        }

        if (StringUtils.isNotBlank(updateRequest.getEntityId())) {
            log.debug("Updating manual entries for entity id {}", updateRequest.getEntityId());
            updateGST3BManualEntries(updateRequest.getGstReport().getGst3BConsolidatedSalesReport(), gstReportManualEntry);
            return updateRequest.getGstReport();
        } else if (StringUtils.isNotBlank(updateRequest.getClientOrderId())) {
            log.debug("Updating manual entries for client order id {}", updateRequest.getClientOrderId());
            updateRequest.getGstReport().getGst3BSalesReports().values().stream()
                    .filter(gst3BSalesReport -> updateRequest.getClientOrderId().equals(gst3BSalesReport.getClientOrderId()))
                    .findFirst()
                    .ifPresent(salesReport -> updateGST3BManualEntries(salesReport, gstReportManualEntry));
            return updateRequest.getGstReport();
        }
        throw new MasGetGST3BReportException("Could not update manual entries for request " + updateRequest);
    }

    private GST3BConsolidatedSalesReportRecord getGST3BConsolidatedSalesReports(String entityId) throws MasGSTNoEntityFoundException, MasGetGST3BReportException {
        if (StringUtils.isNotBlank(entityId)) {
            ClientOrder consolidatedClientOrder = clientOrderRepository.findByEntityIdAndClientOrderIdIsNull(entityId);
            if (consolidatedClientOrder == null) {
                throw new MasGSTNoEntityFoundException("No client order found with entity id " + entityId);
            }
            if (!ClientOrder.ClientOrderReportStatus.COMPLETED.toString().equals(consolidatedClientOrder.getReportStatus())) {
                throw new MasGetGST3BReportException("Report is still being generated. Please try again in a while.");
            }

            List<ProfileDetail> profileDetailList = profileDetailRepository.findByClientOrderId(consolidatedClientOrder.getId());
            return new GST3BConsolidatedSalesReportRecord(
                    getGST3BReportProfileDetails(profileDetailList),
                    Optional.of(getGST3BSalesReports(entityId, consolidatedClientOrder, profileDetailList, ReportType.CONSOLIDATED)));
        }
        return new GST3BConsolidatedSalesReportRecord(Collections.emptyList(), Optional.empty());
    }

    private GST3BSingleSalesReportRecord getGST3BSingleSalesReports(String entityId, List<String> clientOrderIds)
            throws MasGSTNoEntityFoundException, MasGetGST3BReportException {
        Map<String, GST3BSalesReport> gst3BSalesReports = new LinkedHashMap<>();
        List<GSTReportProfileDetail> gstReportProfileDetails = new ArrayList<>();

        if (CollectionUtils.isNotEmpty(clientOrderIds)) {
            for (String clientOrderId : clientOrderIds) {
                ClientOrder clientOrder = clientOrderRepository.findByEntityIdAndClientOrderId(entityId, clientOrderId);
                if (clientOrder == null) {
                    log.debug("Could not find any client order with entity id {} and client order id {}, searching by only client order id", entityId, clientOrder);
                    clientOrder = clientOrderRepository.findByClientOrderId(clientOrderId);
                }
                if (clientOrder == null) {
                    throw new MasGSTNoEntityFoundException("No client order found with client order id " + clientOrderId);
                }

                addNewSingleGST3BSalesReport(entityId, clientOrder, gstReportProfileDetails, gst3BSalesReports);
            }
        } else if (StringUtils.isNotBlank(entityId)) {
            List<ClientOrder> clientOrders = clientOrderRepository.findByEntityIdAndClientOrderIdIsNotNull(entityId);
            if (CollectionUtils.isEmpty(clientOrders)) {
                log.error("No client order ids were specified, returning empty record for entity id {} and client order ids {}", entityId, clientOrderIds);
            }

            for (ClientOrder clientOrder : CollectionUtils.emptyIfNull(clientOrders)) {
                addNewSingleGST3BSalesReport(entityId, clientOrder, gstReportProfileDetails, gst3BSalesReports);
            }
        }

        return new GST3BSingleSalesReportRecord(gstReportProfileDetails, gst3BSalesReports);
    }

    private void addNewSingleGST3BSalesReport(
            String entityId,
            ClientOrder clientOrder,
            List<GSTReportProfileDetail> gstReportProfileDetails,
            Map<String, GST3BSalesReport> gst3BSalesReports) throws MasGetGST3BReportException {
        if (!ClientOrder.ClientOrderReportStatus.COMPLETED.toString().equals(clientOrder.getReportStatus())) {
            throw new MasGetGST3BReportException("Report is still being generated. Please try again in a while.");
        }

        List<ProfileDetail> profileDetailList = profileDetailRepository.findByClientOrderId(clientOrder.getId());
        gstReportProfileDetails.add(getGST3BReportProfileDetails(profileDetailList).get(0));

        GST3BSalesReport singleGST3BSalesReport = getGST3BSalesReports(entityId, clientOrder, profileDetailList, ReportType.SINGLE);
        gst3BSalesReports.put(profileDetailList.get(0).getGstNumber(), singleGST3BSalesReport);
    }

    private List<GSTReportProfileDetail> getGST3BReportProfileDetails(List<ProfileDetail> profileDetailList) {
        return profileDetailList.stream()
                .map(profileDetail -> GSTReportProfileDetail.builder()
                        .legalName(profileDetail.getLegalName())
                        .tradeName(profileDetail.getTradeName())
                        .panNumber(profileDetail.getPanNumber())
                        .gstNumber(profileDetail.getGstNumber())
                        .placeOfBusiness(profileDetail.getPlaceOfBusiness())
                        .state(profileDetail.getState())
                        .status(profileDetail.getStatus())
                        .build())
                .toList();
    }

    private void verifyPANDetails(
            String entityId,
            List<String> clientOrderIds,
            GST3BConsolidatedSalesReportRecord gst3BConsolidatedSalesReportRecord,
            GST3BSingleSalesReportRecord gst3BSingleSalesReportRecord) throws MasGetGST3BReportException {
        if (StringUtils.isNotBlank(entityId) && CollectionUtils.isNotEmpty(clientOrderIds)
                && CollectionUtils.isNotEmpty(gst3BConsolidatedSalesReportRecord.gstReportProfileDetails())
                && CollectionUtils.isNotEmpty(gst3BSingleSalesReportRecord.gstReportProfileDetails())) {
            String consolidatedPan = gst3BConsolidatedSalesReportRecord.gstReportProfileDetails().get(0).getPanNumber();
            for (GSTReportProfileDetail profileDetail : gst3BSingleSalesReportRecord.gstReportProfileDetails()) {
                if (!consolidatedPan.equals(profileDetail.getPanNumber())) {
                    log.error("PAN of consolidated report and single reports do not match for entity id {} and client order ids {}", entityId, clientOrderIds);
                    throw new MasGetGST3BReportException("PAN of consolidated report and single reports do not match");
                }
            }
        }
    }

    private GST3BSalesReport getGST3BSalesReports(String entityId, ClientOrder clientOrder, List<ProfileDetail> profileDetailList, ReportType reportType) {
        final List<GSTR3B> gstr3BList = gstr3BRepository.findByParticularsContainingAndClientOrderId(GSTR_3B_REVENUE_GROUP, clientOrder.getId());
        final List<Summary> summaryList = summaryRepository.findByParticularsAndClientOrderId(ADJUSTED_PURCHASES_EXPENSES, clientOrder.getId());
        final List<FilingDetail> filingDetailList;
        if (ReportType.CONSOLIDATED == reportType) {
            filingDetailList = getFilingDetailListForConsolidatedReport(clientOrder, profileDetailList);
        } else {
            filingDetailList = filingDetailRepository.findGST3RBFilingsByClientOrderId(clientOrder.getId(), GSTR3B);
        }

        List<SuppliersAnalysis> suppliersAnalysisList = suppliersAnalysisRepository.findByFieldGroupFieldGroupNameContainingAndClientOrderId(TTM, clientOrder.getId());
        List<CustomersAnalysis> customersAnalysisList = customersAnalysisRepository.findByFieldGroupFieldGroupNameContainingAndClientOrderId(TTM, clientOrder.getId());

        List<CustomerSupplierAnalysisTotal> customerSupplierAnalysisOthers = customerSupplierAnalysisTotalRepository
                .findByFieldGroupFieldGroupNameContainingAndFieldNameContainingAndClientOrderId(TTM, OTHERS, clientOrder.getId());
        CustomerSupplierAnalysisTotal suppliersOthersTotal = getGroupNameFromCustomerSupplierAnalysisTotalList(customerSupplierAnalysisOthers, SUPPLIERS);
        CustomerSupplierAnalysisTotal customersOthersTotal = getGroupNameFromCustomerSupplierAnalysisTotalList(customerSupplierAnalysisOthers, CUSTOMERS);

        List<CustomerSupplierAnalysisTotal> customerSupplierAnalysisTotals = customerSupplierAnalysisTotalRepository
                .findByFieldGroupFieldGroupNameContainingAndFieldNameContainingAndClientOrderId(TTM, FULL_TOTAL, clientOrder.getId());
        CustomerSupplierAnalysisTotal suppliersFullTotal = getGroupNameFromCustomerSupplierAnalysisTotalList(customerSupplierAnalysisTotals, SUPPLIERS);
        CustomerSupplierAnalysisTotal customersFullTotal = getGroupNameFromCustomerSupplierAnalysisTotalList(customerSupplierAnalysisTotals, CUSTOMERS);

        GSTReportManualEntry gstReportManualEntry = gstReportManualEntryRepository.findByClientOrderId(clientOrder.getId());

        return GST3BSalesReport.builder()
                .entityId(clientOrder.getEntityId())
                .clientOrderId(clientOrder.getClientOrderId())
                .tradeName(CollectionUtils.isNotEmpty(profileDetailList) ? profileDetailList.get(0).getTradeName() : null)
                .gst3BSales(getGST3BSales(gstr3BList, summaryList, filingDetailList))
                .gst3BSuppliers(getGST3BSuppliers(suppliersAnalysisList, suppliersOthersTotal, suppliersFullTotal, gstReportManualEntry))
                .gst3BCustomers(getGST3BCustomers(customersAnalysisList, customersOthersTotal, customersFullTotal, gstReportManualEntry))
                .interStateSuppliersAnalysis(calculateInterStateSuppliersAnalysis(profileDetailList, suppliersAnalysisList, clientOrder))
                .interStateCustomersAnalysis(calculateInterStateCustomersAnalysis(profileDetailList, customersAnalysisList, clientOrder))
                .grossAdjustedRevenue(getGrossAdjustedRevenue(entityId, clientOrder, profileDetailList, reportType))
                .circularOrOthersSuppliersAnalysis(gstReportManualEntry != null ? gstReportManualEntry.getCircularOrOthersSuppliersAnalysis() : null)
                .circularOrOthersCustomersAnalysis(gstReportManualEntry != null ? gstReportManualEntry.getCircularOrOthersCustomersAnalysis() : null)
                .totalNumberOfMonthsSuppliersAnalysis(gstReportManualEntry != null ? gstReportManualEntry.getTotalNumberOfMonthsSuppliersAnalysis() : null)
                .totalNumberOfMonthsCustomersAnalysis(gstReportManualEntry != null ? gstReportManualEntry.getTotalNumberOfMonthsCustomersAnalysis() : null)
                .build();
    }

    private GST3BSales getGST3BSales(List<GSTR3B> gstr3BList, List<Summary> summaryList, List<FilingDetail> filingDetailList) {
        List<GST3BSalesDetail> gst3BSalesDetails = new ArrayList<>();
        List<GSTR3BValue> latest12MonthsGSTR3BValueList = gstr3BList.isEmpty()
                ? Collections.emptyList()
                : getLast12MonthsGST3B(gstr3BList.get(0).getValues());
        List<SummaryValue> monthlySummaryValueList = summaryList.stream()
                .filter(summary -> StringUtils.containsIgnoreCase(summary.getFieldGroup().getFieldGroupName(), MONTHLY_SUMMARY))
                .flatMap(summary -> summary.getValues().stream())
                .toList();
        List<SummaryValue> latest12MonthsSummaryValueList = getLatest12MonthsRecords(monthlySummaryValueList);
        List<FilingDetailRecord> latest12MonthsFilingDetailRecordList = getSummarizedFilingDetailRecordList(filingDetailList);

        BigDecimal totalSales = BigDecimal.ZERO;
        BigDecimal totalPurchase = BigDecimal.ZERO;
        Integer totalDelayedDays = null;
        int totalFilings = 0;
        Iterator<GSTR3BValue> gstr3BValueIterator = latest12MonthsGSTR3BValueList.iterator();
        Iterator<SummaryValue> summaryValueIterator = latest12MonthsSummaryValueList.iterator();
        Iterator<FilingDetailRecord> filingDetailIterator = latest12MonthsFilingDetailRecordList.iterator();

        while (gstr3BValueIterator.hasNext() || summaryValueIterator.hasNext() || filingDetailIterator.hasNext()) {
            GST3BSalesDetail.GST3BSalesDetailBuilder builder = GST3BSalesDetail.builder();

            if (gstr3BValueIterator.hasNext()) {
                GSTR3BValue gstr3BValue = gstr3BValueIterator.next();
                builder.month(gstr3BValue.getFieldDateMonthYear().getFromDate());
                BigDecimal sales = gstr3BValue.getFieldValueNumeric() != null ? gstr3BValue.getFieldValueNumeric() : BigDecimal.ZERO;
                builder.sales(sales);
                totalSales = totalSales.add(sales);
            }

            if (summaryValueIterator.hasNext()) {
                SummaryValue summaryValue = summaryValueIterator.next();
                BigDecimal purchase = summaryValue.getFieldValueNumeric() != null ? summaryValue.getFieldValueNumeric() : BigDecimal.ZERO;
                builder.purchase(purchase);
                totalPurchase = totalPurchase.add(purchase);
            }

            if (filingDetailIterator.hasNext()) {
                FilingDetailRecord filingDetailRecord = filingDetailIterator.next();
                if (filingDetailRecord.dueDate() != null) {
                    builder.delayedDays(filingDetailRecord.delayedDays());
                    builder.delayInFiling(filingDetailRecord.delayedDays() != null && filingDetailRecord.delayedDays() > 0);
                    ++totalFilings;
                }
                if (filingDetailRecord.delayedDays() != null) {
                    totalDelayedDays = getIntegerValue(totalDelayedDays) + filingDetailRecord.delayedDays();
                }
            }

            gst3BSalesDetails.add(builder.build());
        }

        BigDecimal averageDelayInDays = totalDelayedDays != null && totalFilings > 0
                ? BigDecimal.valueOf(totalDelayedDays / (double) totalFilings).setScale(1, RoundingMode.HALF_EVEN).stripTrailingZeros()
                : null;
        return GST3BSales.builder()
                .gst3BSalesDetails(gst3BSalesDetails)
                .totalSales(totalSales)
                .totalPurchase(totalPurchase)
                .totalDelayedDays(totalDelayedDays)
                .averageDelayInDays(averageDelayInDays)
                .build();
    }

    private List<FilingDetailRecord> getSummarizedFilingDetailRecordList(List<FilingDetail> filingDetailList) {
        Map<String, List<FilingDetail>> gstFilingsMap = new LinkedHashMap<>();
        for (FilingDetail filingDetail : filingDetailList) {
            List<FilingDetail> gstSpecificList = gstFilingsMap.computeIfAbsent(filingDetail.getTaxPeriod().getFieldDateValue(), x -> new ArrayList<>());
            gstSpecificList.add(filingDetail);
        }

        List<FilingDetailRecord> filingDetailRecordList = new ArrayList<>();
        List<List<FilingDetail>> gstWiseLists = new ArrayList<>(gstFilingsMap.values());
        for (List<FilingDetail> gstWiseList : gstWiseLists) {
            Integer totalDays = null;
            for (FilingDetail filingDetail : gstWiseList) {
                if (filingDetail.getDelayedDays() != null) {
                    totalDays = getIntegerValue(totalDays) + filingDetail.getDelayedDays();
                }
            }
            // dueDate here is just being used for null check
            filingDetailRecordList.add(new FilingDetailRecord(gstWiseList.get(0).getDueDate(), gstWiseList.get(0).getTaxPeriod().getFieldDateValue(), totalDays));
        }

        return getLatest12MonthsRecords(filingDetailRecordList);
    }

    private GST3BSuppliers getGST3BSuppliers(
            List<SuppliersAnalysis> suppliersAnalysisList,
            CustomerSupplierAnalysisTotal suppliersOthersTotal,
            CustomerSupplierAnalysisTotal suppliersFullTotal,
            GSTReportManualEntry gstReportManualEntry) {
        List<GST3BSuppliersDetails> gst3BSuppliersDetails = new ArrayList<>();
        List<GSTReportManualBankingEntry> gstReportManualBankingEntries = gstReportManualEntry != null
                ? gstReportManualEntry.getGstReportManualBankingEntryList()
                : Collections.emptyList();
        BigDecimal adjustedPurchaseAndExpensesTotal = suppliersFullTotal != null ? suppliersFullTotal.getTotalValueNumeric() : BigDecimal.ZERO;
        BigDecimal othersTotal = suppliersOthersTotal != null ? suppliersOthersTotal.getTotalValueNumeric() : BigDecimal.ZERO;

        for (int index = 0; index < suppliersAnalysisList.size(); ++index) {
            SuppliersAnalysis suppliersAnalysis = suppliersAnalysisList.get(index);
            if (index < MAX_CUSTOMERS_SUPPLIERS_RECORDS) {
                GSTReportManualBankingEntry manualBankingEntry = safeIndexAt(gstReportManualBankingEntries, index);
                gst3BSuppliersDetails.add(GST3BSuppliersDetails.builder()
                        .supplierName(suppliersAnalysis.getSupplierName())
                        .adjustedPurchaseAndExpenses(suppliersAnalysis.getAdjustedPurchasesAndExpensesNumeric())
                        .adjustedPurchaseAndExpensesPercent(suppliersAnalysis.getPercentOfAdjustedPurchasesAndExpenses())
                        .numericEntryAsPerBanking(manualBankingEntry != null ? manualBankingEntry.getSuppliersAnalysisNumericEntryAsPerBanking() : null)
                        .numericEntryAddToInterfirm(manualBankingEntry != null ? manualBankingEntry.getSuppliersAnalysisNumericEntryAddToInterfirm() : null)
                        .build());
            } else {
                othersTotal = othersTotal.add(suppliersAnalysis.getAdjustedPurchasesAndExpensesNumeric());
            }
            if (suppliersFullTotal == null) {
                adjustedPurchaseAndExpensesTotal = adjustedPurchaseAndExpensesTotal.add(suppliersAnalysis.getAdjustedPurchasesAndExpensesNumeric());
            }
        }

        // add others record only if more than 10 records present
        if (suppliersAnalysisList.size() > MAX_CUSTOMERS_SUPPLIERS_RECORDS) {
            GSTReportManualBankingEntry manualBankingEntry = safeIndexAt(gstReportManualBankingEntries, MAX_CUSTOMERS_SUPPLIERS_RECORDS);
            gst3BSuppliersDetails.add(GST3BSuppliersDetails.builder()
                    .supplierName(OTHERS)
                    .adjustedPurchaseAndExpenses(othersTotal)
                    .adjustedPurchaseAndExpensesPercent(othersTotal.multiply(HUNDRED).divide(adjustedPurchaseAndExpensesTotal, RoundingMode.HALF_EVEN) + PERCENT)
                    .numericEntryAsPerBanking(manualBankingEntry != null ? manualBankingEntry.getSuppliersAnalysisNumericEntryAsPerBanking() : null)
                    .numericEntryAddToInterfirm(manualBankingEntry != null ? manualBankingEntry.getSuppliersAnalysisNumericEntryAddToInterfirm() : null)
                    .build());
        }

        return GST3BSuppliers.builder()
                .gst3BSuppliersDetails(gst3BSuppliersDetails)
                .adjustedPurchaseAndExpensesTotal(adjustedPurchaseAndExpensesTotal)
                .build();
    }

    private GST3BCustomers getGST3BCustomers(
            List<CustomersAnalysis> customersAnalysisList,
            CustomerSupplierAnalysisTotal customersOthersTotal,
            CustomerSupplierAnalysisTotal customersFullTotal,
            GSTReportManualEntry gstReportManualEntry) {
        List<GST3BCustomersDetails> gst3BCustomersDetails = new ArrayList<>();
        List<GSTReportManualBankingEntry> gstReportManualBankingEntries = gstReportManualEntry != null
                ? gstReportManualEntry.getGstReportManualBankingEntryList()
                : Collections.emptyList();
        BigDecimal adjustedRevenueTotal = customersFullTotal != null ? customersFullTotal.getTotalValueNumeric() : BigDecimal.ZERO;
        BigDecimal othersTotal = customersOthersTotal != null ? customersOthersTotal.getTotalValueNumeric() : BigDecimal.ZERO;

        for (int index = 0; index < customersAnalysisList.size(); index++) {
            CustomersAnalysis customersAnalysis = customersAnalysisList.get(index);
            if (index < MAX_CUSTOMERS_SUPPLIERS_RECORDS) {
                GSTReportManualBankingEntry manualBankingEntry = safeIndexAt(gstReportManualBankingEntries, index);
                gst3BCustomersDetails.add(GST3BCustomersDetails.builder()
                        .customerName(customersAnalysis.getCustomerName())
                        .adjustedRevenue(customersAnalysis.getAdjustedRevenueNumeric())
                        .adjustedRevenuePercent(customersAnalysis.getPercentOfAdjustedRevenue())
                        .numericEntryAsPerBanking(manualBankingEntry != null ? manualBankingEntry.getCustomersAnalysisNumericEntryAsPerBanking() : null)
                        .numericEntryAddToInterfirm(manualBankingEntry != null ? manualBankingEntry.getCustomersAnalysisNumericEntryAddToInterfirm() : null)
                        .build());
            } else {
                othersTotal = othersTotal.add(customersAnalysis.getAdjustedRevenueNumeric());
            }
            if (customersFullTotal == null) {
                adjustedRevenueTotal = adjustedRevenueTotal.add(customersAnalysis.getAdjustedRevenueNumeric());
            }
        }

        // add others record only if more than 10 records present
        if (customersAnalysisList.size() > MAX_CUSTOMERS_SUPPLIERS_RECORDS) {
            GSTReportManualBankingEntry manualBankingEntry = safeIndexAt(gstReportManualBankingEntries, MAX_CUSTOMERS_SUPPLIERS_RECORDS);
            gst3BCustomersDetails.add(GST3BCustomersDetails.builder()
                    .customerName(OTHERS)
                    .adjustedRevenue(othersTotal)
                    .adjustedRevenuePercent(othersTotal.multiply(HUNDRED).divide(adjustedRevenueTotal, RoundingMode.HALF_EVEN) + PERCENT)
                    .numericEntryAsPerBanking(manualBankingEntry != null ? manualBankingEntry.getCustomersAnalysisNumericEntryAsPerBanking() : null)
                    .numericEntryAddToInterfirm(manualBankingEntry != null ? manualBankingEntry.getCustomersAnalysisNumericEntryAddToInterfirm() : null)
                    .build());
        }

        return GST3BCustomers.builder()
                .gst3BCustomersDetails(gst3BCustomersDetails)
                .adjustedRevenueTotal(adjustedRevenueTotal)
                .build();
    }

    private BigDecimal calculateInterStateSuppliersAnalysis(
            List<ProfileDetail> profileDetailList, List<SuppliersAnalysis> suppliersAnalysisList, ClientOrder clientOrder) {
        BigDecimal interState = BigDecimal.ZERO;
        // for consolidated reports, read from intra group sheet
        // for single report, calculate manually by checking if any gst from transactions belongs to same pan
        if (profileDetailList.size() > 1) {
            List<IntraGroupPurchasesAndExpensesValue> intraGroupPurchasesAndExpensesValues = intraGroupPurchasesAndExpensesValueRepository
                    .findByIntraGroupGstinAndFieldDateMonthYearFieldDateValueAndIntraGroupClientOrderId(TOTAL, TTM, clientOrder.getId());
            if (CollectionUtils.isNotEmpty(intraGroupPurchasesAndExpensesValues)) {
                interState = intraGroupPurchasesAndExpensesValues.get(0).getIntraGroupPurchasesAndExpensesNumeric();
            }
        } else {
            for (int index = 0; index < Math.min(suppliersAnalysisList.size(), MAX_CUSTOMERS_SUPPLIERS_RECORDS); ++index) {
                SuppliersAnalysis suppliersAnalysis = suppliersAnalysisList.get(index);
                for (ProfileDetail profileDetail : profileDetailList) {
                    if (StringUtils.containsIgnoreCase(suppliersAnalysis.getSupplierGSTIN(), profileDetail.getPanNumber())) {
                        interState = interState.add(suppliersAnalysis.getAdjustedPurchasesAndExpensesNumeric());
                    }
                }
            }
        }
        return interState;
    }

    private BigDecimal calculateInterStateCustomersAnalysis(
            List<ProfileDetail> profileDetailList, List<CustomersAnalysis> customersAnalysisList, ClientOrder clientOrder) {
        BigDecimal interState = BigDecimal.ZERO;
        // for consolidated reports, read from intra group sheet
        // for single report, calculate manually by checking if any gst from transactions belongs to same pan
        if (profileDetailList.size() > 1) {
            Optional<IntraGroupRevenueValue> intraGroupRevenueValue = getIntraGroupRevenueValue(TOTAL, clientOrder);
            interState = intraGroupRevenueValue.map(IntraGroupRevenueValue::getIntraGroupRevenueNumeric).orElse(null);
        } else {
            for (int index = 0; index < Math.min(customersAnalysisList.size(), MAX_CUSTOMERS_SUPPLIERS_RECORDS); ++index) {
                CustomersAnalysis customersAnalysis = customersAnalysisList.get(index);
                for (ProfileDetail profileDetail : profileDetailList) {
                    if (StringUtils.containsIgnoreCase(customersAnalysis.getCustomerGSTIN(), profileDetail.getPanNumber())) {
                        interState = interState.add(customersAnalysis.getAdjustedRevenueNumeric());
                    }
                }
            }
        }
        return interState;
    }

    private List<FilingDetail> getFilingDetailListForConsolidatedReport(ClientOrder clientOrder, List<ProfileDetail> profileDetailList) {
        BigDecimal maximumRevenue = BigDecimal.ZERO;
        String maximumRevenueGstNumber = StringUtils.EMPTY;
        for (ProfileDetail profileDetail : profileDetailList) {
            Optional<IntraGroupRevenueValue> intraGroupRevenueValue = getIntraGroupRevenueValue(profileDetail.getGstNumber(), profileDetail.getClientOrder());
            BigDecimal intraGroupRevenue = intraGroupRevenueValue.map(IntraGroupRevenueValue::getGrossAdjustedRevenueNumeric).orElse(null);
            if (intraGroupRevenue != null && maximumRevenue.compareTo(intraGroupRevenue) < 0) {
                maximumRevenue = intraGroupRevenue;
                maximumRevenueGstNumber = profileDetail.getGstNumber();
            }
        }

        ClientOrder maximumRevenueClientOrder = clientOrder;
        if (maximumRevenue.compareTo(BigDecimal.ZERO) != 0) {
            List<ClientOrder> entityIdClientOrders = clientOrderRepository.findByEntityIdAndClientOrderIdIsNotNull(clientOrder.getEntityId());
            String finalMaximumRevenueGstNumber = maximumRevenueGstNumber;
            maximumRevenueClientOrder = entityIdClientOrders.stream()
                    .filter(order -> order.getClientOrderReportDetails() != null && finalMaximumRevenueGstNumber.equals(order.getClientOrderReportDetails().getReportGstn()))
                    .findFirst()
                    .orElse(clientOrder);
        }

        if (maximumRevenueClientOrder != clientOrder) {
            log.debug("Using client order {} for filing details of {} as it has maximum revenue {}", maximumRevenueClientOrder, clientOrder, maximumRevenue);
        } else {
            log.debug("No intra group revenue details or maximum revenue was found for {}, using filing details from all values in report", clientOrder);
        }
        return filingDetailRepository.findGST3RBFilingsByClientOrderId(maximumRevenueClientOrder.getId(), GSTR3B);
    }

    private Optional<IntraGroupRevenueValue> getIntraGroupRevenueValue(String gstNumber, ClientOrder clientOrder) {
        List<IntraGroupRevenueValue> intraGroupRevenueValues = intraGroupRevenueValueRepository
                .findByIntraGroupGstinContainingAndFieldDateMonthYearFieldDateValueAndIntraGroupClientOrderId(
                        gstNumber, TTM, clientOrder.getId());
        return CollectionUtils.isNotEmpty(intraGroupRevenueValues) ? Optional.of(intraGroupRevenueValues.get(0)) : Optional.empty();
    }

    private BigDecimal getGrossAdjustedRevenue(String entityId, ClientOrder clientOrder, List<ProfileDetail> profileDetailList, ReportType reportType) {
        BigDecimal grossAdjustedRevenue = null;
        if (StringUtils.isNotBlank(entityId)) {
            String gstNumber = profileDetailList.size() == 1 ? profileDetailList.get(0).getGstNumber() : TOTAL;
            Optional<IntraGroupRevenueValue> intraGroupRevenueValue = Optional.empty();

            // since intra-group details are available only in consolidated report
            // get these details from the sheet directly in case of consolidated report
            // else get the respective entity order based on the entityId parameter and then get
            if (ReportType.CONSOLIDATED == reportType) {
                intraGroupRevenueValue = getIntraGroupRevenueValue(gstNumber, clientOrder);
            } else {
                ClientOrder entityClientOrder = clientOrderRepository.findByEntityIdAndClientOrderIdIsNull(entityId);
                if (entityClientOrder != null) {
                    intraGroupRevenueValue = getIntraGroupRevenueValue(gstNumber, entityClientOrder);
                }
            }
            grossAdjustedRevenue = intraGroupRevenueValue.map(IntraGroupRevenueValue::getIntraGroupRevenueNumeric).orElse(null);
        }
        return grossAdjustedRevenue;
    }

    private <T> List<T> getLatest12MonthsRecords(List<T> resultList) {
        int listSize = resultList.size();
        return listSize <= NUMBER_OF_MONTHS ? resultList : resultList.subList(listSize - NUMBER_OF_MONTHS, listSize);
    }

    public List<GSTR3BValue> getLast12MonthsGST3B(List<GSTR3BValue> resultList) {
        List<GSTR3BValue> sortedList = resultList.stream()
                .sorted(Comparator.comparing(value -> value.getFieldDateMonthYear().getFromDate())) // Sorting by fromDate
                .collect(Collectors.toList());
        int listSize = sortedList.size();
        return listSize < 24 ? sortedList : sortedList.subList(listSize - 24, listSize); // Return last 24 elements
    }
    private int getIntegerValue(Integer value) {
        return value == null ? 0 : value;
    }

    private CustomerSupplierAnalysisTotal getGroupNameFromCustomerSupplierAnalysisTotalList(
            List<CustomerSupplierAnalysisTotal> customerSupplierAnalysisTotals, String groupName) {
        return CollectionUtils.emptyIfNull(customerSupplierAnalysisTotals).stream()
                .filter(customerSupplierAnalysisTotal -> StringUtils.containsIgnoreCase(customerSupplierAnalysisTotal.getFieldGroup().getFieldGroupName(), groupName))
                .findFirst()
                .orElse(null);
    }

    private void updateGST3BManualEntries(GST3BSalesReport salesReport, GSTReportManualEntry gstReportManualEntry) {
        // update sales level fields
        gstReportManualEntry.setCircularOrOthersSuppliersAnalysis(salesReport.getCircularOrOthersSuppliersAnalysis());
        gstReportManualEntry.setCircularOrOthersCustomersAnalysis(salesReport.getCircularOrOthersCustomersAnalysis());
        gstReportManualEntry.setTotalNumberOfMonthsSuppliersAnalysis(salesReport.getTotalNumberOfMonthsSuppliersAnalysis());
        gstReportManualEntry.setTotalNumberOfMonthsCustomersAnalysis(salesReport.getTotalNumberOfMonthsCustomersAnalysis());
        gstReportManualEntry = gstReportManualEntryRepository.save(gstReportManualEntry);

        // update banking level fields
        List<GSTReportManualBankingEntry> gstReportManualBankingEntries = gstReportManualEntry.getGstReportManualBankingEntryList() != null
                ? gstReportManualEntry.getGstReportManualBankingEntryList()
                : new ArrayList<>();
        // suppliers
        List<GST3BSuppliersDetails> gst3BSuppliersDetailsList = salesReport.getGst3BSuppliers().getGst3BSuppliersDetails();
        for (int index = 0; index < gst3BSuppliersDetailsList.size(); ++index) {
            GST3BSuppliersDetails gst3BSuppliersDetails = gst3BSuppliersDetailsList.get(index);
            GSTReportManualBankingEntry manualBankingEntry = safeIndexAt(gstReportManualBankingEntries, index);
            if (manualBankingEntry == null) {
                log.debug("Manual banking entry does not exist at index {}, creating", index);
                manualBankingEntry = new GSTReportManualBankingEntry();
                manualBankingEntry.setGstReportManualEntry(gstReportManualEntry);
                gstReportManualBankingEntries.add(manualBankingEntry);
            }
            manualBankingEntry.setSuppliersAnalysisNumericEntryAsPerBanking(gst3BSuppliersDetails.getNumericEntryAsPerBanking());
            manualBankingEntry.setSuppliersAnalysisNumericEntryAddToInterfirm(gst3BSuppliersDetails.getNumericEntryAddToInterfirm());
        }

        // customers
        List<GST3BCustomersDetails> gst3BCustomersDetailsList = salesReport.getGst3BCustomers().getGst3BCustomersDetails();
        for (int index = 0; index < gst3BCustomersDetailsList.size(); ++index) {
            GST3BCustomersDetails gst3BCustomersDetails = gst3BCustomersDetailsList.get(index);
            GSTReportManualBankingEntry manualBankingEntry = safeIndexAt(gstReportManualBankingEntries, index);
            if (manualBankingEntry == null) {
                log.debug("Manual banking entry does not exist at index {}, creating", index);
                manualBankingEntry = new GSTReportManualBankingEntry();
                manualBankingEntry.setGstReportManualEntry(gstReportManualEntry);
                gstReportManualBankingEntries.add(manualBankingEntry);
            }
            manualBankingEntry.setCustomersAnalysisNumericEntryAsPerBanking(gst3BCustomersDetails.getNumericEntryAsPerBanking());
            manualBankingEntry.setCustomersAnalysisNumericEntryAddToInterfirm(gst3BCustomersDetails.getNumericEntryAddToInterfirm());
        }

        // save
        gstReportManualBankingEntryRepository.saveAll(gstReportManualBankingEntries);
    }

    private <T> T safeIndexAt(List<T> list, int index) {
        return list != null && index < list.size() ? list.get(index) : null;
    }

    private enum ReportType {
        SINGLE,
        CONSOLIDATED,
    }

    private record GST3BConsolidatedSalesReportRecord(
            List<GSTReportProfileDetail> gstReportProfileDetails,
            Optional<GST3BSalesReport> gst3BConsolidatedSalesReports) {
    }

    private record GST3BSingleSalesReportRecord(
            List<GSTReportProfileDetail> gstReportProfileDetails,
            Map<String, GST3BSalesReport> gst3BSalesReports) {
    }

    private record FilingDetailRecord(LocalDate dueDate, String taxPeriod, Integer delayedDays) {
    }
}
