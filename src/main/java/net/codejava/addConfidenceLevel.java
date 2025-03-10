package net.codejava;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;

import java.sql.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@Path("/addconfidenceLevel")

public class addConfidenceLevel {
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
		        
		        String Username = (String) record.get("Username");
	            String mail = (String) record.get("mail");
	            String fieldName = (String) record.get("fieldName");
	            Double confidenceLevel = Double.parseDouble((String) record.get("confidenceLevel"));
	            
		        String statement = "INSERT INTO UserConfidenceLevel (Username,Mail," + fieldName + ")\n"
		        + "VALUES (?,?,?)\n"
		        + "ON DUPLICATE KEY UPDATE \n"
		        + fieldName + " = " + confidenceLevel +";";
		        
		        System.out.println(statement);
		        
		        PreparedStatement pstmt = c.prepareStatement(statement);
	           
	            pstmt.setString(1, Username);
	            pstmt.setString(2, mail);
	            pstmt.setDouble(3, confidenceLevel);
	            pstmt.executeUpdate();
		        
	            if(c!=null)
	            	  c.close();
		         
		        return "true";
			}
			
			catch(Exception e)
			{
				//System.out.println(e);
				return e.toString();
			}
			
			
		}
}
