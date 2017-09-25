package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import entity.AuthenticationCode;

public class AuthenticationCodeDAO {
	// for debugging purpose

/*	public static void main(String[] args) {
		System.out.println("ssss");
		AuthenticationCodeDAO sd = new AuthenticationCodeDAO();
		AuthenticationCode s = sd.retrieveaCode("1", "1");
		System.out.println();
		System.out.println(s.getQRCode());
	    AuthenticationCode code = new AuthenticationCode("1", "2","2017-08-09 12:49:37","mmp6n7J");
		sd.addNew(code);
		
	}*/

	private static final String TBLNAME = "qrauthentication";

	private void handleSQLException(SQLException ex, String sql, String... parameters) {
		String msg = "Unable to access data; SQL=" + sql + "\n";
		for (String parameter : parameters) {
			msg += "," + parameter;
		}
		Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, msg, ex);
		throw new RuntimeException(msg, ex);
	}

	public void addNew(AuthenticationCode newCode) {
		Connection conn = null;
		PreparedStatement stmt = null;
		String sql = "";
		try {
			conn = ConnectionManager.getConnection();

			sql = "INSERT INTO " + TBLNAME + " (week, teaching_group, time, qrcode) VALUES (?,?,?,?)";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, newCode.getWeek());
			stmt.setString(2, newCode.getTeachingGrp());
			stmt.setString(3, newCode.getTimestamp());
			stmt.setString(4, newCode.getQRCode());

			stmt.executeUpdate();

		} catch (SQLException ex) {
			handleSQLException(ex, sql, "AuthenticationCode={" + newCode + "}");
		} finally {
			ConnectionManager.close(conn, stmt);
		}
	}

	public AuthenticationCode retrieveaCode(String week, String group) {
		Connection conn = null;
		PreparedStatement stmt = null;
		String sql = "";
		AuthenticationCode returnAuthenticationCode = null;
		ResultSet rs = null;

		try {
			conn = ConnectionManager.getConnection();

			sql = "select week, teaching_group, time, qrcode from " + TBLNAME + " where week = ? and teaching_group = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, week);
			stmt.setString(2, group);

			rs = stmt.executeQuery();

			while (rs.next()) {
				String weekNo = rs.getString(1);
				String teaching_group = rs.getString(2);
				String time = rs.getString(3);
				String qrcode = rs.getString(4);

				returnAuthenticationCode = new AuthenticationCode(weekNo, teaching_group, time, qrcode);
			}
			// return resultUser;

		} catch (SQLException ex) {
			handleSQLException(ex, sql, "User={" + returnAuthenticationCode + "}");
		} finally {
			ConnectionManager.close(conn, stmt, rs);
		}
		return returnAuthenticationCode;
	}

}
