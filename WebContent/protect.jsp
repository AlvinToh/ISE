<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
// prevent unauthorized access and back button
HttpServletResponse httpResponse = (HttpServletResponse)response;

httpResponse.setHeader("Cache-Control","no-cache, no-store, must-revalidate"); 
response.addHeader("Cache-Control", "post-check=0, pre-check=0");
httpResponse.setHeader("Pragma","no-cache"); 
httpResponse.setDateHeader ("Expires", 0); 
if (session.getAttribute("student") == null && session.getAttribute("professor") == null )  {                               
                response.sendRedirect("login.jsp");
                return;
 }
%>


