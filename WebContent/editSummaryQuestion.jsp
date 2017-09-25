<%@include file="protect.jsp"%>
<%@ page
	import="java.io.*,java.util.*, java.util.concurrent.*, utility.*, entity.Post, dao.*"%>
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
		String id = request.getParameter("q_id");
		int question_id = Integer.parseInt(id);

		SummaryDAO sdao = new SummaryDAO();
		String oldQuestion = sdao.retrieveByQID(question_id);
	%>

	<div style="margin-top: 2%"></div>
	<div class="container text-center">
		<header>
			<h2>Post Class Summary Questions</h2>
			<hr>
		</header>
		<div class="row">

			<div class="col-1">
				<div class="btn-group" role="group" aria-label="Basic example">
					<a class="btn btn-outline-primary" style="width: 6rem"
						href="postClassSummary.jsp"><b>Back </b></a>

				</div>

			</div>

		</div>
		<div class="viewPostBoarder">
			<br>
			<div class="container">
				<form name="replyForm" method="post" action="EditSummaryQuestion">
					<input type="text" name="q_id" value="<%=question_id%>" hidden />
					<div class="form-group row">
						<label for="inputEmail3" class="col-sm-2 col-form-label"><strong>Question</strong></label>
						<div class="col-sm-9">
							<input type="text" name="editQuestion" class="form-control"
								id="inputEmail3" value="<%=oldQuestion%>" required />

						</div>
					</div>

					<div class="form-group row">
						<div class="offset-sm-4 col-sm-6">
							<input type="submit" class="btn btn-primary" value="Submit">
						</div>
					</div>
				</form>
			</div>
		</div>

	</div>

</body>
</html>