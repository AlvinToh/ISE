package servlets;

import utility.Unzip;
//import utility.BootstrapValidation;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.AvatarDAO;

@WebServlet("/UploadNameList")
/**
 * Process Bootstrap
 */
public class UploadNameList extends HttpServlet {

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// String dir = "C:/Users/User/workspace/ISE/WebContent/studentList";
		String dir = "C:/Users/User/workspace/ISE/WebContent/nameList";
		File folder = new File(dir);
		File[] dataFiles = folder.listFiles();
		AvatarDAO avatarDAO = new AvatarDAO();
		int totalUploaded = 0;
		
		for (File file : dataFiles) {
			String extension = "";
			String filename = file.getName();

			int i = filename.lastIndexOf('.');
			if (i >= 0) {
				extension = filename.substring(i + 1);
			}
			// check that it does not unzip a directory
			if (extension.equals("zip")) {
				String unzipPath = dir + File.separator + "data";
				Unzip.unzipFile(file, unzipPath);

				File unzipFolder = new File(unzipPath);
				File[] unzipFiles = unzipFolder.listFiles();
				for (File unzipFile : unzipFiles) {
					ArrayList<String> nameList = avatarDAO.readNameListCSV(unzipFile.getCanonicalPath());
					Object[] obj_names = nameList.stream().distinct().toArray();
	            	String[] names =Arrays.asList(obj_names).toArray(new String[obj_names.length]);
	            	totalUploaded += avatarDAO.upload(names);
				}

			} else {
				ArrayList<String> nameList = avatarDAO.readNameListCSV(file.getCanonicalPath());
				Object[] obj_names = nameList.stream().distinct().toArray();
            	String[] names =Arrays.asList(obj_names).toArray(new String[obj_names.length]);
            	totalUploaded+=avatarDAO.upload(names);
			}
		}

		RequestDispatcher rd = request.getRequestDispatcher("uploadNameList.jsp");
		request.setAttribute("uploadNameListSuccessMsg",totalUploaded+" records uploaded successfully!");
		rd.forward(request, response);
		return;

	}

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on
	// the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>
}
