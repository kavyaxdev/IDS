package Algorithms;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.HashMap;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import net.codejava.Classes.User;

public class Dashboard {
		
	public static void addToDashboard()
	{
		String users=genericFunctions.getUsers();
		
		try
		{
			JSONParser parser = new JSONParser();
	        JSONArray json = (JSONArray) parser.parse(users);

	        for(int i = 0; i < json.size(); i++)
	        {
	        	JSONObject object = (JSONObject) json.get(i);
	        	String name=(String) object.get("username");
	        	
	        	User x=new User(name);
	        	
	        	String Device=updateRisk.getInitDevice(x);
	        	String Location=updateRisk.getInitLocation(x);
	        	int login=updateRisk.loginAttempts(x);
	        	
	        	double risk=updateRisk.getTotalRisk(name);
	        	BigDecimal bigDecimal1 = new BigDecimal(Double.toString(risk));
	            bigDecimal1 = bigDecimal1.setScale(2, RoundingMode.HALF_UP);
	        	risk= bigDecimal1.doubleValue();
	        	
	        	double confidence= 5-risk;
	        	BigDecimal bigDecimal = new BigDecimal(Double.toString(confidence));
	            bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
	            confidence= bigDecimal.doubleValue();
	            
	            double travel=genericFunctions.getRiskLevel(name, "TravelRisk");
	    		double location=genericFunctions.getRiskLevel(name, "LocationRisk");
	    		double device=genericFunctions.getRiskLevel(name, "DeviceRisk");
	    		double browser=genericFunctions.getRiskLevel(name, "BrowserRisk");
	    		double password=genericFunctions.getRiskLevel(name, "PasswordRisk");
	    		double anon=genericFunctions.getRiskLevel(name, "Anonymous");
	    		
	        	int violations=0;
	        	
	        	if(location>=3)
	        		violations++;
	        	
	        	if(travel>=3)
	        		violations++;
	        	
	        	if(device>=3)
	        		violations++;
	        	
	        	if(browser>=3)
	        		violations++;
	        	
	        	if(password>=3)
	        		violations++;
	        	
	        	if(anon>=3)
	        		violations++;
	        	
	        	HashMap<String,String> dashboard = new HashMap<String,String>();
	    		dashboard.put("name", name);
	    		dashboard.put("Device",Device);
	    		dashboard.put("Location",Location);
	    		dashboard.put("login",Long.toString(login));
	    		dashboard.put("confidence",Double.toString(confidence));
	    		dashboard.put("risk",Double.toString(risk));
	    		dashboard.put("violations",Long.toString(violations));
	            
	            JSONObject countryObj = new JSONObject(dashboard);
	          
	           	 String requestBody= countryObj.toJSONString();
	           	 
	           	 HttpClient client = HttpClient.newHttpClient();
	                HttpRequest request = HttpRequest.newBuilder()
	                        .uri(URI.create("http://localhost:8080/IdentityThreats/rest/addDashboard"))
	                        .POST(HttpRequest.BodyPublishers.ofString(requestBody))
	                        .build();

	                HttpResponse<String> response = client.send(request,
	                        HttpResponse.BodyHandlers.ofString());

	                System.out.println(response.body());
		}
		}
		
		catch(Exception e)
		{
			
		}
		
	}
	
	public static void main(String args[])
	{
		addToDashboard();
	}
}
