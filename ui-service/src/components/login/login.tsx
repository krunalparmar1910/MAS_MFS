import React, {useState} from "react";
import {getToken} from "utils/api/request";
import {Box, Button, Container, FormControl, TextField, Typography} from "@mui/material";
import "styles/login.scss";
import {useTranslation} from "react-i18next";
import {isInIFrame} from "app";

export function Login() {
  const [username, setUsername] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [error, setError] = useState<string | null>(isInIFrame() ? "login.noAccessTokenProvided" : null);
  const {t} = useTranslation();

  const onSubmit = (event?: React.MouseEvent) => {
    event?.preventDefault();
    getToken(username, password)
      .then(() => setError(null))
      .catch(() => setError("login.invalidCredentials"));
  };

  const handleKeyDown = (event: React.KeyboardEvent) => {
    if (event.key === "Enter") {
      onSubmit();
    }
  };

  return (
    <Container maxWidth="xs">
      <Box className="login-container">
        <Typography component="h3" variant="h3" className="login-header">{t("login.header")}</Typography>
        <FormControl className="login-form">
          <TextField
            variant="outlined"
            placeholder={t("login.username")}
            type="text"
            onChange={(event) => setUsername(event.target.value)}
            onKeyDown={handleKeyDown}
          />
          <TextField
            variant="outlined"
            placeholder={t("login.password")}
            type="password"
            onChange={(event) => setPassword(event.target.value)}
            onKeyDown={handleKeyDown}
          />
        </FormControl>
        <Button type="submit" onClick={onSubmit} variant="contained">{t("login.login")}</Button>
      </Box>
      {error && <Typography className="login-error">{t(error)}</Typography>}
    </Container>
  );
}
