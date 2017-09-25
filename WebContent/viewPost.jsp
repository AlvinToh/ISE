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
		String tempID = request.getParameter("post_id");
		System.out.println(tempID);
		int postID = Integer.parseInt(tempID);
        
		AvatarDAO avatarDAO= new AvatarDAO();
		PostDAO pd = new PostDAO();
		// retrieve all posts that have same parent ID
		HashMap<Integer, Post> map = pd.retrieveAPost(postID);
		List<Integer> subPostIDList = pd.getSubPostIDRecursion(postID);

		Post parentPost = pd.retrieveParentPost(postID);
	%>
	<div style="margin-top: 2%"></div>
	<div class="container text-center">
		<header>
			<h2>
				View Post:
				<%=parentPost.getPost_title()%></h2>
			<hr>

		</header>
	</div>

	<div class="row justify-content-md-center">


		<div class="col-12 col-md-auto">

			<div class="row">

				<div class="col-2">
					<div class="btn-group" role="group" aria-label="Basic example">
						<a class="btn btn-outline-primary" style="width: 10rem"
							href="forumHome.jsp"><b>Back to Forum </b></a> <a
							class="btn btn-outline-primary" style="width: 5rem"
							href="replyToPost.jsp?post_id=<%=postID%>"><b>Reply</b></a>

					</div>

				</div>

			</div>
			<div class="scroll">

				<table class="table table-bordered">
					<thead class="thead-default">

						<tr>
							<th width="15%" align="center">Avatar Name</th>
							<th width="50%" align="center">Post Content</th>
							<th width="11%" align="center">Datetime</th>							
							<th width="8%" align="center">Votes</th>
							<th width="14%" colspan="2" >Actions</th>

						</tr>
						<tr class="table-warning">
						
							<td><%=avatarDAO.getAvatarName(parentPost.getAvatar_id())%></td>
							<td><%=parentPost.getPost_content()%></td>
							<td><%=parentPost.getTimestamp()%></td>
							<td><span id="up">0</span></td>								
							<td>
								<%
									if (parentPost.isIs_bot() == false) {
										out.println(
												"<button type='button' class='btn btn-primary btn-sm' id='up' onclick='javascript:btnClick()'>Like</button>");
									} else {
										out.println("<button type='button' class='btn btn-primary btn-sm' id='down'>Dislike</button>");
									}
								%>

							</td>
							   <td><a href="replyToRepliedPost.jsp?post_id=<%=postID%>&replyToPostID=<%=parentPost.getPost_id()%>">Reply</a>
							</td>

						</tr>


						<%
						    int counter = 1;
							for (Integer key : map.keySet()) {
								Post post = map.get(key);

								if (post.isIs_question() == false) {
						%>
					</thead>
					<tbody>

						<tr>
				
							<td><%=avatarDAO.getAvatarName(post.getAvatar_id())%></td>
							<td><%=post.getPost_id()%>&nbsp; <%=post.getPost_content()%></td>
							<td><%=post.getTimestamp()%></td>
							<td><span <% out.println("id=up"+counter); %>>0</span></td>					
							<td><%
										if (post.isIs_bot() == false) {
													out.println(
															"<button type='button' class='btn btn-primary btn-sm' id='up' onclick='javascript:btnClick()'>Like</button>");
												} else {
													out.println("<button type='button' class='btn btn-primary btn-sm' id='down'>Dislike</button>");

												}
									%>
							
                            </td>
                            <td><a href="replyToRepliedPost.jsp?post_id=<%=postID%>&replyToPostID=<%=post.getPost_id()%>">Reply</a>
							</td>
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
	<script>
	var upClicked = 0;

	 function btnClick(){
		 upClicked ++;
	     
	    document.getElementById('up').innerHTML = upClicked;
	    return true 
	}


	</script>
	<%@ include file="footer.jsp"%>
</body>


</html>