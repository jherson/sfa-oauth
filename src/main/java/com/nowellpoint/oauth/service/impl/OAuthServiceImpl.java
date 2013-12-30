package com.nowellpoint.oauth.service.impl;

import java.io.Serializable;

import javax.security.auth.login.LoginException;
//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.Entity;
//import javax.ws.rs.client.WebTarget;
//import javax.ws.rs.core.Form;
//import javax.ws.rs.core.Response;

import org.jboss.resteasy.client.ClientRequest;
import org.jboss.resteasy.client.ClientResponse;

import com.nowellpoint.oauth.OAuthConstants;
import com.nowellpoint.oauth.model.Identity;
import com.nowellpoint.oauth.model.OrganizationInfo;
import com.nowellpoint.oauth.model.Token;
import com.nowellpoint.oauth.model.UserInfo;
import com.nowellpoint.oauth.service.OAuthService;

public class OAuthServiceImpl implements OAuthService, Serializable {
	
	private static final long serialVersionUID = 1819521597953621629L;
	
	private static final String API_VERSION = "29.0";
	
	private static final String USER_FIELDS = "Id,Username,LastName,FirstName,Name,CompanyName,Division,Department," +
			"Title,Street,City,State,PostalCode,Country,Latitude,Longitude," +
			"Email,SenderEmail,SenderName,Signature,Phone,Fax,MobilePhone,Alias," +
			"CommunityNickname,IsActive,TimeZoneSidKey,UserRole.Id,UserRole.Name,LocaleSidKey," +
			"EmailEncodingKey,Profile.Id,Profile.Name,Profile.PermissionsCustomizeApplication," +
			"UserType,LanguageLocaleKey,EmployeeNumber,DelegatedApproverId,ManagerId,AboutMe";
		
	private static final String ORGANIZATION_FIELDS = "Id,Name,Division,Street,City,State,PostalCode,Country," +
			"PrimaryContact,DefaultLocaleSidKey,LanguageLocaleKey,FiscalYearStartMonth";
        
    @Override
    public Token authorize(String tokenUrl, String clientId, String clientSecret, String username, String password, String securityToken) throws LoginException {
        ClientRequest request = new ClientRequest(tokenUrl);
        request.header("Content-Type", "application/x-www-form-urlencoded");
        request.queryParameter(OAuthConstants.GRANT_TYPE_PARAMETER, OAuthConstants.PASSWORD_GRANT_TYPE);                
        request.queryParameter(OAuthConstants.CLIENT_ID_PARAMETER, clientId);
        request.queryParameter(OAuthConstants.CLIENT_SECRET_PARAMETER, clientSecret);
        request.queryParameter(OAuthConstants.USERNAME_PARAMETER, username);
        request.queryParameter(OAuthConstants.PASSWORD_PARAMETER, password + (securityToken != null ? securityToken : ""));
            
        ClientResponse<Token> response = null;
        try {
        	response = request.post(Token.class);
        } catch (Exception e) {
            throw new LoginException(e.getMessage());
        } finally {
            request.clear();
        }
        
        return response.getEntity();
    }
    
    @Override
    public Token authorize(String tokenUrl, String clientId, String clientSecret, String redirectUri, String code) throws LoginException {
    	ClientRequest request = new ClientRequest(tokenUrl);
        request.header("Content-Type", "application/x-www-form-urlencoded");        
        request.queryParameter(OAuthConstants.GRANT_TYPE_PARAMETER, OAuthConstants.AUTHORIZATION_GRANT_TYPE);                
        request.queryParameter(OAuthConstants.CLIENT_ID_PARAMETER, clientId);
        request.queryParameter(OAuthConstants.CLIENT_SECRET_PARAMETER, clientSecret);
        request.queryParameter(OAuthConstants.REDIRECT_URI_PARAMETER, redirectUri);
        request.queryParameter(OAuthConstants.CODE_PARAMETER, code);
        
        ClientResponse<Token> response = null;
        try {
        	response = request.post(Token.class);
        } catch (Exception e) {
            throw new LoginException(e.getMessage());
        } finally {
            request.clear();
        }
        
        return response.getEntity();
    }

