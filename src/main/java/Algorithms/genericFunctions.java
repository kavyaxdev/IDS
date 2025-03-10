package Algorithms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import net.codejava.Classes.User;

public class genericFunctions {
	
	public static double getRiskLevel(String Username, String field)
	{
		try {
			
			String URLvalue="http://localhost:8080/IdentityThreats/rest/getRiskLevel/" + Username + "/" + field;
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
			
			String val= response.toString();
			JSONParser parser = new JSONParser();
    		JSONObject riskScore= (JSONObject) parser.parse(val);
    		
    		double risk= (double) riskScore.get(field.toLowerCase());
    		
    		return risk;

		  }
		
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return 0.0;
	}
	
	//Add risk score to DB
	public static void addRisk(User x,Double risk, String field)
    {
    	HashMap<String,String> userRisk = new HashMap<String,String>();
        userRisk.put("Username", x.getUsername());
        userRisk.put("mail",x.getMail());
        userRisk.put("fieldName", field);
        userRisk.put("riskScore", String.valueOf(risk));
        
        JSONObject user = new JSONObject(userRisk);
        
        try
        {
	       	 String requestBody= user.toJSONString();
	       	 
	       	 HttpClient client = HttpClient.newHttpClient();
	            HttpRequest request = HttpRequest.newBuilder()
	                    .uri(URI.create("http://localhost:8080/IdentityThreats/rest/addRiskScore"))
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
	
	
	//Get all users
	public static String getUsers()
    {
		try {
			
			String URLvalue="http://localhost:8080/IdentityThreats/rest/getUsers";
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
			
			//System.out.println(response.toString());
			
			return response.toString();

		  }
		
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return "Error";
    }
	
	public static String getCompromised(String uname, String pwd)
	{
		try {
			
			String URLvalue="http://localhost:8080/IdentityThreats/rest/getCompromised/" + uname+ "/" + pwd;
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
			
			String val= response.toString();
			return val;

		  }
		
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return "fail";
	}
		
	
}
