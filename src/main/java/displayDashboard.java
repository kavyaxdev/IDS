import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class displayDashboard {
	
	public static List<ArrayList<Object>> getDashboard()
	{
		 List<ArrayList<Object>> aList = new ArrayList<ArrayList<Object>>();
		try
		{
	    	String URLvalue="http://localhost:8080/IdentityThreats/rest/getDashboard";
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
			StringBuffer res = new StringBuffer();

			while ((inputLine = in.readLine()) !=null) {
				res.append(inputLine);
			}
			in.close();
			
			String dashboardVals=res.toString();
			
			JSONParser parser = new JSONParser();
			JSONArray userLogs= (JSONArray) parser.parse(dashboardVals);
			
			int n=userLogs.size();
			
			System.out.println("1");
			
			for(int i=0;i<n;i++)
			{
				JSONObject log1 = (JSONObject) userLogs.get(i);
				System.out.println("2");
				ArrayList<Object> x=new ArrayList<Object>();
				x.add((String)log1.get("username"));
				System.out.println("3");
				x.add((String)log1.get("device"));
				x.add((String)log1.get("location"));
				x.add((long)log1.get("loginattempts"));
				x.add((double)log1.get("confidence"));
				x.add((double)log1.get("risk"));
				x.add((long)log1.get("violations"));
				aList.add(x);
			}
			
			return aList;
		}
	   	
	   	catch(Exception e)
	   	{
	   		System.out.println(e);
	   	}
		
		return aList;
	}
	
	public static void main(String args[])
	{
		List<ArrayList<Object>> x=getDashboard();
		
		for(int i=0;i<x.size();i++)
		{
			ArrayList<Object> p=x.get(i);
			
			for(int j=0;j<p.size();j++)
			{
				System.out.println(p.get(j).toString());
			}
		}
	}
}
