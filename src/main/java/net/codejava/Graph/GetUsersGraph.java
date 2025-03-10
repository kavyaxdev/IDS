package net.codejava.Graph;

import com.azure.identity.DeviceCodeCredential;
import com.azure.identity.DeviceCodeCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.httpcore.HttpClients;
import okhttp3.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import net.codejava.Classes.User;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;


public class GetUsersGraph {
    private final static List<String> SCOPES = Arrays.asList("User.Read.All");

    public static void main(String[] args) throws Exception {
    	
    	 final DeviceCodeCredential deviceCodeCred = new DeviceCodeCredentialBuilder()
                 .clientId("388650b0-1c3c-47b1-8677-7e9058573a2b")
                 .tenantId("9187d23d-78c2-466f-b2de-110ddb706ca3")
                 .challengeConsumer(challenge -> System.out.println(challenge.getMessage()))
                 .build();
    	
        final TokenCredentialAuthProvider tokenCredAuthProvider = new TokenCredentialAuthProvider(SCOPES, deviceCodeCred);
        final OkHttpClient httpClient = HttpClients.createDefault(tokenCredAuthProvider);

        final Request request = new Request.Builder().url("https://graph.microsoft.com/v1.0/users").build();

        httpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //System.out.println(response.body().string());
            	String resp=response.body().string();
            	
            	JSONParser parser = new JSONParser();
            	
            	try
            	{
            		JSONObject jsnobject = (JSONObject) parser.parse(resp);
                	JSONArray jsonArray = (JSONArray) jsnobject.get("value");
                	
                	for (int i=0; i<jsonArray.size(); i++)
                	{
                		JSONObject object = (JSONObject) jsonArray.get(i); 
                		//String resultString = object.toString();
                		//System.out.println(resultString + "\n");
                		
                		String username = (String) object.get("userPrincipalName");
                		username= username.split("@")[0];
                		
                		if(username.indexOf("#EXT#") != -1)
                		{
                			System.out.println("Cannot Add EXT User");
                			continue;
                		}
                		
        	            String mail = (String) object.get("userPrincipalName");
        	            String displayName = (String) object.get("displayName");
        	            String firstname = (String) object.get("givenName");
        	            String lastname = (String) object.get("surname");
        	            
        	            String phno=(String) object.get("mobilePhone");
        	            
        	            long phonenumber;
        	            
        	            if(phno !=null)
        	            	phonenumber=Long.parseLong(phno);
        	            
        	            else
        	            	phonenumber=0;
        	            
        	            String password = "unknown";
        	            
                		User user=new User(username,mail,displayName,firstname,lastname,phonenumber,password);
                		
                		user.pushToDB();
                	}
            	}
            	
            	catch(Exception e)
            	{
            		System.out.println(e);
            	}
            	
            }

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
        });
    }
}
