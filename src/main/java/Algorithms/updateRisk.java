package Algorithms;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import net.codejava.Classes.User;

public class updateRisk {
	
	public static void updateRiskConfidence()
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
            	double travelRiskScore=ImpossibleTravel.getRiskScore(name);
            	
            	if(travelRiskScore!=-1)
            	{
            		User x=new User(name);
        			genericFunctions.addRisk(x,travelRiskScore,"TravelRisk");
            	}
            	
            	Location.locationConfidence(name);
            	Browser.browserConfidence(name);
            	Device.deviceConfidence(name);
            	
            }
        }
        
        catch(Exception e)
		{
			System.out.println(e);
		}
    }

	public static double getTotalRisk(String username)
	{
		double travel=genericFunctions.getRiskLevel(username, "TravelRisk");
		double location=genericFunctions.getRiskLevel(username, "LocationRisk");
		double device=genericFunctions.getRiskLevel(username, "DeviceRisk");
		double browser=genericFunctions.getRiskLevel(username, "BrowserRisk");
		double password=genericFunctions.getRiskLevel(username, "PasswordRisk");
		
		travel= (double)(80.0/33.0)*travel;
		location=(double)(40.0/33.0)*location;
		device= (double)(20.0/33.0)*device;
		browser=(double)(20.0/33.0)*browser;
		password=(double)(5.0/33.0)*password;
		
		double total= (travel + location + device + browser + password) / 5;
		
		return total;
	}
	
	public static String getInitLocation(User x)
	{
		String logs=x.getUserLogin();
		JSONParser parser = new JSONParser();
		
		try
		{
			JSONArray userLogs= (JSONArray) parser.parse(logs);
			
			int n=userLogs.size();
			
			if(n==0)
				return "none";
			
			String initialCountry= (String) ((JSONObject) userLogs.get(0)).get("locationcountryorregion");
			
			return initialCountry;
		}
		
		catch(Exception e)
		{
			return e.toString();
		}
	}
	
	public static String getInitDevice(User x)
	{
		String logs=x.getUserLogin();
		JSONParser parser = new JSONParser();
		
		try
		{
			JSONArray userLogs= (JSONArray) parser.parse(logs);
			
			int n=userLogs.size();
			
			if(n==0)
				return "none";
			
			String initialDevice= (String) ((JSONObject) userLogs.get(0)).get("deviceoperatingsystem");
			
			return initialDevice;
		}
		
		catch(Exception e)
		{
			return e.toString();
		}
	}
	
	public static int loginAttempts(User x)
	{
		String logs=x.getUserLogin();
		JSONParser parser = new JSONParser();
		
		try
		{
			JSONArray userLogs= (JSONArray) parser.parse(logs);
			
			int n=userLogs.size();
			
			System.out.println(n);
			
			return n;
		}
		
		catch(Exception e)
		{
			System.out.println(e);
			return 0;
		}
	}
	
	public static void main(String args[])
	{
		updateRiskConfidence();
	}
	
}
