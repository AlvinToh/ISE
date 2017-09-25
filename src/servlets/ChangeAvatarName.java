package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.AvatarDAO;
import entity.Student;
import entity.Professor;
/**
 * Servlet implementation class EditSummaryQuestion
 */
@WebServlet("/ChangeAvatarName")
public class ChangeAvatarName extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ChangeAvatarName() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String old_avatar_name = request.getParameter("old_avatar_name");
		String new_avatar_name = request.getParameter("new_avatar_name");
		
		int avatar_id = -1;
		HttpSession session = request.getSession();
		Professor professor = (Professor) session.getAttribute("professor");
		Student student = (Student) session.getAttribute("student");
		
		if(student!=null){
			avatar_id = student.getAvatar_id();
		}else if(professor!=null){
			avatar_id = professor.getAvatar_id();
		}
		
		AvatarDAO avatarDAO = new AvatarDAO();
		avatarDAO.changeAvatarName(avatar_id, old_avatar_name,new_avatar_name);
		
		RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
		request.setAttribute("changeAvatarNameMsg", "Avatar name is changed successfully!");
		rd.forward(request, response);
		return;

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
