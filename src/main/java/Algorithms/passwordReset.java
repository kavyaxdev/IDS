package Algorithms;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class passwordReset {
	
	public static String getAuth(String uname, String pwd)
	{
		try
		{
			String URLvalue="http://localhost:8080/IdentityThreats/rest/getUserPassword/" + uname + "/" + pwd;
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
    		
    		String risk= (String) riskScore.get("response".toLowerCase());
    		
    		return risk;
		}
		
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		return "error";
	}

}
