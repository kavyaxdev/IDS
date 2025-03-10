package net.codejava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;

@Path("/addMitigation")

public class addMitigation {
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
		        
		        PreparedStatement pstmt = c.prepareStatement("INSERT INTO User_Mitigation values (?, ?, ?, ?, ?, ?)");

		        String Username = (String) record.get("Username");
	            String Mail = (String) record.get("Mail");
	            int Mitigation = Integer.parseInt((String) record.get("Mitigation"));
	            String MitigationType = (String) record.get("MitigationType");
	            String Remarks = (String) record.get("Remarks");
	            String MitigationDate=(String) record.get("MitigationDate");
	            
	            pstmt.setString(1, Username);
	            pstmt.setString(2, Mail);
	            pstmt.setInt(3, Mitigation);
	            pstmt.setString(4, MitigationDate);
	            pstmt.setString(5, MitigationType);
	            pstmt.setString(6, Remarks);
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
