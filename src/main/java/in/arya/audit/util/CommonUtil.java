package in.arya.audit.util;

import in.arya.audit.model.User;
import org.apache.commons.math3.util.Precision;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import java.lang.reflect.Field;
import java.util.Base64;
import java.util.Map;


public class CommonUtil {

	public static String createBasicAuthorization(String user, String password) {
		String notEncoded = user + ":" + password;
		String encodedAuth = "Basic " + Base64.getEncoder().encodeToString(notEncoded.getBytes());
		return encodedAuth;
	}

	@SuppressWarnings("unchecked")
	public static User getOauthUser(OAuth2Authentication auth) {
		User user =  new User();
		String grantType = (String) ((Map<String,Object>)((Map<String,Object>)((Map<String,Object>)auth.getUserAuthentication().getDetails()).get("oauth2Request")).get("requestParameters")).get("grant_type");
		if(grantType.equals("password")) {
			user.setId(((Number) ((Map<String,Object>)((Map<String,Object>)auth.getUserAuthentication().getDetails()).get("details")).get("id")).longValue());
			user.setFirstName((String) ((Map<String,Object>)((Map<String,Object>)auth.getUserAuthentication().getDetails()).get("details")).get("first_name"));
			user.setLastName((String) ((Map<String,Object>)((Map<String,Object>)auth.getUserAuthentication().getDetails()).get("details")).get("last_name"));
		} else {
			user.setFirstName((String) ((Map<String,Object>)((Map<String,Object>)auth.getUserAuthentication().getDetails()).get("oauth2Request")).get("clientId"));
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	public static String getClientId(OAuth2Authentication auth){
		String clientId = null;
		if( auth != null ) {
			try {
				clientId = (String) ((Map<String,Object>)((Map<String,Object>)auth.getUserAuthentication().getDetails()).get("oauth2Request")).get("clientId");
			} catch( Exception e ) {
				e.printStackTrace();
			}
		}
		return clientId;
	}
	
	public static HttpHeaders createBasicHttpHeaders(String user, String password) {
		String encodedAuth = createBasicAuthorization(user, password);
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", encodedAuth);
		return headers;
	}


	public static Object parseValue(Object o, Field field, int scale) throws IllegalAccessException {
		String fieldTypeName = field.getType().getSimpleName();
		switch (fieldTypeName) {
			case "Integer":
				throw new IllegalArgumentException("annotation not support Integer field");
			case "Double":
				return Precision.round(field.getDouble(o), scale);
			case "Float":
				return Precision.round(field.getFloat(o), scale);
			default:
				return field.get(o);
		}
	}

	public static String formatString(Object o, Field field, String caseType) throws IllegalAccessException {
		switch (caseType) {
			case CaseFormat.ALL_LOWER:
				return field.get(o).toString().toLowerCase();
		}
		return null;
	}
}
