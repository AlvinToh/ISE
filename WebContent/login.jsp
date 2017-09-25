<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>IS102 Web Platform</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="style/css/bootstrap.min.css">
<link rel="stylesheet" href="style/css/font-awesome.min.css">

</head>
<body>
	<nav class="navbar navbar-toggleable-md navbar-light bg-faded">
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarTogglerDemo01"
			aria-controls="navbarTogglerDemo01" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarTogglerDemo01">
			<h3><a class="navbar-brand" href="login.jsp">IS102</a></h3>
			<h3><a class="navbar-brand" href="register.jsp">Register</a></h3>					
		</div>
		
	</nav>
	<div>
	
	</div>
	<%
		String errorMsgs = (String) request.getAttribute("error");
		if (errorMsgs != null && errorMsgs.length() > 0) {
			out.println("<div class='container' align='center' style='padding:0px;height:40px'>");
			out.println("<div class='alert alert-warning' style='width:400px'>");
			out.println("<font color='red'>");
			out.println(errorMsgs);
			out.println("</font>");
		}
		request.removeAttribute("error");
		out.println("</div>");
		out.println("</div>");
		
		String successfulMsg = (String) session.getAttribute("successfulMsg");
		if (successfulMsg != null && successfulMsg.length() > 0) {
			out.println("<div class='container' align='center' style='padding:0px;height:40px'>");
			out.println("<div class='alert alert-warning' style='width:400px'>");
			out.println("<font color='blue'>");
			out.println(successfulMsg);
			out.println("</font>");
		}
		session.removeAttribute("successfulMsg");
		out.println("</div>");
		out.println("</div>");
		
		
	%>
	<div style="margin-top: 2%"></div>
	<div class="container text-center">
		<header>
			<h2>Login with SMU Email Account</h2>
			<hr>
		</header>
		<form class="form-horizontal" role="form" method="POST" action="Authenticate">
			<div class="row">
				<div class="col-md-4"></div>
				<div class="form-group">
					<!-- sr only means should be shown to readers of similar devices -->
					
					<div class="input-group mb-2 mr-sm-2 mb-sm-0">
						<div class="input-group-addon" style="width: 2.6rem">
							<i class="fa fa-envelope-o"></i>
						</div>
						<input type="text" name="emailID" class="form-control" id="emailID"
							placeholder="Insert your email ID" required autofocus size="34">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4"></div>
				<div class="form-group">
					<!-- sr only means should be shown to readers of similar devices -->
								<!-- setting margin bottom and margin right for user id -->
					<div class="input-group mb-2 mr-sm-2 mb-sm-0">
						<!-- importing fonts awesome into login page -->
						<div class="input-group-addon" style="width: 2.6rem">
							<i class="fa fa-key"></i>
						</div>
						<input type="password" name="password" class="form-control"
							id="password" placeholder="Insert your password" required
							autofocus size="34">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="col-md-4"></div>
				<button type="submit" class="btn btn-success">
					<i class="fa fa-sign-in"></i>Login
				</button>
				
				<a class="btn btn" href="/passwordRecovery"><i
					class="fa fa-unlock"></i>Forget Password?
				</a>
			</div>
		</form>
	</div>

<script src="//cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
<script src="style/js/jquery-3.2.1.min.js"></script>
<script src="style/js/bootstrap.min.js"></script>
<%@ include file = "footer.jsp" %>
</body>

</html>