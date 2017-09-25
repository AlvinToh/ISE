package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import dao.ConsultationTimeSlotDAO;
import entity.Professor;
import msAuth.Event;
import msAuth.FullCalendarEvent;

import java.util.*;


/**
 * Servlet implementation class Calendarjson
 */
@WebServlet("/Calendarjson")
public class Calendarjson extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Calendarjson() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<FullCalendarEvent> list = new ArrayList<FullCalendarEvent>();
		
		HttpSession session = request.getSession();
		Event[] arrEvents = (Event[]) session.getAttribute("events");
		
		if(arrEvents != null){
		for(Event event: arrEvents){
			FullCalendarEvent fcEvent = new FullCalendarEvent();
			fcEvent.setId(event.getId());
			fcEvent.setTitle(event.getSubject());
			
			Date dEnd = event.getEnd().getDateTime();
			Date dStart = event.getStart().getDateTime();
			
			long difference = dEnd.getTime() - dStart.getTime();
			//if milliseconds == 86400000 means equals 1 day so is full day
			if(difference == 86400000){
				fcEvent.setAllDay(true);
			}
			
			//Conversion to ISO 8601 String from events date
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
			String start = df.format(event.getStart().getDateTime());
			String end = df.format(event.getEnd().getDateTime());
			fcEvent.setStart(start);
			fcEvent.setEnd(end);
			list.add(fcEvent);	
		}
		}
		 
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		PrintWriter out = response.getWriter();
		
		//Add the events from Consultation timeslot for creating free slot by professor
		Professor professor = (Professor) session.getAttribute("professor");
		String professorEmail = professor.getProf_smu_email();
		
		ConsultationTimeSlotDAO consultationTimeSlotDAO = new ConsultationTimeSlotDAO();
		List<FullCalendarEvent> freeTimeSlotList = consultationTimeSlotDAO.retrieveFreeTimeSlots(professorEmail);
		//Does not make sense to get approved timeslot list, final source of truth should be outlook
		//List<FullCalendarEvent> approvedStudentTimeSlotList = consultationTimeSlotDAO.retrieveStudentTimeSlots(professorEmail, "approve");
		
		list.addAll(freeTimeSlotList);
		//list.addAll(approvedStudentTimeSlotList);
		out.write(new Gson().toJson(list));
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
