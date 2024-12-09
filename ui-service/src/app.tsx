import React, {useEffect} from "react";
import {Navigate, Route, Routes} from "react-router-dom";
import {Home} from "pages/home";
import {Login} from "components/login/login";
import {GstinDetails} from "pages/GstinDetails";
import {ACCESS_TOKEN, ACCESS_TOKEN_EXPIRY_TIME, REFRESH_TOKEN} from "utils/constants/constants";
import {ErrorPage} from "pages/errorPage";
import {getLoginInfo, refreshToken} from "utils/api/request";
import {ThemeProvider} from "@mui/material";
import theme from "utils/theme";
import "styles/main.scss";
import {
  BANKING_CONFIGURE_PATH,
  BANKING_REPORT_PATH,
  CIBIL_REPORT_PATH,
  EMI_MASTER,
  GST_REPORT_PATH,
  LOGIN_ROUTE,
} from "utils/constants/routes";
import {CibilDetails} from "pages/CibilDetails";
import {Banking} from "pages/Banking";
import BankingConfiguration from "pages/BankingConfiguration";
import EmiMaster from "pages/EmiMaster";

// iframe constants
const SECONDS_BEFORE_REFRESH_INTERVAL = 30; // call refresh token api before 30 seconds of expiry time of access token
const ACCESS_TOKEN_IFRAME_PARAM = "access-token";
const REFRESH_TOKEN_IFRAME_PARAM = "refresh-token";
const ACCESS_TOKEN_EXPIRY_TIME_IFRAME_PARAM = "access-token-expiry-time";

export function isInIFrame(): boolean {
  return window.location !== window.parent.location;
}

export function App() {
  const setAuthTokenIfExist = () => {
    if (isInIFrame()) {
      const url = new URL(window.location.href);
      const accessToken = url.searchParams.get(ACCESS_TOKEN_IFRAME_PARAM);
      if (accessToken) {
        // if access token is invalid, then axios interceptor will clear local storage
        // and redirect to login screen as server will return 401 unauthorized
        localStorage.setItem(ACCESS_TOKEN, accessToken);
        localStorage.setItem(REFRESH_TOKEN, url.searchParams.get(REFRESH_TOKEN_IFRAME_PARAM) || "");
        localStorage.setItem(ACCESS_TOKEN_EXPIRY_TIME,
          url.searchParams.get(ACCESS_TOKEN_EXPIRY_TIME_IFRAME_PARAM) || "");
      }
    }
  };

  useEffect(() => {
    setAuthTokenIfExist();

    if (localStorage.getItem(ACCESS_TOKEN)) {
      refreshToken().then(() => getLoginInfo().then(() => {
        const accessTokenExpiryTime = Number(localStorage.getItem(ACCESS_TOKEN_EXPIRY_TIME));
        const refreshIntervalInMs = (accessTokenExpiryTime - SECONDS_BEFORE_REFRESH_INTERVAL > 0
          ? accessTokenExpiryTime - SECONDS_BEFORE_REFRESH_INTERVAL : accessTokenExpiryTime) * 1000;
        const refreshInterval = window.setInterval(() => {
          refreshToken().catch(() => window.clearInterval(refreshInterval));
        }, refreshIntervalInMs);
        return () => window.clearInterval(refreshInterval);
      }).catch(() => {})
        .catch(() => {}));
    } else {
      getLoginInfo().catch(() => {});
    }
  }, []);

  return (
    <ThemeProvider theme={theme}>
      <Routes>
        <Route path="/" index element={<Navigate to="ui/home" replace />} />
        <Route path="ui" index element={<Navigate to="/home" replace />} />
        <Route path="ui/" index element={<Navigate to="home" replace />} />
        <Route path="ui/home" element={<Home />} />
        <Route path={LOGIN_ROUTE} element={<Login />} />
        <Route path={GST_REPORT_PATH} element={<GstinDetails />} />
        <Route path={BANKING_REPORT_PATH} element={<Banking />} />
        <Route path={BANKING_CONFIGURE_PATH} element={<BankingConfiguration />} />
        <Route path={CIBIL_REPORT_PATH} element={<CibilDetails />} />
        <Route path={EMI_MASTER} element={<EmiMaster/>} />
        <Route
          path="*"
          element={<ErrorPage errorMessage="errorPage.invalidUrl" header="errorPage.errorMessageNotFoundHeader"/>}
        />
      </Routes>
    </ThemeProvider>
  );
}
