package servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.*;

/**
 * Servlet implementation class AssignMarksForPost
 */
@WebServlet("/AssignMarksForPost")
public class AssignMarksForPost extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AssignMarksForPost() {
        super();
        // TODO Auto-generated constructor stub
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	String errorMsg="";
    	// for checking number format
		WeeklyPostSummaryDAO wpsDAO =  new WeeklyPostSummaryDAO();
		PostDAO postDAO = new PostDAO();
		String tempPostID = request.getParameter("postID");
		int postID = Integer.parseInt(tempPostID);
		
		String tempParentID = request.getParameter("parentID");
		int parentID = Integer.parseInt(tempParentID);
		

		
		String tempMark = request.getParameter("mark");
		
		
		if(wpsDAO.numberOrNot(tempMark)== false){
			errorMsg="Please enter a number!";
			RequestDispatcher rd = request.getRequestDispatcher("viewPostContent.jsp?parentID="+parentID+"&post_id="+postID);
			request.setAttribute("error", errorMsg);
			rd.forward(request, response);
			return;
			
		}	
		
		float mark = Float.parseFloat(tempMark);

		postDAO.updateMark(postID, mark);
		response.sendRedirect("viewPostContent.jsp?parentID=" + parentID + "&post_id=" + postID);
		

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
