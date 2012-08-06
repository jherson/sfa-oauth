package com.redhat.sforce.qb.model.auth;

import java.util.logging.Logger;

import com.redhat.sforce.persistence.EntityManager;
import com.redhat.sforce.persistence.Query;
import com.redhat.sforce.persistence.connection.ConnectionManager;
import com.redhat.sforce.persistence.impl.EntityManagerImpl;
import com.redhat.sforce.qb.exception.QueryException;
import com.redhat.sforce.qb.model.quotebuilder.User;
import com.sforce.ws.ConnectionException;

public class SessionUser extends User {

	private static final long serialVersionUID = -7339108386156929994L;
	
	private static final Logger log = Logger.getLogger(SessionUser.class.getName());
	
	private OAuth oauth;
	
	private Identity identity;
	
	public SessionUser(OAuth oauth, Identity identity) throws QueryException {
		
		User user = queryUser(oauth.getAccessToken(), identity.getUserId());
		
		this.setId(user.getId());
		this.setAlias(user.getAlias());
		this.setCity(user.getCity());
		this.setCompanyName(user.getCompanyName());
		this.setContactId(user.getContactId());
		this.setCountry(user.getCountry());
		this.setCreatedByFirstName(user.getCreatedByFirstName());
		this.setCreatedById(user.getCreatedById());
		this.setCreatedByLastName(user.getCreatedByLastName());
		this.setCreatedByName(user.getCreatedByName());
		this.setCreatedDate(user.getCreatedDate());
		this.setCurrencyIsoCode(user.getCurrencyIsoCode());
		this.setDateFormatPattern(user.getDateFormatPattern());
		this.setDateTimeFormatPattern(user.getDateTimeFormatPattern());
		this.setDefaultCurrencyIsoCode(user.getDefaultCurrencyIsoCode());
		this.setDepartment(user.getDepartment());
		this.setDivision(user.getDivision());
		this.setEmail(user.getEmail());
		this.setExtension(user.getExtension());
		this.setFax(user.getFax());
		this.setFirstName(user.getFirstName());
		this.setFullPhotoUrl(user.getFullPhotoUrl());
		this.setLastModifiedByFirstName(user.getLastModifiedByFirstName());
		this.setLastModifiedById(user.getLastModifiedById());
		this.setLastModifiedByLastName(user.getLastModifiedByLastName());
		this.setLastModifiedByName(user.getLastModifiedByName());
		this.setLastModifiedDate(user.getLastModifiedDate());
		this.setLastName(user.getLastName());
		this.setLocale(user.getLocale());
		this.setMobilePhone(user.getMobilePhone());
		this.setName(user.getName());
		this.setPhone(user.getPhone());
		this.setPostalCode(user.getPostalCode());
		this.setProfileName(user.getProfileName());
		this.setRegion(user.getRegion());
		this.setRoleName(user.getRoleName());
		this.setSmallPhotoUrl(user.getSmallPhotoUrl());
		this.setState(user.getState());
		this.setStreet(user.getStreet());
		this.setTimeZone(user.getTimeZone());
		this.setTitle(user.getTitle());
		this.setUserName(user.getUserName());
		this.setOAuth(oauth);
		this.setIdentity(identity);
	}
	
	private User queryUser(String accessToken, String userId) throws QueryException {
		String queryString = "Select " +
				"Id, " +
				"Username, " +
				"LastName, " +
				"FirstName, " +
				"Name, " +
				"CompanyName, " +
				"Division, " +
				"Department, " +
				"Title, " +
				"Street, " +
				"City, " +
				"State, " +
				"PostalCode, " +
				"Country, " +
				"TimeZoneSidKey, " +
				"Email, " +
				"Phone, " +
				"Fax, " +
				"MobilePhone, " +
				"Alias, " +
				"DefaultCurrencyIsoCode, " +
				"Extension, " +
				"LocaleSidKey, " +
				"FullPhotoUrl, " +
				"SmallPhotoUrl, " +
				"Region__c, " +
				"UserRole.Name, " +
				"Profile.Name " +
				"From  User Where Id = ':userId'";
		
		EntityManager em = new EntityManagerImpl();
		
        try {
			
			ConnectionManager.openConnection(accessToken);
			Query q = em.createQuery(queryString);
			q.addParameter("userId", userId);
			User user = q.getSingleResult();
			return user;
			
        } catch (ConnectionException e) {
        	
			throw new QueryException("ConnectionException", e);
			
		} finally {
			
			try {
				ConnectionManager.closeConnection();
			} catch (ConnectionException e) {
				log.info(e.getMessage());
			}
		}						
	}

	public OAuth getOAuth() {
		return oauth;
	}

	public void setOAuth(OAuth oauth) {
		this.oauth = oauth;
	}	
	
	public Identity getIdentity() {
		return identity;
	}
	
	public void setIdentity(Identity identity) {
		this.identity = identity;
	}
}