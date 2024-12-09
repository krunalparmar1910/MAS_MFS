import i18next from "i18next";
import LanguageDetector from "i18next-browser-languagedetector";
import {initReactI18next} from "react-i18next";
import {en_US} from "languages/en_US";

i18next
  // detect user language
  .use(LanguageDetector)
  // pass the i18n instance to react-i18next.
  .use(initReactI18next)
  // init i18next
  .init({
    resources: {
      en_US,
    },
    lng: "en_US",
    fallbackLng: "en_US",
    interpolation: {
      escapeValue: false, // not needed for react as it escapes by default
    },
  });

export default i18next;
