package net.codejava;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.sql.*;

import org.json.simple.JSONObject;

import java.util.*;
 
@Path("/getUsers")

public class getAllUsers {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get_userInfo() {
    	
    	try
    	{
    		Connection c;
            Statement s;
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/capstone?zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false","root","Lalitha@1");
            s = c.createStatement();
            
            String x="select username from USER;";
            
            ResultSet rs = s.executeQuery(x);
            
            ArrayList<JSONObject> arr = new ArrayList<JSONObject> ();
            
            while(rs.next())
            {
            	HashMap<String,Object> userInfo = new HashMap<String,Object>();
                
                int columns = rs.getMetaData().getColumnCount();
         
                for (int i = 0; i < columns; i++)
                    //obj.put(rs.getMetaData().getColumnLabel(i + 1).toLowerCase(), rs.getObject(i + 1));
                	userInfo.put(rs.getMetaData().getColumnLabel(i + 1).toLowerCase(), rs.getObject(i + 1));
                
                JSONObject obj = new JSONObject(userInfo);
                arr.add(obj);
                //return obj.toString();
            }
            
            return arr.toString();
     
    	}
    	
    	catch(Exception e){
            System.out.println(e);
        }
        
        
        return "null";
    }
}
