import axiosClient, {AxiosError} from "axios";
import {ACCESS_TOKEN, ACCESS_TOKEN_EXPIRY_TIME, BACKEND_URL_PREFIX, REFRESH_TOKEN} from "utils/constants/constants";

export const axios = axiosClient.create({
  baseURL: BACKEND_URL_PREFIX,
});

function removeAccessTokenInLocalStorage() {
  localStorage.removeItem(ACCESS_TOKEN);
  localStorage.removeItem(REFRESH_TOKEN);
  localStorage.removeItem(ACCESS_TOKEN_EXPIRY_TIME);
}

axios.interceptors.request.use(config => {
  if (config?.url && config?.url !== "/auth/authorize") {
    const token = localStorage.getItem(ACCESS_TOKEN);
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

axios.interceptors.response.use(response => response,(error: AxiosError) => {
  if (error?.response?.status === 401) {
    removeAccessTokenInLocalStorage();
    const redirect = window.location.href;
    if (!redirect.includes("redirect") && !window.location.href.includes("login")) {
      window.location.href = "/ui/login?redirect=" + encodeURIComponent(redirect);
    }
  }
  throw error;
});
