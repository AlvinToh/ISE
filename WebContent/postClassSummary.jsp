<%@include file="protect.jsp"%>
<%@ page
	import="java.io.*,java.util.*, java.util.concurrent.*, utility.*, entity.Professor, dao.SummaryDAO"%>
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
		SummaryDAO sdao = new SummaryDAO();
		HashMap<Integer, String> map = sdao.retrieveByAvatar(avatar_id);
	%>
	
	<div style="margin-top: 2%"></div>
		<div class="container text-center">
		<header>
			<h2>Post Class Summary</h2>
			<hr>
		</header>


	</div>

	<div class="row justify-content-md-center">

		<div class="col-12 col-md-auto">

			<div class="row">

				<div class="col-12">
					<a style="width: 12rem; height: 2.4rem"
						href="stuSubmittedSummary.jsp"><b>View Submitted Summary</b></a>
					<div class="btn-group" role="group" aria-label="Basic example">
						<a href="addSummaryQuestion.jsp" class="btn btn-outline-primary"
							style="width: 12rem; height: 2.4rem"><b>Add New
								Question</b></a>
					</div>
				</div>

			</div>
			<div class="scroll">

				<table class="table table-bordered">
					<thead class="thead-default">

						<tr>
							<th width="15%">No</th>
							<th width="70%">Question</th>
							<th width="15%" colspan='2'>Actions</th>

						</tr>
						<%
							int no = 1;
							for (Integer key : map.keySet()) {
								 String question = map.get(key);
						%>
					</thead>
					<tbody>

						<tr>
							<th><%=no++%></th>
							<td><%=question%></td>
							<td><a href="editSummaryQuestion.jsp?q_id=<%=key%>">Edit</a></td>
							<td><a href="deleteSummaryQuestion.jsp?q_id=<%=key%>">Delete</a></td>
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