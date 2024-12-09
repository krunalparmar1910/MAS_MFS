package com.pf.mas.service.corpository;

import com.pf.corpository.model.MasGetReportRequest;
import com.pf.mas.exception.MasGSTNoEntityFoundException;
import com.pf.mas.exception.MasGetReportResponseException;
import com.pf.mas.exception.MasReportSheetReaderException;
import com.pf.mas.model.entity.ClientOrder;
import com.pf.mas.service.report.ReportReadingService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Slf4j
@Service
public class DefaultCorpositoryReportService implements CorpositoryReportService {
    private static final String TEMP_DIRECTORY_PATH = "tempDirectoryPath";
    private static final int BUFFER_SIZE = 16 * 1024;
    private final ReportReadingService reportReadingService;

    public DefaultCorpositoryReportService(ReportReadingService reportReadingService) {
        this.reportReadingService = reportReadingService;
    }

    @Override
    public void readGetReportResponseAndStore(MasGetReportRequest request, byte[] response, String createdBy, String ipAddress)
            throws MasReportSheetReaderException, MasGetReportResponseException, MasGSTNoEntityFoundException {
        File reportFolder;
        try {
            reportFolder = readZipFile(request.getRequestBody().getClientOrderId(), response);
        } catch (MasGetReportResponseException | IOException e) {
            log.warn("Error during reading zip file from response due to {}, checking if zip file exists", e.getLocalizedMessage());
            reportFolder = new File(getReportFolderPath(request.getRequestBody().getClientOrderId()));
            if (!reportFolder.exists()) {
                log.error("Report folder does not exist, throwing error");
                throw new MasReportSheetReaderException(e);
            }
        }

        Collection<File> excelFiles = FileUtils.listFiles(reportFolder, new String[]{"xlsx"}, true);

        if (CollectionUtils.isNotEmpty(excelFiles)) {
            ClientOrder clientOrder = reportReadingService.getOrCreateNewClientOrder(
                    request.getRequestBody().getEntityId(), request.getRequestBody().getClientOrderId(), createdBy, ipAddress);

            for (File file : excelFiles) {
                log.info("Reading report for file {} for client order {}", file.getAbsolutePath(), clientOrder);
                try (FileInputStream fileInputStream = new FileInputStream(file)) {
                    reportReadingService.readAndStoreReport(clientOrder, file.getName(), fileInputStream.readAllBytes());
                    log.info("Completed reading report for file {} for client order {}", file.getAbsolutePath(), clientOrder);
                } catch (IOException e) {
                    throw new MasReportSheetReaderException(e);
                }
            }
        } else {
            log.error("No excel files found, file list {}", Arrays.stream(Optional.ofNullable(reportFolder.list()).orElse(new String[0])).toList());
        }

        cleanupTempFolder(reportFolder);
    }

    private File readZipFile(String clientOrderId, byte[] response) throws IOException, MasGetReportResponseException {
        File reportFolder = null;
        try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(response);
             ZipInputStream zipInputStream = new ZipInputStream(byteArrayInputStream)) {
            reportFolder = getReportFolder(clientOrderId);
            ZipEntry zipEntry = zipInputStream.getNextEntry();
            byte[] buffer = new byte[BUFFER_SIZE];

            while (zipEntry != null) {
                File newFile = newFile(reportFolder, zipEntry);
                if (zipEntry.isDirectory()) {
                    createIfDirectory(newFile);
                } else {
                    // fix for Windows-created archives
                    File parent = newFile.getParentFile();
                    createIfDirectory(parent);

                    // write file content
                    try (FileOutputStream fos = new FileOutputStream(newFile)) {
                        int len;
                        while ((len = zipInputStream.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                    }
                }
                zipEntry = zipInputStream.getNextEntry();
            }

            return reportFolder;
        } catch (IOException e) {
            cleanupTempFolder(reportFolder);
            throw e;
        }
    }

    private File getReportFolder(String clientOrderId) throws IOException {
        String reportFolderPath = getReportFolderPath(clientOrderId);
        log.debug("Report folder path: {}", reportFolderPath);

        File reportFolder = new File(reportFolderPath);
        createIfDirectory(reportFolder);
        return reportFolder;
    }

    private String getReportFolderPath(String clientOrderId) {
        return System.getProperty(TEMP_DIRECTORY_PATH, Paths.get(".").toAbsolutePath().normalize().toString()) + File.separator + clientOrderId;
    }

    // temp folders should be cleaned up after usage, no report file is required on disk
    private void cleanupTempFolder(File tempFolder) throws MasGetReportResponseException {
        if (tempFolder != null) {
            try {
                FileUtils.forceDelete(tempFolder);
                log.debug("Cleaned up report folder {}", tempFolder.getAbsolutePath());
            } catch (IOException e) {
                log.error("Could not cleanup report temp folder at {} due to {}", tempFolder.getAbsolutePath(), e.getLocalizedMessage());
            }
        } else {
            log.warn("Report temp folder was null, skipping cleanup");
        }
    }

    private void createIfDirectory(File file) throws IOException {
        if (!file.isDirectory() && !file.mkdirs()) {
            throw new IOException("Failed to create directory " + file);
        }
        log.debug("Created directory at {}", file.getAbsolutePath());
    }

    private File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());

        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();

        // safety check for zip-slip
        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }
}
