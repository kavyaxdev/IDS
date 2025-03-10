package net.codejava;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;

import java.sql.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@Path("/addDashboard")

public class addDashboard {
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
		        
		        PreparedStatement pstmt = c.prepareStatement("INSERT INTO dashboard values (?, ?, ?, ?, ?, ?, ? ) "
		        		+ "ON DUPLICATE KEY UPDATE "
		        		+ "Device = ?,"
		        		+ "Location = ?,"
		        		+ "loginAttempts = ?,"
		        		+ "confidence = ?,"
		        		+ "risk = ?,"
		        		+ "violations = ?;");

	            String name = (String) record.get("name");
	            String Device = (String) record.get("Device");
	            String Location = (String) record.get("Location");
	            long login = Long.parseLong((String) record.get("login"));
	            double confidence = Double.parseDouble((String) record.get("confidence"));
	            double risk = Double.parseDouble((String) record.get("risk"));
	            long violations = Long.parseLong((String) record.get("violations"));
	            
	            pstmt.setString(1, name);
	            pstmt.setString(2, Device);
	            pstmt.setString(3, Location);
	            pstmt.setLong(4, login);
	            pstmt.setDouble(5, confidence);
	            pstmt.setDouble(6, risk);
	            pstmt.setLong(7, violations);
	            pstmt.setString(8, Device);
	            pstmt.setString(9, Location);
	            pstmt.setLong(10, login);
	            pstmt.setDouble(11, confidence);
	            pstmt.setDouble(12, risk);
	            pstmt.setLong(13, violations);
	     
	            pstmt.executeUpdate();
		         
		        return "true";
			}
			
			catch(Exception e)
			{
				//System.out.println(e);
				return e.toString();
			}
			
			
		}
}