package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClassPartDAO {
	private static final String TBLNAME = "weekly_class_participation";
	
    private void handleSQLException(SQLException ex, String sql, String... parameters) {
        String msg = "Unable to access data; SQL=" + sql + "\n";
        for (String parameter : parameters) {
            msg += "," + parameter;
        }
        Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, msg, ex);
        throw new RuntimeException(msg, ex);
    }
    
    public HashMap<String,String> retrieveWeeklyClassPart(String group_id,int week) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "";
        HashMap<String,String> weeklyRecord = new  HashMap<String,String>();
        ResultSet rs = null;
        
        if(group_id.isEmpty()||week==0){
        	return weeklyRecord;
        }
        
        try {
            conn = ConnectionManager.getConnection();

			sql = "select smu_email_id,IF(temp.student_id IS NULL,'absent','present') as status from student_list left join ( select * from "
					+ TBLNAME + " where week = ? ) as temp " + "on smu_email_id = temp.student_id where group_id = ? ";

			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, week);
			stmt.setString(2, group_id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                String smu_email_id = rs.getString(1);
                String status = rs.getString(2);
                weeklyRecord.put(smu_email_id,status);
            }
            //return resultUser;

        } catch (SQLException ex) {
            handleSQLException(ex, sql, "weeklyRecord={" + weeklyRecord + "}");
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return weeklyRecord;
    }
    
    public ArrayList<Integer> retrieveIndiClassPart(String smu_email_id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "";
        ArrayList<Integer> indiRecord = new  ArrayList<>();
        ResultSet rs = null;
                
        try {
            conn = ConnectionManager.getConnection();
            
            sql = "select week from "+TBLNAME+" where student_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, smu_email_id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                int week = Integer.parseInt(rs.getString(1));
                indiRecord.add(week);
            }
            //return resultUser;

        } catch (SQLException ex) {
            handleSQLException(ex, sql, "individual Record={" + indiRecord + "}");
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return indiRecord;
    }
    
}
