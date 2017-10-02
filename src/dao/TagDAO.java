package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import entity.Tag;

public class TagDAO {
	private static final String POST_TAG_TBL = "post_tag";
	private static final String TAG_TBL = "tag";

	public static void main(String[] args) {
		TagDAO td = new TagDAO();
		System.out.println(td.getTagID("match"));

		List<Tag> tgList = td.retrieveAllTags();
		for(int i=0; i<tgList.size(); i++){
			System.out.println(tgList.get(i).getTag());
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

	// table post_tag, retrieve all tags id for selected post
	public List<Integer> retrieveTags(int post_id) {
		List<Integer> tagList = new ArrayList<Integer>();

		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement preStmt = null;
		int tempTagID = 0;
		try {
			conn = ConnectionManager.getConnection();
			String sql = "select tag_id from " + POST_TAG_TBL + " where post_id =?";
			preStmt = conn.prepareStatement(sql);
			preStmt.setInt(1, post_id);
			rs = preStmt.executeQuery();

			while (rs.next()) {
				tempTagID = rs.getInt(1);
				tagList.add(tempTagID);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(conn, preStmt, rs);

		}
		return tagList;
	}

	// table post_tag, add post tags into database
	public void addTag(int post_id, String[] tagList) {

		Connection conn = null;
		PreparedStatement stmt = null;
		String sql = "";

		try {
			conn = ConnectionManager.getConnection();
			for (int i = 0; i < tagList.length; i++) {
				sql = "INSERT INTO " + POST_TAG_TBL + " values (?,?,?)";
				stmt = conn.prepareStatement(sql);

				stmt.setInt(1, post_id);
				stmt.setInt(2, getTagID(tagList[i]));
				stmt.setFloat(3, 0); // association

				stmt.executeUpdate();

			}

		} catch (SQLException ex) {
			String msg = "An exception occurs when adding new tag";
			handleSQLException(ex, sql, "msg={" + msg + "}");
		} finally {
			ConnectionManager.close(conn, stmt);
		}
	}

	// table tag, retrieve tag id for selected tag
	public int getTagID(String tag) {

		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement preStmt = null;
		int returnTagID = 0;
		try {
			conn = ConnectionManager.getConnection();
			String sql = "select tag_id from " + TAG_TBL + " where tag =?";
			preStmt = conn.prepareStatement(sql);
			preStmt.setString(1, tag);
			rs = preStmt.executeQuery();

			while (rs.next()) {
				returnTagID = rs.getInt(1);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(conn, preStmt, rs);

		}
		return returnTagID;
	}

	// table tag, retrieve all tags
	public List<Tag> retrieveAllTags() {

		List<Tag> tagList = new ArrayList<>();
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement preStmt = null;

		try {
			conn = ConnectionManager.getConnection();
			String sql = "select * from " + TAG_TBL;
			preStmt = conn.prepareStatement(sql);
			rs = preStmt.executeQuery();

			while (rs.next()) {
				int tag_id = rs.getInt(1);
				String tag = rs.getString(2); 
				tagList.add(new Tag(tag_id, tag));

			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.close(conn, preStmt, rs);

		}
		return tagList;
	}

}
