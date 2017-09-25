<%@include file="protect.jsp"%>
<%@ page
	import="java.io.*,java.util.*, java.util.concurrent.*, utility.*, dao.ClassPartDAO, dao.StudentDAO"%>
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
		StudentDAO sd = new StudentDAO();
		ArrayList<String> groups = sd.retrieveUniqueGroupID();
		ClassPartDAO cpDAO = new ClassPartDAO();
		HashMap<String, String> weeklyRecords = new HashMap<>();
		int no = 1;
		String groupId = "";
		int week = 0;
	%>
	<div style="margin-top: 2%"></div>
	<div class="container text-center">
		<header>
			<h2>Attendance Record</h2>
			<hr>
		</header>
	</div>

	<div class="row justify-content-md-center">

		<div class="col-14 col-md-auto">
				<div class="col-2">
					<div class="btn-group" role="group" aria-label="Basic example">
						<a class="btn btn-outline-primary" style="width: 10rem"
							href="home.jsp"><b>Back to Home</b></a>
					</div>
				</div>
				
				<div class="col-5">
					<label>Session </label>
					<select name="session" id="session" required autofocus style="width: 9rem"
						onChange="selectGroupID(this.value)">
							<option value="">Select a session</option>
							
							<% for(String group:groups){%>
							<option value="<%=group%>"><%=group%></option>
						    <%}%>
							
					</select>
				</div>
				
				<div class="col-5">
				<label>Week </label>
					<select name="week" id="week" required autofocus style="width: 10rem"
							onChange="selectWeek(this.value)">
							<option value="">Select a week</option>
							<option value="1">1</option>
							<option value="2">2</option>
							<option value="3">3</option>
							<option value="4">4</option>
							<option value="5">5</option>
							<option value="6">6</option>
							<option value="7">7</option>
							<option value="9">9</option>
							<option value="10">10</option>
							<option value="11">11</option>
							<option value="12">12</option>
							<option value="13">13</option>
					</select>
				</div>
			
			<div class="scroll">

				<table class="table table-bordered">
					<thead class="thead-default">

						<tr>
							<th>S/N</th>
							<th>SMU Email</th>
							<th>Week</th>
							<th>Section</th>
							<th>Status</th>
						</tr>
						<%if(groups!=null&&!groups.isEmpty()){
							
							Cookie[]cookies = request.getCookies();
							if(cookies!=null && cookies.length!=0){
								for(Cookie cookie:cookies){
									String name = cookie.getName();
									String value = cookie.getValue();
									String path = cookie.getPath();
									
									if(name.equals("group_id")&&!value.isEmpty()){
										 groupId = cookie.getValue();
						%>
						
						<script>
							document.cookie = "group_id=;expires=Thu, 01 Jan 1970 00:00:00 UTC; path=<%=path%>;";
					    </script>
						
						<% 	
									}else if(name.equals("week")&&!value.isEmpty()){
										 week = Integer.parseInt(cookie.getValue());
						%>
						
						<script> document.cookie = "week=;expires=Thu, 01 Jan 1970 00:00:00 UTC; path=<%=path%>;";</script>
						
						<%
									}
								}
								
						    weeklyRecords = cpDAO.retrieveWeeklyClassPart(groupId, week);
							if(weeklyRecords!=null&&!weeklyRecords.isEmpty()){
						
							for (String smu_email_id : weeklyRecords.keySet()) {
								String status = weeklyRecords.get(smu_email_id);
						%>
					</thead>
					<tbody>

						<tr>
							<td><%=no++%></td>
							<td><a href="viewIndividualAttendance.jsp?smu_email_id=<%=smu_email_id%>"><%=smu_email_id%></a></td>
							<td><%=week%></td>
							<td><%=groupId%></td>
							<td><%=status%></td>

						</tr>


					</tbody>

					<%
								}//end for
							}
							}
						}else{
							out.println("<div class='container' align='center' style='padding:0px;height:40px'>");
							out.println("<div class='alert alert-warning' style='width:400px'>");
							out.println("<font color='red'>");
							out.println("Please upload the class list");
							out.println("</font>");
						}
					%>


				</table>
			</div>
		</div>
	</div>
	<div class="col-8"></div>

	<script>
	var groupCalled = false;
	var weekCalled = false;
	
	 function selectGroupID(selectedGroup){
		 document.cookie = "group_id="+selectedGroup;
		 groupCalled = true;
		 reload();
	 }
	 
	 function selectWeek(selectedWeek){
		 document.cookie = "week="+selectedWeek;
		 weekCalled = true;
		 reload();
	 }
	 
	 function reload(){
		 if(groupCalled && weekCalled){
			 groupCalled = false;
			 weekCalled = false;
			
			 location.reload();
		 }
	 }

	</script>
	
	<script
		src="//cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
	<script src="style/js/jquery-3.2.1.min.js"></script>
	<script src="style/js/bootstrap.min.js"></script>
	<%@ include file="footer.jsp"%>
</body>


</html>