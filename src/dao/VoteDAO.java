package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import entity.Post;
import entity.Tag;
import entity.Vote;

public class VoteDAO {
	private static final String TBLNAME = "vote";

	public static void main(String[] args) {
		VoteDAO voteDAO = new VoteDAO();
		//voteDAO.cancelVotes(5,1);
	Map<String, Vote> map = voteDAO.retrieveVotes(1);
		for(Vote v: map.values()){
			// need to post_id upvote and downvote
			// if upvote = true, display downvote
			// if downvote = true, display upvote
			System.out.println(v.getPost_id() +" "+v.isUpvote()+" "+v.isDownvote());
		}
		
		for(String k: map.keySet()){
			System.out.println(k);
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

	// retrieve all votes voted by avatar_id
	public Map<String, Vote> retrieveVotes(int avatar_id) {
		Map<String, Vote> resultMap = new HashMap();
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement preStmt = null;
		
		try {
			conn = ConnectionManager.getConnection();
			String sql = "select post_id, upvote, downvote, timestamp from " + TBLNAME + " where avatar_id =?";
			preStmt = conn.prepareStatement(sql);
			preStmt.setInt(1, avatar_id);
			rs = preStmt.executeQuery();

			while (rs.next()) {
				int post_id = rs.getInt(1);
				boolean upvote = rs.getBoolean("upvote");
				boolean downvote = rs.getBoolean("downvote");
				String timestamp = rs.getString("timestamp");
				String key = post_id+"-"+avatar_id;
				
				resultMap.put(key, new Vote(post_id,avatar_id, upvote, downvote, timestamp));
                
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(conn, preStmt, rs);

		}
		return resultMap;
	}

	// give a upvote
	public void addUpvote(int post_id, int avatar_id, int vote) {

		Connection conn = null;
		PreparedStatement stmt = null;
		String sql = "";

		try {
			conn = ConnectionManager.getConnection();
			
				sql = "INSERT INTO " + TBLNAME + " values (?,?,?,?,?)";
				stmt = conn.prepareStatement(sql);

				stmt.setInt(1, post_id);
				stmt.setInt(2, avatar_id);
				stmt.setInt(3,vote);
				stmt.setInt(4, 0); // downvote
				stmt.setTimestamp(5,getCurrentTimeStamp()); 

				stmt.executeUpdate();

			

		} catch (SQLException ex) {
			String msg = "An exception occurs when adding new upvote";
			handleSQLException(ex, sql, "msg={" + msg + "}");
		} finally {
			ConnectionManager.close(conn, stmt);
		}
	}
	
	// cancel upvote
		public void cancelVotes(int post_id, int avatar_id) {

			Connection conn = null;
			PreparedStatement stmt = null;
			String sql = "";

			try {
				conn = ConnectionManager.getConnection();
				
					sql = "Delete from " + TBLNAME + " WHERE post_id =? and avatar_id=?";
			
					stmt = conn.prepareStatement(sql);
					stmt.setInt(1, post_id);
					stmt.setInt(2, avatar_id);
 

					stmt.executeUpdate();

				

			} catch (SQLException ex) {
				String msg = "An exception occurs when removing the vote (up & down)";
				handleSQLException(ex, sql, "msg={" + msg + "}");
			} finally {
				ConnectionManager.close(conn, stmt);
			}
		}
	
	// give a downvote
	public void addDownvote(int post_id, int avatar_id, int vote) {

			Connection conn = null;
			PreparedStatement stmt = null;
			String sql = "";

			try {
				conn = ConnectionManager.getConnection();
				
					sql = "INSERT INTO " + TBLNAME + " values (?,?,?,?,?)";
					stmt = conn.prepareStatement(sql);

					stmt.setInt(1, post_id);
					stmt.setInt(2, avatar_id);
					stmt.setInt(3,0);
					stmt.setInt(4, vote); // downvote
					stmt.setTimestamp(5,getCurrentTimeStamp()); 

					stmt.executeUpdate();

				

			} catch (SQLException ex) {
				String msg = "An exception occurs when adding new downvote";
				handleSQLException(ex, sql, "msg={" + msg + "}");
			} finally {
				ConnectionManager.close(conn, stmt);
			}
		}
	
	private static java.sql.Timestamp getCurrentTimeStamp() {

		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());
	  }	

}

