package Algorithms;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

import net.codejava.Classes.User;

//velocity function
public class ImpossibleTravel{
	
    public static double velocity(double lat1, double long1, long time_diff, double lat2, double long2)
    {
        double dlon = Math.abs(long2 - long1);
    	double dlat = Math.abs(lat2 - lat1);
    	double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2),2);
        double c_ = 2 * Math.asin(Math.sqrt(a));
        double r = 6371;
    	double dist=c_*r;
    	
    	double speed;
    	
    	if(time_diff==0)
    		speed=-1;
    	
    	else
    		speed = dist/time_diff;
    	
        return speed;
    }
    
    //Risk score calculation
    public static double Riskscore(double lat1, double long1, long time_diff, double lat2, double long2)
    {
    	double speed = velocity(lat1, long1, time_diff, lat2, long2);
    	
        if(speed==-1)
        {
        	if(lat1==lat2 && long1==long2)
        		return 0;
        	
        	else
        		return 5;
        }
        
        double maxSpeed = 0.24;
        double R = 5*speed/maxSpeed;
        if(R>=5)
            R = 5;
        return R;
    }
    
    public static double getRiskScore(String name)
    {
    	try
    	{
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

    		while ((inputLine = in.readLine()) !=null) {
    			response.append(inputLine);
    		}
    		in.close();
    		
    		String logs=response.toString();
    		
    		double riskScore=-1;
    		
    		JSONParser parser = new JSONParser();
    		JSONArray userLogs= (JSONArray) parser.parse(logs);
    		
    		int n=userLogs.size();
    		
    		int start;
    		
    		if(n<10)
    			start=0;
    		
    		else
    			start=n-10;
    		
    		if(n==0)
    		{
    			System.out.println("no logs found for user");
    			return -1;
    		}
    		
    		if(n==1)
    		{
    			return 0.0;
    		}
    		
    		for(int j=start;j<n-1;j++)
    		{
    			JSONObject log1 = (JSONObject) userLogs.get(j);
    			JSONObject log2 = (JSONObject) userLogs.get(j+1);
    			
    			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); 
        		LocalDateTime dateTime1 = LocalDateTime.parse((String) log1.get("createdatetime"), formatter);    			
        		LocalDateTime dateTime2 = LocalDateTime.parse((String)log2.get("createdatetime"), formatter);
        		
        		long time = dateTime1.until( dateTime2, ChronoUnit.SECONDS );
    			
    			double score;
    			double lon1 = Math.toRadians((double)log1.get("locationlongitude"));
        	    double lon2 = Math.toRadians((double)log2.get("locationlongitude"));
        	    double lat1 = Math.toRadians((double)log1.get("locationlatitude"));
        	    double lat2 = Math.toRadians((double)log2.get("locationlatitude"));
        	    
        	    score=Riskscore(lat1,lon1,time,lat2,lon2);
        	    riskScore=Math.max(riskScore,score);
        	    
        	    BigDecimal bigDecimal = new BigDecimal(Double.toString(riskScore));
                bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
                riskScore= bigDecimal.doubleValue();
    		}
    		
    		return riskScore;
    	}
    	
    	catch(Exception e)
    	{
    		System.out.println(e);
    		return -1.0;
    	}
    }

}