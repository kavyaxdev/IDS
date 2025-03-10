package net.codejava;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.sql.*;

import org.json.simple.JSONObject;

import java.util.*;

@Path("/getUserPassword/{name}/{password}")

public class getUserPassword {
	@GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get_userPass(@PathParam("name")String name,@PathParam("password")String initialPassword) {
    	
    	try
    	{
    		Connection c;
            Statement s;
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/capstone?zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false","root","Lalitha@1");
            s = c.createStatement();
            
            String x="select password from homeAuthDetails WHERE username = '" + name + "' ;";
            
            ResultSet rs = s.executeQuery(x);
            
            HashMap<String,String> passwordAuth = new HashMap<String,String>();
            
            if (rs.next() == false) 
            { 
            	passwordAuth.put("response", "user not found");
            	JSONObject passAuth = new JSONObject(passwordAuth);
                
                return passAuth.toString();
            }
            
            rs = s.executeQuery(x);
            
        	while(rs.next())
            {
            	System.out.println("in");
        		String ans;
         	
            	ans=rs.getObject(1).toString();
            	
            	if(ans.compareTo(initialPassword)==0)
            	{
            		passwordAuth.put("response", "accepted");
	            	JSONObject passAuth = new JSONObject(passwordAuth);
	                return passAuth.toString();
            	}
            	
            	else
            	{
            		passwordAuth.put("response", "incorrect");
		        	JSONObject passAuth = new JSONObject(passwordAuth);
		            return passAuth.toString();
            	}
            }
     
    	}
    	
    	catch(Exception e){
            System.out.println(e);
        }
        
        
        return "null";
    }
}
