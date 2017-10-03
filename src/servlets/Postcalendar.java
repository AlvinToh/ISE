package servlets;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
//import java.util.logging.Level;
//import java.util.logging.Logger;

//import javax.mail.BodyPart;
//import javax.mail.MessagingException;
//import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeMultipart;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.simplejavamail.email.Email;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.config.ServerConfig;
import org.simplejavamail.mailer.config.TransportStrategy;
import org.simplejavamail.util.ConfigLoader;

import msAuth.FullCalendarEvent;
import msAuth.MSEvent;
import msAuth.OutlookService;
import msAuth.OutlookServiceBuilder;

/**
 * Servlet implementation class Postcalendar
 */
@WebServlet("/Postcalendar")
public class Postcalendar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Postcalendar() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
    	HttpSession session = request.getSession();
    	FullCalendarEvent FcEvent = (FullCalendarEvent) session.getAttribute("FcEvent");
    	
		String accessToken = (String) session.getAttribute("accessToken");
		String postAction = (String) session.getAttribute("postAction");
		
		//POSTCalendar smtp Server is hardcoded for now
		ConfigLoader.loadProperties("simplejavamail.properties", true); // optional default
		ConfigLoader.loadProperties("overrides.properties", true); // optional extra
		
		String smtpGmail = "catbot102ise@gmail.com";
		String smtpGmailPW = "catbotpassword";
		
		Mailer mailer = new Mailer(new ServerConfig("smtp.gmail.com", 587, smtpGmail, smtpGmailPW), TransportStrategy.SMTP_TLS);
		Email studentEmail = new Email();
		Email profEmail = new Email();
		
		//emailSend.addRecipients("optional default name", CC, "rocky@candyshop.org; Chocobo <chocobo@candyshop.org>");
		
		//HTML mail content
