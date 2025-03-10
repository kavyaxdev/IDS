<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    import="net.codejava.Graph.userMitigation" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>

<%@ page import="com.azure.identity.DeviceCodeCredential,com.azure.identity.DeviceCodeCredentialBuilder,
com.microsoft.graph.authentication.TokenCredentialAuthProvider,com.microsoft.graph.httpcore.HttpClients,okhttp3.*,
java.io.IOException,java.util.Arrays,java.util.List" %>

<body>
	<%String uname = request.getParameter("uname").toString(); %>
	<%userMitigation.addUser(uname); %>
	<h1>User added to risky group</h1>
</body>
</html>