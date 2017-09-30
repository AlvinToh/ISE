package servlets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.RequestDispatcher;
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
		
		//Round off seconds component from the Date string to 00
		String subStartDateTime = startDateTime.substring(0, startDateTime.length()-2) + "00";
		String subEndDateTime = endDateTime.substring(0, endDateTime.length()-2) + "00";
		
		String errorTimeSlotMsg = "";
		Date startDate = new Date();
		Date endDate = new Date();
		
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try{
			startDate = df.parse(startDateTime);
			endDate = df.parse(endDateTime);
		}catch (ParseException e){
			e.printStackTrace();
		}
		
		if(startDate.after(endDate)){
			errorTimeSlotMsg = "Error: Start datetime is later than the End datetime!";
			RequestDispatcher rd = request.getRequestDispatcher("consultation.jsp");
			request.setAttribute("errorTimeSlotMsg", errorTimeSlotMsg);
			rd.forward(request, response);
			
    	} else{
		
    		ConsultationTimeSlotDAO consultationTimeSlotDAO = new ConsultationTimeSlotDAO();
    		consultationTimeSlotDAO.createFreeTimeSlot(professorEmail, title, subStartDateTime, subEndDateTime);
		
    		String msg="Free timeslot created successfully.";
			session.setAttribute("successfulTimeSlotMsg", msg);
			response.sendRedirect("consultation.jsp");
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
