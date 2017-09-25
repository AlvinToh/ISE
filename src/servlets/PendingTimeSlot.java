package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ConsultationTimeSlotDAO;
import msAuth.FullCalendarEvent;

/**
 * Servlet implementation class PendingTimeSlot
 */
@WebServlet("/PendingTimeSlot")
public class PendingTimeSlot extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PendingTimeSlot() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	
    	HttpSession session = request.getSession();
    	String professorEmail = (String) session.getAttribute("email");
    	String timeSlotID = request.getParameter("timeslot_id");
    	String postAction = request.getParameter("postAction");
    	
    	ConsultationTimeSlotDAO consultationTimeSlotDAO = new ConsultationTimeSlotDAO();
    	consultationTimeSlotDAO.updateStatus(professorEmail, timeSlotID, postAction);
    	FullCalendarEvent FcEvent = consultationTimeSlotDAO.retrieveStudentTimeSlot(professorEmail, timeSlotID);
    	
    	if(postAction.equals("approve")){
    		String msg="Student timeslot approved successfully.";
    		session.setAttribute("postAction", postAction);
    		session.setAttribute("approvedTimeSlotMsg", msg);
    		session.setAttribute("FcEvent", FcEvent);
    		response.sendRedirect(request.getContextPath() + "/Postcalendar");
    	}
    	if(postAction.equals("reject")){
    		String msg="Student timeslot rejected successfully.";
    		session.setAttribute("postAction", postAction);
    		session.setAttribute("rejectedTimeSlotMsg", msg);
    		session.setAttribute("FcEvent", FcEvent);
    		response.sendRedirect(request.getContextPath() + "/Postcalendar");
    	}
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
