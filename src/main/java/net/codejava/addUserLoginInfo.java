package net.codejava;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;

import java.sql.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@Path("/add_UserLoginInfo")
public class addUserLoginInfo {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	
		public String add(String reqBod) 
		{
			JSONParser jsonParser = new JSONParser();
			
			try
			{
				JSONObject record = (JSONObject) jsonParser.parse(reqBod);
				//JSONArray jsonArray = (JSONArray) jsonObject.get("users");
				Connection c;
		        Statement s;
		        Class.forName("com.mysql.cj.jdbc.Driver");
		        c = DriverManager.getConnection("jdbc:mysql://localhost:3306/capstone?zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false","root","Lalitha@1");
		        s = c.createStatement();
		        
		        PreparedStatement pstmt = c.prepareStatement("INSERT IGNORE INTO UserLoginInfo values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		        
	            String displayName = (String) record.get("DisplayName");
	            String Created = (String) record.get("CreateDateTime");
	            String ip = (String) record.get("ipAddress");
	            String client = (String) record.get("ClientAppUsed");
	            String OperatingSystem = (String) record.get("DeviceOperatingSystem");
	            String browser = (String) record.get("DeviceBrowser");
	            int isCom = Integer.parseInt((String) record.get("DeviceisComplient"));
	            int isMan = Integer.parseInt((String) record.get("DeviceisManaged"));
	            String city = (String) record.get("LocationCity");
	            String state = (String) record.get("LocationState");
	            String country = (String) record.get("LocationCountryorRegion");
	            Double lat = Double.parseDouble((String) record.get("LocationLatitude"));
	            Double longi = Double.parseDouble((String) record.get("LocationLongitude"));
	            String status = (String) record.get("AccessStatus");
	            String name = (String) record.get("Username");
	            pstmt.setString(1, displayName);
	            pstmt.setString(2, Created);
	            pstmt.setString(3, ip);
	            pstmt.setString(4, client);
	            pstmt.setString(5, OperatingSystem);
	            pstmt.setString(6, browser);
	            pstmt.setInt(7, isCom);
	            pstmt.setInt(8, isMan);
	            pstmt.setString(9, city);
	            pstmt.setString(10, state);
	            pstmt.setString(11, country);
	            pstmt.setDouble(12, lat);
	            pstmt.setDouble(13, longi);
	            pstmt.setString(14, status);
	            pstmt.setString(15, name);
	            pstmt.executeUpdate();
		         
		        return "true";
			}
			
			catch(Exception e)
			{
				return e.toString();
			}
			
		}

}