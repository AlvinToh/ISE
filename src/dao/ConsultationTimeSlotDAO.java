package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import msAuth.FullCalendarEvent;

public class ConsultationTimeSlotDAO {
	
	private static final String TBLNAME = "consultationTimeSlot";
	
	private void handleSQLException(SQLException ex, String sql, String... parameters) {
        String msg = "Unable to access data; SQL=" + sql + "\n";
        for (String parameter : parameters) {
            msg += "," + parameter;
        }
        Logger.getLogger(ConsultationTimeSlotDAO.class.getName()).log(Level.SEVERE, msg, ex);
        throw new RuntimeException(msg, ex);
    }
	
	public String createFreeTimeSlot(String professorEmail,String title, String startDateTime, String endDateTime){
    	String msg = "";
    	Connection conn = null;
	    PreparedStatement stmt = null;
	    String sql = "";
	    

	    try {
	    	conn = ConnectionManager.getConnection();
	    	sql = "insert into consultationTimeSlot (smu_email,title,start_DateTime,end_DateTime) values (?,?,?,?)";
	        stmt = conn.prepareStatement(sql);
	        stmt.setString(1,professorEmail);
	        stmt.setString(2,title);
	        stmt.setString(3,startDateTime);
	        stmt.setString(4,endDateTime);
	        stmt.executeUpdate();

        } catch (SQLException ex) {
        	 msg = "An exception occurs during creating free time slot for professor";
	         handleSQLException(ex, sql, "msg={" + msg + "}");
	    } finally {
	            ConnectionManager.close(conn, stmt);
	    }
    	return msg;
    }
	
	public ArrayList<FullCalendarEvent> retrieveFreeTimeSlots(String professorEmail) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "";
        ResultSet rs = null;
        
        ArrayList<FullCalendarEvent> fcEventList = new ArrayList<>();

        try {
            conn = ConnectionManager.getConnection();

            sql = "select title, start_DateTime, end_DateTime from " + TBLNAME + " where smu_email = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, professorEmail);

            rs = stmt.executeQuery();

            while (rs.next()) {
                String title = rs.getString(1);
                String startDateTime = rs.getString(2);
                String endDateTime = rs.getString(3);
                
                FullCalendarEvent fcEvent = new FullCalendarEvent();
                fcEvent.setTitle(title);
                fcEvent.setStart(startDateTime);
                fcEvent.setEnd(endDateTime);
                fcEvent.setColor("green");
                
                fcEventList.add(fcEvent);
                
            }
            //return resultUser;

        } catch (SQLException ex) {
            handleSQLException(ex, sql, "User={" + fcEventList + "}");
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return fcEventList;
	}
    
	public void updateStatus (String professorEmail, String timeslotId, String action) {
		Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "";
        ResultSet rs = null;
		

		
		try {
	            conn = ConnectionManager.getConnection();

	            sql = "update student_consultationtimeslot set status = ? where smu_email = ? and timeslot_id = ? ";
	            stmt = conn.prepareStatement(sql);
	            stmt.setString(1, action);
	            stmt.setString(2, professorEmail);
	            stmt.setString(3, timeslotId);

	            stmt.executeUpdate();

	        } catch (SQLException ex) {
	            handleSQLException(ex, sql, "User={" + professorEmail + "}");
	        } finally {
	            ConnectionManager.close(conn, stmt, rs);
	        }
	}
	
	public FullCalendarEvent retrieveStudentTimeSlot (String professorEmail, String timeslot_id){
		Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "";
        ResultSet rs = null;
		
        FullCalendarEvent fcEvent = new FullCalendarEvent();
        try{
        	conn = ConnectionManager.getConnection();
        	sql = "select title, student_email, start_DateTime, end_DateTime from student_consultationtimeslot where smu_email = ? and timeslot_id = ? ";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, professorEmail);
        	stmt.setString(2, timeslot_id);
        	
        	rs = stmt.executeQuery();
        	
        	while (rs.next()) {
                String title = rs.getString(1);
                String studentEmail = rs.getString(2); 
                String startDateTime = rs.getString(3);
                String endDateTime = rs.getString(4);
                
                fcEvent.setTitle(title);
                fcEvent.setStudentEmail(studentEmail);
                fcEvent.setStart(startDateTime);
                fcEvent.setEnd(endDateTime);
                fcEvent.setColor("yellow");                
            }
            //return resultUser;

        } catch (SQLException ex) {
            handleSQLException(ex, sql, "User={" + fcEvent + "}");
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        	
		return fcEvent;
	}
	
	public ArrayList<FullCalendarEvent> retrieveStudentTimeSlots(String professorEmail, String action) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "";
        ResultSet rs = null;
        
        ArrayList<FullCalendarEvent> fcEventList = new ArrayList<>();

        try {
            conn = ConnectionManager.getConnection();

            sql = "select title, student_email, start_DateTime, end_DateTime from student_consultationtimeslot where smu_email = ? and status = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, professorEmail);
            stmt.setString(2, action);

            rs = stmt.executeQuery();

            while (rs.next()) {
                String title = rs.getString(1);
                String studentEmail = rs.getString(2); 
                String startDateTime = rs.getString(3);
                String endDateTime = rs.getString(4);
                
                FullCalendarEvent fcEvent = new FullCalendarEvent();
                fcEvent.setTitle(title);
                fcEvent.setStudentEmail(studentEmail);
                fcEvent.setStart(startDateTime);
                fcEvent.setEnd(endDateTime);
                fcEvent.setColor("yellow"); 
                fcEventList.add(fcEvent);
                
            }
            //return resultUser;

        } catch (SQLException ex) {
            handleSQLException(ex, sql, "User={" + fcEventList + "}");
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return fcEventList;
	}
	
}
