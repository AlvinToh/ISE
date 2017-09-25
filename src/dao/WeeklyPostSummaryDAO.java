package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import entity.WeeklyPostSummary;

public class WeeklyPostSummaryDAO {
	
private static final String TBLNAME = "weekly_post_summary";
	
	public static void main(String[] args){
		WeeklyPostSummaryDAO wdao = new WeeklyPostSummaryDAO();
		System.out.println("This is a test!!!!           "+ wdao.retrieveMark("undefined",1));
		wdao.updateMark("dddd", 100, 100);
		
		System.out.println(wdao.numberOrNot("2")+"........");
		System.out.println(wdao.numberOrNot("u"));
		

	}

	
    private void handleSQLException(SQLException ex, String sql, String... parameters) {
        String msg = "Unable to access data; SQL=" + sql + "\n";
        for (String parameter : parameters) {
            msg += "," + parameter;
        }
        Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, msg, ex);
        throw new RuntimeException(msg, ex);
    }
    
    // retrieve summary marks
    public int retrieveMark(String studentID, int weekNo) {
    	
        int mark=Integer.MAX_VALUE; 
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement preStmt = null;
        
        try {
            conn = ConnectionManager.getConnection();
            String sql = "select mark from " + TBLNAME +" where smu_email_id = ? and week = ?";
   
            preStmt = conn.prepareStatement(sql);
            preStmt.setString(1, studentID);
            preStmt.setInt(2, weekNo);
            rs = preStmt.executeQuery();

            while (rs.next()) {
            	mark = rs.getInt("mark");
            	
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, preStmt, rs);
        }
        return mark;
    }
    // assign marks
    public void saveMark(String studentID, int weekNo, float mark){
    	Connection conn = null;
	    PreparedStatement stmt = null;
	    String sql = "";	   
	
	    
	    try {
	    	conn = ConnectionManager.getConnection();
	    	sql = "INSERT INTO "+ TBLNAME +" (smu_email_id, week, mark) values (?,?,?)";
	        stmt = conn.prepareStatement(sql);
	       
	        stmt.setString(1,studentID); 
	        stmt.setInt(2,weekNo); 
	        stmt.setFloat(3, mark);
	        stmt.executeUpdate();

        } catch (SQLException ex) {
        	 String msg = "An exception occurs when adding marks to weekly post summary";
	         handleSQLException(ex, sql, "msg={" + msg + "}");
	    } finally {
	            ConnectionManager.close(conn, stmt);
	    }
    }
    
    //edit mark
    public void updateMark (String studentID, int weekNo, float mark){
       	Connection conn = null;
	    PreparedStatement stmt = null;
	    String sql = "";	   
	    
	    try {
	    	conn = ConnectionManager.getConnection();
	    	sql = "update "+ TBLNAME +" set mark = ? where smu_email_id = ? and week = ?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setFloat(1, mark);
	        stmt.setString(2,studentID); 
	        stmt.setInt(3,weekNo); 
	        stmt.executeUpdate();

        } catch (SQLException ex) {
        	 String msg = "An exception occurs when update student's marks in the post summary ";
	         handleSQLException(ex, sql, "msg={" + msg + "}");
	    } finally {
	            ConnectionManager.close(conn, stmt);
	    }
    }

	public Boolean numberOrNot(String input) {
	
		try {
			Float.parseFloat(input);
		} catch (NumberFormatException ex) {
			return false;
		}
		return true;
	}
  
    

}
