package com.redhat.sforce.qb.servlet;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.jboss.logging.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.redhat.sforce.qb.bean.QuotebuilderProperties;

@WebServlet("/authorize")
public class AuthorizeServlet extends HttpServlet {

	private static final long serialVersionUID = -3102834362805354464L;

	@Inject 
	QuotebuilderProperties properties;	
	
	@Inject
	Logger log;
       
    public AuthorizeServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String sessionId = (String) request.getParameter("sessionId");
		
		log.info("sessionId: " + sessionId);
		
        if (sessionId == null) {
        	
        	String code = request.getParameter("code");

            if (code == null) {
            	
            	String authUrl = null;
        		try {
        			authUrl = properties.getEnvironment()
        			        + "/services/oauth2/authorize?response_type=code&client_id="
        			        + properties.getClientId() + "&redirect_uri="
        			        + URLEncoder.encode(request.getRequestURL().toString(), "UTF-8");
        			
        			log.info("callback url: " + request.getRequestURL().toString());
        			
        			response.sendRedirect(authUrl);
        			return;
        			
        		} catch (UnsupportedEncodingException e) {
                    log.error("UnsupportedEncodingException", e);
        		}
        		
            } else {
                            	
            	String tokenUrl = properties.getEnvironment() + "/services/oauth2/token";
            	            	
            	PostMethod postMethod = new PostMethod(tokenUrl);
                postMethod.addParameter("code", code);
                postMethod.addParameter("grant_type", "authorization_code");
                postMethod.addParameter("client_id", properties.getClientId());
                postMethod.addParameter("client_secret", properties.getClientSecret());
                postMethod.addParameter("redirect_uri", request.getRequestURL().toString());

                HttpClient httpClient = new HttpClient();
                httpClient.getParams().setSoTimeout(60000);

                try {
                    httpClient.executeMethod(postMethod);
                    JSONObject authResponse = new JSONObject(new JSONTokener(new InputStreamReader(postMethod.getResponseBodyAsStream())));                    
                    sessionId = authResponse.getString("access_token");
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }              	
                
            }
        }

        response.sendRedirect(request.getContextPath() + "/index.jsf?sessionId=" + sessionId + "&opportunityId=006P0000003U4G1");

	}
}