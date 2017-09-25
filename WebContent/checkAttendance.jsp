<%@include file="protect.jsp"%>
<%@ page
	import="java.io.*,java.util.*, java.util.concurrent.*, utility.*, dao.*, entity.*"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>


<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>IS102 Web Platform</title>
<meta name="viewport" content="width=device-width, initial-scale=1">


<link rel="stylesheet" href="style/css/bootstrap.min.css">
<link rel="stylesheet" href="style/css/font-awesome.min.css">
<link rel="stylesheet" href="style/css/forumHomePageLayout.css">
</head>
<body>
	<%@ include file="navigationBar.jsp"%>
	<%
		//StudentDAO sd = new StudentDAO();
			//ArrayList<String> groups = sd.retrieveUniqueGroupID();
		ProfessorDAO profDAO = new ProfessorDAO();
		Professor prof = (Professor) session.getAttribute("professor");
		int profAvatar = prof.getAvatar_id();
		ArrayList<String> groups = profDAO.retrieveProfessorSections(profAvatar);

		ClassPartDAO cpDAO = new ClassPartDAO();
		HashMap<String, String> weeklyRecords = new HashMap<>();
		int no = 1;
		String groupId = "";
		int week = 0;
	%>
	<div style="margin-top: 2%"></div>
	<div class="container text-center">
		<header>
			<h2>Attendance Record</h2>
			<hr>
		</header>
	</div>

	<div class="row justify-content-md-center">

		<form class="form-horizontal" role="form" method="GET"
			action="viewAllAttendanceStatus.jsp">
			<div class="form-group">
				<div class="row">

					<div class="input-group-addon" style="width: 4rem; height: 2.6rem">
						<label>week: &nbsp</label>
					</div>

					<select name="week" id="week" required autofocus
						style="width: 10rem">
						<option value="">Select a week</option>
						<%
							for (int i = 1; i < 13; i++) {
								if (i != 8) {
									out.println("<option value='" + i + "'>" + i + "</option>");
								}

							}
						%>
					</select>

				</div>
				<br>
				<div class="row">

					<div class="input-group-addon" style="width: 5rem; height: 2.6rem">
						<label>Session: &nbsp</label>
					</div>

					<select name="session" id="session" required autofocus
						style="width: 9rem">
						<option value="">Select a session</option>
						<%
							for (int i = 0; i < groups.size(); i++) {
								
								out.println("<option value='" + groups.get(i) + "'>" + groups.get(i) + "</option>");
							}
						%>

					</select>

				</div>
				<br>
				<div class="row">

					<button type="submit" id="mySubmit" class="btn btn-primary"
						style="width: 14rem; height: 2.6rem">View Attendance
						Status</button>
				</div>
			</div>
		</form>
	</div>





	<script
		src="//cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
	<script src="style/js/jquery-3.2.1.min.js"></script>
	<script src="style/js/bootstrap.min.js"></script>
	<%@ include file="footer.jsp"%>
</body>


</html>