package net.codejava.Classes;

import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Countries_Dump {
	
	String Country_Code;
	String Country_Name;
	String City_Name;
	
	public static JSONObject getGeoPointInfo()
	{
		return null;
	}
	
	public static String jsonToString (JSONObject obj)
	{
		return obj.toJSONString();
	}
	
	public static void pushToDB()
	{
		return;
	}
	
	public static void getCountryInfo(String s)
	{
		return;
	}
	
	public static double getDistance(Countries_Dump c)
	{
		/*double lat1= 
		double dlon = Math.abs(long2 - long1);
    	double dlat = Math.abs(lat2 - lat1);
    	double a = Math.pow(Math.sin(dlat / 2), 2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2),2);
        double c_ = 2 * Math.asin(Math.sqrt(a));
        double r = 6371;
    	double dist=c_*r;*/
		
		return 0.0;
	}
	
}
