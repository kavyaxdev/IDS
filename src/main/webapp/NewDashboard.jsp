<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<! DOCTYPE html>  
<html lang="en">  
<head>  
  <title> Bootstrap 4 datatable example </title>  
  <meta charset="utf-8">  
  <meta name="viewport" content="width=device-width, initial-scale=1">  
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css">  
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"> </script>  
  <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"> </script>  
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.1.3/js/bootstrap.min.js"> </script>  
  <link rel="stylesheet" href="https://cdn.datatables.net/1.10.13/css/jquery.dataTables.min.css">   
  <script src="https://cdn.datatables.net/1.10.23/js/jquery.dataTables.min.js"> </script>  
   <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" >  
</head>  
<style>  
body {  
  margin: 0;  
    min-height: 100vh;  
  padding: 0;  
  background-color: var(--clr-light);  
  color: var(--clr-black);  
  font-family: 'Poppins', sans-serif;  
  font-size: 1.125rem;  
  justify-content: center;  
  align-items: center;  
}  
h2 {  
font-family: 'Georgia', cursive;  
font-size: 32px;
  font-weight: bold;  
  text-align: center;  
  padding: 20px 0;  
}  

h3 {  
font-family: 'Georgia', cursive;  
  font-weight: bold;  
  text-align: center;
}  
  
table caption {  
    padding: .5em 0;  
}  
  
table.dataTable th  
{  
  white-space: nowrap;  
}  
table.dataTable td {  
  white-space: nowrap;  
}  
.p {  
  text-align: center;  
  padding-top: 140px;  
  font-size: 14px;  
}  

td {
  text-align: center;
}

</style> 

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page import="java.util.*,org.json.simple.parser.JSONParser,org.json.simple.JSONArray,org.json.simple.JSONObject" %>
<%@ page import="java.io.BufferedReader,java.io.InputStreamReader" %>
<%@ page import="java.net.HttpURLConnection,java.net.URL" %>

<body>  
<h2> Defending Identity Based Threats </h2>
<h3> Admin Dashboard</h3>  
  
<div class="container">  
  <div class="row">  
    <div class="col-xs-12">  
      <table summary="This table shows how to create responsive tables using Datatables' extended functionality" class="table table-bordered table-hover dt-responsive">  
    <thead>
      <tr>
        <th>Username</th>
        <th>Devices</th>
        <th>Location</th>
        <th>Number of <br> Login <br> Attempts</th>
        <th>Confidence <br> Level</th>
        <th>Risk <br> Level</th>
        <th>Number of <br> Violations</th>
        <th>More Information</th>
        <th>Action</th>
      </tr>
    </thead>
    
    <%
		 List<ArrayList<Object>> aList = new ArrayList<ArrayList<Object>>();
		try
		{
	    	String URLvalue="http://localhost:8080/IdentityThreats/rest/getDashboard";
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
			
			String dashboardVals=res.toString();
			
			JSONParser parser = new JSONParser();
			JSONArray userLogs= (JSONArray) parser.parse(dashboardVals);
			
			int n=userLogs.size();
			
			for(int i=0;i<n;i++)
			{
				JSONObject log1 = (JSONObject) userLogs.get(i);
				ArrayList<Object> x=new ArrayList<Object>();
				x.add((String)log1.get("username"));
				x.add((String)log1.get("device"));
				x.add((String)log1.get("location"));
				x.add((long)log1.get("loginattempts"));
				x.add((double)log1.get("confidence"));
				x.add((double)log1.get("risk"));
				x.add((long)log1.get("violations"));
				aList.add(x);
			}
			
		}
	   	
	   	catch(Exception e)
	   	{
	   		System.out.println(e);
	   	}
		
    %>
   	
   		<tbody id="myTable">

        <% for (int i = 0; i < aList.size(); i++) { 
           ArrayList<Object> rowObj = aList.get(i);
        %>
        <tr id=<%=i+1 %>>

            <% for (int j = 0; j < 7; j++) {
               Object cell = rowObj.get(j); 
            %>
            <td class="row-data">
                   <%=cell.toString()%>
            </td>
            
            <% } %>
            
            <td><button type="button" class="btn btn-danger" onClick=show()>Examine</button></td>
            <td><button type="button" class="btn btn-danger" onClick=mitigate()>Mitigate</button></td>
        </tr>
      
        <% } %>
        
   		</tbody>
        <tfoot>  
          
     </tfoot>  
      </table>  
    </div>  
  </div>  
</div>  
<script>  
$('table').DataTable();  

function show()
{
	var rowId = event.target.parentNode.parentNode.id
    var data = document.getElementById(rowId).querySelectorAll(".row-data"); 
  
    var name = data[0].innerHTML;
    name=name.trim();
    
    window.open("examine.jsp?uname=" + name);
}

function mitigate()
{
	var rowId = event.target.parentNode.parentNode.id
    var data = document.getElementById(rowId).querySelectorAll(".row-data"); 
  
    var name = data[0].innerHTML;
    name=name.trim();
    
    name=name+"@dishipes1outlook.onmicrosoft.com";
    
    window.open("mitigate.jsp?uname=" + name);
}

</script>  
</body>  
</html>  