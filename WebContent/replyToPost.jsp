<%@include file="protect.jsp"%>
<%@ page
	import="java.io.*,java.util.*, java.util.concurrent.*, utility.*, entity.Post, dao.PostDAO"%>
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
		String tempID = request.getParameter("post_id");
		int postID = Integer.parseInt(tempID);

		PostDAO pd = new PostDAO();
		Post parentPost = pd.retrieveParentPost(postID);

		int lastPostID = pd.lastPostID(postID);

		String errorMsgs = (String) request.getAttribute("replyToPost");
	%>
	<div style="margin-top: 2%"></div>

	<div class="container text-center">

		<header>
			<h2>Reply to Post: <%=parentPost.getPost_title() %> </h2>
			<hr>
		</header>
		<div class="row justify-content-md-left">
			<div class="col-12 col-md-auto">

				<div class="row">

					<div class="col-2">
						<div class="btn-group" role="group" aria-label="Basic example">
							<a class="btn btn-outline-primary" style="width: 10rem"
								href="viewPost.jsp?post_id=<%=postID%>"><b>Back to Post</b></a>
								
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="viewPostBoarder">
			<br>


			<div class="container">

				<%
					if (errorMsgs != null && errorMsgs.length() > 0) {
						out.println("<div class='container' align='center' style='padding:0px;height:40px'>");
						out.println("<div class='alert alert-warning' style='width:400px'>");
						out.println("<font color='red'>");
						out.println(errorMsgs);
						out.println("</font>");
						out.println("</div>");
						out.println("</div><br>	");
					}
					request.removeAttribute("replyToPost");
				%>

				<form name="replyForm" method="post" action="ReplyToPost">
					<input type="text" name="postID" value="<%=postID%>" hidden />
					<div class="form-group row">
						<label for="inputEmail3" class="col-sm-2 col-form-label"><strong>Post
								Title</strong></label>
						<div class="col-sm-9">
							<input type="text" name="postTitle" class="form-control"
								id="inputEmail3" value="<%=parentPost.getPost_title()%>"
								readonly />
						</div>
					</div>
					<div class="form-group row">
						<label for="inputEmail3" class="col-sm-2 col-form-label"><strong>Post
								Content</strong></label>
						<div class="col-sm-9">
							<textarea name="postContent" class="form-control"
								id="inputEmail3" readonly> <%=parentPost.getPost_content()%></textarea>

						</div>
					</div>
					<div class="form-group row">
						<label for="inputEmail3" class="col-sm-2 col-form-label"><strong>Post
								Content</strong></label>
						<div class="col-sm-9">
							<textarea name="postContent" class="form-control"
								id="inputEmail3" placeholder="Enter your reply here"></textarea>

						</div>
					</div>

					<div class="form-group row">
						<div class="offset-sm-2 col-sm-8">

							<input type="submit" class="btn btn-primary" value="Submit">
						</div>
					</div>
				</form>
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