/*		Session session = Session.getDefaultInstance(props);
		MimeMessage message = new MimeMessage(session);
		try{
			MimeMultipart multipart = new MimeMultipart();
			BodyPart messageBodyPart = new MimeBodyPart();
			
			Map<String, String> input = new HashMap<String, String>();
			String htmlText = readEmailFromHtml("templates/HTMLEmailTemplate", input);
			messageBodyPart.setContent(htmlText, "text/html");
			multipart.addBodyPart(messageBodyPart); 
			message.setContent(multipart);
		} catch (MessagingException ex) {
			Logger.getLogger(Postcalendar.class.getName()).log(Level.SEVERE, null, ex);
		} catch (Exception ae) {
			ae.printStackTrace();
		}
		*/
		
		if(postAction.equals("approve")){
			
			OutlookService outlookService = OutlookServiceBuilder.getOutlookService(accessToken, FcEvent.getProfEmail());

			// Set the parameters to create an event in Outlook Calendar
			// Need to set the correct format for JSON to sent to outlook server
			MSEvent msEvent = new MSEvent(FcEvent.getTitle(), FcEvent.getStart(), FcEvent.getEnd());
			
			try {
				outlookService.createEvent(msEvent).execute().body();
				//Used for checking if event is successfully created
				String msg = "Successfully created Event in outlook";
				session.setAttribute("successfulCreateEvent", msg);
			} catch (IOException e) {
				session.setAttribute("error", e.getMessage());
			}

			//Student Approved Email Notification
			studentEmail.setFromAddress("CAT102 Platform", smtpGmail);
			studentEmail.setReplyToAddress("CAT102 Platform", smtpGmail);
			
			//Testing my SMU Email address first
			studentEmail.addNamedToRecipients(FcEvent.getStudentEmail(), FcEvent.getStudentEmail());
			studentEmail.setSubject("Approved Consultation Timeslot for: " + FcEvent.getTitle());
			studentEmail.setText("Hi " + FcEvent.getStudentEmail() + ", "
				+ "\n\tyour consultation timeslot has been approved. "
				+ "\n\tThe details of the consultation timeslot are as follows,"
				+ "\n\t\tTitle: " + FcEvent.getTitle()
				+ "\n\t\tStart: " + FcEvent.getStart()
				+ "\n\t\tEnd: " + FcEvent.getEnd()
				+ "\n\tThank you for using CATBot 102 Platform."
			);
			
			mailer.sendMail(studentEmail);
			
			//Professor Approved Email Notification
			profEmail.setFromAddress("CAT102 Platform", smtpGmail);
			profEmail.setReplyToAddress("CAT102 Platform", smtpGmail);
			
			//Testing ISE outlook email address first
			profEmail.addNamedToRecipients(FcEvent.getProfEmail(), FcEvent.getProfEmail());
			profEmail.setSubject("Approved Consultation Timeslot for: " + FcEvent.getTitle());
			profEmail.setText("Hi " + FcEvent.getProfEmail() + ", "
				+ "\n\tyour consultation timeslot has been approved. "
				+ "\n\tThe details of the consultation timeslot are as follows,"
				+ "\n\t\tTitle: " + FcEvent.getTitle()
				+ "\n\t\tStart: " + FcEvent.getStart()
				+ "\n\t\tEnd: " + FcEvent.getEnd()
				+ "\n\tThank you for using CATBot 102 Platform."
			);
			
			mailer.sendMail(profEmail);
			
		}else if(postAction.equals("reject")){
			
			//Student Rejected Email Notification
			studentEmail.setFromAddress("CAT102 Platform", smtpGmail);
			studentEmail.setReplyToAddress("CAT102 Platform", smtpGmail);
			
			//Testing my SMU Student Email address first
			studentEmail.addNamedToRecipients(FcEvent.getStudentEmail(), FcEvent.getStudentEmail());
			studentEmail.setSubject("Rejected Consultation Timeslot for: " + FcEvent.getTitle());
			studentEmail.setText("Hi " + FcEvent.getStudentEmail() + ", "
				+ "\n\tyour consultation timeslot has been rejected. "
				+ "\n\tThe details of the consultation timeslot are as follows,"
				+ "\n\t\tTitle: " + FcEvent.getTitle()
				+ "\n\t\tStart: " + FcEvent.getStart()
				+ "\n\t\tEnd: " + FcEvent.getEnd()
				+ "\n\tThank you for using CATBot 102 Platform."
			);
			
			mailer.sendMail(studentEmail);
			
			//Professor Rejected Email Notification
			profEmail.setFromAddress("CAT102 Platform", smtpGmail);
			profEmail.setReplyToAddress("CAT102 Platform", smtpGmail);
			
			//Testing ISE outlook email address first
			profEmail.addNamedToRecipients(FcEvent.getProfEmail(), FcEvent.getProfEmail());
			profEmail.setSubject("Rejected Consultation Timeslot for: " + FcEvent.getTitle());
			profEmail.setText("Hi " + FcEvent.getProfEmail() + ", "
				+ "\n\tyour consultation timeslot has been rejected. "
				+ "\n\tThe details of the consultation timeslot are as follows,"
				+ "\n\t\tTitle: " + FcEvent.getTitle()
				+ "\n\t\tStart: " + FcEvent.getStart()
				+ "\n\t\tEnd: " + FcEvent.getEnd()
				+ "\n\tThank you for using CATBot 102 Platform."
			);
			
			mailer.sendMail(profEmail);
			
		}
		
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
	
	// Method to replace the values for keys
	protected String readEmailFromHtml(String filePath, Map<String, String> input){
		String msg = readContentFromFile(filePath);
		try{
			Set<Entry<String, String>> entries = input.entrySet();
			for(Map.Entry<String, String> entry : entries) {
				msg = msg.replace(entry.getKey().trim(), entry.getValue().trim());
			}
		} catch(Exception exception){
			exception.printStackTrace();
		}
			return msg;
	}

	// Method to read HTML file as a String
	private String readContentFromFile(String fileName) {
		StringBuffer contents = new StringBuffer();
		try {
		//use buffering, reading one line at a time
		BufferedReader reader = new BufferedReader(new FileReader(fileName));
			try {
				String line = null; 
				while (( line = reader.readLine()) != null){
					contents.append(line);
					contents.append(System.getProperty("line.separator")); 
				}
			} finally {
				reader.close();
			}
		} catch (IOException ex){
			ex.printStackTrace();
		}
		return contents.toString();
	}
}
