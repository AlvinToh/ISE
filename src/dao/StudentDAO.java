package dao;

import entity.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.logging.*;

import com.opencsv.CSVReader;



public class StudentDAO {
	//for debugging purpose
	
//   public static void main(String[] args){
//		System.out.println("ssss");
//		StudentDAO sd = new StudentDAO();
//		HashMap<String,String> list = sd.readStudentListCSV("C:/Users/xiaoyu/Documents/GitHub/ISE/WebContent/studentList/student_blank lines.csv");
//		for (String section:list.keySet()){
//			System.out.println(section+"--"+list.get(section));
//		}
//	}
//	
	private static final String TBLNAME = "student";
	
    private void handleSQLException(SQLException ex, String sql, String... parameters) {
        String msg = "Unable to access data; SQL=" + sql + "\n";
        for (String parameter : parameters) {
            msg += "," + parameter;
        }
        Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, msg, ex);
        throw new RuntimeException(msg, ex);
    }
    
    public Student retrieveStudent(String emailID, String password) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "";
        Student returnStudent = null;
        ResultSet rs = null;

        try {
            conn = ConnectionManager.getConnection();

            sql = "select smu_email_id, smu_email, tele_username, group_id, password, chat_id, veri_code, temp_smu_email_address,avatar_id from " + TBLNAME + " where smu_email_id = binary? and password = SHA1(?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, emailID);
            stmt.setString(2, password);

            rs = stmt.executeQuery();

            while (rs.next()) {
                String smu_email_id = rs.getString(1);
                String smu_email= rs.getString(2); 
                String tele_username= rs.getString(3); 
                String group_id = rs.getString(4); 
                String correctPassword = rs.getString(5);
    			int chat_id = rs.getInt(6); 
    			String veri_code = rs.getString(7); 
    			String temp_smu_email_address = rs.getString(8);
    			int avatar_id = rs.getInt(9);

              //  returnStudent = new Student(smu_email_id, tele_id, group_id,correctPassword);
                returnStudent = new Student(smu_email_id, smu_email, tele_username, group_id, correctPassword,	chat_id, veri_code, temp_smu_email_address,avatar_id);
            }
            //return resultUser;

        } catch (SQLException ex) {
            handleSQLException(ex, sql, "User={" + returnStudent + "}");
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return returnStudent;
    }
    
    public ArrayList<Student> retrieveStudentBySection(String group_id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "";
        ArrayList<Student> students = new ArrayList<>();
        ResultSet rs = null;

        try {
            conn = ConnectionManager.getConnection();

            sql = "select smu_email_id, smu_email, tele_username, group_id, password, chat_id, veri_code, temp_smu_email_address,avatar_id from " + TBLNAME + " where group_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, group_id);

            rs = stmt.executeQuery();

            while (rs.next()) {
                String smu_email_id = rs.getString(1);
                String smu_email= rs.getString(2); 
                String tele_username= rs.getString(3); 
                String correctPassword = rs.getString(5);
    			int chat_id = rs.getInt(6); 
    			String veri_code = rs.getString(7); 
    			String temp_smu_email_address = rs.getString(8);
    			int avatar_id = rs.getInt(9);

              //  returnStudent = new Student(smu_email_id, tele_id, group_id,correctPassword);
                students.add(new Student(smu_email_id, smu_email, tele_username, group_id, correctPassword,	chat_id, veri_code, temp_smu_email_address,avatar_id));
            }
            //return resultUser;

        } catch (SQLException ex) {
            handleSQLException(ex, sql, "students={" + students + "}");
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return students;
    }
    
    public HashMap<String,String> retrieveStudentNotRegistered(String group_id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "";
        LinkedHashMap<String,String> studentsNotRegistered = new LinkedHashMap<String,String>();
        ResultSet rs = null;

        try {
            conn = ConnectionManager.getConnection();
            
            sql = "select student_list.smu_email_id,student_list.group_id from student_list left join student on student_list.smu_email_id = student.smu_email_id where student.smu_email is null and student.group_id is null ";
            
            if(group_id !=null && group_id.length()!=0 && !group_id.equals("0")){
            	sql= sql+"and student_list.group_id = '"+group_id+"' order by CONVERT(MID(student_list.group_id,2),unsigned) ";
            }else{
            	sql=sql+"order by CONVERT(MID(student_list.group_id,2),unsigned)";
            }
            //System.out.println(sql);
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String smu_email_id = rs.getString(1);
                String groupid = rs.getString(2);
                studentsNotRegistered.put(smu_email_id,groupid);
            }
            //return resultUser;

        } catch (SQLException ex) {
            handleSQLException(ex, sql, "students={" + studentsNotRegistered + "}");
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return studentsNotRegistered;
    }
    
    public ArrayList<String> retrieveUniqueGroupID() {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "";
        ArrayList<String> groups = new  ArrayList<String>();
        ResultSet rs = null;

        try {
            conn = ConnectionManager.getConnection();
            sql = "select distinct group_id from student_list order by CONVERT(MID(group_id,2),unsigned) ";
            stmt = conn.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String groupid = rs.getString(1);
                groups.add(groupid);
            }
            //return resultUser;

        } catch (SQLException ex) {
            handleSQLException(ex, sql, "groups={" + groups + "}");
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return groups;
    }
    
    public HashMap<String,String> readStudentListCSV(String filename){
    	HashMap<String,String> studentList = new HashMap<>();
    	String[] record = null;
    	int userCol = 0;
    	int sectionCol = 0;
    	boolean headersReaded = false;
    	ArrayList<String> sections = new ArrayList<>();
    		
    	try{
    		CSVReader reader = new CSVReader(
                    new InputStreamReader(new FileInputStream(filename), "UTF-8"), ',', '"');
	    	while((record = reader.readNext())!=null){
	    		if(record.length!=1){
	    			if(userCol==0&&sectionCol==0){
		    			int col=0;
		    			for(String header:record){
		    				if(header.toLowerCase().equals("user id")){
		    					userCol=col;
		    				}
		    				if(header.toLowerCase().equals("section")){
		    					sectionCol=col;
		    				}
		    				
		    				col++;
		    			}
		    			
		    			if(userCol!=0 && sectionCol!=0){
		    				headersReaded=true;
		    			}
		    			
	    			}else if(headersReaded){
	    				String section = record[sectionCol];
	    				if(!sections.contains(section)){
	    					sections.add(section);
	    				}
	    				studentList.put(record[userCol],record[sectionCol]);
	    			}
	    		}
	    	}
	    	reader.close();
	    	
    	}catch(IOException e){
    		e.printStackTrace();
    	}
        deleteAllInSections(sections);
    	return studentList;
    }
    
    public void deleteAllInSections (ArrayList<String> sections){
    	Connection conn = null;
        PreparedStatement preStmt = null;
        String sql = "";
        
        try{
        	conn = ConnectionManager.getConnection();
        	sql = "delete from student_list where group_id = ?";
        	preStmt = conn.prepareStatement(sql);
        	for(int i=0;i<sections.size();i++){
        		String section = sections.get(i);
        		preStmt.setString(1, section);
        		preStmt.executeUpdate();
        	}
        }catch(SQLException e){
        	e.printStackTrace();
        }finally{
        	ConnectionManager.close(conn, preStmt);
        }
    }
    
    public String upload(HashMap<String, String> studentList) {
    	int totalUploaded = 0;
    	String msg ="";
    	Connection conn = null;
        PreparedStatement preStmt = null;
        try {
        	conn = ConnectionManager.getConnection();
            conn.setAutoCommit(false);
            String sql = "insert into student_list (smu_email_id,group_id) values (?,?)";
            preStmt = conn.prepareStatement(sql);
                //preStmt.executeUpdate();
            
            final int batchSize = 1000;
            int count = 0;
            
                for (String smu_email_id:studentList.keySet()) {
                    String group_id = studentList.get(smu_email_id);
                    preStmt.setString(1, smu_email_id);
                    preStmt.setString(2, group_id);
                    preStmt.addBatch();
                    
                    if (++count % batchSize == 0) {
                        totalUploaded += preStmt.executeBatch().length;
                    }
                }
                
                totalUploaded += preStmt.executeBatch().length; // insert remaining records
                conn.commit();
                preStmt.close();

            } catch (SQLException e) {
                e.printStackTrace();
                return msg;
            } finally {
                ConnectionManager.close(conn);
            }
        
        return msg;

    }
    
}
