<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="Algorithms.genericFunctions"%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
</head>

<style>
	table {
    border-collapse: collapse;
    width: 45%;
    align: "center";
    font-family: Garamond, serif;
    font-size:14px;
}

tr {
    border-bottom: 1px solid #ccc;
}

body
{
	background-image: url("/IdentityThreats/bgImg");
	background-repeat:no-repeat;
	background-size:cover;
}

</style>

<script
	src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.4/Chart.js">
</script>
	
	<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
	<%@ page import="java.util.*,org.json.simple.parser.JSONParser,org.json.simple.JSONArray,org.json.simple.JSONObject" %>
	<%@ page import="java.io.BufferedReader,java.io.InputStreamReader" %>
	<%@ page import="java.net.HttpURLConnection,java.net.URL" %>
	
<body>
	<br><br><br>
	  <%String uname = request.getParameter("uname").toString(); %>
	  
	  <%
		  	String mail = "";
			String firstname = "";
			String lastname = "";
			long phno=0L;
			double travel = 0.0;
			double location = 0.0;
			double browser = 0.0;
			double device = 0.0;
			double password = 0.0;
			double anon = 0.0;
			double isReg=5.0;
			String isAnon=" ";
			String isRegis=" ";
		try
		{
	    	String URLvalue="http://localhost:8080/IdentityThreats/rest/getUserRiskDetails/" + uname;
			URL url = new URL(URLvalue);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Content-Type", "text/plain; charset=utf-8");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}
			
			BufferedReader in = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));
			String inputLine;
			StringBuffer res = new StringBuffer();

			while ((inputLine = in.readLine()) !=null) {
				res.append(inputLine);
			}
			in.close();
			
			String userInfo=res.toString();
			
			JSONParser parser = new JSONParser();
			JSONObject userinfoObj= (JSONObject) parser.parse(userInfo);
			
			mail = (String)userinfoObj.get("mail");
			firstname = (String)userinfoObj.get("firstname");
			lastname = (String)userinfoObj.get("lastname");
			travel = (double)userinfoObj.get("travel");
			location = (double)userinfoObj.get("location");
			browser = (double)userinfoObj.get("browser");
			device = (double)userinfoObj.get("device");
			password = (double)userinfoObj.get("password");
			anon = (double)userinfoObj.get("anon");
			phno= (long)userinfoObj.get("phonenumber");
			double reg=genericFunctions.getRiskLevel(uname,"isRegistered");
			
			if(reg!=5)
				isRegis="Yes";
			
			else
				isRegis="No";
			
			if(anon!=0)
				isAnon="Yes";
			
			else
				isAnon="No";
		}
	   	
	   	catch(Exception e)
	   	{
	   		System.out.println(e);
	   	}
		
    %>
    
    <div align="center">
    	<table>
		    <tr>
		    	<td><strong>User Information</strong></td>
		    </tr>
		    <tr>
		    	<td><i class="glyphicon glyphicon-user"></i>&emsp;<strong>Username</strong></td>
		    	<td style="color:blue;"><%=uname %></td>
		    </tr>
		    <tr>
		    	<td><i class="glyphicon glyphicon-envelope"></i>&emsp;<strong>Mail</strong></td>
		    	<td style="color:blue;"><%=mail %></td>
		    </tr>
		    <tr>
	    		<td><i class="glyphicon glyphicon-asterisk"></i>&emsp;<strong>First Name</strong></td>
	    		<td style="color:blue;"><%=firstname %></td>
	    	</tr>
	    	<tr>
	    		<td><i class="glyphicon glyphicon-cloud"></i>&emsp;<strong>Last Name</strong></td>
	    		<td style="color:blue;"><%=lastname %></td>
	    	</tr>
	    	<tr>
	    		<td><i class="glyphicon glyphicon-phone"></i>&emsp;<strong>Phone number</strong></td>
	    		<td style="color:blue;"><%=phno %></td>
	    	</tr>
	    	<tr>
	    		<td><i class='glyphicon glyphicon-eye-close'></i>&emsp;<strong>Anonymous Login</strong></td>
	    		<td style="color:blue;"><%= isAnon %></td>
	    	</tr>
	    	<tr>
	    		<td><i class='glyphicon glyphicon-list-alt'></i>&emsp;<strong>Registered Device</strong></td>
	    		<td style="color:blue;"><%= isRegis %></td>
	    	</tr>
    	</table>
    	
    	<br><br><br><br>
    
    <canvas id="horizontalBarChartCanvas" style="width:100%;max-width:600px"></canvas>
    
    <script>
    Chart.defaults.global.defaultFontFamily = "Lato";

    var horizontalBarChart = new Chart(horizontalBarChartCanvas, {
       type: 'horizontalBar',
       data: {
          labels: ["Travel Risk", "Location Risk", "Browser Risk", "Device Risk", "Password Risk"],
          datasets: [{
             data: [<%= travel%>, <%= location%>, <%= browser%>, <%= device%>, <%= password%>],
             backgroundColor: ["#DC143C", "#DC143C", "#DC143C", "#DC143C", "#DC143C", "#DC143C", "#DC143C"], 
          }]
       },
       options: {
          tooltips: {
            enabled: false
          },
          responsive: true,
          legend: {
             display: false,
             position: 'bottom',
             fullWidth: true,
             labels: {
               boxWidth: 10,
               padding: 50
             }
          },
          scales: {
             yAxes: [{
               barPercentage: 0.75,
               gridLines: {
                 display: true,
                 drawTicks: true,
                 drawOnChartArea: false
               },
               ticks: {
                 fontColor: '#555759',
                 fontFamily: 'Lato',
                 fontSize: 11
               }
                
             }],
             xAxes: [{
                 gridLines: {
                   display: true,
                   drawTicks: false,
                   tickMarkLength: 5,
                   drawBorder: false
                 },
               ticks: {
                 padding: 5,
                 beginAtZero: true,
                 fontColor: '#555759',
                 fontFamily: 'Lato',
                 fontSize: 11,
                 callback: function(label, index, labels) {
                  return label;
                 }
                   
               },
                scaleLabel: {
                  display: true,
                  padding: 10,
                  fontFamily: 'Lato',
                  fontColor: '#555759',
                  fontSize: 16,
                  fontStyle: 700,
                  labelString: 'Risk Scores(0-5)'
                },
               
             }]
          },
          
          tooltips: {
              mode: 'index',
              intersect: false
           },
           hover: {
              mode: 'index',
              intersect: false
           }
       }
    });
	</script>

    </div>
</body>
</html>