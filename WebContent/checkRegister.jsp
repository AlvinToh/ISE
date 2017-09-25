<%@include file="protect.jsp"%>
<%@ page
	import="java.io.*,java.util.*, java.util.concurrent.*, utility.*, entity.Professor, dao.StudentDAO,dao.ProfessorDAO"%>
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
		int no = 1;
			StudentDAO sd = new StudentDAO();
			//ArrayList<String> groups = sd.retrieveUniqueGroupID();
		ProfessorDAO profDAO = new ProfessorDAO();
		Professor prof = (Professor) session.getAttribute("professor");
		int profAvatar = prof.getAvatar_id();
		ArrayList<String> groups = profDAO.retrieveProfessorSections(profAvatar);
		String groupId = "";
		Cookie[] cookies = request.getCookies();
		if (cookies != null && cookies.length != 0) {
			for (Cookie cookie : cookies) {
				String name = cookie.getName();
				String value = cookie.getValue();
				String path = cookie.getPath();
				if (name.equals("group_id") && !value.isEmpty()) {
					groupId = cookie.getValue();
	%>
	<script>
	document.cookie = "group_id=;expires=Thu, 01 Jan 1970 00:00:00 UTC; path=<%=path%>;";
	</script>
	<% 
				}
			}
		}
		
		LinkedHashMap<String,String> students = new LinkedHashMap<>();
		  if(groupId.equals("")){
		   for(String group:groups){
		    HashMap<String,String> studentBySection = sd.retrieveStudentNotRegistered(group);
		    students.putAll(studentBySection);
		   }
		  }else{
		   students = (LinkedHashMap)sd.retrieveStudentNotRegistered(groupId);
		  }
		//out.println(groupId);
	%>
	<div style="margin-top: 2%"></div>
	<div class="container text-center">
		<header>
			<h2>Not Registered Students</h2>
			<hr>
		</header>


	</div>


	<div class="row justify-content-md-center">


		<div class="col-12 col-md-auto">
			<div class="row">
				<div class="col-3">
					<div class="btn-group"  role="group" aria-label="Basic example">
						<a class="btn btn-outline-primary" style="width: 10rem;height: 2.4rem"
							href="home.jsp"><b>Back to Home</b></a>
                          &nbsp;
							<div class="input-group">								
								<select name="session" id="session"
									required autofocus style="width: 10rem" class="form-control"
									onchange="selectGroupID(this.value)">
									<option value="">Select a session</option>

									<%
										for (String group : groups) {
									%>
									<option value="<%=group%>"><%=group%></option>
									<%
										}
									%>

								</select> 

							</div>

					</div>

				</div>

			</div>


			
			<div class="scroll">

				<table class="table table-bordered">
					<thead class="thead-default">

						<tr>
							<th>S/N</th>
							<th>SMU Email</th>
							<th>Section</th>
						</tr>
					</thead>

					<%
						if (groups != null && !groups.isEmpty()) {
							if (students != null && !students.isEmpty()) {

								for (String smu_email_id : students.keySet()) {
									String group_id = students.get(smu_email_id);
					%>
					<tbody>
						<tr>
							<td><%=no++%></td>
							<td><%=smu_email_id%></td>
							<td><%=group_id%></td>

						</tr>
					</tbody>
					<%
						} //end for
							}else{
								out.println("<div class='container' align='center' style='padding:0px;height:40px'>");
								out.println("<div class='alert alert-warning' style='width:400px'>");
								out.println("<font color='red'>");
								out.println("No unregistered students");
								out.println("</font>");
							}
						}
					%>
				</table>
<%
 
if (groups == null || groups.isEmpty()) {
	
	out.println("<div class='container' align='center' style='padding:0px;height:40px'>");
	out.println("<div class='alert alert-warning' style='width:400px'>");
	out.println("<font color='red'>");
	out.println("Please upload the class list");
	out.println("</font>");
}



%>
			</div>
		</div>
	</div>
	<div class="col-8"></div>

	<script>
	 function selectGroupID(selectedGroup){
		 document.cookie = "group_id="+selectedGroup;
		 location.reload();
	 }
	 

	</script>
	
	<script
		src="//cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
	<script src="style/js/jquery-3.2.1.min.js"></script>
	<script src="style/js/bootstrap.min.js"></script>
	<%@ include file="footer.jsp"%>
</body>


</html>