<%@include file="protect.jsp"%>
<%@ page
	import="java.io.*,java.util.*, java.util.concurrent.*, utility.*, dao.ClassPartDAO, dao.StudentDAO"%>
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
		String smu_email_id = request.getParameter("smu_email_id");
		ClassPartDAO cpDAO = new ClassPartDAO();
		ArrayList<Integer> indiRecords = cpDAO.retrieveIndiClassPart(smu_email_id);
		int[] weeks = { 1, 2, 3, 4, 5, 6, 7, 9, 10, 11, 12, 13 };
		String status = "";
		
		String tempWeek = request.getParameter("week");
		int weekNo = 0;
		if (tempWeek != null) {
			weekNo = Integer.parseInt(tempWeek);
		}
		String section = request.getParameter("session");
	%>
	<div style="margin-top: 2%"></div>
	<div class="container text-center">
		<header>
			<h2>Individual Attendance Record</h2>
			<hr>
		</header>
	</div>

	<div class="row justify-content-md-center">

		<div class="col-14 col-md-auto">
			<div class="row">
				<div class="col-2">
					<div class="btn-group" role="group" aria-label="Basic example">
						<a class="btn btn-outline-primary" style="width: 6rem"
							href="viewAllAttendanceStatus.jsp?week=<%=weekNo%>&session=<%=section%>"><b>Back</b></a>
					</div>
				</div>

			</div>

			<div class="scroll">

				<table class="table table-bordered">
					<thead class="thead-default">

						<tr>
							<th>Week</th>
							<th>Status</th>
						</tr>

					</thead>
					<tbody>
						<%
						for (int week : weeks) {
							out.println("<tr><td>" + week + "</td>");
							if (indiRecords != null && !indiRecords.isEmpty()&&indiRecords.contains(week)) {
								status = "present";
								out.println("<td><font color='green'>present</font></td></tr>");
							} else {
								status = "absent";
								out.println("<td><font color='red'>absent</font></td>");
							}
						%>


					</tbody>

					<%
						} //end for
						
					%>

				</table>
				
			
			</div>
		</div>
	</div>
	<div class="col-8"></div>


	
	<script
		src="//cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
	<script src="style/js/jquery-3.2.1.min.js"></script>
	<script src="style/js/bootstrap.min.js"></script>
	<%@ include file="footer.jsp"%>
</body>


</html>