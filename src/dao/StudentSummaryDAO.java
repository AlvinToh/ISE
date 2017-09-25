package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import entity.PostClassSummaryAnswer;


public class StudentSummaryDAO {
	private static final String TBLNAME = "post_class_summary_answer";

	public static void main(String[] args) {
		StudentSummaryDAO ssDAO = new StudentSummaryDAO();
	/*	HashMap <String, PostClassSummaryAnswer> map = ssDAO.retriveStudentSummary("G10", 1);
		
		for(PostClassSummaryAnswer p: map.values()){
			System.out.println("Q: "+ p.getQuestion_hist() + ",  A: "+p.getAnswer());
		}*/
		
		List<String> stuList = ssDAO.getStudentList("G10", 1);
		for(String s: stuList){
			System.out.println(s);
		}

	}
	
	  private void handleSQLException(SQLException ex, String sql, String... parameters) {
	        String msg = "Unable to access data; SQL=" + sql + "\n";
	        for (String parameter : parameters) {
	            msg += "," + parameter;
	        }
	        Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, msg, ex);
	        throw new RuntimeException(msg, ex);
	    }
	  
	  // retrieve student summary by student email id, view this student's all submission	  
	  public HashMap<Integer,PostClassSummaryAnswer> retriveStudentSummaryByEmailID (String smu_email_id, int week){
		    Connection conn = null;
	        PreparedStatement stmt = null;
	        String sql = "";
	        HashMap<Integer,PostClassSummaryAnswer> tempSummary = new HashMap<>();
	        ResultSet rs = null;

	        try {
	            conn = ConnectionManager.getConnection();

	            sql = "select question_id, question_hist, answer from " + TBLNAME + " where smu_email_id = ? and week = ?";
	            stmt = conn.prepareStatement(sql);
	            stmt.setString(1, smu_email_id);
	            stmt.setInt(2, week);
	          

	            rs = stmt.executeQuery();

	            while (rs.next()) {
	                int qid=rs.getInt("question_id");
	                String questionHist = rs.getString("question_hist");	  
	                String answer = rs.getString("answer");
	                tempSummary.put(qid, new PostClassSummaryAnswer(qid, questionHist, answer));
	            }

	        } catch (SQLException ex) {
	            handleSQLException(ex, sql, "Summary Record={" + tempSummary + "}");
	        } finally {
	            ConnectionManager.close(conn, stmt, rs);
	        }
	        return tempSummary;
	  }
	  
	  // retrieve submitted student list and display in stuSubmittedSummary.jsp 
	  public List<String> getStudentList(String groupID, int week){
		  Connection conn = null;
	        PreparedStatement stmt = null;
	        String sql = "";
	        List<String> studentList = new ArrayList<>();
	        ResultSet rs = null;

	        try {
	            conn = ConnectionManager.getConnection();

	            sql = "select * from " + TBLNAME + " where group_id = ? and week = ?";
	            stmt = conn.prepareStatement(sql);
	            stmt.setString(1, groupID);
	            stmt.setInt(2, week);

	            rs = stmt.executeQuery();

			while (rs.next()) {
				String smu_email_id=rs.getString("smu_email_id");
				if(!studentList.contains(smu_email_id)){
					studentList.add(smu_email_id);
				}

				
			}

	        } catch (SQLException ex) {
	            handleSQLException(ex, sql, "Student List={" + studentList + "}");
	        } finally {
	            ConnectionManager.close(conn, stmt, rs);
	        }
	        return studentList;
		  
	  }
	  
	  //retrieve all Summary by groupID and Week
	  public HashMap<String,PostClassSummaryAnswer> retriveStudentSummary (String groupID, int week){
		    Connection conn = null;
	        PreparedStatement stmt = null;
	        String sql = "";
	        HashMap<String,PostClassSummaryAnswer> studentSummary = new HashMap<>();
	        ResultSet rs = null;

	        try {
	            conn = ConnectionManager.getConnection();

	            sql = "select * from " + TBLNAME + " where group_id = ? and week = ?";
	            stmt = conn.prepareStatement(sql);
	            stmt.setString(1, groupID);
	            stmt.setInt(2, week);

	            rs = stmt.executeQuery();

			while (rs.next()) {
				String smu_email_id=rs.getString("smu_email_id");
				int weekNo=rs.getInt("week");
				int question_id=rs.getInt("question_id");
				String question_hist=rs.getString("question_hist");
				String answer=rs.getString("answer");
				String group_id=rs.getString("group_id");

				studentSummary.put(smu_email_id+"-"+question_id, new PostClassSummaryAnswer(smu_email_id, weekNo, question_id, question_hist, answer, group_id));
			}

	        } catch (SQLException ex) {
	            handleSQLException(ex, sql, "Student Summary={" + studentSummary + "}");
	        } finally {
	            ConnectionManager.close(conn, stmt, rs);
	        }
	        return studentSummary;
	  }
}
