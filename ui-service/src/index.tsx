import React from "react";
import ReactDOM from "react-dom/client";
import {App} from "app";
import {I18nextProvider} from "react-i18next";
import i18n from "utils/i18n";
import {BrowserRouter} from "react-router-dom";

const root = ReactDOM.createRoot(document.getElementById("root") as HTMLElement);

root.render(
  <React.StrictMode>
    <BrowserRouter>
      <I18nextProvider i18n={i18n}>
        <App />
      </I18nextProvider>
    </BrowserRouter>
  </React.StrictMode>,
);
