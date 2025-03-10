package net.codejava;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.sql.*;

import org.json.simple.JSONObject;

import java.sql.ResultSet;

import java.util.*;
 
@Path("/getRiskLevel/{name}/{field}")

public class getRiskScore {
	 
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getRiskLevel(@PathParam("name")String name, @PathParam("field")String field) {
    	
    	try
    	{
    		Connection c;
            Statement s;
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/capstone?zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false","root","Lalitha@1");
            s = c.createStatement();
            
            String x="select " + field +" from UserRiskLevel WHERE Username = '" + name + "' ;";
            
            ResultSet rs = s.executeQuery(x);
            
            while(rs.next())
            {
            	HashMap<String,Object> userInfo = new HashMap<String,Object>();
                
                int columns = rs.getMetaData().getColumnCount();
         
                for (int i = 0; i < columns; i++)
                    //obj.put(rs.getMetaData().getColumnLabel(i + 1).toLowerCase(), rs.getObject(i + 1));
                	userInfo.put(rs.getMetaData().getColumnLabel(i + 1).toLowerCase(), rs.getObject(i + 1));
                
                JSONObject obj = new JSONObject(userInfo);
                
                return obj.toString();
            }
            
            if(c!=null)
          	  c.close();
     
    	}
    	
    	catch(Exception e){
            System.out.println(e);
        }
        
        
        return "null";
    }
}
