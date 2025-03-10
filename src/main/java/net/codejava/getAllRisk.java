package net.codejava;

import Algorithms.genericFunctions;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import org.json.simple.JSONObject;

import java.util.*;

import net.codejava.Classes.User;
 
@Path("/getUserRiskDetails/{uname}")

public class getAllRisk {
	@GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getuserRiskInfo(@PathParam("uname")String name) {
        User x=new User(name);
        
        HashMap<String,Object> userInfo = new HashMap<String,Object>();
        userInfo.put("mail",x.getMail());
        userInfo.put("firstname", x.getFirstname());
        userInfo.put("lastname", x.getLastname());
        userInfo.put("phonenumber",x.getPhonenumber());
        userInfo.put("travel", genericFunctions.getRiskLevel(name,"TravelRisk"));
        userInfo.put("location", genericFunctions.getRiskLevel(name,"LocationRisk"));
        userInfo.put("browser", genericFunctions.getRiskLevel(name,"BrowserRisk"));
        userInfo.put("device", genericFunctions.getRiskLevel(name,"DeviceRisk"));
        userInfo.put("password", genericFunctions.getRiskLevel(name,"PasswordRisk"));
        userInfo.put("anon", genericFunctions.getRiskLevel(name,"Anonymous"));
        
        JSONObject obj = new JSONObject(userInfo);
        
        return obj.toString();
	}
}
