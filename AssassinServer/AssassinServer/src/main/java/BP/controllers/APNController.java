package BP.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import BP.events.ApnManager;
import BP.events.PushNotificationInterface;

import org.json.*;

public class APNController {

	private static final String kKey = "";
	private static final String kSecret = "";
	private static final String kBaseUrl = "https://go.urbanairship.com/api/push/";

	ApnManager apnManager;

	public APNController() {
		this.apnManager = new ApnManager(kKey, kSecret, kBaseUrl);
	}

	public void sendNotification(PushNotificationInterface notification,
			String message) throws JSONException {
		ArrayList<HashMap<String, String>> input = notification
				.getNotificationObject();
		JSONObject jsonPayload = new JSONObject();
		JSONArray iOSDeviceTokens = new JSONArray();
		JSONArray AndroidDeviceTokens = new JSONArray();

		jsonPayload.put("device_types", "all");
		jsonPayload.put("audience", new JSONObject());

		for (HashMap<String, String> hashMap : input) {
			if (hashMap.get("platformId").equalsIgnoreCase("ios")) {
				iOSDeviceTokens.put(hashMap.get("apn"));
			} else {
				AndroidDeviceTokens.put(hashMap.get("apn"));
			}
		}
		if (iOSDeviceTokens.length() > 0) {
			if (iOSDeviceTokens.length() == 1) {
				jsonPayload.getJSONObject("audience").put("device_token",
						iOSDeviceTokens.getString(0));
			} else {
				jsonPayload.getJSONObject("audience").put("AND", new JSONArray());
				for (int i = 0; i < iOSDeviceTokens.length(); i++) {
					String apn = iOSDeviceTokens.getString(i);
					jsonPayload.getJSONObject("audience").getJSONArray("AND").put(new JSONObject().put("device_token", apn));
				}
			}
		} else {
			if (AndroidDeviceTokens.length() == 1) {
				jsonPayload.getJSONObject("audience").put("apid",
						AndroidDeviceTokens.getString(0));
			} else {
				jsonPayload.getJSONObject("audience").put("AND", new JSONArray());
				for (int i = 0; i < AndroidDeviceTokens.length(); i++) {
					String apn = AndroidDeviceTokens.getString(i);
					jsonPayload.getJSONObject("audience").getJSONArray("AND").put(new JSONObject().put("apid", apn));
				}
			}
		}
		jsonPayload.put("notification", new JSONObject().put("alert", message));

		this.apnManager.sendPush(jsonPayload);
	}

}
