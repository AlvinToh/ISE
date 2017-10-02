package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.WeeklyPostSummaryDAO;
/**
 * Servlet implementation class AssignMarks
 */
@WebServlet("/AssignMarks")
public class AssignMarks extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssignMarks() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	String errorMsg="";
		WeeklyPostSummaryDAO wpsDAO =  new WeeklyPostSummaryDAO();
		String studentID = request.getParameter("stuid");
		
		String week = request.getParameter("week");
		int weekNo = Integer.parseInt(week);
		
		String group = request.getParameter("group");
		
		String tempMark = request.getParameter("mark");
		
		
		if(wpsDAO.numberOrNot(tempMark)== false){
			errorMsg="Please enter a number!";
			RequestDispatcher rd = request.getRequestDispatcher("viewSummaries.jsp?week="+weekNo+ "&session="+group);
			request.setAttribute("error", errorMsg);
			rd.forward(request, response);
			return;
			
		}		
		
		float  mark = Float.parseFloat(tempMark);
		String btnName = request.getParameter("hdnbt");	


		if(btnName.equals("Save")){
			wpsDAO.saveMark(studentID, weekNo, mark);
			response.sendRedirect("viewSummaries.jsp?week="+weekNo+ "&session="+group);
		}
		if (btnName.equals("Edit")) {
			wpsDAO.updateMark(studentID, weekNo, mark);
			response.sendRedirect("viewSummaries.jsp?week=" + weekNo + "&session=" + group);

		}
		
/*				
		System.out.println(studentID+group+"PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
		System.out.println(weekNo+"PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
		System.out.println(tempMark+"__PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");
		System.out.println(btnName+"__PPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPPP");*/
		
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
