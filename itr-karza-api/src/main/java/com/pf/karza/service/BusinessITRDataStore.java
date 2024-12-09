package com.pf.karza.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pf.common.exception.MasRuntimeException;
import com.pf.karza.model.dto.response.ItrResponse;
import com.pf.karza.model.entity.Alerts;
import com.pf.karza.model.entity.AuditInfo;
import com.pf.karza.model.entity.BadDebtDetails;
import com.pf.karza.model.entity.FormDetails;
import com.pf.karza.model.entity.NatureOfCompany;
import com.pf.karza.model.entity.OutstandingDemand;
import com.pf.karza.model.entity.Response;
import com.pf.karza.model.entity.TopCustomer;
import com.pf.karza.model.entity.TopSupplier;
import com.pf.karza.model.entity.UserRequest;
import com.pf.karza.model.entity.ais.Ais;
import com.pf.karza.model.entity.bankingrelations.BankingRelations;
import com.pf.karza.model.entity.financialInformation.FinancialInformation;
import com.pf.karza.model.entity.generalinfobusiness.GeneralInfo;
import com.pf.karza.model.entity.itrfilled.ItrFilled;
import com.pf.karza.model.entity.keypersonsinfo.KeyPersonsInfo;
import com.pf.karza.model.entity.twentySixAS.TwentySixASData;
import com.pf.karza.model.entity.ubo.Ubo;
import com.pf.karza.repository.AisRepository;
import com.pf.karza.repository.AlertsRepository;
import com.pf.karza.repository.AuditInfoRepository;
import com.pf.karza.repository.BadDebtDetailsRepository;
import com.pf.karza.repository.BankingRelationsRepository;
import com.pf.karza.repository.FinancialInformationRepository;
import com.pf.karza.repository.FormDetailsRepository;
import com.pf.karza.repository.GeneralInfoRepository;
import com.pf.karza.repository.ItrFilledRepository;
import com.pf.karza.repository.KeyPersonsInfoRepository;
import com.pf.karza.repository.NatureOfCompanyRepository;
import com.pf.karza.repository.OutstandingDemandRepository;
import com.pf.karza.repository.RequestRepository;
import com.pf.karza.repository.ResponseRepository;
import com.pf.karza.repository.TopCustomerRepository;
import com.pf.karza.repository.TopSupplierRepository;
import com.pf.karza.repository.TwentySixASDataRepository;
import com.pf.karza.repository.UboRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class BusinessITRDataStore {
    private final ObjectMapper objectMapper;
    private final RequestRepository requestRepository;
    private final ResponseRepository responseRepository;
    private final GeneralInfoRepository generalInfoRepository;
    private final NatureOfCompanyRepository natureOfCompanyRepository;
    private final TwentySixASDataRepository twentySixASDataRepository;
    private final FinancialInformationRepository financialInformationRepository;
    private final AuditInfoRepository auditInfoRepository;
    private final FormDetailsRepository formDetailsRepository;
    private final OutstandingDemandRepository outstandingDemandRepository;
    private final UboRepository uboRepository;
    private final BadDebtDetailsRepository badDebtDetailsRepository;
    private final AlertsRepository alertsRepository;
    private final TopCustomerRepository topCustomerRepository;
    private final TopSupplierRepository topSupplierRepository;
    private final BankingRelationsRepository bankingRelationsRepository;
    private final KeyPersonsInfoRepository keyPersonsInfoRepository;
    private final ItrFilledRepository itrFilledRepository;
    private final AisRepository aisRepository;

    @Autowired
    public BusinessITRDataStore(ObjectMapper objectMapper,
                                RequestRepository requestRepository,
                                ResponseRepository responseRepository,
                                GeneralInfoRepository generalInfoRepository,
                                NatureOfCompanyRepository natureOfCompanyRepository,
                                TwentySixASDataRepository twentySixASDataRepository,
                                FinancialInformationRepository financialInformationRepository,
                                AuditInfoRepository auditInfoRepository,
                                FormDetailsRepository formDetailsRepository,
                                OutstandingDemandRepository outstandingDemandRepository,
                                UboRepository uboRepository,
                                BadDebtDetailsRepository badDebtDetailsRepository,
                                AlertsRepository alertsRepository,
                                TopCustomerRepository topCustomerRepository,
                                TopSupplierRepository topSupplierRepository,
                                BankingRelationsRepository bankingRelationsRepository,
                                KeyPersonsInfoRepository keyPersonsInfoRepository,
                                ItrFilledRepository itrFilledRepository,
                                AisRepository aisRepository) {
        this.objectMapper = objectMapper;
        this.requestRepository = requestRepository;
        this.responseRepository = responseRepository;
        this.generalInfoRepository = generalInfoRepository;
        this.natureOfCompanyRepository = natureOfCompanyRepository;
        this.twentySixASDataRepository = twentySixASDataRepository;
        this.financialInformationRepository = financialInformationRepository;
        this.auditInfoRepository = auditInfoRepository;
        this.formDetailsRepository = formDetailsRepository;
        this.outstandingDemandRepository = outstandingDemandRepository;
        this.uboRepository = uboRepository;
        this.badDebtDetailsRepository = badDebtDetailsRepository;
        this.alertsRepository = alertsRepository;
        this.topCustomerRepository = topCustomerRepository;
        this.topSupplierRepository = topSupplierRepository;
        this.bankingRelationsRepository = bankingRelationsRepository;
        this.keyPersonsInfoRepository = keyPersonsInfoRepository;
        this.itrFilledRepository = itrFilledRepository;
        this.aisRepository = aisRepository;
    }

    @Transactional
    public ItrResponse saveBusinessITRData(UserRequest userRequest, String responseBody, String masRefId, String ipAddress, String createdBy) throws JsonProcessingException {
        try {
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            JsonNode resultNode = jsonNode.has(ItrUtils.RESULT) ? jsonNode.path(ItrUtils.RESULT) : null;

            if (resultNode == null) {
                throw new MasRuntimeException(HttpStatus.BAD_REQUEST, "Could not read itr json response.");
            }
            resultNode = ItrUtils.flattenCptyArrays(resultNode);

            // Save response data
            Response response = ItrUtils.extractResponse(jsonNode, masRefId);

            if (response != null) {
                log.info("Triggering itr business data store.");
                response.setUserRequest(userRequest);
                requestRepository.save(userRequest);
                responseRepository.save(response);
                log.info("Response and request table saved!!");

                saveAIS(resultNode, userRequest);
                saveGeneralInfoBusiness(resultNode, userRequest);
                saveNatureOfCompany(resultNode, userRequest);
                save26AS(resultNode, userRequest);
                saveFinancialInformation(resultNode, userRequest);
                saveBankingRelations(resultNode, userRequest);
                saveKeyPersonsInfo(resultNode, userRequest);
                saveAuditInfo(resultNode, userRequest);
                saveFormDetails(resultNode, userRequest);
                saveOutstandingDemand(resultNode, userRequest);
                saveUBO(resultNode, userRequest);
                saveBadDebtDetails(resultNode, userRequest);
                saveAlerts(resultNode, userRequest);
                saveTopCustomers(resultNode, userRequest);
                saveTopSuppliers(resultNode, userRequest);
                saveItrFilled(resultNode, userRequest);

                // Build and return ItrResponse
                return ItrUtils.getItrResponse(response, masRefId);
            } else {
                log.error("Unable to extract request data. JSON structure might be incomplete.");
                throw new MasRuntimeException(HttpStatus.BAD_REQUEST, "Unable to extract request data. JSON structure might be incomplete.");
            }
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON: {}", e.getMessage());
            throw e;
        }
    }

    private void saveAIS(JsonNode resultNode, UserRequest userRequest) {
        // Save AIS Data
        JsonNode aisNode = resultNode.has("ais") ? resultNode.path("ais") : null;

        if (aisNode != null && !aisNode.isNull() && !aisNode.isMissingNode()) {
            Ais ais = objectMapper.convertValue(aisNode, Ais.class);
            ais.setUserRequest(userRequest);
            aisRepository.save(ais);
            log.debug("AIS table saved!!");
        } else {
            log.debug("ais block not found. Skipped ais data storage.");
        }
    }

    @Transactional
    private void saveItrFilled(JsonNode resultNode, UserRequest userRequest) {
        // Save itrFilled
        JsonNode itrFilledArrayNode = resultNode.has("itrFilled") ? resultNode.path("itrFilled") : null;
        if (itrFilledArrayNode != null && !itrFilledArrayNode.isNull() && !itrFilledArrayNode.isMissingNode()) {
            List<ItrFilled> itrFilledList = objectMapper.convertValue(itrFilledArrayNode, new TypeReference<List<ItrFilled>>() {
            });
            itrFilledList.forEach(each -> each.setUserRequest(userRequest));
            itrFilledRepository.saveAll(itrFilledList);
            log.debug("Itr Filled table save!!");
        } else {
            log.debug("itrFilled block not found. Skipped itrFilled data storage.");
        }
    }

    @Transactional
    private void saveTopSuppliers(JsonNode resultNode, UserRequest userRequest) {
        // Save Top Suppliers
        JsonNode topSuppliersNode = resultNode.has("topSuppliers") ? resultNode.path("topSuppliers") : null;
        if (topSuppliersNode != null && !topSuppliersNode.isNull() && !topSuppliersNode.isMissingNode()) {
            List<TopSupplier> topSupplierList = objectMapper.convertValue(topSuppliersNode, new TypeReference<List<TopSupplier>>() {
            });
            topSupplierList.forEach(each -> each.setUserRequest(userRequest));
            topSupplierRepository.saveAll(topSupplierList);
            log.debug("Top Suppliers table saved!!");
        } else {
            log.debug("topSuppliers block not found. Skipped topSuppliers data storage.");
        }
    }

    @Transactional
    private void saveTopCustomers(JsonNode resultNode, UserRequest userRequest) {
        // Save Top Customers
        JsonNode topCustomersNode = resultNode.has("topCustomers") ? resultNode.path("topCustomers") : null;
        if (topCustomersNode != null && !topCustomersNode.isNull() && !topCustomersNode.isMissingNode()) {
            List<TopCustomer> topCustomerList = objectMapper.convertValue(topCustomersNode, new TypeReference<List<TopCustomer>>() {
            });
            topCustomerList.forEach(each -> each.setUserRequest(userRequest));
            topCustomerRepository.saveAll(topCustomerList);
            log.debug("Top Customers table saved!!");
        } else {
            log.debug("topCustomers block not found. Skipped topCustomers data storage.");
        }
    }

    @Transactional
    private void saveAlerts(JsonNode resultNode, UserRequest userRequest) {
        // Save Alerts
        JsonNode alertsNode = resultNode.has("alerts") ? resultNode.path("alerts") : null;
        if (alertsNode != null && !alertsNode.isNull() && !alertsNode.isMissingNode()) {
            Alerts alerts = objectMapper.convertValue(alertsNode, Alerts.class);
            alerts.setUserRequest(userRequest);
            alertsRepository.save(alerts);
            log.debug("Alerts table saved!!");
        } else {
            log.debug("alerts block not found. Skipped alerts data storage.");
        }
    }

    @Transactional
    private void saveBadDebtDetails(JsonNode resultNode, UserRequest userRequest) {
        // Save Bad Debt Details
        JsonNode badDebtDetailsNode = resultNode.has("badDebtDetails") ? resultNode.path("badDebtDetails") : null;
        if (badDebtDetailsNode != null && !badDebtDetailsNode.isNull() && !badDebtDetailsNode.isMissingNode()) {
            BadDebtDetails badDebtDetails = objectMapper.convertValue(badDebtDetailsNode, BadDebtDetails.class);
            badDebtDetails.setUserRequest(userRequest);
            badDebtDetailsRepository.save(badDebtDetails);
            log.debug("BadDebtDetails table saved!!");
        } else {
            log.debug("badDebtDetails block not found. Skipped badDebtDetails data storage.");
        }
    }

    @Transactional
    private void saveUBO(JsonNode resultNode, UserRequest userRequest) {
        // Save UBO
        JsonNode uboNode = resultNode.has("ubo") ? resultNode.path("ubo") : null;
        if (uboNode != null && !uboNode.isNull() && !uboNode.isMissingNode()) {
            List<Ubo> uboEntityList = objectMapper.convertValue(uboNode, new TypeReference<List<Ubo>>() {
            });
            uboEntityList.forEach(each -> each.setUserRequest(userRequest));
            uboRepository.saveAll(uboEntityList);
            log.debug("UBO table saved!!");
        } else {
            log.debug("ubo block not found. Skipped ubo data storage.");
        }

    }

    @Transactional
    private void saveOutstandingDemand(JsonNode resultNode, UserRequest userRequest) {
        // Save OutStandingDemand
        JsonNode outstandingDemandNode = resultNode.has("outstandingDemand") ? resultNode.path("outstandingDemand") : null;
        if (outstandingDemandNode != null && !outstandingDemandNode.isNull() && !outstandingDemandNode.isMissingNode()) {
            List<OutstandingDemand> outstandingDemandList = objectMapper.convertValue(outstandingDemandNode, new TypeReference<List<OutstandingDemand>>() {
            });
            outstandingDemandList.forEach(each -> each.setUserRequest(userRequest));
            outstandingDemandRepository.saveAll(outstandingDemandList);
            log.debug("OutstandingDemand details saved!!");
        } else {
            log.debug("outstandingDemand block not found. Skipped outstandingDemand data storage.");
        }
    }

    @Transactional
    private void saveFormDetails(JsonNode resultNode, UserRequest userRequest) {

        JsonNode formDetailsNode = resultNode.has("formDetails") ? resultNode.path("formDetails") : null;
        if (formDetailsNode != null && !formDetailsNode.isNull() && !formDetailsNode.isMissingNode()) {
            FormDetails formDetails = objectMapper.convertValue(formDetailsNode, FormDetails.class);
            formDetails.setUserRequest(userRequest);
            formDetailsRepository.save(formDetails);
            log.debug("FormDetails table saved!!");
        } else {
            log.debug("formDetails block not found. Skipped formDetails data storage.");
        }
    }

    @Transactional
    private void saveAuditInfo(JsonNode resultNode, UserRequest userRequest) {

        JsonNode auditInfoNode = resultNode.has("auditInfo") ? resultNode.path("auditInfo") : null;
        if (auditInfoNode != null && !auditInfoNode.isNull() && !auditInfoNode.isMissingNode()) {
            AuditInfo auditInfo = objectMapper.convertValue(auditInfoNode, AuditInfo.class);
            auditInfo.setUserRequest(userRequest);
            auditInfoRepository.save(auditInfo);
            log.debug("Audit Info table saved!!");

        } else {
            log.debug("auditInfo block not found. Skipped auditInfo data storage.");
        }
    }

    @Transactional
    private void saveKeyPersonsInfo(JsonNode resultNode, UserRequest userRequest) {

        JsonNode keyPersonInfoNode = resultNode.has("keyPersonsInfo") ? resultNode.path("keyPersonsInfo") : null;
        if (keyPersonInfoNode != null && !keyPersonInfoNode.isNull() && !keyPersonInfoNode.isMissingNode()) {
            KeyPersonsInfo keyPersonsInfo = objectMapper.convertValue(keyPersonInfoNode, KeyPersonsInfo.class);
            keyPersonsInfo.setUserRequest(userRequest);
            keyPersonsInfoRepository.save(keyPersonsInfo);
            log.debug("KeyPersonInfo table saved!!");

        } else {
            log.debug("keyPersonsInfo block not found. Skipped keyPersonsInfo data storage.");
        }
    }

    @Transactional
    private void saveBankingRelations(JsonNode resultNode, UserRequest userRequest) {

        JsonNode bankingRelationsNode = resultNode.has("bankingRelations") ? resultNode.path("bankingRelations") : null;
        if (bankingRelationsNode != null && !bankingRelationsNode.isNull() && !bankingRelationsNode.isMissingNode()) {
            BankingRelations bankingRelations = objectMapper.convertValue(bankingRelationsNode, BankingRelations.class);
            bankingRelations.setUserRequest(userRequest);
            bankingRelationsRepository.save(bankingRelations);
            log.debug("Banking relations table saved!!");

        } else {
            log.debug("bankingRelations block not found. Skipped bankingRelations data storage.");
        }
    }


    @Transactional
    private void saveFinancialInformation(JsonNode resultNode, UserRequest userRequest) {
        JsonNode financialInformationArrayNode = resultNode.has("financialInformation") ? resultNode.path("financialInformation") : null;
        if (financialInformationArrayNode != null && !financialInformationArrayNode.isNull() && !financialInformationArrayNode.isMissingNode()) {
            List<FinancialInformation> financialInformationList = objectMapper.convertValue(financialInformationArrayNode, new TypeReference<List<FinancialInformation>>() {
            });
            financialInformationList.forEach(each -> each.setUserRequest(userRequest));
            financialInformationRepository.saveAll(financialInformationList);
            log.debug("Financial Information table saved!!");
        } else {
            log.debug("financialInformation block not found. Skipped financialInformation data storage.");
        }
    }


    @Transactional
    private void save26AS(JsonNode resultNode, UserRequest userRequest) {
        JsonNode twentySixASNode = resultNode.has("26asData") ? resultNode.path("26asData") : null;
        if (twentySixASNode != null && !twentySixASNode.isNull() && !twentySixASNode.isMissingNode()) {

            List<TwentySixASData> twentySixASDataList = new ArrayList<>();
            for (JsonNode parentNode : twentySixASNode) {
                JsonNode someDataNode = parentNode.get("data");
                TwentySixASData twentySixASData = objectMapper.convertValue(someDataNode, TwentySixASData.class);
                twentySixASData.setStatusOf26asData(ItrUtils.getValue(parentNode.get("statusOf26asData")));
                twentySixASData.setAssessmentYear(ItrUtils.getValue(parentNode.get("assessmentYear")));
                twentySixASData.setUserRequest(userRequest);
                twentySixASDataList.add(twentySixASData);
            }
            twentySixASDataRepository.saveAll(twentySixASDataList);
            log.debug("26AS table saved!!");

        } else {
            log.debug("26asData block not found. Skipped 26asData data storage.");
        }

    }

    @Transactional
    private void saveNatureOfCompany(JsonNode resultNode, UserRequest userRequest) {
        JsonNode natureOfCompanyNode = resultNode.has("natureOfComp") ? resultNode.path("natureOfComp") : null;

        if (natureOfCompanyNode != null && !natureOfCompanyNode.isNull() && !natureOfCompanyNode.isMissingNode()) {
            NatureOfCompany natureOfCompany = objectMapper.convertValue(natureOfCompanyNode, NatureOfCompany.class);
            natureOfCompany.setUserRequest(userRequest);
            natureOfCompanyRepository.save(natureOfCompany);
            log.debug("NatureOfCompany table saved!!");
        } else {
            log.debug("natureOfComp block not found. Skipped natureOfComp data storage.");
        }
    }

    @Transactional
    private void saveGeneralInfoBusiness(JsonNode resultNode, UserRequest userRequest) {
        JsonNode generalInformationNode = resultNode.has("generalInformation") ? resultNode.path("generalInformation") : null;

        if (generalInformationNode != null && !generalInformationNode.isNull() && !generalInformationNode.isMissingNode()) {
            GeneralInfo generalInfo = objectMapper.convertValue(generalInformationNode, GeneralInfo.class);
            generalInfo.setUserRequest(userRequest);
            generalInfoRepository.save(generalInfo);
            log.debug("GeneralProfileInfo table saved!!");
        } else {
            log.debug("generalInformation block not found. Skipped generalInformation data storage.");
        }
    }
}