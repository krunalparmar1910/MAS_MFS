import React from "react";
import Container from "@mui/material/Container";
import Typography from "@mui/material/Typography";
import {List, ListItem} from "@mui/material";
import "styles/errorPage.scss";
import {useTranslation} from "react-i18next";

interface ErrorPageProps {
  errorMessage: string;
  errorPoints?: string[];
  header: string;
}

export function ErrorPage(props: ErrorPageProps) {
  const {t} = useTranslation();

  return (
    <Container className="error-page" maxWidth="md">
      <Typography variant="h4" component="h1" gutterBottom>
        {t(props.header)}
      </Typography>
      <Typography variant="h6">
        {t(props.errorMessage)}
      </Typography>
      {props.errorPoints &&
        <div className="error-points">
          <List className="list-container" dense disablePadding>
            {props.errorPoints?.map((err, index) => {
              return (
                <ListItem className="list-item" alignItems="center" disableGutters disablePadding key={index}>
                  <Typography variant="subtitle2">
                    {t(err)}
                  </Typography>
                </ListItem>
              );
            })}
          </List>
        </div>
      }
    </Container>
  );
}
