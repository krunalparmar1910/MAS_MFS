export interface AuthorizeUserRequestDTO {
  username: string;
  password: string;
  clientId: string;
}

export interface AccessTokenResponse {
  access_token: string;
  expires_in: number;
  refresh_token: string;
}

export interface RefreshTokenRequestDTO {
  clientId: string;
  refreshToken: string;
}
