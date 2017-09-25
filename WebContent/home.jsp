<%@include file="protect.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>

 <%@ page language="java" import="javazoom.upload.*,java.util.*,java.io.*,entity.*,utility.BootstrapUpload" %>
	
	<%

	//String dir =  request.getContextPath()+"/studentList";
	String dir = "C:/Users/User/workspace/ISE/WebContent/studentList";

    //out.println(dir);
    File folder = new File(dir);
  
    if(!folder.exists()){
    	folder.mkdir();
    }
    %>
    <jsp:useBean id="upBean" scope="page" class="javazoom.upload.UploadBean" >
    <jsp:setProperty name="upBean" property="folderstore" value="<%=dir%>" />
	</jsp:useBean>
	
      
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
		String changeAvatarNameMsg = (String) request.getAttribute("changeAvatarNameMsg");
		if (changeAvatarNameMsg != null && changeAvatarNameMsg.length() > 0) {
			out.println("<div class='container' align='center' style='padding:0px;height:40px'>");
			out.println("<div class='alert alert-warning' style='width:400px'>");
			out.println("<font color='blue'>");
			out.println(changeAvatarNameMsg);
			out.println("</font>");
			out.println("</div>");
			out.println("</div>");
			request.removeAttribute("changeAvatarNameMsg");
		}
		
		Student student = (Student) session.getAttribute("student");
		if (student != null) {
			return;
		}
		BootstrapUpload.cleanAll(dir);
		if (MultipartFormDataRequest.isMultipartFormData(request)) {
			// Uses MultipartFormDataRequest to parse the HTTP request.
			MultipartFormDataRequest mrequest = new MultipartFormDataRequest(request);
			Hashtable files = mrequest.getFiles();
			UploadFile file = (UploadFile) files.get("uploadfile");
			if (file != null && file.getFileName() != null) {
				if (file.getContentType().equals("application/vnd.ms-excel") || file.getContentType().equals(".csv")
						|| file.getContentType().equals("application/x-zip-compressed")
						|| file.getContentType().equals("application/zip")) {
					out.println("<li>Form field : uploadfile" + "<BR> Uploaded file : " + file.getFileName() + " ("
							+ file.getFileSize() + " bytes)" + "<BR> Content Type : " + file.getContentType());
					upBean.store(mrequest, "uploadfile");
					response.sendRedirect(request.getContextPath() + "/Upload");
				} // Uses the bean now to store specified by jsp:setProperty at the top.
				else {
					out.println("<div class='container' align='center' style='padding:0px;height:40px'>");
					out.println("<div class='alert alert-warning' style='width:400px'>");
					out.println("<font color='red'>");
					out.println("Invalid file type");
					out.println("</font>");
					out.println("</div>");
					out.println("</div>");
				}
			} else {
				out.println("<div class='container' align='center' style='padding:0px;height:40px'>");
				out.println("<div class='alert alert-warning' style='width:400px'>");
				out.println("<font color='red'>");
				out.println("No uploaded files");
				out.println("</font>");
				out.println("</div>");
				out.println("</div>");
			}
		}

		String bootstrapSuccessMsg = (String) request.getAttribute("bootstrapSuccessMsg");
		String bootstrapErrorMsg = (String) request.getAttribute("bootstrapErrorMsg");
		if (bootstrapSuccessMsg != null && bootstrapSuccessMsg.length() > 0) {
			out.println("<div class='container' align='center' style='padding:0px;height:40px'>");
			out.println("<div class='alert alert-warning' style='width:400px'>");
			out.println("<font color='blue'>");
			out.println(bootstrapSuccessMsg);
			out.println("</font>");
			request.removeAttribute("bootstrapSuccessMsg");
			out.println("</div>");
			out.println("</div>");
		}

		if (bootstrapErrorMsg != null && bootstrapErrorMsg.length() > 0) {
			out.println("<div class='container' align='center' style='padding:0px;height:40px'>");
			out.println("<div class='alert alert-warning' style='width:400px'>");
			out.println("<font color='red'>");
			out.println(bootstrapErrorMsg);
			out.println("</font>");
			request.removeAttribute("bootstrapErrorMsg");
			out.println("</div>");
			out.println("</div>");
		}
	%>
	<div style="margin-top: 2%"></div>
	<div class="container text-center">
		<div class="container">
			<h1>Welcome to IS102 CAT Platform</h1>
			<hr>
		</div>

	</div>

	


	<div class="row justify-content-md-center">

		<div class="col-12 col-md-auto">
			<div class="row">

				<div class="col-12">
					<div class="btn-group" role="group" aria-label="Basic example">
						<a class="btn btn-outline-primary"
							style="width: 16rem; height: 2.4rem" href="checkRegister.jsp"><b>View
								Unregistered Students</b></a>
					</div>
					<div class="btn-group" role="group" aria-label="Basic example">
						<a href="checkAttendance.jsp" class="btn btn-outline-primary"
							style="width: 14rem; height: 2.4rem"><b>View Attendance
								Status</b></a>
					</div>
				</div>

			</div>


				<div class="viewPostBoarder">
				<form method="post" action="home.jsp" name="upform"
					enctype="multipart/form-data">
					<br>

					<div class="form-group">
						<div class="row">
							<div class="col-md-1"></div>
							<div class="form-control" style="height: 3rem; width: 27rem">
								<label><font size="3"><b>File to upload:</b></font></label>
							</div>


						</div>

						<div class="row">
							<div class="col-md-1"></div>
							<div class="form-control" style="height: 3rem; width: 27rem">
								<input type="file" name="uploadfile" class="form-control-label"
									accept=".zip,.csv,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel">

							</div>

						</div>

						<div class="row">
							<div class="col-md-3"></div>
							<input type="submit" style="width: 15rem;"
								class="btn btn-primary" name="Submit" value="Upload">
						</div>
					</div>
				</form>

			</div>

		</div>


	</div>



	<script
		src="//cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
	<script src="style/js/jquery-3.2.1.min.js"></script>
	<script src="style/js/bootstrap.min.js"></script>
	<%@ include file="footer.jsp"%>

</body>

</html>