package Algorithms;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import net.codejava.Classes.User;

public class Device {
	public static Boolean addInitialDevice(String browser,User x)
	{
		HashMap<String,String> countryDetails = new HashMap<String,String>();
		countryDetails.put("Username", x.getUsername());
		countryDetails.put("mail",x.getMail());
		countryDetails.put("fieldName","DeviceID");
		countryDetails.put("fieldValue",browser );
        
        JSONObject countryObj = new JSONObject(countryDetails);
        
        try
        {
       	 String requestBody= countryObj.toJSONString();
       	 
       	 HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/IdentityThreats/rest/addInitial"))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            System.out.println(response.body());
            
            return true;
        }
        
        catch (Exception e) 
        {
            System.out.println(e);
        }
        
        return false;
	}
	
	public static Boolean addConfidence(Double conf,User x)
	{
		HashMap<String,String> countryDetails = new HashMap<String,String>();
		countryDetails.put("Username", x.getUsername());
		countryDetails.put("mail",x.getMail());
		countryDetails.put("fieldName","DeviceConfidence");
		countryDetails.put("confidenceLevel",String.valueOf(conf));
        
        JSONObject countryObj = new JSONObject(countryDetails);
        
        try
        {
       	 String requestBody= countryObj.toJSONString();
       	 
       	 HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/IdentityThreats/rest/addconfidenceLevel"))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            System.out.println(response.body());
            
            return true;
        }
        
        catch (Exception e) 
        {
            System.out.println(e);
        }
        
        return false;
	}
	
	public static void deviceRisk(User x,Double confidence)
	{
		Double risk=5- confidence;
		
		BigDecimal bigDecimal = new BigDecimal(Double.toString(risk));
        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        risk= bigDecimal.doubleValue();
		
		HashMap<String,String> countryDetails = new HashMap<String,String>();
		countryDetails.put("Username", x.getUsername());
		countryDetails.put("mail",x.getMail());
		countryDetails.put("fieldName","DeviceRisk");
		countryDetails.put("riskScore",String.valueOf(risk));
        
        JSONObject countryObj = new JSONObject(countryDetails);
        
        try
        {
       	 String requestBody= countryObj.toJSONString();
       	 
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
	
	public static void deviceConfidence(String username)
	{
		try
		{
			User x= new User(username);
			String logs=x.getUserLogin();
			
			JSONParser parser = new JSONParser();
    		JSONArray userLogs= (JSONArray) parser.parse(logs);
    		
    		int n=userLogs.size();
    		
    		if(n==0)
    			return;
    		
    		String initialDevice= (String) ((JSONObject) userLogs.get(0)).get("deviceoperatingsystem");
    		
    		if(addInitialDevice(initialDevice,x)==false)
    			return;
    		
    		int initial;
    		
    		if(n<10)
    			initial=0;
    		
    		else
    			initial=n-10;
    		
    		Double confidence;
    		
    		String first= (String) ((JSONObject) userLogs.get(initial)).get("deviceoperatingsystem");
    		
    		if(first==initialDevice)
    			confidence=0.5;
    		
    		else
    			confidence=0.45;
    		
    		for(int i=initial;i<n-1;i++)
    		{
    			JSONObject log1= (JSONObject) userLogs.get(i);
    			String country1=(String) log1.get("deviceoperatingsystem");
    			
    			JSONObject log2= (JSONObject) userLogs.get(i+1);
    			String country2=(String) log2.get("deviceoperatingsystem");
    			
    			
    			if(country1.equals(country2))
    			{
    				if(country1.equals(initialDevice))
    					confidence+=0.5;
    				
    				else
    					confidence+=0.45;
    			}
    			
    			else
    				confidence/=2;
    			
    			System.out.println(country1 + " " + country2 + " " + confidence + "\n");
    		}
    		
    		BigDecimal bigDecimal = new BigDecimal(Double.toString(confidence));
            bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
            confidence= bigDecimal.doubleValue();
            
    		System.out.println(confidence);
    		
    		addConfidence(confidence,x);
    		deviceRisk(x,confidence);
    		
		}
		
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
	
	public static void main(String args[])
	{
		deviceConfidence("Sarah");
	}
}
