<%@include file="protect.jsp"%>
<%@ page
	import="java.io.*,java.util.*, java.util.concurrent.*, utility.*, entity.Post, dao.AvatarDAO, dao.PostDAO"%>
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
		String tempID = request.getParameter("avatar_id");
		System.out.println(tempID);
		int avatarID = Integer.parseInt(tempID);
        
		AvatarDAO avatarDAO= new AvatarDAO();
		PostDAO pd = new PostDAO();
		// retrieve all posts submitted by avatar
	    List<Integer> resultList = pd.retrieveAllPosts(avatarID);
		System.out.println(resultList.size());
		

	%>
	<div style="margin-top: 2%"></div>
	<div class="container text-center">
		<header>
			<h2>
				View all posts submitted by:
				<%=avatarDAO.getAvatarName(avatarID)%></h2>
			<hr>

		</header>
	</div>

	<div class="row justify-content-md-center">


		<div class="col-12 col-md-auto">

			<div class="row">

				<div class="col-2">
					<div class="btn-group" role="group" aria-label="Basic example">
						<a class="btn btn-outline-primary" style="width: 10rem"
							href="forumHome.jsp"><b>Back to Forum </b></a> 

					</div>

				</div>

			</div>
			<div class="scroll">

				<table class="table table-bordered">
					<thead class="thead-default">

						<tr>
							<th width="15%" align="center">Post Title</th>
							<th width="57%" align="center">Post Content</th>
							<th width="13%" align="center">Datetime</th>
							<th width="15%" align="center">Actions</th>

						</tr>												
					</thead>
					<tbody>
						<%
							if (resultList != null || resultList.size() != 0) {
								Post tempPost = null;
								for (Integer i : resultList) {

									tempPost = pd.retrievePostbyID(i);
									System.out.println(tempPost);
						%>

						<tr>				
							<td><%=tempPost.getPost_title()%></td>
							<td><%=tempPost.getPost_content()%></td>
							<td><%=tempPost.getTimestamp()%></td>
							<td>tbc</td>
						</tr>


					</tbody>




					<%
						}
						} // end for
					%>


				</table>
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