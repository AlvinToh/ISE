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
		String msg = "";
		String searchText = request.getParameter("searchText").trim();

		AvatarDAO avatarDAO = new AvatarDAO();
		PostDAO postDAO = new PostDAO();
		HashMap<Integer, Post> returnSearchResults = postDAO.searchByKeyword(searchText);

		if (returnSearchResults == null || returnSearchResults.size() == 0) {
			msg = "Your search for " + searchText + " did not match any records";
			System.out.println(msg);
		}
	%>
	<div style="margin-top: 2%"></div>
	<div class="container text-center">
		<header>
			<h2>Search Results:</h2>
			<hr>
		</header>
	</div>

	<div class="row justify-content-md-center">
		<%
			if (msg != null && msg.length() > 0) {
				out.println("<div class='container' align='center' style='padding:0px;height:40px'>");
				out.println("<div class='alert alert-warning' style='width:400px'>");
				out.println("<font color='red'>");
				out.println(msg);
				out.println("</font>");
				out.println("</div>");
				out.println("</div><br>	");
			}
		%>

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

							<th width="15%" align="center">Avatar Name</th>
							<th width="20%" align="center">Post Title</th>
							<th width="52%" align="center">Post Content</th>
							<th width="13%" align="center">DateTime</th>


						</tr>
						<%
							if (returnSearchResults != null && returnSearchResults.size() != 0) {
								for (Integer key : returnSearchResults.keySet()) {
									Post post = returnSearchResults.get(key);
						%>
					</thead>
					<tbody>

						<tr>

							<td><%=avatarDAO.getAvatarName(post.getAvatar_id())%></td>
							<td><a href="viewPost.jsp?post_id=<%=post.getPost_id()%>"><%=post.getPost_title()%></a></td>

							<td><%=post.getPost_content()%></td>
							<td><%=post.getTimestamp()%></td>

						</tr>


					</tbody>




					<%
						}
						} // end for
						session.removeAttribute("searchResults");
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