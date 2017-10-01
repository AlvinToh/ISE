<%@include file="protect.jsp"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html>

<%@ page language="java"
	import="javazoom.upload.*,java.util.*,java.io.*,utility.BootstrapUpload"%>

<%
	/* String dir =  request.getContextPath()+"/WebContent/studentList";
	out.println(dir); */

	String dir = "C:/Users/User/workspace/ISE/WebContent/nameList";
	//String dir = "C:/Users/xiaoyu/Desktop/ISE  - 10 Sep V1/ISE/WebContent/nameList";
	//out.println(dir);
	File folder = new File(dir);

	if (!folder.exists()) {
		folder.mkdir();
	}
%>
<jsp:useBean id="upBean" scope="page" class="javazoom.upload.UploadBean">
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
<%
	BootstrapUpload.cleanAll(dir);
	if (MultipartFormDataRequest.isMultipartFormData(request)) {
		// Uses MultipartFormDataRequest to parse the HTTP request.
		MultipartFormDataRequest mrequest = new MultipartFormDataRequest(request);
		Hashtable files = mrequest.getFiles();
		UploadFile file = (UploadFile) files.get("uploadNameList");
		if (file != null && file.getFileName() != null) {
			if (file.getContentType().equals("application/vnd.ms-excel") || file.getContentType().equals(".csv")
					|| file.getContentType().equals("application/x-zip-compressed")
					|| file.getContentType().equals("application/zip")) {
				out.println("<li>Form field : uploadfile" + "<BR> Uploaded file : " + file.getFileName() + " ("
						+ file.getFileSize() + " bytes)" + "<BR> Content Type : " + file.getContentType());
				upBean.store(mrequest, "uploadNameList");
				response.sendRedirect(request.getContextPath() + "/UploadNameList");
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

	String uploadNameListSuccessMsg = (String) request.getAttribute("uploadNameListSuccessMsg");
	if (uploadNameListSuccessMsg != null && uploadNameListSuccessMsg.length() > 0) {
		out.println("<div class='container' align='center' style='padding:0px;height:40px'>");
		out.println("<div class='alert alert-warning' style='width:400px'>");
		out.println("<font color='blue'>");
		out.println(uploadNameListSuccessMsg);
		out.println("</font>");
		request.removeAttribute("uploadNameListSuccessMsg");
		out.println("</div>");
		out.println("</div>");
	}
%>

<body>


				<form method="post" action="uploadNameList.jsp" name="upform"
					enctype="multipart/form-data">
					<br>

					
								<input type="file" name="uploadNameList" class="form-control-label"
									accept=".zip,.csv,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel">

						
				
							<input type="submit" style="width: 15rem;"
								class="btn btn-primary" name="Submit" value="Upload">
					
				</form>

		



	<script
		src="//cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"></script>
	<script src="style/js/jquery-3.2.1.min.js"></script>
	<script src="style/js/bootstrap.min.js"></script>
	<%@ include file="footer.jsp"%>

</body>

</html>