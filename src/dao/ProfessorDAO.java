package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import entity.*;


public class ProfessorDAO {
	//for debugging purpose
		
//		public static void main(String[] args){
//			System.out.println("ssss");
//			ProfessorDAO pd = new ProfessorDAO();
//			ArrayList<String> sections = new ArrayList<>();
//			sections.add("G3");
//			sections.add("G2");
//			String msg = pd.registerProfessorSections(1,sections);
//			System.out.print(msg);
//	
//		}
		
		private static final String TBLNAME = "professor";
		
	    private void handleSQLException(SQLException ex, String sql, String... parameters) {
	        String msg = "Unable to access data; SQL=" + sql + "\n";
	        for (String parameter : parameters) {
	            msg += "," + parameter;
	        }
	        Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, msg, ex);
	        throw new RuntimeException(msg, ex);
	    }
	    
	    public Professor retrieveProfessor(String emailID, String password) {
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        String sql = "";
	        Professor returnProfessor = null;
	        ResultSet rs = null;

	        try {
	            conn = ConnectionManager.getConnection();

	            sql = "select smu_email, password,avatar_id from " + TBLNAME + " where smu_email_id = binary? and password = SHA1(?)";
	            stmt = conn.prepareStatement(sql);
	            stmt.setString(1, emailID);
	            stmt.setString(2, password);

	            rs = stmt.executeQuery();

	            while (rs.next()) {
	                String prof_smu_email = rs.getString(1);
	                String correctPassword = rs.getString(2);
	                int avatar_id = Integer.parseInt(rs.getString(3));
	                
	                returnProfessor = new Professor(prof_smu_email,correctPassword,avatar_id);
	            }
	            //return resultUser;

	        } catch (SQLException ex) {
	            handleSQLException(ex, sql, "User={" + returnProfessor + "}");
	        } finally {
	            ConnectionManager.close(conn, stmt, rs);
	        }
	        return returnProfessor;
	    }
	    
	    public boolean CheckUniqueEmailID(String emailID) {
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        String sql = "";
	        Professor returnProfessor = null;
	        ResultSet rs = null;

	        try {
	            conn = ConnectionManager.getConnection();

	            sql = "select smu_email, password,avatar_id from " + TBLNAME + " where smu_email_id = ? ";
	            stmt = conn.prepareStatement(sql);
	            stmt.setString(1, emailID);

	            rs = stmt.executeQuery();

	            while (rs.next()) {
	                String prof_smu_email = rs.getString(1);
	                String correctPassword = rs.getString(2);
	                int avatar_id = Integer.parseInt(rs.getString(3));
	                returnProfessor = new Professor(prof_smu_email,correctPassword,avatar_id);
	            }
	            //return resultUser;

	        } catch (SQLException ex) {
	            handleSQLException(ex, sql, "User={" + returnProfessor + "}");
	        } finally {
	            ConnectionManager.close(conn, stmt, rs);
	        }
	       
	        if(returnProfessor !=null){
	        	return false;
	        }else{
	        	return true;
	        }
	    }
	    
	    public boolean CheckSectionExist(String section) {
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        String sql = "";
	        ResultSet rs = null;
	        Integer retrievedAvatar = null;
	        
	        try {
	            conn = ConnectionManager.getConnection();

	            sql = "select avatar_id from professor_section where group_id = ? ";
	            stmt = conn.prepareStatement(sql);
	            stmt.setString(1, section);

	            rs = stmt.executeQuery();

	            while (rs.next()) {
	                retrievedAvatar = rs.getInt(1);
	        
	            }
	        
	        } catch (SQLException ex) {
	            handleSQLException(ex, sql, "retrievedAvatar={" + retrievedAvatar + "}");
	        } finally {
	            ConnectionManager.close(conn, stmt, rs);
	        }
	       
	        if(retrievedAvatar !=null){
	        	return true;
	        }else{
	        	return false;
	        }
	    }
	    
	    public String registerProfessorSections(int avatar_id, String[] sections){
	    	String msg = "";
	    	Connection conn = null;
		    PreparedStatement stmt = null;
		    String sql = "";
		    
		    try {
		    	conn = ConnectionManager.getConnection();
		    	
		    	for(String section:sections){
				    if(CheckSectionExist(section)){
				    	msg = "Please check the sections you teach";
				    	return msg;
				    }
				    
				    sql = "insert into professor_section (avatar_id,group_id) values (?,?)";
			        stmt = conn.prepareStatement(sql);
			        stmt.setInt(1, avatar_id);
			        stmt.setString(2,section); 
			        stmt.executeUpdate();

			    }
		    	
	        } catch (SQLException ex) {
	        	 msg = "An exception occurs during registration";
		         handleSQLException(ex, sql, "msg={" + msg + "}");
		    } finally {
		            ConnectionManager.close(conn, stmt);
		    }
	    	return msg;
	    }
	    
	    public String registerProfessor(String email,String password,int avatar_id){
	    	String msg = "";
	    	Connection conn = null;
		    PreparedStatement stmt = null;
		    String sql = "";
		    String emailID =  email.substring(0,email.indexOf("@"));
		    
		    if(!CheckUniqueEmailID(emailID)){
		    	msg = "email already exists!";
		    	return msg;
		    }
	
		    
		    try {
		    	conn = ConnectionManager.getConnection();
		    	sql = "insert into professor (smu_email, smu_email_id,avatar_id,password) values (?,?,?,SHA1(?))";
		        stmt = conn.prepareStatement(sql);
		        stmt.setString(1, email);
		        stmt.setString(2,emailID);
		        stmt.setInt(3,avatar_id);
		        stmt.setString(4, password);  
		        stmt.executeUpdate();

	        } catch (SQLException ex) {
	        	 msg = "An exception occurs during registration";
		         handleSQLException(ex, sql, "msg={" + msg + "}");
		    } finally {
		            ConnectionManager.close(conn, stmt);
		    }
	    	return msg;
	    }
	    
	    public  void deleteProfessorSections(String[] sections){
	        Connection conn = null;
	         String sql = "";
	         PreparedStatement stmt = null;

	         try {
	             conn = ConnectionManager.getConnection();
	             sql = "delete from professor_section where group_id = ?";
	             for(String section:sections){
	              stmt = conn.prepareStatement(sql);;
	              stmt.setString(1, section);
	              stmt.executeUpdate();
	             }
	  
	         } catch (SQLException ex) {
	            ex.printStackTrace();
	         } finally {
	             ConnectionManager.close(conn, stmt);
	         }
	  
	     }
	    
	    public  ArrayList<String> retrieveProfessorSections(int avatar_id){
	    	  	Connection conn = null;
		        PreparedStatement stmt = null;
		        String sql = "";
		        ArrayList<String> sections = new ArrayList<>();
		        ResultSet rs = null;

		        try {
		            conn = ConnectionManager.getConnection();

		            sql = "select group_id from professor_section where avatar_id = ? order by CONVERT(MID(group_id,2),unsigned)";
		            stmt = conn.prepareStatement(sql);
		            stmt.setInt(1, avatar_id);

		            rs = stmt.executeQuery();

		            while (rs.next()) {
		                String group_id = rs.getString(1);
		                sections.add(group_id);
		            }

		        } catch (SQLException ex) {
		            handleSQLException(ex, sql, "sections={" + sections + "}");
		        } finally {
		            ConnectionManager.close(conn, stmt, rs);
		        }
		        return sections;
	    }

}
