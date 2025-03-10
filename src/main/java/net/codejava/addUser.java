package net.codejava;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.core.MediaType;

import java.sql.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

@Path("/add_User")

public class addUser {
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
		        
		        PreparedStatement pstmt = c.prepareStatement("INSERT IGNORE INTO USER values (?, ?, ?, ?, ?, ?, ? )");

	            String username = (String) record.get("username");
	            String mail = (String) record.get("mail");
	            String displayName = (String) record.get("displayName");
	            String firstname = (String) record.get("firstname");
	            String lastname = (String) record.get("lastname");
	            long phonenumber = Long.parseLong((String) record.get("phonenumber"));
	            String password = (String) record.get("password");
	            pstmt.setString(1, username);
	            pstmt.setString(2, mail);
	            pstmt.setString(3, displayName);
	            pstmt.setString(4, firstname);
	            pstmt.setString(5, lastname);
	            pstmt.setLong(6, phonenumber);
	            pstmt.setString(7, password);
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
