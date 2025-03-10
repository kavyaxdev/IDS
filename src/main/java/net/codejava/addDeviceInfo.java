package net.codejava;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;

import java.sql.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@Path("/add_DeviceInfo")

public class addDeviceInfo {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	
		public boolean add(String reqBod) 
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
		        
		        PreparedStatement pstmt = c.prepareStatement("INSERT INTO DeviceInfo values (?, ?, ?, ?, ?, ?, ?, ? )");
		        
	            String deviceID = (String) record.get("DeviceID");
	            String displayName = (String) record.get("DisplayName");
	            String Created = (String) record.get("CreatedDateTime");
	            String LastIn = (String) record.get("LastSignInDateTime");
	            String OperatingSystem = (String) record.get("OperatingSystem");
	            int isRooted = Integer.parseInt((String) record.get("isRooted"));
	            int isMan = Integer.parseInt((String) record.get("isManaged"));
	            int isCom = Integer.parseInt((String) record.get("isComplient"));
	            pstmt.setString(1, deviceID);
	            pstmt.setString(2, displayName);
	            pstmt.setString(3, Created);
	            pstmt.setString(4, LastIn);
	            pstmt.setString(5, OperatingSystem);
	            pstmt.setInt(6, isRooted);
	            pstmt.setInt(7, isMan);
	            pstmt.setInt(8, isCom);
	            pstmt.executeUpdate();
		        
		        System.out.println("Records inserted.....");
		         
		        return true;
			}
			
			catch(Exception e)
			{
				System.out.println(e);
			}
			
			return false;
		}

}