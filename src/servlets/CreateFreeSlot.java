package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ConsultationTimeSlotDAO;
import entity.Professor;

/**
 * Servlet implementation class CreateFreeSlot
 */
@WebServlet("/CreateFreeSlot")
public class CreateFreeSlot extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CreateFreeSlot() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	
    	HttpSession session = request.getSession();
    	Professor professor = (Professor) session.getAttribute("professor");
    	String professorEmail = professor.getProf_smu_email();
    	String title = request.getParameter("title");
    	String startDateTime = request.getParameter("startDateTime");
		String endDateTime = request.getParameter("endDateTime");
		
		ConsultationTimeSlotDAO consultationTimeSlotDAO = new ConsultationTimeSlotDAO();
		consultationTimeSlotDAO.createFreeTimeSlot(professorEmail, title, startDateTime, endDateTime);
		
		String msg="Free timeslot created successfully.";
		session.setAttribute("successfulTimeSlotMsg", msg);
		response.sendRedirect("consultation.jsp");
    }
    

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		processRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
