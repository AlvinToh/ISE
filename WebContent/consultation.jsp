<%@include file="protect.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>IS102 Web Platform</title>
<meta name="viewport" content="width=device-width, initial-scale=1">


<link rel="stylesheet" href="style/css/bootstrap.min.css" />
<link rel="stylesheet" href="style/css/bootstrap-datetimepicker.min.css" />
<link rel="stylesheet" href="style/css/font-awesome.min.css" />
<link rel="stylesheet" href="style/css/fullcalendar.css"  />

<script src="//cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
<script src="style/js/moment.min.js"></script>
<script src="style/js/jquery.min.js"></script>
<script src="style/js/bootstrap.min.js"></script>
<script src="style/js/bootstrap-datetimepicker.min.js"></script>
<script src="style/js/fullcalendar.js"></script>

<script type="text/javascript">
	$(document).ready(function() {

		// page is now ready, initialize the calendar...

		$('#calendar').fullCalendar({
			editable : false, // Don't allow editing of events
			handleWindowResize : true,
			weekends : true, // Hide weekends
			header : {
				left : 'prev,next today',
				center : 'title',
				right : 'month,agendaWeek,agendaDay,list'
			}, // Hide buttons/titles
			defaultView : 'agendaWeek',
			minTime : '08:00:00', // Start time for the calendar
			maxTime : '24:00:00', // End time for the calendar
			displayEventTime : true, // Display event time
			events : "Calendarjson"
		// put your options and callbacks here
		})

	});
