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
		StudentDAO sd = new StudentDAO();
		//ArrayList<String> groups = sd.retrieveUniqueGroupID();
		ClassPartDAO cpDAO = new ClassPartDAO();

		ProfessorDAO profDAO = new ProfessorDAO();
		Professor prof = (Professor) session.getAttribute("professor");
		int profAvatar = prof.getAvatar_id();
		ArrayList<String> groups = profDAO.retrieveProfessorSections(profAvatar);

		int no = 1;

		String week = request.getParameter("week");
		int weekNo = 0;
		if (week != null) {
			weekNo = Integer.parseInt(week);
		}
		String section = request.getParameter("session");

		HashMap<String, String> weeklyRecords = cpDAO.retrieveWeeklyClassPart(section, weekNo);
	%>
	<div style="margin-top: 2%"></div>
	<div class="container text-center">
		<header>
			<h2>Attendance Record</h2>
			<hr>
		</header>
	</div>

	<div class="row justify-content-md-center">

		<div class="col-12 col-md-auto">
			<div class="row">
				<div class="col-2">
					<div class="btn-group" role="group" aria-label="Basic example">
						<a class="btn btn-outline-primary" style="width: 6rem"
							href="checkAttendance.jsp"><b>Back</b></a>
					</div>
				</div>
				
			</div>
			<div class="scroll">

				<table class="table table-bordered">
					<thead class="thead-default">

						<tr>
							<th width="10%">S/N</th>
							<th width="40%">SMU Email</th>
							<th width="10%">Week</th>
							<th width="20%">Section</th>
							<th width="20%">Status</th>
						</tr>
						<%
							if (groups != null && !groups.isEmpty()) {

								if (weeklyRecords != null && !weeklyRecords.isEmpty()) {

									for (String smu_email_id : weeklyRecords.keySet()) {
										String status = weeklyRecords.get(smu_email_id);
						%>
					</thead>
					<tbody>

						<tr>
							<td><%=no++%></td>
							<td><a
								href="viewIndividualAttendance.jsp?week=<%=weekNo%>&session=<%=section%>&smu_email_id=<%=smu_email_id%>"><%=smu_email_id%></a></td>
							<td><%=week%></td>
							<td><%=section%></td>
							<td><%=status%></td>

						</tr>


					</tbody>

					<%
						} //end for
							}
						}
					%>
				</table>
				<%
			   // System.out.println(groups +"********Retrieve Group = 0************");
				if(groups == null || groups.isEmpty()){
					out.println("<div class='container' align='center' style='padding:0px;height:40px'>");
					out.println("<div class='alert alert-warning' style='width:400px'>");
					out.println("<font color='red'>");
					out.println("Please upload the class list");
					out.println("</font>");
				}
				
				
				%>
			</div>
		</div>
	</div>

	
	<script
		src="//cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
	<script src="style/js/jquery-3.2.1.min.js"></script>
	<script src="style/js/bootstrap.min.js"></script>
	<%@ include file="footer.jsp"%>
</body>


</html>