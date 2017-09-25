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
		String tempParentID = request.getParameter("parentID");
	    int ParentID = Integer.parseInt(tempParentID);
	    String tempID = request.getParameter("post_id");
		int postID = Integer.parseInt(tempID);
		
		
		PostDAO pd = new PostDAO();
		Post parentPost = pd.retrieveParentPost(ParentID);
		Post post = pd.retrieveParentPostID(postID);
		
		
		
		AvatarDAO avatarDAO = new AvatarDAO();		

	%>
	<div style="margin-top: 2%"></div>

	<div class="container text-center">
		<header>
			<h2>
				View Post: <%=parentPost.getPost_title() %>
			</h2>
			<hr>
		</header>
		
		<div class="row justify-content-md-left">
			<div class="col-12 col-md-auto">

				<div class="row">

					<div class="col-2">
						<div class="btn-group" role="group" aria-label="Basic example">					
							<a class="btn btn-outline-primary" style="width: 8rem"
								href="viewPost.jsp?post_id=<%=ParentID%>"><b>Back</b></a>
							<a class="btn btn-outline-primary" style="width: 5rem"
							    href="replyToRepliedPost.jsp?parentID=<%=ParentID%>&post_id=<%=postID%>"><b>Reply</b></a>
								
								
						</div>
					</div>
				</div>
			</div>
		</div>

		<div class="viewPostBoarder">
  
			<table class="table table-bordered">
				<%
					// error msg for assign marks
					String errorMsgs = (String) request.getAttribute("error");
					if (errorMsgs != null && errorMsgs.length() > 0) {
						out.println("<div id = 'myElem' class='container' align='center' style='padding:0px;height:40px'>");
						out.println("<div class='alert alert-warning' style='width:400px'>");
						out.println("<font color='red'>");
						out.println(errorMsgs);
						out.println("</font>");
					}
					request.removeAttribute("error");
					out.println("</div>");
					out.println("</div><br>");
				%>
				<tr>
					<th width="15%">Post Content</th>
					<td colspan="4" ><%=post.getPost_content() %></td>
				
				<tr>
				
				<tr>
					<th width="15%">Post by</th>
					<td colspan="4" ><%=avatarDAO.getAvatarName(post.getAvatar_id())%></td>				
				
				
				<tr>
				
				<tr>
					<th width="15%">Upvotes</th>
					<td colspan="4"> 0</td>
				
				<tr>
				<tr>
					<th width="15%">Post Time</th>
					<td colspan="4" ><%=post.getTimestamp().toString().replaceAll("\\.\\d+", "")%></td>
				
				
				
				<tr>
				
				<tr>
					<th width="15%">Assign Marks</th>
					<td>
					<%
						if (post.getThoughfulness_score() == 0.0) {
							out.println("<form method='post'  action='AssignMarksForPost'>");
							out.println("<input type ='text' name='postID' value='"+postID+"' hidden/>");
							out.println("<input type ='text' name='parentID' value='"+ParentID+"' hidden/>");
							out.println("<div class='form-group row'><div class='col-3'>  <input class='form-control' type='text' name='mark' id='example-text-input' style='height: 2rem'></div>");
						    out.println("<input type='hidden' name='hdnbt' value='Save'/><input type='submit' class='btn btn-primary btn-sm' value='Save' style='width: 3rem; height: 2rem'></div></form>");
							

						}else{
							out.println("<form method='post'  action='AssignMarksForPost'>");
							out.println("<input type ='text' name='postID' value='"+postID+"' hidden/>");
							out.println("<input type ='text' name='parentID' value='"+ParentID+"' hidden/>");
                        	out.println("<div class='form-group row'><div class='col-3'>  <input class='form-control' type='text' name='mark' value='"+post.getThoughfulness_score()+"' disabled id='editMark' style='height: 2rem'></div>");
						    out.println("<input type='button' name='editMarkBtn' value='Edit' class='btn btn-primary btn-sm' id='btnEdit'  style='width: 3rem; height: 2rem'>");
						    out.println("<input type='hidden' name='hdnbt' value='Edit'/><input type='submit' name='editMarkBtn' value='Save' class='btn btn-primary btn-sm' id='btnEdit2' hidden style='width: 3rem; height: 2rem'></div></form></div>");
                        	
						}
					%>
					</td>
				
				<tr>
			</table>
		</div>
	</div>



	<script
		src="//cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
	<script src="style/js/jquery-3.2.1.min.js"></script>
	<script src="style/js/bootstrap.min.js"></script>
	<script>
		// dynamic edit/save button

		$(':input').click(function() {	

			var btnEdit = "#btnEdit";
			var editMark = "editMark";
			var btnEdit2 = "btnEdit2";
			$(btnEdit).hide();
			document.getElementById(editMark).removeAttribute('disabled');
			document.getElementById(btnEdit2).removeAttribute('hidden');
		});
	</script>

	<%@ include file="footer.jsp"%>
</body>


</html>