    @Override
    public Identity getIdentity(String identityUrl, String accessToken) throws LoginException {
    	ClientRequest request = new ClientRequest(identityUrl);
        request.header("Content-Type", "application/x-www-form-urlencoded");
        request.queryParameter(OAuthConstants.OAUTH_TOKEN_PARAMETER, accessToken);
        request.followRedirects(Boolean.TRUE);
        
        ClientResponse<Identity> response = null;
        try {
            response = request.get(Identity.class);
        } catch (Exception e) {
            throw new LoginException(e.getMessage());
        } finally {
            request.clear();
        }
            
        return response.getEntity();        
    }

    @Override
    public void revokeToken(String revokeUrl, String accessToken) throws LoginException {
        ClientRequest request = new ClientRequest(revokeUrl);
        request.header("Content-Type", "application/x-www-form-urlencoded");
        request.queryParameter(OAuthConstants.TOKEN_PARAMETER, accessToken);

        try {
        	request.post();
        } catch (Exception e) {
        	throw new LoginException(e.getMessage());
        } finally {
        	request.clear();
        }
    }

    @Override
    public Token refreshToken(String tokenUrl, String clientId, String clientSecret, String accessToken) throws LoginException {        
        ClientRequest request = new ClientRequest(tokenUrl);
        request.header("Content-Type", "application/x-www-form-urlencoded");
        request.queryParameter(OAuthConstants.GRANT_TYPE_PARAMETER, OAuthConstants.REFRESH_GRANT_TYPE);                
        request.queryParameter(OAuthConstants.CLIENT_ID_PARAMETER, clientId);
        request.queryParameter(OAuthConstants.CLIENT_SECRET_PARAMETER, clientSecret);
        request.queryParameter(OAuthConstants.REFRESH_GRANT_TYPE, accessToken);
        
        ClientResponse<Token> response = null;
        try {
        	response = request.post(Token.class);
        } catch (Exception e) {
            throw new LoginException(e.getMessage());
        } finally {
            request.clear();
        }
        
        return response.getEntity();
    }
    
    @Override
    public UserInfo getUserInfo(Token token, Identity identity) throws LoginException {
    	
    	String url = new StringBuilder().append(getSObjectUrl(identity))
				.append("User/")
				.append(identity.getUserId())
				.append("?fields=")
				.append(USER_FIELDS)
				.toString();
    	
    	ClientRequest request = new ClientRequest(url);
		request.header("Content-type", "application/x-www-form-urlencoded");
		request.header("Authorization", "OAuth " + token.getAccessToken());
		
		ClientResponse<UserInfo> response = null;
		try {
			response = request.get(UserInfo.class);
		} catch (Exception e) {
			throw new LoginException(e.getMessage());
		} finally {
			request.clear();
		}
		
		return response.getEntity();
    }
    
    @Override
    public OrganizationInfo getOrganizationInfo(Token token, Identity identity) throws LoginException {
    	
    	String url = new StringBuilder().append(getSObjectUrl(identity))
				.append("Organization/")
				.append(identity.getOrganizationId())
				.append("?fields=")
				.append(ORGANIZATION_FIELDS)
				.toString();
    	
    	ClientRequest request = new ClientRequest(url);
		request.header("Content-type", "application/x-www-form-urlencoded");
		request.header("Authorization", "OAuth " + token.getAccessToken());
		
		ClientResponse<OrganizationInfo> response = null;
		try {
			response = request.get(OrganizationInfo.class);
		} catch (Exception e) {
			throw new LoginException(e.getMessage());
		} finally {
			request.clear();
		}
		
		return response.getEntity();
    }
    
    private String getSObjectUrl(Identity identity) {
    	return identity.getUrls().getSObjects().replace("{version}", API_VERSION);
    }
}

//import java.io.Serializable;
//
//import javax.security.auth.login.LoginException;
//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.Entity;
//import javax.ws.rs.client.WebTarget;
//import javax.ws.rs.core.Form;
//import javax.ws.rs.core.Response;

