package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.SummaryDAO;
import entity.Professor;

/**
 * Servlet implementation class EditSummaryQuestion
 */
@WebServlet("/EditSummaryQuestion")
public class EditSummaryQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditSummaryQuestion() {
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
		HttpSession session = request.getSession();

		Professor professor = (Professor) session.getAttribute("professor");
		int avatar_id = professor.getAvatar_id();

		SummaryDAO summaryDAO = new SummaryDAO();

		// direct to jsp or forward the message;

		String id = request.getParameter("q_id");
		String editedQuestion = request.getParameter("editQuestion");
		int question_id = Integer.parseInt(id);
		summaryDAO.updateQuestion(question_id, editedQuestion); 		
		response.sendRedirect("postClassSummary.jsp");

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
