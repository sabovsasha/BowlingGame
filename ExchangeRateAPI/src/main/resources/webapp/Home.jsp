<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login page</title>
</head>
<body>
    Login
	<form action="authenticate" method="post">
		<input type="text" name="name"><br>
		<!--input type="password" name="password"><br-->
		<input type="text" name="password"><br>
		<input type="submit"><br> <br> <br> <br>
	</form>
	
	Create user
	<form action="createUser" method="post">		
		<input type="text" name="name"><br> 
		<input type="text" name="password"><br>
		<input type="submit"><br> <br> <br> <br>
	</form>
	
	Unblock user
	<form action="unlockUser" method="post">		
		<input type="text" name="name"><br>		
		<input type="submit">
	</form>

	<%
	if (request.getAttribute("message") != null) {
	%>
	<script type="text/javascript">
	   alert("${message}");
	</script>
	<%
	}
	%>


</body>
</html>