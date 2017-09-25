<%@include file="protect.jsp"%>
<%@ page
	import="java.io.*,java.util.*, java.util.concurrent.*, utility.*, entity.*, dao.*"%>
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
		Professor professor = (Professor) session.getAttribute("professor");
		int avatar_id = professor.getAvatar_id();
		StudentSummaryDAO ssDAO = new StudentSummaryDAO();

		String week = request.getParameter("week");
		int weekNo = 0;
		if (week != null) {
			weekNo = Integer.parseInt(week);
		}
		String section = request.getParameter("session");
		
		// get submitted student list
		List<String> studentList = ssDAO.getStudentList(section, weekNo);
		
		// error message
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
		out.println("</div>");
	%>
	


	<div style="margin-top: 2%"></div>
		<div class="container text-center">
		<header>
			<%
				if (week != null && section != null) {
					out.println("<h2>Week_"+week+" "+section+ " Submitted Summary</h2>");
				} else {
					out.println("<h2>Submitted Summary</h2>");
				}
			%>

			<hr>
		</header>


	</div>

	<div class="row justify-content-md-center">

		<div class="col-12 col-md-auto">
			<div class="row">

				<div class="col-2">
					<div class="btn-group" role="group" aria-label="Basic example">
						<a class="btn btn-outline-primary"
							style="width: 6rem; height: 2.4rem"
							href="stuSubmittedSummary.jsp"><b>Back</b></a>

					</div>

				</div>

			</div>

			<div class="scrollSummary">

				<table class="table table-bordered">
					<thead class="thead-default">

						<tr>
							<th align="center" width="30%">Student</th>
							<th align="center" width="30%">Summary</th>
							<th align="right" width="40%">Mark</th>

						</tr>
						<%
							WeeklyPostSummaryDAO wpsDAO = new WeeklyPostSummaryDAO();
						    int counter=0;
							for (String key : studentList) {
								counter++;
								int mark = wpsDAO.retrieveMark(key, weekNo);
						%>
					</thead>
					<tbody>


						<tr>
							<th><%=key%></th>
							<td><a
								href="viewStuSubmittedSummary.jsp?emailID=<%=key%>&Week=<%=weekNo%>&Group=<%=section%>"
								<%=section%>>Week_<%=weekNo%> Summary</a></td>
							<td>
							<input type ='text' id='studentListSize' value='<%=studentList.size()%>' hidden/>
							                                         
								<%if (mark== Integer.MAX_VALUE){
									out.println("<div id = 'dynamicForm' ><form method='post'  action='AssignMarks'>");
									out.println("<input type ='text' name='stuid' value='"+key+"' hidden/>");
									out.println("<input type ='text' name='week' value='"+weekNo+"' hidden/>");
									out.println("<input type ='text' name='group' value='"+section+"' hidden/>");
									out.println("<div class='form-group row'><div class='col-3'>  <input class='form-control' type='text' name='mark' id='example-text-input' style='height: 2rem'></div>");
								    out.println("<input type='hidden' name='hdnbt' value='Save'/><input type='submit' class='btn btn-primary btn-sm' value='Save' style='width: 3rem; height: 2rem'></div></form></div>");

                                }else{
                        			out.println("<div id = 'dynamicForm' ><form method='post'  action='AssignMarks'>");
									out.println("<input type ='text' name='stuid' value='"+key+"' hidden/>");
									out.println("<input type ='text' name='week' value='"+weekNo+"' hidden/>");
									out.println("<input type ='text' name='group' value='"+section+"' hidden/>");
                                	out.println("<div class='form-group row'><div class='col-3'>  <input class='form-control' type='text' name='mark' value='"+mark+"' disabled id='editMark"+counter+"' style='height: 2rem'></div>");
								    out.println("<input type='button' name='editMarkBtn' value='Edit' class='btn btn-primary btn-sm' id='btnEdit_"+counter+"'  style='width: 3rem; height: 2rem'>");
								    out.println("<input type='hidden' name='hdnbt' value='Edit'/><input type='submit' name='editMarkBtn' value='Save' class='btn btn-primary btn-sm' id='btnEdit2"+counter+"' hidden style='width: 3rem; height: 2rem'></div></form></div>");
                                	
                                }%>

							</td>
						</tr>


					</tbody>


					<%
						} // end for
					%>


				</table>
			</div>
		</div>
	</div>
	<div class="col-8"></div>


	<script
		src="//cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
	<script src="style/js/jquery-3.2.1.min.js"></script>
	<script src="style/js/bootstrap.min.js"></script>

	<script>
	// dynamic edit/save button

	$(':input').click(function() {
		var id = $(this).attr('id');
		var counter=id.split("_")[1];
		   //alert(id+" 00-- "+ counter); 
		   
		   var btnEdit = "#btnEdit_"+counter;
	    	var editMark = "editMark"+counter;
	    	var btnEdit2="btnEdit2"+counter;
			$(btnEdit).hide();
			document.getElementById(editMark).removeAttribute('disabled');
		    document.getElementById(btnEdit2).removeAttribute('hidden');
		});

</script>
	

	
	</script>

	

	<%@ include file="footer.jsp"%>
</body>


</html>