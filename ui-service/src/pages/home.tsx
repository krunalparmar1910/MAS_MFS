import React, {useEffect, useState} from "react";
import "styles/home.scss";
import {
  Box,
  Button,
  Container,
  Input,
  InputLabel,
  Paper,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Typography,
} from "@mui/material";
import {useTranslation} from "react-i18next";
import {ErrorResponse, getGST3BReportRequestHistory, parseExcelReport} from "utils/api/request";
import {
  GetGST3BReportRequestHistoryResponseDTO,
  GetGST3BReportRequestResponseInfo,
  ProfileDetailResponse,
} from "interfaces/gst";
import {AxiosError} from "axios";
import {useNavigate} from "react-router-dom";
import {GST_REPORT_PATH} from "utils/constants/routes";
import {CLIENT_ORDER_IDS_PARAM, ENTITY_ID_PARAM} from "utils/constants/constants";

const ENTITY_ID_INPUT = "entity-id-input";
const FILE_UPLOAD_INPUT = "file-upload-input";

export function Home() {
  const [entityIdInput, setEntityIdInput] = useState<string>();
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const [reportFile, setReportFile] = useState<File>();
  const [profileDetailsResponse, setProfileDetailsResponse] = useState<ProfileDetailResponse | null>(null);
  const [parseInProgress, setParseInProgress] = useState<boolean>(false);
  const [gst3BReportRequestHistory, setGST3BReportRequestHistory] = useState<GetGST3BReportRequestHistoryResponseDTO>();
  const {t} = useTranslation();
  const navigate = useNavigate();

  useEffect(() => {
    getGST3BReportRequestHistory().then(setGST3BReportRequestHistory);
  }, []);

  const isExcelFile = (file: File): boolean => {
    return file.name.endsWith(".xls") || file.name.endsWith(".xlsx");
  };

  const sendFileUploadRequest = (formData: FormData): void => {
    setParseInProgress(true);
    parseExcelReport(formData)
      .then(setProfileDetailsResponse)
      .catch((err: AxiosError<ErrorResponse>) => setErrorMessage(err.message + ": " + err?.response?.data?.message))
      .finally(() => {
        setParseInProgress(false);
        getGST3BReportRequestHistory()
          .then(setGST3BReportRequestHistory)
          .catch(() => {});
      });
  };

  const handleFileUpload = (): void => {
    setProfileDetailsResponse(null);
    if (reportFile && isExcelFile(reportFile)) {
      const formData = new FormData();
      formData.append("file", reportFile);
      if (!entityIdInput) {
        setErrorMessage("home.fileUpload.error.inputRequired");
      } else {
        formData.append("entity-id", entityIdInput);
        setErrorMessage(null);
        sendFileUploadRequest(formData);
      }
    } else {
      setErrorMessage("home.fileUpload.error.fileRequired");
    }
  };

  const getProfileDetailsResponseMessage = (): string => {
    const respClientOrderId = profileDetailsResponse?.clientOrderId;
    const respEntityId = profileDetailsResponse?.entityId;
    if (respClientOrderId && respEntityId) {
      return t("home.fileUpload.successEntityIdClientOrderId", {cOId: respClientOrderId, eId: respEntityId});
    } else if (respClientOrderId) {
      return t("home.fileUpload.successClientOrderId", {cOId: respClientOrderId});
    } else if (respEntityId) {
      return t("home.fileUpload.successEntityId", {eId: respEntityId});
    } else {
      return t("home.fileUpload.success");
    }
  };

  const renderUploadContainer = () => {
    return (
      <Box className="upload-container">
        <Typography component="h4" variant="h4" textAlign="center">{t("home.fileUpload.title")}</Typography>
        <Box className="upload-container-item">
          <InputLabel htmlFor={ENTITY_ID_INPUT}>{t("home.fileUpload.entityId")}</InputLabel>
          <Input
            id={ENTITY_ID_INPUT}
            type="text"
            onChange={(event) => setEntityIdInput(event.target.value)}
            placeholder={t("home.fileUpload.entityId")}
          />
        </Box>
        <Box className="upload-container-item">
          <InputLabel htmlFor={FILE_UPLOAD_INPUT}>{t("home.fileUpload.uploadLabel")}</InputLabel>
          <Input
            id={FILE_UPLOAD_INPUT}
            type="file"
            onChange={(event: React.ChangeEvent<HTMLInputElement>) => setReportFile(event?.target?.files?.[0])}
          />
        </Box>
        <Box className="upload-container-item">
          <Button
            type="submit"
            onClick={handleFileUpload}
            variant="contained"
            disabled={parseInProgress}
          >
            {t("home.fileUpload.upload")}
          </Button>
          {parseInProgress && <Typography>{t("home.fileUpload.parseInProgress")}</Typography>}
          {errorMessage && <Typography className="login-error">{t(errorMessage)}</Typography>}
          {profileDetailsResponse && <Typography>{getProfileDetailsResponseMessage()}</Typography>}
        </Box>
      </Box>
    );
  };

  const getViewGST3BReportLink = (request: GetGST3BReportRequestResponseInfo): void => {
    let searchRequest = "";
    if (request.entityId && request.clientOrderIds) {
      searchRequest
        += `?${ENTITY_ID_PARAM}=${request.entityId}&${CLIENT_ORDER_IDS_PARAM}=${request.clientOrderIds.toString()}`;
    } else if (request.entityId) {
      searchRequest += `?${ENTITY_ID_PARAM}=${request.entityId}`;
    } else if (request.clientOrderIds) {
      searchRequest += `?${CLIENT_ORDER_IDS_PARAM}=${request.clientOrderIds.toString()}`;
    }
    navigate({
      pathname: "/" + GST_REPORT_PATH,
      search: searchRequest,
    });
  };

  const renderGST3BRequestHistory = () => {
    return (
      <Box className="request-history-container">
        <Typography component="h4" variant="h4" textAlign="center">{t("home.requestHistory.title")}</Typography>
        <TableContainer component={Paper}>
          <Table>
            <TableHead>
              <TableRow>
                <TableCell>{t("home.requestHistory.column.srNo")}</TableCell>
                <TableCell>{t("home.requestHistory.column.companyName")}</TableCell>
                <TableCell>{t("home.requestHistory.column.entityId")}</TableCell>
                <TableCell>{t("home.requestHistory.column.clientOrderId")}</TableCell>
                <TableCell>{t("home.requestHistory.column.view")}</TableCell>
              </TableRow>
            </TableHead>
            <TableBody>
              {gst3BReportRequestHistory?.getGST3BReportRequestResponseInfoList?.map((request, index) => {
                return (
                  <TableRow key={gst3BReportRequestHistory?.getGST3BReportRequestResponseInfoList.length - index}>
                    <TableCell>
                      {t(String(gst3BReportRequestHistory?.getGST3BReportRequestResponseInfoList.length - index))}
                    </TableCell>
                    <TableCell>{t(request?.companyName || "-")}</TableCell>
                    <TableCell>{t(request?.entityId || "-")}</TableCell>
                    <TableCell>{t(request?.clientOrderIds?.toString() || "-")}</TableCell>
                    <TableCell>
                      <Button variant="contained" onClick={() => getViewGST3BReportLink(request)}>
                        {t("home.requestHistory.column.view")}
                      </Button>
                    </TableCell>
                  </TableRow>
                );
              }).reverse()}
            </TableBody>
          </Table>
        </TableContainer>
      </Box>
    );
  };

  return (
    <Box className="home-container">
      <Container maxWidth="sm">
        {renderUploadContainer()}
      </Container>
      <Container maxWidth="md">
        {renderGST3BRequestHistory()}
      </Container>
    </Box>
  );
}
