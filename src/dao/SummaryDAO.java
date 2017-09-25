package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SummaryDAO {

	private static final String TBLNAME = "post_class_summary_questions";
	
	public static void main(String[] args){
		System.out.println("test here");
		SummaryDAO sdao = new SummaryDAO();
		//sdao.addQuestion("hahahahha", 1);
		HashMap<Integer,String> map =  sdao.retrieveByAvatar(1);
		for (Integer key:map.keySet()){
			System.out.println(key+"--"+map.get(key));
		}
		sdao.updateQuestion(4,"question ba");
		
		System.out.println(sdao.retrieveByQID(7));

	}

	
    private void handleSQLException(SQLException ex, String sql, String... parameters) {
        String msg = "Unable to access data; SQL=" + sql + "\n";
        for (String parameter : parameters) {
            msg += "," + parameter;
        }
        Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, msg, ex);
        throw new RuntimeException(msg, ex);
    }
    
    public void addQuestion(String question, int avatar_id){
    	Connection conn = null;
	    PreparedStatement stmt = null;
	    String sql = "";	   
	
	    
	    try {
	    	conn = ConnectionManager.getConnection();
	    	sql = "INSERT INTO "+ TBLNAME +" (question,avatar_id) values (?,?)";
	        stmt = conn.prepareStatement(sql);
	       
	        stmt.setString(1,question); 
	        stmt.setInt(2,avatar_id); 
	        stmt.executeUpdate();

        } catch (SQLException ex) {
        	 String msg = "An exception occurs when adding new question in the post class summary questions";
	         handleSQLException(ex, sql, "msg={" + msg + "}");
	    } finally {
	            ConnectionManager.close(conn, stmt);
	    }
    }
    
    public HashMap<Integer, String> retrieveByAvatar(int avatar_id) {
        HashMap<Integer, String> summaryQuestions = new HashMap<>();

        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement preStmt = null;
        
        try {
            conn = ConnectionManager.getConnection();
            String sql = "select question_id, question from " + TBLNAME +" where avatar_id = ?";
            preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, avatar_id);
            rs = preStmt.executeQuery();

            while (rs.next()) {
            	int question_id = rs.getInt(1);
            	String question = rs.getString(2);
            	summaryQuestions.put(question_id, question);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, preStmt, rs);
        }
        return summaryQuestions;
    }
    
    public String retrieveByQID(int QID) {
        String returnQ = null;

        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement preStmt = null;
        
        try {
            conn = ConnectionManager.getConnection();
            String sql = "select question from " + TBLNAME +" where question_id = ?";
            preStmt = conn.prepareStatement(sql);
            preStmt.setInt(1, QID);
            rs = preStmt.executeQuery();

            while (rs.next()) {
            	returnQ = rs.getString("question");
            	
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            ConnectionManager.close(conn, preStmt, rs);
        }
        return returnQ;
    }
    
    public void deleteQuestion (int question_id){
    	Connection conn = null;
	    PreparedStatement stmt = null;
	    String sql = "";	   
	    
	    try {
	    	conn = ConnectionManager.getConnection();
	    	sql = "delete from "+ TBLNAME +" where question_id=?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setInt(1,question_id); 
	        stmt.executeUpdate();

        } catch (SQLException ex) {
        	 String msg = "An exception occurs when delete in the post class summary questions";
	         handleSQLException(ex, sql, "msg={" + msg + "}");
	    } finally {
	            ConnectionManager.close(conn, stmt);
	    }
    }
    
    public void updateQuestion (int question_id,String updated_question){
       	Connection conn = null;
	    PreparedStatement stmt = null;
	    String sql = "";	   
	    
	    try {
	    	conn = ConnectionManager.getConnection();
	    	sql = "update "+ TBLNAME +" set question = ? where question_id = ?";
	        stmt = conn.prepareStatement(sql);
	        stmt.setString(1,updated_question); 
	        stmt.setInt(2,question_id); 
	        stmt.executeUpdate();

        } catch (SQLException ex) {
        	 String msg = "An exception occurs when update question in the post class summary questions";
	         handleSQLException(ex, sql, "msg={" + msg + "}");
	    } finally {
	            ConnectionManager.close(conn, stmt);
	    }
    }
}
