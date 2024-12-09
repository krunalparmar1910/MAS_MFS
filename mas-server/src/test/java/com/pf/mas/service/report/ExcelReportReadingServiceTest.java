package com.pf.mas.service.report;

import com.pf.mas.exception.MasGSTNoEntityFoundException;
import com.pf.mas.exception.MasReportSheetReaderException;
import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.repository.ClientOrderRepository;
import com.pf.mas.repository.SheetTypeRepository;
import com.pf.mas.service.report.sheet.profile.DefaultProfileSheetReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExcelReportReadingServiceTest {
    // constants
    private static final String CLIENT_ORDER_ID = "1";

    // mocks
    @Mock
    private ClientOrderRepository clientOrderRepository;
    @Mock
    private ReportDetailsReadingService reportDetailsReadingService;
    @Mock
    private SheetTypeRepository sheetTypeRepository;
    @Mock
    private SheetReadingService sheetReadingService;
    @Mock
    private DefaultProfileSheetReader profileSheetReader;

    // captors
    @Captor
    private ArgumentCaptor<ClientOrder> clientOrderArgumentCaptor;

    // fields
    private ReportReadingService reportReadingService;

    @BeforeEach
    public void setup() {
        reportReadingService = new ExcelReportReadingService(sheetTypeRepository, clientOrderRepository, reportDetailsReadingService, sheetReadingService, profileSheetReader);
        when(clientOrderRepository.save(any())).thenAnswer(m -> m.getArgument(0));
    }

    @Test
    void testParseReport() throws MasReportSheetReaderException, IOException, MasGSTNoEntityFoundException {
        String filePath = SheetTestUtils.getFilePath(SheetTestUtils.SINGLE_REPORT_FILES.get(0));
        assertNotNull(filePath);

        ClientOrder clientOrder = getAndValidateClientOrder();
        reportReadingService.readAndStoreReport(clientOrder, SheetTestUtils.SINGLE_REPORT_FILES.get(0), SheetTestUtils.getFileData(filePath));

        verify(clientOrderRepository, times(2)).save(clientOrderArgumentCaptor.capture());
        List<ClientOrder> allClientOrders = clientOrderArgumentCaptor.getAllValues();
        assertEquals(clientOrder, allClientOrders.get(0));
        clientOrder.setReportStatus(ClientOrder.ClientOrderReportStatus.COMPLETED.toString());
        assertEquals(clientOrder, allClientOrders.get(1));

        verify(profileSheetReader, times(1)).readSheetAndStoreData(any(), any(), any());
        verify(reportDetailsReadingService, times(1)).readAndStoreReportDetails(any(), any(), any());
        verify(sheetReadingService, times(1)).readAllSheetsAndStoreData(any(), any(), any());
    }

    @Test
    void testParseReportDeletesClientOrderOnFailure() throws MasReportSheetReaderException, IOException, MasGSTNoEntityFoundException {
        MasReportSheetReaderException expectedTestFailure = new MasReportSheetReaderException("expected test failure");
        doThrow(expectedTestFailure).when(sheetReadingService).readAllSheetsAndStoreData(any(), any(), any());

        String filePath = SheetTestUtils.getFilePath(SheetTestUtils.SINGLE_REPORT_FILES.get(0));
        assertNotNull(filePath);

        ClientOrder clientOrder = getAndValidateClientOrder();

        boolean expectedTestFailureCaught = false;
        try {
            reportReadingService.readAndStoreReport(clientOrder, SheetTestUtils.SINGLE_REPORT_FILES.get(0), SheetTestUtils.getFileData(filePath));
        } catch (MasReportSheetReaderException e) {
            expectedTestFailureCaught = true;
            assertEquals(expectedTestFailure.getLocalizedMessage(), e.getLocalizedMessage());
        }
        assertTrue(expectedTestFailureCaught);

        verify(profileSheetReader, times(1)).readSheetAndStoreData(any(), any(), any());
        verify(reportDetailsReadingService, times(1)).readAndStoreReportDetails(any(), any(), any());
        verify(sheetReadingService, times(1)).readAllSheetsAndStoreData(any(), any(), any());

        verify(clientOrderRepository, times(1)).save(clientOrderArgumentCaptor.capture());
        assertEquals(clientOrder, clientOrderArgumentCaptor.getValue());
        assertEquals(clientOrder.getReportStatus(), ClientOrder.ClientOrderReportStatus.ERROR.toString());
    }

    private ClientOrder getAndValidateClientOrder() throws MasGSTNoEntityFoundException {
        ClientOrder clientOrder = reportReadingService.getOrCreateNewClientOrder(null, CLIENT_ORDER_ID, null, null);
        assertNotNull(clientOrder);
        assertEquals(CLIENT_ORDER_ID, clientOrder.getClientOrderId());
        return clientOrder;
    }
}
