<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
	String loginUrl = (String) session.getAttribute("loginUrl");	
	if (session.getAttribute("professor") != null) {
%>
<nav class="navbar navbar-toggleable-md navbar-light bg-faded">
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarTogglerDemo01" aria-controls="navbarTogglerDemo01"
		aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse" id="navbarTogglerDemo01">
		<a class="navbar-brand" href="home.jsp">IS102</a>
		<ul class="navbar-nav mr-auto mt-2 mt-lg-0">
			<li class="nav-item"><a class="nav-link" href="attendance.jsp">Attendance</a></li>
			<li class="nav-item"><a class="nav-link"
				href="postClassSummary.jsp">Post-class Summary</a></li>
			<li class="nav-item"><a class="nav-link" href="<%=loginUrl%>">Consultation</a></li>
			<li class="nav-item"><a class="nav-link" href="#">Dashboard</a></li>
			<li class="nav-item"><a class="nav-link" href="forumHome.jsp">Forum</a></li>
			<li class="nav-item"><a class="nav-link" href="changeAvatarName.jsp">Change Avatar Name</a></li>
			<li class="nav-item"><a class="nav-link" href="uploadNameList.jsp">Upload Name List</a></li>
			<li class="nav-item"><a class="nav-link" href="logout.jsp">Logout</a></li>
		</ul>
	</div>
</nav>
<%
	}else{

%>
<nav class="navbar navbar-toggleable-md navbar-light bg-faded">
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarTogglerDemo01" aria-controls="navbarTogglerDemo01"
		aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>
	<div class="collapse navbar-collapse" id="navbarTogglerDemo01">
		<a class="navbar-brand" href="home.jsp">IS102</a>
		<ul class="navbar-nav mr-auto mt-2 mt-lg-0">
			<li class="nav-item"><a class="nav-link" href="#">Dashboard</a></li>
			<li class="nav-item"><a class="nav-link" href="forumHome.jsp">Forum</a></li>
			<li class="nav-item"><a class="nav-link" href="changeAvatarName.jsp">Change Avatar Name</a></li>
			<li class="nav-item"><a class="nav-link" href="logout.jsp">Logout</a></li>
		</ul>
	</div>
</nav>

<%	}%>