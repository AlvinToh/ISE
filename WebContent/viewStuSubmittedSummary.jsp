<%@include file="protect.jsp"%>
<%@ page
	import="java.io.*,java.util.*, java.util.concurrent.*, utility.*, entity.*, dao.*"%>
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
	    Professor professor = (Professor)session.getAttribute("professor");
		int avatar_id = professor.getAvatar_id();
		StudentSummaryDAO ssDAO = new StudentSummaryDAO();
		
		String emailID = request.getParameter("emailID");
		String week = request.getParameter("Week");
		int weekNo = Integer.parseInt(week);
		String section = request.getParameter("Group");
		
		
		HashMap <Integer, PostClassSummaryAnswer>  map = ssDAO.retriveStudentSummaryByEmailID(emailID, weekNo);
	%>
	
	<div style="margin-top: 2%"></div>
		<div class="container text-center">
		<header>
			<h2>Submitted Summary</h2>
			<hr>
		</header>


	</div>

	<div class="row justify-content-md-center">

		<div class="col-12 col-md-auto">
			<div class="row">

				<div class="col-2">
					<div class="btn-group" role="group" aria-label="Basic example">
						<a class="btn btn-outline-primary"
							style="width: 6rem; height: 2.4rem"
							href="viewSummaries.jsp?week=<%=weekNo%>&session=<%=section%>"><b>Back</b></a>

					</div>

				</div>

			</div>
			<div class="scrollSummary">

				<table class="table table-bordered">
					<thead class="thead-default">
						
						<tr>
						   <th width="5%">#</th>
							<th width="50%">Question</th>
							<th width ="45%">Answer</th>						

						</tr>
						<%
							for (Integer key : map.keySet()) {

								PostClassSummaryAnswer tempRecord = map.get(key);
						%>
					</thead>
					<tbody>

						<tr>
							<td><%=tempRecord.getQuestion_id()%></td>
							<td><%=tempRecord.getQuestion_hist()%></td>
							<td><%=tempRecord.getAnswer()%></td>

						</tr>


					</tbody>




					<%
									} // end for
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