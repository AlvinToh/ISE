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
<style>

.text {
  display: block;
  width: 600px;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
}


</style>

</head>
<body>
	<%@ include file="navigationBar.jsp"%>


	<%
		String tempID = request.getParameter("post_id");
		System.out.println(tempID);
		int postID = Integer.parseInt(tempID);

		AvatarDAO avatarDAO = new AvatarDAO();
		PostDAO pd = new PostDAO();
	
        // level =0 is the post which has parentID=0, isQuestion=1
		List<String> subPostIDList = pd.getSubPostIDRecursion(postID, 1);

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
			<div class="scroll_viewPost">

				<table class="table table-bordered">
					<thead class="thead-default">
						<tr>
							<th width="3%">Post ID</th>
							<th width="3%">Post Level</th>
							<th width="60%" >Post Content</th>
							<th width="10%">Avartar Name</th>
							<th width="24%">Datetime</th>
							

						</tr>
						<tr class="table-warning">
						<td><%=parentPost.getPost_id()%></td>
							<td><%=parentPost.getLevel()%></td>				
							<td><a href="viewPostContent.jsp?parentID=<%=parentPost.getPost_id() %>&post_id=<%=parentPost.getPost_id()%>"><span class='text'><%=parentPost.getPost_content()%></span></a></td>
							<td><%=avatarDAO.getAvatarName(parentPost.getAvatar_id())%></td>
							<td><%=parentPost.getTimestamp().toString().replaceAll("\\.\\d+", "")%></td>
						</tr>
						




						<%
						int counter = 0;
						for (String key : subPostIDList) {
							String[] t = key.split("-");
							Post post = pd.retrieveParentPostID(Integer.parseInt(t[0]));

					%>
					</thead>
					<tbody>

						<tr>
						<%
						
						if(Integer.parseInt(t[1])==1){
							out.println("<td>"+post.getPost_id()+"</td>");
							out.println("<td width='10%'>"+t[1]+"</td>");							
							out.println("<td><a href='viewPostContent.jsp?parentID="+postID+"&post_id="+post.getPost_id()+"'><span class='text'>&nbsp;&rarr;"+post.getPost_content()+"</span></a></td>");
							out.println("<td>"+avatarDAO.getAvatarName(post.getAvatar_id())+"</td>");
							out.println("<td>"+post.getTimestamp().toString().replaceAll("\\.\\d+", "")+"</td>");
						
							
						}
						if(Integer.parseInt(t[1])==2){
							out.println("<td>"+post.getPost_id()+"</td>");
							out.println("<td>"+t[1]+"</td>");
							out.println("<td><a href='viewPostContent.jsp?parentID="+postID+"&post_id="+post.getPost_id()+"'><span class='text'>&nbsp;&nbsp;&nbsp;&rarr;"+post.getPost_content()+"</span></a></td>");
							out.println("<td>"+avatarDAO.getAvatarName(post.getAvatar_id())+"</td>");
							out.println("<td>"+post.getTimestamp().toString().replaceAll("\\.\\d+", "")+"</td>");
							
						}
						if(Integer.parseInt(t[1])==3){
							out.println("<td>"+post.getPost_id()+"</td>");
							out.println("<td>"+t[1]+"</td>");
							out.println("<td><a href='viewPostContent.jsp?parentID="+postID+"&post_id="+post.getPost_id()+"'><span class='text'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&rarr;"+post.getPost_content()+"</span></a></td>");
							out.println("<td>"+avatarDAO.getAvatarName(post.getAvatar_id())+"</td>");
							out.println("<td>"+post.getTimestamp().toString().replaceAll("\\.\\d+", "")+"</td>");
							
						}
						if(Integer.parseInt(t[1])==4){
							out.println("<td>"+post.getPost_id()+"</td>");
							out.println("<td>"+t[1]+"</td>");
							out.println("<td><a href='viewPostContent.jsp?parentID="+postID+"&post_id="+post.getPost_id()+"'><span class='text'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&rarr;"+post.getPost_content()+"</span></a></td>");
							out.println("<td>"+avatarDAO.getAvatarName(post.getAvatar_id())+"</td>");
							out.println("<td>"+post.getTimestamp().toString().replaceAll("\\.\\d+", "")+"</td>");
							
						}
						if(Integer.parseInt(t[1])==5){
							out.println("<td>"+post.getPost_id()+"</td>");
							out.println("<td>"+t[1]+"</td>");
							out.println("<td><a href='viewPostContent.jsp?parentID="+postID+"&post_id="+post.getPost_id()+"'><span class='text'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&rarr;"+post.getPost_content()+"</span></a></td>");
							out.println("<td>"+avatarDAO.getAvatarName(post.getAvatar_id())+"</td>");
							out.println("<td>"+post.getTimestamp().toString().replaceAll("\\.\\d+", "")+"</td>");
							
						}
						if(Integer.parseInt(t[1])>=6){
							out.println("<td>"+post.getPost_id()+"</td>");
							out.println("<td>"+t[1]+"</td>");
							out.println("<td><a href='viewPostContent.jsp?parentID="+postID+"&post_id="+post.getPost_id()+"'><span class='text'>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&rarr;"+post.getPost_content()+"</span></a></td>");
							out.println("<td>"+avatarDAO.getAvatarName(post.getAvatar_id())+"</td>");
							out.println("<td>"+post.getTimestamp().toString().replaceAll("\\.\\d+", "")+"</td>");
							
						}

						
						%>

						</tr>
					</tbody>




					<%
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

		function btnClick() {
			upClicked++;

			// document.getElementById('up').innerHTML = upClicked;
			return true
		}

		$(':button').click(function() {
			var id = $(this).attr('id');
			var counter = id.split("_")[1];
			alert(id + " -- " + counter); //up_0 -- 0
			var btnEdit2 = "up_" + counter;

			alert("~" + $('#up_' + counter).html());
			var numOfVote = $('#up_' + counter).html();
			alert(numOfVote);

		});
	</script>
	<%@ include file="footer.jsp"%>
</body>


</html>