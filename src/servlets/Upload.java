package servlets;

import utility.Unzip;
//import utility.BootstrapValidation;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.ProfessorDAO;
import dao.StudentDAO;
import entity.Professor;
import utility.BootstrapUpload;


@WebServlet("/Upload")
/**
 * Process Bootstrap
 */
public class Upload extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	String dir = "C:/Users/User/workspace/ISE/WebContent/studentList";  
    	File folder = new File(dir);
        File[] dataFiles = folder.listFiles();
        StudentDAO studentDAO = new StudentDAO();
        String bootstrapErrorMsg = "";
        
        HttpSession session = request.getSession();
        Professor prof = (Professor)session.getAttribute("professor");
        ProfessorDAO profDAO = new ProfessorDAO();
        int prof_avatar = prof.getAvatar_id();
        
        for (File file : dataFiles) {
        	String extension = "";
        	String filename = file.getName();
        	
        	int i = filename.lastIndexOf('.');
        	if (i >= 0) { extension = filename.substring(i+1); }
            //check that it does not unzip a directory
        	if (extension.equals("zip")) {
        		String unzipPath = dir + File.separator + "data";
        		Unzip.unzipFile(file,unzipPath);
        		
        		File unzipFolder = new File(unzipPath);
                File[] unzipFiles = unzipFolder.listFiles();
                for(File unzipFile:unzipFiles){
	              	HashMap<String,String> studentList = studentDAO.readStudentListCSV(unzipFile.getCanonicalPath());
	              	bootstrapErrorMsg = studentDAO.upload(studentList);
	              	Object[] obj_sections = studentList.values().stream().distinct().toArray();
	                String[] sections =Arrays.asList(obj_sections).toArray(new String[obj_sections.length]);
	                profDAO.deleteProfessorSections(sections);
	                profDAO.registerProfessorSections(prof_avatar, sections);
                }
               
            }else{
            	HashMap<String,String> studentList = studentDAO.readStudentListCSV(file.getCanonicalPath());
            	bootstrapErrorMsg = studentDAO.upload(studentList);
            	Object[] obj_sections = studentList.values().stream().distinct().toArray();
                String[] sections =Arrays.asList(obj_sections).toArray(new String[obj_sections.length]);
                profDAO.deleteProfessorSections(sections);
                profDAO.registerProfessorSections(prof_avatar, sections);
  
            }
        }
        
        if(bootstrapErrorMsg !=""){
        	RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
     		request.setAttribute("bootstrapErrorMsg", bootstrapErrorMsg);
     		rd.forward(request, response);	
     		return;
        }else{
	        RequestDispatcher rd = request.getRequestDispatcher("home.jsp");
			request.setAttribute("bootstrapSuccessMsg", "Uploaded successfully!");
			rd.forward(request, response);	
			return;
        }
}

// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
/**
 * Handles the HTTP <code>GET</code> method.
 *
 * @param request servlet request
 * @param response servlet response
 * @throws ServletException if a servlet-specific error occurs
 * @throws IOException if an I/O error occurs
 */
@Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
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
