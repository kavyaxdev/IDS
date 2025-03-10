package net.codejava.Graph;

import com.azure.identity.DeviceCodeCredential;
import com.azure.identity.DeviceCodeCredentialBuilder;
import com.microsoft.graph.authentication.TokenCredentialAuthProvider;
import com.microsoft.graph.httpcore.HttpClients;
import okhttp3.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class userMitigation {
	
	static String auth;
	
	private final static List<String> SCOPES = Arrays.asList("User.Read.All","GroupMember.ReadWrite.All","Group.ReadWrite.All",
			"AuditLog.Read.All");
	
	public static void addUser(String username)
	{
			final DeviceCodeCredential deviceCodeCred = new DeviceCodeCredentialBuilder()
	                 .clientId("388650b0-1c3c-47b1-8677-7e9058573a2b")
	                 .tenantId("9187d23d-78c2-466f-b2de-110ddb706ca3")
	                 .challengeConsumer(challenge -> {
	                	 System.out.println(challenge.getMessage());
	                 })
	                 .build();
	    	
	        final TokenCredentialAuthProvider tokenCredAuthProvider = new TokenCredentialAuthProvider(SCOPES, deviceCodeCred);
	        final OkHttpClient httpClient = HttpClients.createDefault(tokenCredAuthProvider);
	        
	        String json = "{\"@odata.id\":\"https://graph.microsoft.com/v1.0/users/" + username + "\"}";
	        
	        RequestBody body = RequestBody.create(json,
	        	      MediaType.parse("application/json"));

	        final Request request = new Request.Builder()
	        		.url("https://graph.microsoft.com/v1.0/groups/8365676c-0f96-4a40-b982-b488ffa006d9/members/$ref")
	        		.post(body)
	        		.build();

	        try {
	            Response response = httpClient.newCall(request).execute();
	           
	            // Do something with the response.
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        
	}
	
	public static void main(String args[])
	{
		addUser("admin@dishipes1outlook.onmicrosoft.com");
	}
}
