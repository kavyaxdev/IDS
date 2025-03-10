package net.codejava.Graph;

import com.azure.identity.DeviceCodeCredential;
import com.azure.identity.DeviceCodeCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.httpcore.HttpClients;
import okhttp3.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import net.codejava.Classes.User_LoginInfo;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

public class GetUserLoginInfoGraph 
{
    private final static List<String> SCOPES = Arrays.asList("User.Read.All");

    public static void main(String[] args) throws Exception {
    	
    	 final DeviceCodeCredential deviceCodeCred = new DeviceCodeCredentialBuilder()
    			 .clientId("388650b0-1c3c-47b1-8677-7e9058573a2b")
                 .tenantId("9187d23d-78c2-466f-b2de-110ddb706ca3")
                 .challengeConsumer(challenge -> System.out.println(challenge.getMessage()))
                 .build();
    	
        final TokenCredentialAuthProvider tokenCredAuthProvider = new TokenCredentialAuthProvider(SCOPES, deviceCodeCred);
        final OkHttpClient httpClient = HttpClients.createDefault(tokenCredAuthProvider);

        final Request request = new Request.Builder().url("https://graph.microsoft.com/v1.0/auditLogs/signIns").build();

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
                		
                		String Username = (String) object.get("userPrincipalName");
                		Username=Username.split("@")[0];
                		
                		if(Username.indexOf("#EXT#") != -1)
                		{
                			System.out.println("Cannot Add EXT User");
                			continue;
                		}
                		
                		Username = Username.substring(0, 1).toUpperCase() + Username.substring(1); 
                		String displayName = (String) object.get("userDisplayName");
        	            String createdDateTime = (String) object.get("createdDateTime");
        	            createdDateTime=createdDateTime.replace('T', ' ');
        	            createdDateTime=createdDateTime.substring(0, createdDateTime.length() - 1); 
        	            String ipAddress= (String) object.get("ipAddress");
        	            String clientAppUsed = (String) object.get("clientAppUsed");
        	            JSONObject deviceDetail = (JSONObject) object.get("deviceDetail");
        	            String device_operatingSystem= (String) deviceDetail.get("operatingSystem");
        	            String device_Browser= (String) deviceDetail.get("browser");
        	            
        	            Boolean dc= (Boolean) deviceDetail.get("isCompliant");
        	            
        	            int device_isCompliant;
        	            
        	           device_isCompliant = (dc) ? 1 : 0;
        	            
        	            int device_isManaged;
        	            
        	            Boolean dm= (Boolean) deviceDetail.get("isManaged");
        	            device_isManaged = (dm) ? 1 : 0;
        	            
        	            JSONObject location = (JSONObject) object.get("location");
        	            String location_city= (String) location.get("city");
        	            String location_state= (String) location.get("state");
        	            String location_countryOrRegion= (String) location.get("countryOrRegion");
        	            JSONObject geoCoordinates  = (JSONObject) location.get("geoCoordinates");
        	            Double location_latitude= (Double) geoCoordinates.get("latitude");
        	            Double location_longitude= (Double) geoCoordinates.get("longitude");
        	            String accessStatus = (String) object.get("conditionalAccessStatus");
        	            String device= (String) object.get("device");
        	            
        	            User_LoginInfo userLogin= new User_LoginInfo(Username,displayName,createdDateTime,ipAddress,
	                    clientAppUsed,device_operatingSystem,device_Browser,device_isCompliant,device_isManaged,location_city,
	                    location_state,location_countryOrRegion,location_latitude,location_longitude,accessStatus,device);
        	            
        	            userLogin.pushToDB();
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
