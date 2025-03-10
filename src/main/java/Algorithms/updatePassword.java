package Algorithms;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;

import org.json.simple.JSONObject;

public class updatePassword {
	
	public static void update(String uname,String pass)
	{
		HashMap<String,String> userUpdate = new HashMap<String,String>();
		userUpdate.put("username", uname);
		userUpdate.put("password", pass);
        
        JSONObject userUpdateObj = new JSONObject(userUpdate);
        
        try
        {
       	 String requestBody= userUpdateObj.toJSONString();
       	 
       	 HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/IdentityThreats/rest/updatePass"))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());

            System.out.println(response.body());
            
            return;
        }
        
        catch (Exception e) 
        {
            System.out.println(e);
        }
        
	}
	
}
