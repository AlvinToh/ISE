<%@ page
	import="java.io.*,java.util.*, java.util.concurrent.*, utility.*, dao.SummaryDAO"%>
<%@include file="protect.jsp"%>
<%

String id = request.getParameter("q_id");
int question_id = Integer.parseInt(id);

SummaryDAO sdao = new SummaryDAO();
sdao.deleteQuestion(question_id);
response.sendRedirect("postClassSummary.jsp");

%>