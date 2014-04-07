package BP.events;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import org.json.*;
import org.apache.commons.codec.binary.Base64;

import com.fasterxml.jackson.core.io.JsonStringEncoder;
import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;


public class ApnManager {

	private String key;
	private String secret;
	private String urlBase;
	private Base64 base64;
	
	public ApnManager(String _key, String _secret, String _urlBase) {
		this.key = _key;
		this.secret = _secret;
		this.urlBase = _urlBase;
		this.base64 = new Base64();
	}
	
	
	public void sendPush(JSONObject apnInfo) {
		try {
		URL url = new URL(urlBase);
		String nameAndPassword = key+":"+secret;
		String authorizationString = "Basic " + base64.encode(nameAndPassword.getBytes());
		 
		HTTPRequest request = new HTTPRequest(url, HTTPMethod.POST);
		request.addHeader(new HTTPHeader("Authorization", authorizationString));
		request.addHeader(new HTTPHeader("Content-Type", "application/json"));
		 
		String jsonPayload = apnInfo.toString();
		request.setPayload(jsonPayload.getBytes());
		URLFetchService urlFetchService = URLFetchServiceFactory.getURLFetchService();
		HTTPResponse fetchedResponse = urlFetchService.fetch(request);
		if (fetchedResponse.getResponseCode() != 200) {
		    // something went wrong...
			// do some sort of logging...
			throw new RuntimeException("APNs failed. code: " + fetchedResponse.getResponseCode());
		}
		} catch (IOException except) {
			throw new RuntimeException("APNs failed (IOException): " + except.toString());
		}
		throw new RuntimeException("APN should've hit");
	}
	
}
