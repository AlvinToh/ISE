package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.PostDAO;
import entity.Professor;
import entity.Student;

/**
 * Servlet implementation class ReplyToRepliedPost
 */
@WebServlet("/ReplyToRepliedPost")
public class ReplyToRepliedPost extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReplyToRepliedPost() {
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
		
		int postID = Integer.parseInt((String)request.getParameter("postID"));
		
		// for reply to reply
        String tempParentID = (String)request.getParameter("ParentID");
		int ParentID = Integer.parseInt(tempParentID);
		int level = postDAO.getPostLevel(postID);
		String tempPostTitle = request.getParameter("postTitle");
		String tempPostContent = request.getParameter("postContent");
		String tempReplyToPostContent = request.getParameter("replyToPostContent");
		
		System.out.println("postID "+postID);
		System.out.println("ParentID "+ParentID);
		System.out.println(" postID level "+level);
		
		System.out.println("tempPostTitle "+tempPostTitle);
		System.out.println("tempPostContent "+tempPostContent);
		System.out.println("tempReplyToPostContent "+tempReplyToPostContent);
		
		//ParentID is NOT empty, but tempPostContent is empty	
		if(tempReplyToPostContent.isEmpty()||tempReplyToPostContent == null){
			errorMsg = "Your reply content cannot be empty!";
			RequestDispatcher rd = request.getRequestDispatcher("replyToRepliedPost.jsp?parentID="+ParentID+"&post_id="+postID);
			request.setAttribute("replyToPost", errorMsg);
			rd.forward(request, response);
			return;
		}else{
			postDAO.replyToPost(avatar_id, level, postID, tempPostTitle, tempReplyToPostContent);
			RequestDispatcher rd = request.getRequestDispatcher("viewPost.jsp?post_id="+ParentID);
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
