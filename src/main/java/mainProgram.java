import com.azure.identity.DeviceCodeCredential;
import com.azure.identity.DeviceCodeCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.httpcore.HttpClients;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.codejava.Classes.User;
import net.codejava.Classes.User_LoginInfo;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;

import java.util.Timer;
import java.util.TimerTask;

import Algorithms.Dashboard;
import Algorithms.genericFunctions;
import Algorithms.updateRisk;

public class mainProgram {
	
	static ArrayList<String> yes = new ArrayList<String>();
	static ArrayList<String> no = new ArrayList<String>();
	
	private final static List<String> SCOPES = Arrays.asList("User.Read.All");
	
	 final static DeviceCodeCredential deviceCodeCred = new DeviceCodeCredentialBuilder()
            .clientId("388650b0-1c3c-47b1-8677-7e9058573a2b")
            .tenantId("9187d23d-78c2-466f-b2de-110ddb706ca3")
            .challengeConsumer(challenge -> System.out.println(challenge.getMessage()))
            .build();
	
   final static TokenCredentialAuthProvider tokenCredAuthProvider = new TokenCredentialAuthProvider(SCOPES, deviceCodeCred);
   final static OkHttpClient httpClient = HttpClients.createDefault(tokenCredAuthProvider);
   
   public static void user()
   {
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
   
   public static void loginLogs()
   {
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
               		
               		//String anon= (String) object.get("riskEventTypes");
               		
               		JSONArray anonJ = (JSONArray) object.get("riskEventTypes");
               		String anon= anonJ.toJSONString();
               		
               		double arisk=anon.indexOf("anonymizedIPAddress");
               		
               		String Username = (String) object.get("userPrincipalName");
               		Username=Username.split("@")[0];
               		
               		double a=genericFunctions.getRiskLevel(Username, "Anonymous");
               		
               		if(arisk==-1)
               		{
               			if(a>0)
               				arisk=a-0.5;
               			
               			else
               				arisk=0.0;
               		}
               		
               		else 
               		{
               			arisk=5.0;
               		}
               		
               		if(Username.indexOf("#EXT#") != -1)
               		{
               			System.out.println("Cannot Add EXT User");
               			continue;
               		}
               		
               		User x=new User(Username);
               		
               		genericFunctions.addRisk(x, arisk, "Anonymous");
               		
               		Username = Username.substring(0, 1).toUpperCase() + Username.substring(1); 
               		String displayName = (String) object.get("userDisplayName");
       	            String createdDateTime = (String) object.get("createdDateTime");
       	            createdDateTime=createdDateTime.replace('T', ' ');
       	            createdDateTime=createdDateTime.substring(0, createdDateTime.length() - 1); 
       	            String ipAddress= (String) object.get("ipAddress");
       	            String clientAppUsed = (String) object.get("clientAppUsed");
       	            JSONObject deviceDetail = (JSONObject) object.get("deviceDetail");
       	            String device_operatingSystem= (String) deviceDetail.get("operatingSystem");
       	            String deviceID= (String) deviceDetail.get("deviceId");
       	            String device_Browser= (String) deviceDetail.get("browser");
       	            
       	            if(deviceID.length()>0)
       	            {
       	            	//no.add(Username);
       	            	genericFunctions.addRisk(x,0.0, "isRegistered");
       	            }
       	            
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
       	            
       	            User_LoginInfo userLogin= new User_LoginInfo(Username,displayName,createdDateTime,ipAddress,
	                    clientAppUsed,device_operatingSystem,device_Browser,device_isCompliant,device_isManaged,location_city,
	                    location_state,location_countryOrRegion,location_latitude,location_longitude,accessStatus,deviceID);
       	            
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
   
	public static void main(String args[])
	{
		Timer timer = new Timer();
		 timer.schedule(new TimerTask() {
		    @Override
		    public void run() 
		    { 
		    	user();
		    	
		    	try
		        {
		            Thread.sleep(1000 * 60);
		        }
		        catch(InterruptedException ex)
		        {
		            Thread.currentThread().interrupt();
		        }
		    	
		    	loginLogs();
		    	
		    	try
		        {
		            Thread.sleep(1000 * 60);
		        }
		        catch(InterruptedException ex)
		        {
		            Thread.currentThread().interrupt();
		        }
		    	
		    	updateRisk.updateRiskConfidence();
		    	Dashboard.addToDashboard();
		    	
		    }
		 }, 0, 1000 * 60 * 30);
	}
}
