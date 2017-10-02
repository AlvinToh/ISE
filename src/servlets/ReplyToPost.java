package servlets;

import java.io.*;
import dao.*;
import entity.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Authenticate
 */
@WebServlet("/ReplyToPost")
public class ReplyToPost extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReplyToPost() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Student student = (Student)session.getAttribute("student");
		Professor professor = (Professor)session.getAttribute("professor");
		int avatar_id=0;
		if(student != null){
			avatar_id = student.getAvatar_id();
			System.out.println(avatar_id);
		}
		
		if(professor != null){
			avatar_id = professor.getAvatar_id();
			System.out.println(avatar_id);
		}
		
		String errorMsg = "";
		PostDAO postDAO = new PostDAO();
		
		String tempPostID = (String)request.getParameter("postID");
		int post_id = Integer.parseInt(tempPostID);
		int level = postDAO.getPostLevel(post_id);
		String tempPostTitle = request.getParameter("postTitle");
		String tempPostContent = request.getParameter("postContent");
		
		// for reply to reply
       // String replyToPostID = (String)request.getParameter("replyToPostID");
		// if replyToPostID != null, add a reply reply
        // otherwise, redirect to replyToRepliedPost.jsp
        //http://localhost:8080/ISE/replyToRepliedPost.jsp?post_id=1&replyToPostID=1
		
		//System.out.println(post_id +" "+ tempPostTitle+" "+tempPostContent);
		
		//post_id is NOT empty, but tempPostContent is empty	
		if(tempPostContent.isEmpty()||tempPostContent == null){
			errorMsg = "Your reply content cannot be empty!";
			RequestDispatcher rd = request.getRequestDispatcher("replyToPost.jsp?post_id="+post_id);
			request.setAttribute("replyToPost", errorMsg);
			rd.forward(request, response);
			return;
		}else{
			postDAO.replyToPost(avatar_id, level, post_id, tempPostTitle, tempPostContent);
			RequestDispatcher rd = request.getRequestDispatcher("viewPost.jsp?post_id="+post_id);
			rd.forward(request, response);
			return;
		}
		

		

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
