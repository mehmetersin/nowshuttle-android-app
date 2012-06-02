package com.mesoft.mobile.android.nowshuttle.om;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.SharedPreferences;

import com.mesoft.mobile.android.nowshuttle.Constants;

public class DataProvider {

	private static String mainUrl = "http://64.79.104.166:8080/NowShuttleWebApi/operations/";

	public static boolean checkUser(String username, String password) {
		String URL = mainUrl + "checkUser";
		Map prm = new HashMap<String, String>();
		prm.put("email", username);
		prm.put("password", password);
		return postRequest(URL, prm);
	}

	public static boolean signUp(String username) {
		String URL = mainUrl + "signup";
		Map prm = new HashMap<String, String>();
		prm.put("email", username);
		return postRequest(URL, prm);
	}

	public static void persistUser(String userId, SharedPreferences settings) {

		String pUserId = settings.getString(Constants.PREF_USER_ID, null);
		if (pUserId == null) {
			SharedPreferences.Editor editor = settings.edit();
			editor.putString(Constants.PREF_USER_ID, userId);
			editor.commit();
		}
	}

	public static boolean checkUserInSystem(SharedPreferences settings) {
		String pUserId = settings.getString(Constants.PREF_USER_ID, null);
		if (pUserId == null) {
			return false;
		}
		return true;
	}

	public static boolean updateUserDisplayName(String userId, String displayName) {
		
		String URL = mainUrl + "updateUser";
		Map prm = new HashMap<String, String>();
		prm.put("userId", userId);
		prm.put("displayName",displayName );
		return postRequest(URL, prm);
	}

	public static List<Shuttle> getShuttleList() {
		String URL = mainUrl + "getShuttles";
		String result = getRequest(URL, new HashMap<String, String>());
		List<Shuttle> list = new ArrayList<Shuttle>();
		try {
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				Shuttle sh = new Shuttle();
				JSONObject e = jArray.getJSONObject(i);
				sh.setName(e.getString("name"));
				sh.setId(e.getString("id"));
				list.add(sh);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static boolean informTransferStatus(String shuttleId, String userId,
			String state,String date) {
		String URL = mainUrl + "informTransferStatus";
		Map prm = new HashMap<String, String>();
		prm.put("shuttleId", shuttleId);
		prm.put("userId", userId);
		prm.put("state", state);
		prm.put("date", date);
		return postRequest(URL, prm);
	}

	public static List<TransferStatus> getTodayTransferList(String shuttleId,String date) {

		String URL = mainUrl + "getTransferList";
		HashMap<String, String> prms = new HashMap<String, String>();
		prms.put("shuttleId", shuttleId);
		prms.put("date", date);
		
		String result = getRequest(URL, prms);
		List<TransferStatus> list = new ArrayList<TransferStatus>();
		try {
			JSONArray jArray = new JSONArray(result);
			for (int i = 0; i < jArray.length(); i++) {
				TransferStatus sh = new TransferStatus();
				JSONObject e = jArray.getJSONObject(i);
				sh.setState(e.getString("state"));

				JSONObject u = e.getJSONObject("user");

				User user = new User();
				user.setDisplayName(u.getString("displayName"));
				user.setEmail(u.getString("email"));
				user.setUserId(u.getString("userId"));
				sh.setUser(user);

				list.add(sh);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return list;

	}

	private static final int REGISTRATION_TIMEOUT = 30 * 1000; // ms

	public static String getRequest(String url, HashMap<String, String> prms) {

		InputStream is = null;
		String result = "";
		JSONArray jArray = null;

		// http post
		try {
			HttpClient httpclient = new DefaultHttpClient();
			HttpParams params = httpclient.getParams();
			HttpConnectionParams.setConnectionTimeout(params,
					REGISTRATION_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, REGISTRATION_TIMEOUT);
			ConnManagerParams.setTimeout(params, REGISTRATION_TIMEOUT);

			Set<String> set = prms.keySet();
			int count = 0;
			for (Iterator iterator = set.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				if (count == 0) {
					url = url + "?" + key + "=" + prms.get(key);
					count = 1;
				} else {
					url = url + "&" + key + "=" + prms.get(key);
				}
			}

			HttpGet httpget = new HttpGet(url);
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					is, "utf-8"), 8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result = sb.toString();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return result;
		}

	}

	public static boolean postRequest(String path, Map params) {

		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpost = new HttpPost(path);
		Iterator iter = params.entrySet().iterator();
		try {
			JSONObject data = new JSONObject();
			while (iter.hasNext()) {
				Map.Entry pairs = (Map.Entry) iter.next();
				String key = (String) pairs.getKey();
				String value = (String) pairs.getValue();
				data.put(key, value);
			}
			StringEntity se = new StringEntity(data.toString());
			httpost.setEntity(se);
			httpost.setHeader("Accept", "application/json");
			httpost.setHeader("Content-type", "application/json");

			HttpResponse response = httpclient.execute(httpost);
			if (response.getStatusLine().getStatusCode() == 202) {
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	public static User getUserByEmail(String email) {
		String URL = mainUrl + "getUserByEmail";
		HashMap<String, String> prms = new HashMap<String, String>();
		prms.put("email", email);
		String result = getRequest(URL, prms);
		User u = new User();
		try {
			JSONObject e = new JSONObject(result);
			u.setDisplayName(e.getString("displayName"));
			u.setEmail(e.getString("email"));
			u.setUserId(e.getString("userId"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return u;
	}

	public static User getUserByUserId(String userId) {
		String URL = mainUrl + "getUser";
		HashMap<String, String> prms = new HashMap<String, String>();
		prms.put("userId", userId);
		String result = getRequest(URL, prms);
		User u = new User();
		try {
			JSONObject e = new JSONObject(result);
			u.setDisplayName(e.getString("displayName"));
			u.setEmail(e.getString("email"));
			u.setUserId(e.getString("id"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return u;
	}

}
