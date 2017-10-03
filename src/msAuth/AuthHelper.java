package msAuth;

import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import org.springframework.web.util.UriComponentsBuilder;

import dao.ProfessorDAO;

public class AuthHelper {
  private static final String authority = "https://login.microsoftonline.com";
  private static final String authorizeUrl = authority + "/common/oauth2/v2.0/authorize";

  private static String[] scopes = { 
    "openid", 
    "offline_access",
    "profile", 
    "User.Read",
    "Mail.Read",
    "Calendars.ReadWrite",
    "Contacts.Read"
  };

  private static String appId = null;
  private static String appPassword = null;
  private static String redirectUrl = null;
  private static String profID = null;

  private static String getAppId() {
    if (appId == null) {
      try {
        loadConfig();
      } catch (Exception e) {
        return null;
      }
    }
    return appId;
  }
  private static String getAppPassword() {
    if (appPassword == null) {
      try {
        loadConfig();
      } catch (Exception e) {
        return null;
      }
    }
    return appPassword;
  }

  private static String getRedirectUrl() {
    if (redirectUrl == null) {
      try {
        loadConfig();
      } catch (Exception e) {
        return null;
      }
    }
    return redirectUrl;
  }

  private static String getScopes() {
    StringBuilder sb = new StringBuilder();
    for (String scope: scopes) {
      sb.append(scope + " ");
    }
    return sb.toString().trim();
  }

  private static void loadConfig() throws IOException {
   /* String authConfigFile = "auth.properties";
    InputStream authConfigStream = AuthHelper.class.getClassLoader().getResourceAsStream(authConfigFile);

    if (authConfigStream != null) {
      Properties authProps = new Properties();
      try {
        authProps.load(authConfigStream);
        appId = authProps.getProperty("appId");
        appPassword = authProps.getProperty("appPassword");
        redirectUrl = authProps.getProperty("redirectUrl");
      } finally {
        authConfigStream.close();
      }
    }
    else {
      throw new FileNotFoundException("Property file '" + authConfigFile + "' not found in the classpath.");
    }*/
	  
	  // Retrieving professor Access codes from email
	  ProfessorDAO profDAO = new ProfessorDAO();
	  String[] profAccessCodes = profDAO.retrieveProfessorAccessCode(profID);
	  appId = profAccessCodes[0];
	  appPassword = profAccessCodes[1];
	  redirectUrl = profAccessCodes[2];
  }

  public static String getLoginUrl(UUID state, UUID nonce, String profEmailID) {
	
	profID = profEmailID;
    UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromHttpUrl(authorizeUrl);
    urlBuilder.queryParam("client_id", getAppId());
    urlBuilder.queryParam("redirect_uri", getRedirectUrl());
    urlBuilder.queryParam("response_type", "code id_token");
    urlBuilder.queryParam("scope", getScopes());
    urlBuilder.queryParam("state", state);
    urlBuilder.queryParam("nonce", nonce);
    urlBuilder.queryParam("response_mode", "form_post");

    return urlBuilder.toUriString();
  }
  
  public static TokenResponse getTokenFromAuthCode(String authCode, String tenantId) {
	  // Create a logging interceptor to log request and responses
	  HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
	  interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

	  OkHttpClient client = new OkHttpClient.Builder()
	      .addInterceptor(interceptor).build();

	  // Create and configure the Retrofit object
	  Retrofit retrofit = new Retrofit.Builder()
	      .baseUrl(authority)
	      .client(client)
	      .addConverterFactory(JacksonConverterFactory.create())
	      .build();

	  // Generate the token service
	  TokenService tokenService = retrofit.create(TokenService.class);

	  try {
	    return tokenService.getAccessTokenFromAuthCode(tenantId, getAppId(), getAppPassword(), 
	        "authorization_code", authCode, getRedirectUrl()).execute().body();
	  } catch (IOException e) {
	    TokenResponse error = new TokenResponse();
	    error.setError("IOException");
	    error.setErrorDescription(e.getMessage());
	    return error;
	  }
	}
  
  public static TokenResponse ensureTokens(TokenResponse tokens, String tenantId) {
	  // Are tokens still valid?
	  Calendar now = Calendar.getInstance();
	  if (now.getTime().before(tokens.getExpirationTime())) {
	    // Still valid, return them as-is
	    return tokens;
	  }
	  else {
	    // Expired, refresh the tokens
	    // Create a logging interceptor to log request and responses
	    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
	    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

	    OkHttpClient client = new OkHttpClient.Builder()
	        .addInterceptor(interceptor).build();

	    // Create and configure the Retrofit object
	    Retrofit retrofit = new Retrofit.Builder()
	        .baseUrl(authority)
	        .client(client)
	        .addConverterFactory(JacksonConverterFactory.create())
	        .build();

	    // Generate the token service
	    TokenService tokenService = retrofit.create(TokenService.class);

	    try {
	      return tokenService.getAccessTokenFromRefreshToken(tenantId, getAppId(), getAppPassword(), 
	          "refresh_token", tokens.getRefreshToken(), getRedirectUrl()).execute().body();
	    } catch (IOException e) {
	      TokenResponse error = new TokenResponse();
	      error.setError("IOException");
	      error.setErrorDescription(e.getMessage());
	      return error;
	    }
	  }
	}
  
}
