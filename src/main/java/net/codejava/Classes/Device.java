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


public class Device {
	String LastSignInDateTime;
	String createdDateTime;
	String deviceID;
	int isComplient;
	int isManaged;
	int isRooted;
	String operatingSystem;
	
	public Device(String LastSignInDateTime, String createdDateTime, String deviceID, int isComplient,
	int isManaged, int isRooted, String operatingSystem)
	{
		this.LastSignInDateTime=LastSignInDateTime;
		this.createdDateTime=createdDateTime;
		this.deviceID=deviceID;
		this.isComplient=isComplient;
		this.isManaged=isManaged;
		this.isRooted=isRooted;
		this.operatingSystem=operatingSystem;
	}
	
	public String jsonToString (JSONObject record )
	{
		return record.toJSONString();
	}
	
	public void pushToDB(String username)
	{
		HashMap<String,String> deviceDetails = new HashMap<String,String>();
        deviceDetails.put("LastSignInDateTime", this.LastSignInDateTime);
        deviceDetails.put("CreatedDateTime",this.createdDateTime);
        deviceDetails.put("DeviceID",this.deviceID);
        deviceDetails.put("isComplient", Integer.toString(this.isComplient));
        deviceDetails.put("isManaged", Integer.toString(this.isManaged));
        deviceDetails.put("isRooted", Integer.toString(this.isRooted));
        deviceDetails.put("OperatingSystem", this.operatingSystem);
        deviceDetails.put("DisplayName", username);
        
        JSONObject user = new JSONObject(deviceDetails);
        
        try
        {
       	 String requestBody= user.toJSONString();
       	 
       	 HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/IdentityThreats/rest/add_DeviceInfo"))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            System.out.println(response.body());
            
            return;
        }
        
        catch (Exception e) 
        {
            System.out.println(e);
        }
        
        return;
	}
	
	public String readDeviceInfo()
	{
		try {
			
			String name=this.deviceID;
			String URLvalue="http://localhost:8080/IdentityThreats/rest/getDeviceInfo/" + name;
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
	
	public static void main(String args[])
	{
		Device d= new Device("2022-08-22 11:44:07","2022-08-22 11:44:07","x",1,1,1,"x");
		d.pushToDB("Lalitha");
		System.out.println(d.readDeviceInfo());
	}
	
	
}
