package net.codejava;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.sql.*;

import org.json.simple.JSONObject;

import java.util.*;

@Path("/getCompromised/{name}/{pwd}")

public class getCompromised {

		@GET
	    @Produces(MediaType.TEXT_PLAIN)
	    public String getConfLevel(@PathParam("name")String name,@PathParam("pwd")String pwd) {
	    	
	    	try
	    	{
	    		Connection c;
	            Statement s;
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/capstone?zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false","root","Lalitha@1");
	            s = c.createStatement();
	            
	            String x="select * from user_compromised WHERE username = '" + name + "' and password = '" + pwd + "' ;";
	            
	            ResultSet rs = s.executeQuery(x);
	           
	            if(rs.next())
	            	return "true";
	            
	            else
	            	return "false";
	     
	    	}
	    	
	    	catch(Exception e){
	            System.out.println(e);
	        }
	        
	        
	        return "null";
	    }
	}
