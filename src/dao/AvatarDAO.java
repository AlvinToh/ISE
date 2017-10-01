package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.opencsv.CSVReader;

public class AvatarDAO {
	 private static final String TBLNAME = "avatar";
	 private static final String TBLNAME2 = "avatar_name_list";
	 
/*	 public static void main(String[] args){
		 AvatarDAO ad= new AvatarDAO();
		 System.out.println(ad.getAvatarName(2));
	 }*/
	 
	 private void handleSQLException(SQLException ex, String sql, String... parameters) {
	        String msg = "Unable to access data; SQL=" + sql + "\n";
	        for (String parameter : parameters) {
	            msg += "," + parameter;
	        }
	        Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, msg, ex);
	        throw new RuntimeException(msg, ex);
	 }
	 
	 public void registerAvatar(String avatarName){
		    Connection conn = null;
		    PreparedStatement stmt = null;
		    String sql = "";
		    
		    try {
		    	conn = ConnectionManager.getConnection();
		    	sql = "insert into avatar (avatar_name, icon, avatar_qa_coin, avatar_thoughtfulness_score, section_id, is_bot) values (?,?,?,?,?,?)";
		        stmt = conn.prepareStatement(sql);
		        stmt.setString(1, avatarName);
		        stmt.setString(2,"");//icon
		        stmt.setInt(3,0);//avatar_qa_coin
		        stmt.setInt(4, 0); //avatar_thoughtfulness_score
		        stmt.setString(5, ""); //section_id
		        stmt.setInt(6, 0); //is_bot
		        stmt.executeUpdate();

	        } catch (SQLException ex) {
	        	String msg = "An exception occurs during registration";
		        handleSQLException(ex, sql, "msg={" + msg + "}");
		    } finally {
		            ConnectionManager.close(conn, stmt);
		    }
	
	    }
	 
	 public int lastAvatarID(){
   	  Connection conn = null;
         PreparedStatement stmt = null;
         String sql = "";
         int returnAvatarID = 0;
         ResultSet rs = null;

         try {
             conn = ConnectionManager.getConnection();

             sql = "select MAX(avatar_id) FROM " + TBLNAME;
             stmt = conn.prepareStatement(sql);          
             rs = stmt.executeQuery();

             while (rs.next()) {              
           	  returnAvatarID = rs.getInt(1); 
             }

         } catch (SQLException ex) {
             handleSQLException(ex, sql, "avatar_id={" + returnAvatarID + "}");
         } finally {
             ConnectionManager.close(conn, stmt, rs);
         }
         return returnAvatarID;
   }
	 
	 
	 public String getAvatarName(int avatar_id){
		 
		 Connection conn = null;
         PreparedStatement stmt = null;
         String sql = "";
         String returnAvatarName = "";
         ResultSet rs = null;

         try {
             conn = ConnectionManager.getConnection();

             sql = "select avatar_name FROM " + TBLNAME +" WHERE avatar_id = ?";
             stmt = conn.prepareStatement(sql);    
             stmt.setInt(1, avatar_id);
             rs = stmt.executeQuery();

             while (rs.next()) {              
            	 returnAvatarName = rs.getString(1); 
             }

         } catch (SQLException ex) {
             handleSQLException(ex, sql, "avatar_name={" + returnAvatarName + "}");
         } finally {
             ConnectionManager.close(conn, stmt, rs);
         }
         return returnAvatarName;
		 
	 }
	 
	 public void changeAvatarName(int avatar_id, String old_avatar_name,String new_avatar_name){
		   Connection conn = null;
		      PreparedStatement stmt = null;
		      String sql = "";    
		      
		      try {
		       conn = ConnectionManager.getConnection();
		       sql = "update "+ TBLNAME +" set avatar_name = ? where avatar_id = ?";
		          stmt = conn.prepareStatement(sql);
		          stmt.setString(1,new_avatar_name); 
		          stmt.setInt(2,avatar_id); 
		          stmt.executeUpdate();

		         } catch (SQLException ex) {
		           String msg = "An exception occurs when update question in the post class summary questions";
		           handleSQLException(ex, sql, "msg={" + msg + "}");
		      } finally {
		              ConnectionManager.close(conn, stmt);
		      }
		      tookAvatarName(new_avatar_name);
		      notUseAvatarName(old_avatar_name);
		     }
		  
		 public void tookAvatarName(String avatar_name){
		   Connection conn = null;
		      PreparedStatement stmt = null;
		      String sql = "";    
		      
		      try {
		       conn = ConnectionManager.getConnection();
		       sql = "update "+ TBLNAME2 +" set status = 1 where avatar_name = ?";
		          stmt = conn.prepareStatement(sql);
		          stmt.setString(1,avatar_name); 
		          stmt.executeUpdate();

		         } catch (SQLException ex) {
		           String msg = "An exception occurs when update question in the post class summary questions";
		           handleSQLException(ex, sql, "msg={" + msg + "}");
		      } finally {
		              ConnectionManager.close(conn, stmt);
		      }
		  }
		 
		 public void notUseAvatarName(String avatar_name){
		   Connection conn = null;
		      PreparedStatement stmt = null;
		      String sql = "";    
		      
		      try {
		       conn = ConnectionManager.getConnection();
		       sql = "update "+ TBLNAME2 +" set status = 0 where avatar_name = ?";
		          stmt = conn.prepareStatement(sql);
		          stmt.setString(1,avatar_name); 
		          stmt.executeUpdate();

		         } catch (SQLException ex) {
		           String msg = "An exception occurs when update question in the post class summary questions";
		           handleSQLException(ex, sql, "msg={" + msg + "}");
		      } finally {
		              ConnectionManager.close(conn, stmt);
		      }
		  }
		  
		 public ArrayList<String> getUnusedAvatarName(){
		   
		   Connection conn = null;
		         PreparedStatement stmt = null;
		         String sql = "";
		         ArrayList<String> avatarNames = new ArrayList<>();
		         ResultSet rs = null;

		         try {
		             conn = ConnectionManager.getConnection();

		             sql = "select avatar_name FROM " + TBLNAME2 +" WHERE status = 0";
		             stmt = conn.prepareStatement(sql);    
		             rs = stmt.executeQuery();

		             while (rs.next()) {              
		              avatarNames.add(rs.getString(1)); 
		             }

		         } catch (SQLException ex) {
		             handleSQLException(ex, sql, "avatarNames={" + avatarNames + "}");
		         } finally {
		             ConnectionManager.close(conn, stmt, rs);
		         }
		         return avatarNames; 
		  }
		 public int retrieveAvatarID(String username){
		      Connection conn = null;
		         PreparedStatement stmt = null;
		         String sql = "";
		         int returnAvatarID = 0;
		         ResultSet rs = null;

		         try {
		             conn = ConnectionManager.getConnection();

		             sql = "select avatar_id FROM " + TBLNAME+" where avatar_name=?";
		             stmt = conn.prepareStatement(sql); 
		             stmt.setString(1,username);
		             rs = stmt.executeQuery();

		             while (rs.next()) {              
		              returnAvatarID = rs.getInt(1); 
		             }

		         } catch (SQLException ex) {
		             handleSQLException(ex, sql, "avatar_id={" + returnAvatarID + "}");
		         } finally {
		             ConnectionManager.close(conn, stmt, rs);
		         }
		         return returnAvatarID;
		   }
		 
		 public ArrayList<String> readNameListCSV(String filename){
			 ArrayList<String> nameList = new ArrayList<String>();
			  String[] record = null;
			   
			  try{
			   CSVReader reader = new CSVReader(
			                 new InputStreamReader(new FileInputStream(filename), "UTF-8"), ',', '"');
			      while((record = reader.readNext())!=null){
			       if(record.length!=1){       
			          nameList.add(record[0].trim());
			       }
			      }
			    }catch(IOException e){
			   e.printStackTrace();
			  }
			  return nameList;
			  }
			 
			 public int upload(String[] nameList) {
			  int totalUploaded = 0;
			  Connection conn = null;
			     PreparedStatement preStmt = null;
			     try {
			      conn = ConnectionManager.getConnection();
			         conn.setAutoCommit(false);
			         String sql = "insert into "+TBLNAME2+" (avatar_name,status) values (?,?)";
			         preStmt = conn.prepareStatement(sql);
			             //preStmt.executeUpdate();
			         
			         final int batchSize = 1000;
			         int count = 0;
			         
			             for ( String avatar_name:nameList) {
			                 preStmt.setString(1,avatar_name);
			                 preStmt.setInt(2, 0);
			                 preStmt.addBatch();
			                 
			                 if (++count % batchSize == 0) {
			                     totalUploaded += preStmt.executeBatch().length;
			                 }
			             }
			             
			             totalUploaded += preStmt.executeBatch().length; // insert remaining records
			             conn.commit();
			             preStmt.close();

			         } catch (SQLException e) {
			            //do nothing;
			         } finally {
			             ConnectionManager.close(conn);
			         }
			     return totalUploaded;
			    
			  }
		 
		 
}
