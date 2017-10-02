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
@WebServlet("/Authenticate_register")
public class Authenticate_register extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Authenticate_register() {
		super();
		// TODO Auto-generated constructor stub
	}

	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String msg = "";
		String username = request.getParameter("username");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String emailID = email.substring(0,email.indexOf("@"));
		
		Boolean isStudent =emailID .matches(".*\\d");
		String profRegMsg ="";
		Student student = null;

		
		if(!email.contains("smu.edu.sg")){
			msg = "Please register with SMU email";
			RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
			request.setAttribute("rmsg", msg);
			rd.forward(request, response);
			return;
		}
		
		ProfessorDAO pDAO = new ProfessorDAO();
		StudentDAO sDAO = new StudentDAO();
		AvatarDAO aDAO = new AvatarDAO();
	
		int avatarID = -1;// = aDAO.lastAvatarID()+1;
		
	    if(!isStudent ){	   		
	   		aDAO.registerAvatar(username);
	   		avatarID = aDAO.retrieveAvatarID(username);
	   		profRegMsg = pDAO.registerProfessor(email,password,avatarID); 
	    }else{ 
	    	student = sDAO.retrieveStudent(emailID, password);
	    }
	    
		
		if(!profRegMsg.equals("")){
			msg = profRegMsg;
			RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
			request.setAttribute("rmsg", msg);
			rd.forward(request, response);
			return;
		}else if(isStudent&&student != null){
			msg="You have registered in the CAT bot";
			RequestDispatcher rd = request.getRequestDispatcher("register.jsp");
			request.setAttribute("rmsg", msg);
			rd.forward(request, response);
			return;
		}else{
			HttpSession session = request.getSession();
			msg="You have successfully registered in our web portal.";
			session.setAttribute("successfulMsg", msg);
			response.sendRedirect("login.jsp");
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
