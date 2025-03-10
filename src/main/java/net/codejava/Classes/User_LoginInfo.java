package net.codejava.Classes;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class User_LoginInfo {
	String Username;
	String displayName;
	String createdDateTime;
	String ipAddress;
	String clientAppUsed;
	String device_operatingSystem;
	String device_Browser;
	int device_isComplient;
	int device_isManaged;
	String location_city;
	String location_state;
	String location_countryOrRegion;
	Double location_latitude;
	Double location_longitude;
	String accessStatus;
	String device;
	
	public User_LoginInfo(String Username,String displayName,String createdDateTime,String ipAddress,
	String clientAppUsed, String device_operatingSystem,String device_Browser,int device_isComplient,
	int device_isManaged,String location_city,String location_state,String location_countryOrRegion,
	Double location_latitude,Double location_longitude,String accessStatus,String device)
	{
		this.Username=Username;
		this.displayName=displayName;
		this.createdDateTime=createdDateTime;
		this.ipAddress=ipAddress;
		this.clientAppUsed=clientAppUsed;
		this.device_operatingSystem=device_operatingSystem;
		this.device_Browser=device_Browser;
		this.device_isComplient=device_isComplient;
		this.device_isManaged=device_isManaged;
		this.location_city=location_city;
		this.location_state=location_state;
		this.location_countryOrRegion=location_countryOrRegion;
		this.location_latitude=location_latitude;
		this.location_longitude=location_longitude;
		this.accessStatus=accessStatus;
		this.device=device;
	}
	
	public User_LoginInfo(String Username)
	{
		this.Username=Username;
	}
	
	public String getCountry()
	{
		return this.location_countryOrRegion;
	}
	
	public String jsonToString (JSONObject record )
	{
		return record.toJSONString();
	}
	
	public void pushToDB()
	{         
         HashMap<String,String> userLoginDetails = new HashMap<String,String>();
         userLoginDetails.put("DisplayName", this.displayName);
         userLoginDetails.put("CreateDateTime",this.createdDateTime);
         userLoginDetails.put("ipAddress",this.ipAddress);
         userLoginDetails.put("ClientAppUsed", this.clientAppUsed);
         userLoginDetails.put("DeviceOperatingSystem", this.device_operatingSystem);
         userLoginDetails.put("DeviceBrowser", this.device_Browser);
         userLoginDetails.put("DeviceisComplient", String.valueOf(this.device_isComplient));
         userLoginDetails.put("DeviceisManaged", String.valueOf(this.device_isManaged));
         userLoginDetails.put("LocationCity", this.location_city);
         userLoginDetails.put("LocationState", this.location_state);
         userLoginDetails.put("LocationCountryorRegion", this.location_countryOrRegion);
         userLoginDetails.put("LocationLatitude", String.valueOf(this.location_latitude));
         userLoginDetails.put("LocationLongitude", String.valueOf(this.location_longitude));
         userLoginDetails.put("AccessStatus", this.accessStatus);
         userLoginDetails.put("Username", this.Username);
         userLoginDetails.put("device", this.device);
         
         JSONObject user = new JSONObject(userLoginDetails);
         
         try
         {
        	 String requestBody= user.toJSONString();
        	 
        	 HttpClient client = HttpClient.newHttpClient();
             HttpRequest request = HttpRequest.newBuilder()
                     .uri(URI.create("http://localhost:8080/IdentityThreats/rest/add_UserLoginInfo"))
                     .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                     .build();

             HttpResponse<String> response = client.send(request,
                     HttpResponse.BodyHandlers.ofString());

             //System.out.println(response.body());
             
             return;
         }
         
         catch (Exception e) 
         {
             System.out.println(e);
         }
         
         return;
	}
	
	public String readLoginInfo()
	{
		try {
			
			String name=this.Username;
			String URLvalue="http://localhost:8080/IdentityThreats/rest/getLoginInfo/" + name;
			URL url = new URL(URLvalue);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "text/plain; charset=utf-8");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			
			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			return response.toString();

		  }
		
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return "error";
	}
	
	public JSONObject getLoginInfo()
	{
		String info=readLoginInfo();
		JSONParser jsonParser = new JSONParser();  
		
		try
		{
			JSONObject json = (JSONObject) jsonParser.parse(info);
			return json;
		}
		
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return null;
	}
	
	
}