//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.WebTarget;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.core.Response.Status;
//
//import com.nowellpoint.oauth.OAuthConstants;
//import com.nowellpoint.oauth.service.OAuthService;
//
//public class OAuthServiceImpl implements OAuthService, Serializable {
//
//	private static final long serialVersionUID = 1819521597953621629L;
//	
//	@Override
//	public String getAuthResponse(String tokenUrl, String clientId, String clientSecret, String username, String password, String securityToken) throws LoginException {	
//		Client client = ClientBuilder.newClient();
//        WebTarget target = client.target(tokenUrl);
//        Response response = target
//        		.queryParam(OAuthConstants.GRANT_TYPE_PARAMETER, OAuthConstants.PASSWORD_GRANT_TYPE)
//				.queryParam(OAuthConstants.CLIENT_ID_PARAMETER, clientId)
//				.queryParam(OAuthConstants.CLIENT_SECRET_PARAMETER, clientSecret)
//				.queryParam(OAuthConstants.USERNAME_PARAMETER, username)
//				.queryParam(OAuthConstants.PASSWORD_PARAMETER, password + securityToken)
//				.request()
//				.header("Content-Type", "application/x-www-form-urlencoded")
//        		.post(null);
//        
//        String authResponse = null;
//        
//        System.out.println("response: " + response.getStatusInfo());
//        
//        if (response.getStatusInfo() == Status.OK) {
//        	authResponse = response.readEntity(String.class);
//        } else {        	
//        	System.out.println(response.readEntity(String.class));
//        }
//        
//        response.close();
//        
//        return authResponse;
//	}
//	
//	@Override
//	public String getAuthResponse(String tokenUrl, String clientId, String clientSecret, String redirectUri, String code) throws LoginException {
//		Client client = ClientBuilder.newClient();
//        WebTarget target = client.target(tokenUrl);
//        Response response = target
//        		.queryParam(OAuthConstants.GRANT_TYPE_PARAMETER, OAuthConstants.AUTHORIZATION_GRANT_TYPE)
//				.queryParam(OAuthConstants.CLIENT_ID_PARAMETER, clientId)
//				.queryParam(OAuthConstants.CLIENT_SECRET_PARAMETER, clientSecret)
//				.queryParam(OAuthConstants.REDIRECT_URI_PARAMETER, redirectUri)
//				.request()
//				.header("Content-Type", "application/x-www-form-urlencoded")
//        		.post(null);
//        
//        String authResponse = null;
//        
//        if (response.getStatusInfo() == Status.OK) {
//        	authResponse = response.readEntity(String.class);
//        }
//        
//        response.close();
//        
//        return authResponse;
//	}
//
//	@Override
//	public String getIdentity(String identityUrl, String accessToken) throws LoginException {	
//		Client client = ClientBuilder.newClient();
//        WebTarget target = client.target(identityUrl);
//        Response response = target
//        		.queryParam(OAuthConstants.OAUTH_TOKEN_PARAMETER, accessToken)
//        		.request()
//				.header("Content-Type", "application/x-www-form-urlencoded")
//				.post(null);
//        
//        String identity = null;
//        
//        if (response.getStatusInfo() == Status.OK) {
//        	identity = response.readEntity(String.class);
//        }
//        
//        response.close();
//        
//        return identity;
//	}
//
//	@Override
//	public void revokeToken(String revokeUrl, String accessToken) throws LoginException {
//		Client client = ClientBuilder.newClient();
//        WebTarget target = client.target(revokeUrl);
//        Response response = target
//        		.queryParam(OAuthConstants.TOKEN_PARAMETER, accessToken)
//        		.request()
//				.header("Content-Type", "application/x-www-form-urlencoded")
//				.post(null);
//        
//        response.close();        
//	}
//
//	@Override
//	public String refreshToken(String tokenUrl, String clientId, String clientSecret, String accessToken) throws LoginException {   
//		Client client = ClientBuilder.newClient();
//        WebTarget target = client.target(tokenUrl);
//        Response response = target
//        		.queryParam(OAuthConstants.GRANT_TYPE_PARAMETER, OAuthConstants.REFRESH_GRANT_TYPE)
//				.queryParam(OAuthConstants.CLIENT_ID_PARAMETER, clientId)
//				.queryParam(OAuthConstants.CLIENT_SECRET_PARAMETER, clientSecret)
//				.queryParam(OAuthConstants.REFRESH_GRANT_TYPE, accessToken)
//				.request()
//				.header("Content-Type", "application/x-www-form-urlencoded")
//        		.post(null);
//        
//        String refreshToken = null;
//        
//        if (response.getStatusInfo() == Status.OK) {
//        	refreshToken = response.readEntity(String.class);
//        }
//        
//        response.close();
//        
//        return refreshToken;
//	}
//}
