package net.codejava;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;

import org.json.simple.JSONObject;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.*;

@Path("/getLoginInfo/{name}")
public class getLoginInfo {
 
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get_LoginInfo(@PathParam("name")String name) {
    	
    	try
    	{
    		Connection c;
            Statement s;
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/capstone?zeroDateTimeBehavior=CONVERT_TO_NULL&useSSL=false","root","Lalitha@1");
            s = c.createStatement();
            
            String x="select * from UserLoginInfo WHERE Username = '" + name + "' ORDER BY CreateDateTime;";
            
            ResultSet rs = s.executeQuery(x);
            
            ArrayList<JSONObject> arr = new ArrayList<JSONObject> ();
            //JSONArray JSONarr= new JSONArray();
            
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
            
            //JSONarr = (JSONArray) arr;
            
            c.close();
            
            return arr.toString();
     
    	}
    	
    	catch(Exception e){
            System.out.println(e);
        }
        
        
        return "null";
    }
}