<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="java.io.*,java.util.*, entity.*, dao.AvatarDAO"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>IS102 Web Platform</title>
<meta name="viewport" content="width=device-width, initial-scale=1">

<link rel="stylesheet" href="style/css/bootstrap.min.css">
<link rel="stylesheet" href="style/css/font-awesome.min.css">

</head>
<body>
	<%@ include file="navigationBar.jsp"%>
	<%
		Professor professor = (Professor) session.getAttribute("professor");
		Student student = (Student) session.getAttribute("student");

		int avatar_id = -1;
		if (student != null) {
			avatar_id = student.getAvatar_id();
		} else if (professor != null) {
			avatar_id = professor.getAvatar_id();
		}

		AvatarDAO avatarDAO = new AvatarDAO();
		String old_avatar_name = avatarDAO.getAvatarName(avatar_id);
		ArrayList<String> unusedAvatarNames = avatarDAO.getUnusedAvatarName();
	%>
	<div style="margin-top: 2%"></div>
	<div class="container text-center">
		<header>
			<h2>Change Avatar Name</h2>
			<hr>
		</header>
		<form class="form-horizontal" role="form" method="POST"
			action="ChangeAvatarName">
			<div class="form-group">
				<div class="row">
					<div class="col-md-5"></div>
					<div class="input-group-addon" style="width: 9rem; height: 2.6rem">
						<label>Old Avatar Name &nbsp</label>
					</div>

					<input type="text" name="old_avatar_name" id="old_avatar_name"
						value="<%=old_avatar_name%>" style="width: 9rem" readonly>

				</div>
				<br>
				<div class="row">
					<div class="col-md-5"></div>
					<div class="input-group-addon" style="width: 9rem; height: 2.6rem">
						<label>New Avatar Name &nbsp</label>
					</div>

					<select name="new_avatar_name" id="new_avatar_name" required
						autofocus style="width: 9rem">
						<option value="">Select a name</option>
						<%
							for (int i = 0; i < unusedAvatarNames.size(); i++) {
								out.println(
										"<option value='" + unusedAvatarNames.get(i) + "'>" + unusedAvatarNames.get(i) + "</option>");
							}
						%>

					</select>

				</div>
				<br>
				<div class="row">
					<div class="col-md-7"></div>
					<button type="submit" class="btn btn-primary">Change</button>
				</div>
			</div>
		</form>
	</div>

	<script
		src="//cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
	<script src="style/js/jquery-3.2.1.min.js"></script>
	<script src="style/js/bootstrap.min.js"></script>
	<%@ include file="footer.jsp"%>
</body>

</html>