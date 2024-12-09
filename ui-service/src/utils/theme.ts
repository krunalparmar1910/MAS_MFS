import {createTheme} from "@mui/material";
import {
  themeColors,
  font,
  border,
  borderRadius,
} from "utils/constants/themeConstants";

const typographyOverrides = {
  color: themeColors.primary,
};

const theme = createTheme({
  palette: {
    mode: "light",
    primary: {
      main: themeColors.primary,
      contrastText: themeColors.primaryContrast,
    },
    secondary: {
      main: themeColors.secondary,
      contrastText: themeColors.secondaryContrast,
    },
  },
  typography: {
    fontFamily: font.family,
    fontSize: font.size,
  },
  components: {
    MuiAppBar: {
      styleOverrides: {
        root: {
          padding: "0 10px",
        },
      },
    },
    MuiAccordionSummary: {
      styleOverrides: {
        root: {
          backgroundColor: themeColors.secondary,
          ".MuiAccordionSummary-content": {
            margin: "0",
          },
          ".MuiAccordionSummary-content.Mui-expanded": {
            margin: "0",
          },
          "&.Mui-expanded": {
            minHeight: "revert",
          },
        },
      },
    },
    MuiTypography: {
      styleOverrides: {
        root: {
          margin: "0.35em 0",
        },
        h1: typographyOverrides,
        h2: typographyOverrides,
        h3: typographyOverrides,
        h4: typographyOverrides,
        h5: typographyOverrides,
        h6: typographyOverrides,
        subtitle2: typographyOverrides,
      },
    },
    MuiTableContainer: {
      styleOverrides: {
        root: {
          boxShadow: "none",
          border: border,
          borderCollapse: "separate",
        },
      },
    },
    MuiTableHead: {
      styleOverrides: {
        root: {
          textTransform: "uppercase",
          "tr": {
            "th": {
              color: themeColors.tableCell,
              backgroundColor: themeColors.bgGrey,
              fontWeight: "bold",
              fontSize: font.tableHeaderFontSize,
              letterSpacing: "0.5px",
            },
          },
        },
      },
    },
    MuiTableCell: {
      styleOverrides: {
        root: {
          border: border,
          borderCollapse: "separate",
          padding: "6px 9px",
          color: themeColors.tableCell,
          "&.sticky-column": {
            position: "sticky",
            borderRight: "none",
            backgroundColor: themeColors.bgGrey,
            minWidth: "100px",
            border: border,
            borderCollapse: "separate",
            "&::before": {
              borderCollapse: "separate",
              border: border,
            },
            "&::after": {
              borderCollapse: "separate",
              border: border,
            },
          },
          "&.sticky-column:nth-child(1)": {
            left: "0",
            minWidth: "200px",
          },
          "&.sticky-column:nth-child(2)": {
            left: "219px",
          },
          "&.sticky-column:nth-child(3)": {
            left: "360px",
          },
          "&.sticky-column:nth-child(4)": {
            left: "510px",
          },
          "&.sticky-column:nth-child(5)": {
            left: "650px",
            minWidth: "200px",
          },
        },
      },
    },
    MuiCheckbox: {
      styleOverrides: {
        root: {
          padding: "0",
        },
      },
    },
    MuiTableBody: {
      styleOverrides: {
        root: {
          color: themeColors.tableCell,
        },
      },
    },
    MuiButton: {
      styleOverrides: {
        root: {
          textTransform: "none",
        },
      },
    },
    MuiInputBase: {
      styleOverrides: {
        root: {
          backgroundColor: themeColors.bgGrey,
          borderRadius: borderRadius,
          ".MuiInputBase-input": {
            textAlign: "center",
            fontFamily: font.family,
            fontSize: font.size,
            color: themeColors.tableCell,
          },
        },
      },
    },
    MuiListItem: {
      styleOverrides: {
        root: {
          "::marker": {
            color: themeColors.primary,
          },
        },
      },
    },
    MuiChip: {
      styleOverrides: {
        root: {
          borderRadius: borderRadius,
          margin: "5px 3px 5px 0",
        },
      },
    },
  },
});

export default theme;
