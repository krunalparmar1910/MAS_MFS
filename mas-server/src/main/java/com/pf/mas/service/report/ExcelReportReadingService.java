package com.pf.mas.service.report;

import com.pf.mas.exception.MasGSTNoEntityFoundException;
import com.pf.mas.exception.MasReportSheetReaderException;
import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.model.entity.SheetType;
import com.pf.mas.model.entity.SheetTypeName;
import com.pf.mas.model.entity.sheet.ProfileDetail;
import com.pf.mas.repository.ClientOrderRepository;
import com.pf.mas.repository.SheetTypeRepository;
import com.pf.mas.service.report.sheet.profile.ProfileSheetReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ExcelReportReadingService implements ReportReadingService {
    private static final Set<SheetTypeName> ALL_SHEET_TYPE_NAMES = Arrays.stream(SheetTypeName.values()).collect(Collectors.toSet());
    private final SheetTypeRepository sheetTypeRepository;
    private final ClientOrderRepository clientOrderRepository;
    private final ReportDetailsReadingService reportDetailsReadingService;
    private final SheetReadingService sheetReadingService;
    private final ProfileSheetReader profileSheetReader;
    private final List<SheetType> sheetTypes;

    public ExcelReportReadingService(
            SheetTypeRepository sheetTypeRepository,
            ClientOrderRepository clientOrderRepository,
            ReportDetailsReadingService reportDetailsReadingService,
            SheetReadingService sheetReadingService,
            ProfileSheetReader profileSheetReader) {
        this.sheetTypeRepository = sheetTypeRepository;
        this.clientOrderRepository = clientOrderRepository;
        this.reportDetailsReadingService = reportDetailsReadingService;
        this.sheetReadingService = sheetReadingService;
        this.profileSheetReader = profileSheetReader;
        sheetTypes = new CopyOnWriteArrayList<>();
    }

    @Override
    public ClientOrder getOrCreateNewClientOrder(String entityId, String clientOrderId, String createdBy, String ipAddress) throws MasGSTNoEntityFoundException {
        if (StringUtils.isBlank(entityId) && StringUtils.isBlank(clientOrderId)) {
            throw new MasGSTNoEntityFoundException("Either entity id or client order id is required");
        }
        ClientOrder clientOrder = clientOrderRepository.findByEntityIdAndClientOrderId(entityId, clientOrderId);
        if (clientOrder != null) {
            log.info("Client order with entity id {} and client order id {} already exists, deleting", entityId, clientOrder);
            // this will trigger cascading delete on field groups and all sheet data parsed so far
            clientOrderRepository.delete(clientOrder);
        }
        clientOrder = createNewClientOrder(entityId, clientOrderId, createdBy, ipAddress);
        return clientOrder;
    }

    private ClientOrder createNewClientOrder(String entityId, String clientOrderId, String createdBy, String ipAddress) {
        ClientOrder clientOrder = new ClientOrder();
        clientOrder.setClientOrderId(clientOrderId);
        clientOrder.setEntityId(entityId);
        clientOrder.setReportStatus(ClientOrder.ClientOrderReportStatus.IN_PROGRESS.toString());
        clientOrder.setCreatedBy(createdBy);
        clientOrder.setIpAddress(ipAddress);

        log.info("Creating new client order {}", clientOrder);
        clientOrder = clientOrderRepository.save(clientOrder);
        log.info("Saved client order {}", clientOrder);

        return clientOrder;
    }

    @Override
    public List<ProfileDetail> readAndStoreReport(ClientOrder clientOrder, String fileName, byte[] fileData) throws MasReportSheetReaderException {
        long startTime = System.currentTimeMillis();
        Map<SheetTypeName, Sheet> sheetMap = new EnumMap<>(SheetTypeName.class);
        Map<SheetTypeName, SheetType> sheetTypeMap = new EnumMap<>(SheetTypeName.class);

        try (ByteArrayInputStream inputStream = new ByteArrayInputStream(fileData);
             Workbook workbook = new XSSFWorkbook(inputStream)) {

            // verify sheet names
            for (Sheet sheet : workbook) {
                SheetTypeName sheetTypeName = SheetTypeName.getFromSheetName(sheet.getSheetName());
                if (sheetTypeName != null && !ALL_SHEET_TYPE_NAMES.contains(sheetTypeName)) {
                    log.error("Unexpected sheet name encountered: {}", sheet.getSheetName());
                }
                sheetMap.put(sheetTypeName, sheet);
            }

            // read and store remaining sheet data
            if (sheetTypes.isEmpty()) {
                log.debug("sheetTypes list is empty, querying to initialize");
                sheetTypes.addAll(ListUtils.emptyIfNull(sheetTypeRepository.findAll()));
                log.debug("Updated sheetTypes list: {}", sheetTypes);
            }
            sheetTypes.forEach(type -> sheetTypeMap.put(SheetTypeName.getFromSheetName(type.getSheetTypeName()), type));

            // read and store profile details
            List<ProfileDetail> profileDetails = profileSheetReader.readSheetAndStoreData(
                    sheetMap.get(SheetTypeName.PROFILE_FILING_TABLE),
                    sheetTypeMap.get(SheetTypeName.PROFILE_FILING_TABLE),
                    clientOrder);

            // read report header and details
            reportDetailsReadingService.readAndStoreReportDetails(null, sheetMap, clientOrder);

            // read and store all sheet details
            sheetReadingService.readAllSheetsAndStoreData(sheetMap, sheetTypeMap, clientOrder);

            // mark client order status as completed and save
            clientOrder.setReportStatus(ClientOrder.ClientOrderReportStatus.COMPLETED.toString());
            clientOrder = clientOrderRepository.save(clientOrder);

            long endTime = System.currentTimeMillis();
            log.info("Total time to parse file data was {} seconds", (endTime - startTime) / 1000);

            return profileDetails;
        } catch (Exception e) {
            log.error("Error occurred during reading sheet: {}, marking client order report status as error", e.getLocalizedMessage(), e);
            try {
                clientOrder.setReportStatus(ClientOrder.ClientOrderReportStatus.ERROR.toString());
                clientOrderRepository.saveAndFlush(clientOrder);
                log.error("Marked client order {} report status as error", clientOrder);
            } catch (RuntimeException e1) {
                log.error("Unable to mark client order {} report status as error", clientOrder, e);
                throw new MasReportSheetReaderException(e1);
            }
            throw e instanceof MasReportSheetReaderException ex ? ex : new MasReportSheetReaderException(e);
        }
    }
}
