package net.codejava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

@Path("/updatePass")

public class updatePassword {
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	
		public boolean updatePass(String reqBod) 
		{
			JSONParser jsonParser = new JSONParser();
			
			try
			{
				JSONObject record = (JSONObject) jsonParser.parse(reqBod);
				Connection c;
		        Class.forName("com.mysql.cj.jdbc.Driver");
		        c = DriverManager.getConnection("jdbc:mysql://localhost:3306/capstone?zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false","root","Lalitha@1");
		        
		        PreparedStatement pstmt = c.prepareStatement("UPDATE homeAuthDetails set password=? WHERE username=?");

		        String uname = (String) record.get("username");
	            String pass=(String) record.get("password");
	            
	            pstmt.setString(2, uname);
	            pstmt.setString(1, pass);
	            pstmt.executeUpdate();
		         
		        return true;
			}
			
			catch(Exception e)
			{
				System.out.println(e);
			}
			
			return false;
		}
}