</script>
</head>
<body>
	<jsp:include page="/Authcalendar" />
	<%
		String loginUrl = (String) session.getAttribute("loginUrl");	
	%>
	<nav class="navbar navbar-toggleable-md navbar-light bg-faded">
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarTogglerDemo01"
			aria-controls="navbarTogglerDemo01" aria-expanded="false"
			aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div class="collapse navbar-collapse" id="navbarTogglerDemo01">
			<a class="navbar-brand" href="home.jsp">IS102</a>
			<ul class="navbar-nav mr-auto mt-2 mt-lg-0">
				<li class="nav-item"><a class="nav-link" href="attendance.jsp">Attendance</a></li>
				<li class="nav-item"><a class="nav-link" href="#">Post-class Summary</a></li>
				<li class="nav-item"><a class="nav-link" href="<%=loginUrl%>">Consultation</a></li>
				<li class="nav-item"><a class="nav-link" href="#">Dashboard</a></li>
				<li class="nav-item"><a class="nav-link" href="javascript:logInForum()" id="forum">Forum</a></li>
				<li class="nav-item"><a class="nav-link" href="logout.jsp">Logout</a></li>
			</ul>
		</div>
	</nav>
	<%
		String successfulTimeSlotMsg = (String) session.getAttribute("successfulTimeSlotMsg");
		if (successfulTimeSlotMsg != null && successfulTimeSlotMsg.length() > 0) {
			out.println("<div class='container' align='center' style='padding:0px;height:40px'>");
			out.println("<div class='alert alert-warning' style='width:400px'>");
			out.println("<font color='blue'>");
			out.println(successfulTimeSlotMsg);
			out.println("</font>");
		}
		session.removeAttribute("successfulTimeSlotMsg");
		
		String approvedTimeSlotMsg = (String) session.getAttribute("approvedTimeSlotMsg");
		if (approvedTimeSlotMsg != null && approvedTimeSlotMsg.length() > 0) {
			out.println("<div class='container' align='center' style='padding:0px;height:40px'>");
			out.println("<div class='alert alert-warning' style='width:400px'>");
			out.println("<font color='blue'>");
			out.println(approvedTimeSlotMsg);
			out.println("</font>");
		}
		session.removeAttribute("approvedTimeSlotMsg");
		
		String rejectedTimeSlotMsg = (String) session.getAttribute("rejectedTimeSlotMsg");
		if (rejectedTimeSlotMsg != null && rejectedTimeSlotMsg.length() > 0) {
			out.println("<div class='container' align='center' style='padding:0px;height:40px'>");
			out.println("<div class='alert alert-warning' style='width:400px'>");
			out.println("<font color='red'>");
			out.println(rejectedTimeSlotMsg);
			out.println("</font>");
		}
		session.removeAttribute("rejectedTimeSlotMsg");
		out.println("</div>");
		out.println("</div>");
	%>
	<br />
	<div id="block_container" style="display:flex">
		<div id="calendar" style="width:200%"></div>

		
		<div class="container" style="width:100%">
			<div class="card">
			<form action="CreateFreeSlot" class="form-horizontal" role="form" method="POST">
				<fieldset>
				<div class="text-center">
					<br />
					<h3>Create Free Slot </h3>
				</div>
					<div class="form-group">
						<label for="Subject" class="col-md-12 control-label">Title </label>
						<div class="input-group col-md-12">
							<input class="form-control" type="text" size="20" id="title" name="title"
								value="" /><br />
						</div>
					</div>
					<div class="form-group">
						<label for="startDateTime" class="col-md-12 control-label">Start
							Date and Time </label>
						<div class="input-group date form_datetime col-md-12"
							data-date-format="dd MM yyyy - HH:ii p"
							data-link-field="startDateTime">
							<input class="form-control" size="20" type="text" value=""
								readonly> <span class="input-group-addon"><span
								class="fa fa-times"></span></span> <span class="input-group-addon"><span
								class="fa fa-calendar"></span></span>
						</div>
						<input type="hidden" id="startDateTime" name="startDateTime" value=""/>
					</div>
					<div class="form-group">
						<label for="endDateTime" class="col-md-12 control-label">End
							Date and Time</label>
						<div class="input-group date form_datetime col-md-12"
							data-date-format="dd MM yyyy - HH:ii p"
							data-link-field="endDateTime">
							<input class="form-control" size="20" type="text" value=""
								readonly> <span class="input-group-addon"><span
								class="fa fa-times"></span></span> <span class="input-group-addon"><span
								class="fa fa-calendar"></span></span>
						</div>
						<input type="hidden" id="endDateTime" name="endDateTime" value="" />
					</div>
					<div class="form-group col-md-2">
						<button type="submit" class="btn btn-primary">Submit</button>
					</div>
				</fieldset>
			</form>
		</div>
		</div>
	</div>
		<script type="text/javascript">
			$('.form_datetime').datetimepicker({
				weekStart : 1,
				todayBtn : 1,
				autoclose : 1,
				todayHighlight : 1,
				startView : 2,
				forceParse : 0,
				showMeridian : 1
			});
		</script>
		
		<br />
		<%@ page import="java.sql.ResultSet" %>
		<%@ page import="java.sql.Statement" %>
		<%@ page import="java.sql.Connection" %>
		<%@ page import="java.sql.DriverManager" %>
		
		<div class="container">
			<form action="PendingTimeSlot" class="form-horizontal" role="form" method="POST">
			<div class="card">
			<div class="text-center">
					<br />
					<h3>Pending Consultation Slots </h3>
					<br />
				</div>
				<table class="table table-bordered">
  					<thead>
    					<tr>
     						<th>#</th>
      						<th>Subject</th>
      						<th>Student Email</th>
      						<th>Start Date & Time</th>
      						<th>End Date & Time</th>
      						<th>Status </th>
      						<th>Action </th>
    					</tr>
  					</thead>
<%
try
{
Class.forName("com.mysql.jdbc.Driver");
String url="jdbc:mysql://localhost:3306/is102_cat";
String username="root";
String password="";

String email = (String) session.getAttribute("email");
String query="select * from student_consultationtimeslot where smu_email = '" + email + "' and status = 'pending'";
Connection conn=DriverManager.getConnection(url, username, password);
Statement stmt=conn.createStatement();
ResultSet rs=stmt.executeQuery(query);
while(rs.next())
{

%>

<tr>
	<td><%=rs.getInt("timeslot_id") %><input type="hidden" value="<%=rs.getInt("timeslot_id") %>" name="timeslot_id" /></td>
	<td><%=rs.getString("title") %></td>
	<td><%=rs.getString("student_email") %></td>
	<td><%=rs.getString("start_DateTime") %></td>
	<td><%=rs.getString("end_DateTime") %></td>
	<td><%=rs.getString("status") %></td>
	<td>
		<button type="submit" class="btn btn-primary" name="postAction" value="approve">Approve</button>
		<button type="submit" class="btn btn-danger" name="postAction" value="reject">Reject</button>
	</td>
</tr>
<%

}
%>
  				</table>
<%
rs.close();
stmt.close();
conn.close();
}
catch(Exception e)
{
e.printStackTrace();
}
%>
  			</div>
  		</form>
  		</div>
		<%@ include file="footer.jsp"%>
</body>
</html>