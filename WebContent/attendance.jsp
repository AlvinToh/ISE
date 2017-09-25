<%@include file="protect.jsp"%>
<%@ page import = "java.io.*,java.util.*, utility.*" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>IS102 Web Platform</title>
<meta name="viewport" content="width=device-width, initial-scale=1">


<link rel="stylesheet" href="style/css/bootstrap.min.css">
<link rel="stylesheet" href="style/css/font-awesome.min.css">

<%@ page language="java" import="entity.Professor,dao.ProfessorDAO" %>
<%
 ProfessorDAO profDAO = new ProfessorDAO();
 Professor prof = (Professor)session.getAttribute("professor");
 int profAvatar = prof.getAvatar_id();
 ArrayList<String> groups = profDAO.retrieveProfessorSections(profAvatar);  

%>

</head>
<body>
<%@ include file="navigationBar.jsp"%>

	<div style="margin-top: 2%"></div>
	<div class="container text-center">
		<header>
			<h2>Generate a dynamic QR Code</h2>
			<hr>
		</header>
		<form class="form-horizontal" role="form" method="GET"
			action="QRcode.jsp">
			<div class="form-group">
				<div class="row">
					<div class="col-md-5"></div>
					<div class="input-group-addon" style="width: 4rem;height: 2.6rem">
						<label>week: &nbsp</label>
					</div>

					<select name="week" id="week" required autofocus style="width: 10rem"
						onChange="checkWeek()">
						<option value="">Select a week</option>
						<%
							for (int i = 1; i <= 13; i++) {
								if (i != 8) {
									out.println("<option value='" + i + "'>" + i + "</option>");
								}

							}
						%>
					</select>

				</div>
				<br>
				<div class="row">
					<div class="col-md-5"></div>
					<div class="input-group-addon" style="width: 5rem;height: 2.6rem">
						<label>Session: &nbsp</label>
					</div>

					<select name="session" id="session" required autofocus style="width: 9rem"
						onChange="checkSession()">
						<option value="">Select a session</option>
						<%
							for (int i = 0; i < groups.size(); i++) {

								out.println("<option value='" + groups.get(i).substring(1) + "'>" + groups.get(i) + "</option>");
							}
						%>

					</select>

				</div>
				<br>
				<div class="row">
					<div class="col-md-5" style="width: 5rem;height: 2.6rem"></div>
					<button type="submit" id="mySubmit" class="btn btn-primary"
						name="QRcode" value="QRcode" disabled style="width: 14rem">Generate your QR
						code</button>
				</div>
			</div>
		</form>
	</div>



	<script src="//cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
	<script src="style/js/jquery-3.2.1.min.js"></script>
	<script src="style/js/bootstrap.min.js"></script>
<%@ include file = "footer.jsp" %>
</body>

<script>
	var week;
	var session;

	function checkWeek() {
		week = document.getElementById("week").value;
		if (week == "") {
			alert("Please Select a week");
			document.getElementById("mySubmit").disabled = true;

		} else {
			alert("You have selected " + week + " !");
		}

	}
	function checkSession() {
		session = document.getElementById("session").value;
		if (session == "") {
			alert("Please Select a session");
			document.getElementById("mySubmit").disabled = true;
		} else {
			alert("You have selected " + session + " !");
			document.getElementById("mySubmit").disabled = false;
		}

	}
	

</script>

</html>