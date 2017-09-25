<%@include file="protect.jsp"%>
<%@ page
	import="java.io.*,java.util.*, java.util.concurrent.*, utility.*, entity.Post, dao.AvatarDAO,dao.PostDAO"%>
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
		PostDAO pd = new PostDAO();
		HashMap<Integer, Post> map = pd.retrieveAll();
		AvatarDAO avatarDAO= new AvatarDAO();
	%>
	<div style="margin-top: 2%"></div>
	<div class="container text-center">
		<header>
			<h2>CAT Forum</h2>
			<hr>
		</header>


	</div>


	<div class="row justify-content-md-center">


		<div class="col-12 col-md-auto">
			<div class="row">

				<div class="col-2">
					<div class="btn-group" role="group" aria-label="Basic example">
						<a class="btn btn-outline-primary" style="width: 12rem; height: 2.4rem"
							href="newPost.jsp"><b>Post a New Question</b></a>
						<form method="post" action="searchResults.jsp">
							<div class="input-group">
								<input type="text" style="width: 14rem" class="form-control"
									name= "searchText" placeholder="Search for..."> <span
									class="input-group-btn">
									<button class="btn btn-secondary" type="submit">Go!</button>
								</span>

							</div>
						</form>
					</div>

				</div>

			</div>
			<div class="scroll">

				<table class="table table-bordered">
					<thead class="thead-default">

						<tr>
							<th width="54%">Post Title</th>
							<th width="10%">Avatar Name</th>
							<th width="10%">QA Coins</th>
							<th width="5%">Votes</th>
							<th width="11%">Datetime</th>
							<th width="10%">Actions</th>

						</tr>
						<%
							for (Integer key : map.keySet()) {
								Post post = map.get(key);
						%>
					</thead>
					<tbody>

						<tr>
							<td><a href="viewPost.jsp?post_id=<%=post.getPost_id()%>"><%=post.getPost_title()%></a></td>
							<th><a href="viewYourPosts.jsp?avatar_id=<%=post.getAvatar_id()%>"><%=avatarDAO.getAvatarName(post.getAvatar_id())%></a></th>
							<td>20</td>
							<td>20/20</td>
							<td><%=post.getTimestamp().toString().replaceAll("\\.\\d+", "")%></td>
							<td><a href="replyToPost.jsp?post_id=<%=post.getPost_id()%>">Reply</a>
							</td>

						</tr>


					</tbody>




					<%
						} // end for
					%>


				</table>
			</div>
		</div>
		<div class="col col-lg-2">
			.
			<table class="table table-bordered">
				<thead class="thead-default">
					<tr>
						<th>will add topic and avatar content</th>



					</tr>
					<%
						for (Integer key : map.keySet()) {
							Post post = map.get(key);
					%>
				</thead>
				<tbody>

					<tr>
						<th><%=post.getAvatar_id()%></th>
					</tr>


				</tbody>




				<%
					} // end for
				%>


			</table>
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