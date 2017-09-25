package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import entity.*;


public class PostDAO {
	//for debugging purpose
	
		public static void main(String[] args){
			System.out.println("ssss");
			PostDAO pd = new PostDAO();
			HashMap<Integer, Post> map = pd.searchByKeyword("s") ;
			//Recursive
			List<Integer> t = pd.getSubPostIDRecursion(1);
			List<List<Integer>> postList = new ArrayList<>();
			for(Integer i: t){
				System.out.println(i);
			}
			
			
			//String string="SMU adopts a Code of Academic Integrity.  There are serious penalties, ddincluding expulsion, for cheating, plagiarism, fabrication, multiple";
			//System.out.println("substring   "+string.length());
			//System.out.println("Post Level:    " + pd.getPostLevel(1));
			
		  //  System.out.println(map.size());
	/*		for (Integer key : map.keySet()) {
			    System.out.println(key + " " + map.get(key) + " " +map.get(key).getAvatar_id() +"   "+ map.get(key).getPost_id()+ " " +map.get(key).getPost_content());
			}
			
			System.out.println(pd.lastPostID(1));
			
			
			System.out.println(pd.lastPostIDofAvatar(2));
			System.out.println(pd.lastPostIDofAvatar(1));
	*/		
/*			List<Integer> tempList = pd.retrieveAllPosts(2);
			for(Integer i: tempList){
				
			
			System.out.println(pd.retrievePostbyID(i).getPost_title());
			}*/

		}
		private List<Integer> temp = new ArrayList<>();
		public List<Integer> getSubPostIDRecursion (int postid){
			// if this postid doesn't have sub post
			if(retrieveAPost(postid)==null){
				temp.add(postid);
			}else{	
				// postid has sub post
				// using treemap to sort returned map - retrieveAPost
				// then loop through each sub post				
				for(Integer i:new TreeMap<Integer, Post>(retrieveAPost(postid)).keySet()){
					temp.add(i);
					getSubPostIDRecursion(i);
				}
				
			}
			return temp;

		}
		
		// get level by postid - to convert recursive postid list to level list
		  public int getLevel(int postID){
			    Connection conn = null;
		        PreparedStatement stmt = null;
		        String sql = "";
		        Integer returnLevel = null;
		        ResultSet rs = null;

		        try {
		            conn = ConnectionManager.getConnection();

		            sql = "select level FROM " + TBLNAME + " where post_id = ?";
		            stmt = conn.prepareStatement(sql);	         
		            stmt.setInt(1, postID);
		            
		            rs = stmt.executeQuery();

		            while (rs.next()) {            	
		            	returnLevel = rs.getInt(1);
		             
		            	
		            }
		            //return resultUser;

		        } catch (SQLException ex) {
		            handleSQLException(ex, sql, "level for post id" + postID + "={ "+returnLevel+"}");
		        } finally {
		            ConnectionManager.close(conn, stmt, rs);
		        }
		        return returnLevel;
		    }
		
	
		private static final String TBLNAME = "post";
		    
		
		
	    private void handleSQLException(SQLException ex, String sql, String... parameters) {
	        String msg = "Unable to access data; SQL=" + sql + "\n";
	        for (String parameter : parameters) {
	            msg += "," + parameter;
	        }
	        Logger.getLogger(StudentDAO.class.getName()).log(Level.SEVERE, msg, ex);
	        throw new RuntimeException(msg, ex);
	    }
	    
	    
	    // retrieve all parent post  -> display in forumHome.jsp
	    public HashMap<Integer, Post> retrieveAll() {
	        HashMap<Integer, Post> postMap = new HashMap<>();

	        Connection conn = null;
	        ResultSet rs = null;
	        PreparedStatement preStmt = null;
	        Post tempPost = null;
	        try {
	            conn = ConnectionManager.getConnection();
	            String sql = "select * from " + TBLNAME + " where is_question=1";
	            preStmt = conn.prepareStatement(sql);
	            rs = preStmt.executeQuery();

	            while (rs.next()) {
	            	int avatar_id = rs.getInt(1);
	            	int parent_id = rs.getInt(2);
	            	int level = rs.getInt(3);
	            	int post_id = rs.getInt(4);
	            	String post_title = rs.getString(5);
	            	String post_content = rs.getString(6);
	            	boolean is_question = rs.getBoolean(7);
	            	boolean is_bot = rs.getBoolean(8);
	            	boolean is_qa_bountiful = rs.getBoolean(9);
	            	String timestamp = rs.getString(10);
	            	int time_limit_qa = rs.getInt(11);
	            	int time_limit_bot = rs.getInt(12);
	            	float qa_coin_basic = rs.getFloat(13);
	            	float qa_coin_bounty = rs.getFloat(14);
	            	float thoughfulness_score = rs.getFloat(15);
	            	boolean no_show = rs.getBoolean(16);
	            	int previous_version = rs.getInt(17);
	            	int number_of_upvotes = rs.getInt(18);
	            	int number_of_downvotes = rs.getInt(19);

	             
	            	tempPost = new Post(avatar_id, parent_id, level, post_id, post_title, post_content, is_question, is_bot, is_qa_bountiful, timestamp, time_limit_qa, time_limit_bot, qa_coin_basic, qa_coin_bounty, thoughfulness_score,
	            		 no_show, previous_version, number_of_upvotes, number_of_downvotes);
	            	
	                
	                postMap.put(post_id, tempPost);
	            
	                
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            ConnectionManager.close(conn, preStmt, rs);
	            
	        }
	        return postMap;
	    }

	    
	    // retrieve parent post and its subpost (if any) -> for viewPost.jsp
	    public HashMap<Integer, Post> retrieveAPost(int postID) {
	        HashMap<Integer, Post> postMap = new HashMap<>();

	        Connection conn = null;
	        ResultSet rs = null;
	        PreparedStatement preStmt = null;
	        Post tempPost = null;
	        try {
	            conn = ConnectionManager.getConnection();
	            String sql = "select * from " + TBLNAME + " where parent_id = ?";
	            preStmt = conn.prepareStatement(sql);
	            preStmt.setInt(1, postID);
	            rs = preStmt.executeQuery();

	            while (rs.next()) {
	            	int avatar_id = rs.getInt(1);
	            	int parent_id = rs.getInt(2);
	            	int level = rs.getInt(3);
	            	int post_id = rs.getInt(4);
	            	String post_title = rs.getString(5);
	            	String post_content = rs.getString(6);
	            	boolean is_question = rs.getBoolean(7);
	            	boolean is_bot = rs.getBoolean(8);
	            	boolean is_qa_bountiful = rs.getBoolean(9);
	            	String timestamp = rs.getString(10);
	            	int time_limit_qa = rs.getInt(11);
	            	int time_limit_bot = rs.getInt(12);
	            	float qa_coin_basic = rs.getFloat(13);
	            	float qa_coin_bounty = rs.getFloat(14);
	            	float thoughfulness_score = rs.getFloat(15);
	            	boolean no_show = rs.getBoolean(16);
	            	int previous_version = rs.getInt(17);
	            	int number_of_upvotes = rs.getInt(18);
	            	int number_of_downvotes = rs.getInt(19);

	             
	            	tempPost = new Post(avatar_id, parent_id, level, post_id, post_title, post_content, is_question, is_bot, is_qa_bountiful, timestamp, time_limit_qa, time_limit_bot, qa_coin_basic, qa_coin_bounty, thoughfulness_score,
	            		 no_show, previous_version, number_of_upvotes, number_of_downvotes);
	            	
	            	       
	                postMap.put(post_id, tempPost);
	           
	                
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            ConnectionManager.close(conn, preStmt, rs);
	            
	        }
	        return postMap;
	    }
	    
	    // retrieve Parent post -> for viewPost.jsp	    
	    public Post retrieveParentPost(int postID) {
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        String sql = "";
	        Post returnPost = null;
	        ResultSet rs = null;

	        try {
	            conn = ConnectionManager.getConnection();

	            sql = "select * from " + TBLNAME + " where post_id = ? and is_question=1";
	            stmt = conn.prepareStatement(sql);	
	            stmt.setInt(1, postID);
	            
	            rs = stmt.executeQuery();

	            while (rs.next()) {
	            	int avatar_id = rs.getInt(1);
	            	int parent_id = rs.getInt(2);
	            	int level = rs.getInt(3);
	            	int post_id = rs.getInt(4);
	            	String post_title = rs.getString(5);
	            	String post_content = rs.getString(6);
	            	boolean is_question = rs.getBoolean(7);
	            	boolean is_bot = rs.getBoolean(8);
	            	boolean is_qa_bountiful = rs.getBoolean(9);
	            	String timestamp = rs.getString(10);
	            	int time_limit_qa = rs.getInt(11);
	            	int time_limit_bot = rs.getInt(12);
	            	float qa_coin_basic = rs.getFloat(13);
	            	float qa_coin_bounty = rs.getFloat(14);
	            	float thoughfulness_score = rs.getFloat(15);
	            	boolean no_show = rs.getBoolean(16);
	            	int previous_version = rs.getInt(17);
	            	int number_of_upvotes = rs.getInt(18);
	            	int number_of_downvotes = rs.getInt(19);

	             
	            	returnPost = new Post(avatar_id, parent_id, level, post_id, post_title, post_content, is_question, is_bot, is_qa_bountiful, timestamp, time_limit_qa, time_limit_bot, qa_coin_basic, qa_coin_bounty, thoughfulness_score,
	            		 no_show, previous_version, number_of_upvotes, number_of_downvotes);
	            }
	            //return resultUser;

	        } catch (SQLException ex) {
	            handleSQLException(ex, sql, "Post={" + returnPost + "}");
	        } finally {
	            ConnectionManager.close(conn, stmt, rs);
	        }
	        return returnPost;
	    }
	    
	    // reply to reply  --> replyToRepliedPost.jsp
	    public Post retrieveParentPostID(int postID) {
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        String sql = "";
	        Post returnPost = null;
	        ResultSet rs = null;

	        try {
	            conn = ConnectionManager.getConnection();

	            sql = "select * from " + TBLNAME + " where post_id = ?";
	            stmt = conn.prepareStatement(sql);	
	            stmt.setInt(1, postID);
	            
	            rs = stmt.executeQuery();

	            while (rs.next()) {
	            	int avatar_id = rs.getInt(1);
	            	int parent_id = rs.getInt(2);
	            	int level = rs.getInt(3);
	            	int post_id = rs.getInt(4);
	            	String post_title = rs.getString(5);
	            	String post_content = rs.getString(6);
	            	boolean is_question = rs.getBoolean(7);
	            	boolean is_bot = rs.getBoolean(8);
	            	boolean is_qa_bountiful = rs.getBoolean(9);
	            	String timestamp = rs.getString(10);
	            	int time_limit_qa = rs.getInt(11);
	            	int time_limit_bot = rs.getInt(12);
	            	float qa_coin_basic = rs.getFloat(13);
	            	float qa_coin_bounty = rs.getFloat(14);
	            	float thoughfulness_score = rs.getFloat(15);
	            	boolean no_show = rs.getBoolean(16);
	            	int previous_version = rs.getInt(17);
	            	int number_of_upvotes = rs.getInt(18);
	            	int number_of_downvotes = rs.getInt(19);

	             
	            	returnPost = new Post(avatar_id, parent_id, level, post_id, post_title, post_content, is_question, is_bot, is_qa_bountiful, timestamp, time_limit_qa, time_limit_bot, qa_coin_basic, qa_coin_bounty, thoughfulness_score,
	            		 no_show, previous_version, number_of_upvotes, number_of_downvotes);
	            }
	            //return resultUser;

	        } catch (SQLException ex) {
	            handleSQLException(ex, sql, "Post={" + returnPost + "}");
	        } finally {
	            ConnectionManager.close(conn, stmt, rs);
	        }
	        return returnPost;
	    }
	    
	 // retrieve all posts -> for viewYourPost.jsp	    
	    public Post retrievePostbyID(int postID) {
	        Connection conn = null;
	        PreparedStatement stmt = null;
	        String sql = "";
	        Post returnPost = null;
	        ResultSet rs = null;

	        try {
	            conn = ConnectionManager.getConnection();

	            sql = "select * from " + TBLNAME + " where post_id = ?";
	            stmt = conn.prepareStatement(sql);	
	            stmt.setInt(1, postID);
	            
	            rs = stmt.executeQuery();

	            while (rs.next()) {
	            	int avatar_id = rs.getInt(1);
	            	int parent_id = rs.getInt(2);
	            	int level = rs.getInt(3);
	            	int post_id = rs.getInt(4);
	            	String post_title = rs.getString(5);
	            	String post_content = rs.getString(6);
	            	boolean is_question = rs.getBoolean(7);
	            	boolean is_bot = rs.getBoolean(8);
	            	boolean is_qa_bountiful = rs.getBoolean(9);
	            	String timestamp = rs.getString(10);
	            	int time_limit_qa = rs.getInt(11);
	            	int time_limit_bot = rs.getInt(12);
	            	float qa_coin_basic = rs.getFloat(13);
	            	float qa_coin_bounty = rs.getFloat(14);
	            	float thoughfulness_score = rs.getFloat(15);
	            	boolean no_show = rs.getBoolean(16);
	            	int previous_version = rs.getInt(17);
	            	int number_of_upvotes = rs.getInt(18);
	            	int number_of_downvotes = rs.getInt(19);

	             
	            	returnPost = new Post(avatar_id, parent_id, level, post_id, post_title, post_content, is_question, is_bot, is_qa_bountiful, timestamp, time_limit_qa, time_limit_bot, qa_coin_basic, qa_coin_bounty, thoughfulness_score,
	            		 no_show, previous_version, number_of_upvotes, number_of_downvotes);
	            }
	            //return resultUser;

	        } catch (SQLException ex) {
	            handleSQLException(ex, sql, "Post={" + returnPost + "}");
	        } finally {
	            ConnectionManager.close(conn, stmt, rs);
	        }
	        return returnPost;
	    }
	    
	    public int lastPostID(int postID){
	    Connection conn = null;
        PreparedStatement stmt = null;
        String sql = "";
        Integer returnPostID = null;
        ResultSet rs = null;

        try {
            conn = ConnectionManager.getConnection();

            sql = "select MAX(post_id) FROM " + TBLNAME + " where parent_id = ?";
            stmt = conn.prepareStatement(sql);	         
            stmt.setInt(1, postID);
            
            rs = stmt.executeQuery();

            while (rs.next()) {            	
            	returnPostID = rs.getInt(1);
             
            	
            }
            //return resultUser;

        } catch (SQLException ex) {
            handleSQLException(ex, sql, "PostID={" + returnPostID + "}");
        } finally {
            ConnectionManager.close(conn, stmt, rs);
        }
        return returnPostID;
    }
	    
	    // get avatarID's last post
	    public int lastPostIDofAvatar(int avatarID){
		    Connection conn = null;
	        PreparedStatement stmt = null;
	        String sql = "";
	        Integer returnPostID = null;
	        ResultSet rs = null;

	        try {
	            conn = ConnectionManager.getConnection();

	            sql = "select MAX(post_id) FROM " + TBLNAME + " where avatar_id = ?";
	            stmt = conn.prepareStatement(sql);	         
	            stmt.setInt(1, avatarID);
	            
	            rs = stmt.executeQuery();

	            while (rs.next()) {            	
	            	returnPostID = rs.getInt(1);
	             
	            	
	            }
	            //return resultUser;

	        } catch (SQLException ex) {
	            handleSQLException(ex, sql, "PostID={" + returnPostID + "}");
	        } finally {
	            ConnectionManager.close(conn, stmt, rs);
	        }
	        return returnPostID;
	    }
	    
	   public void addNewPost(int avatar_id, String post_title, String post_content){
	   
	    	Connection conn = null;
		    PreparedStatement stmt = null;
		    String sql = "";	   
		
		    
		    try {
		    	conn = ConnectionManager.getConnection();
		    	sql = "INSERT INTO "+ TBLNAME +" (avatar_id, parent_id, level, post_title, post_content, is_question, is_bot, is_qa_bountiful, timestamp, time_limit_qa, time_limit_bot, qa_coin_basic, qa_coin_bounty, thoughfulness_score, no_show, previous_version, number_of_upvotes, number_of_downvotes) values "+
		    	"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		        stmt = conn.prepareStatement(sql);
		       
		        stmt.setInt(1,avatar_id);
		        stmt.setInt(2,0); // parent_id
		        stmt.setInt(3,0); //level 
		   
		        stmt.setString(4,post_title); 
		        stmt.setString(5,post_content); 
		        stmt.setInt(6,1); //is_question
		        stmt.setInt(7,0); //is_bot
		        stmt.setInt(8,0); //is_qa_bountiful
		        stmt.setTimestamp(9,getCurrentTimeStamp()); 
		        stmt.setInt(10,0); // time_limit_qa
		        stmt.setInt(11,0); // time_limit_bot
		        stmt.setFloat(12,0);//qa_coin_basic 
		        stmt.setFloat(13,0);//qa_coin_bounty 
		        stmt.setFloat(14,0);//thoughfulness_score 
		        stmt.setInt(15,0);  // no show 0 = false
		        stmt.setInt(16,0); //previous_version
		        stmt.setInt(17,0); // num_of_upvotes
		        stmt.setInt(18,0); //num_of_upvotes
		        
		        stmt.executeUpdate();

	        } catch (SQLException ex) {
	        	 String msg = "An exception occurs when adding new post";
		         handleSQLException(ex, sql, "msg={" + msg + "}");
		    } finally {
		            ConnectionManager.close(conn, stmt);
		    }
	   }
	   
	   public int getPostLevel(int post_id){
		   
		    Connection conn = null;
	        PreparedStatement stmt = null;
	        String sql = "";
	        Integer returnLevel = null;
	        ResultSet rs = null;

	        try {
	            conn = ConnectionManager.getConnection();

	            sql = "select level FROM " + TBLNAME + " where post_id = ?";
	            stmt = conn.prepareStatement(sql);	         
	            stmt.setInt(1, post_id);
	            
	            rs = stmt.executeQuery();

	            while (rs.next()) {            	
	            	returnLevel = rs.getInt(1);         	
	             	            	
	            }
	            //return resultUser;

	        } catch (SQLException ex) {
	            handleSQLException(ex, sql, "Post Level={" + returnLevel + "}");
	        } finally {
	            ConnectionManager.close(conn, stmt, rs);
	        }
	        return returnLevel;
		   
	   }
	   
	   public void replyToPost(int avatar_id, int level, int post_id, String post_title, String post_content){
		   
	    	Connection conn = null;
		    PreparedStatement stmt = null;
		    String sql = "";	   
		
		    
		    try {
		    	conn = ConnectionManager.getConnection();
		    	sql = "INSERT INTO "+ TBLNAME +" (avatar_id, parent_id, level, post_title, post_content, is_question, is_bot, is_qa_bountiful, timestamp, time_limit_qa, time_limit_bot, qa_coin_basic, qa_coin_bounty, thoughfulness_score, no_show, previous_version, number_of_upvotes, number_of_downvotes) values "+
		    	"(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		        stmt = conn.prepareStatement(sql);
		       
		        stmt.setInt(1,avatar_id);
		        stmt.setInt(2,post_id); // parentID
		        if(level==8){
		        	stmt.setInt(3, 8); // level
		        }else{
		        	stmt.setInt(3,level+1); //level 
		        }
		        
		   
		        stmt.setString(4,post_title); 
		        stmt.setString(5,post_content); 
		        stmt.setInt(6,0); //is_question
		        stmt.setInt(7,0); //is_bot
		        stmt.setInt(8,0); //is_qa_bountiful
		        stmt.setTimestamp(9,getCurrentTimeStamp()); 
		        stmt.setInt(10,0); // time_limit_qa
		        stmt.setInt(11,0); // time_limit_bot
		        stmt.setFloat(12,0);//qa_coin_basic 
		        stmt.setFloat(13,0);//qa_coin_bounty 
		        stmt.setFloat(14,0);//thoughfulness_score 
		        stmt.setInt(15,0);  // no show 0 = false
		        stmt.setInt(16,0); //previous_version
		        stmt.setInt(17,0); // num_of_upvotes
		        stmt.setInt(18,0); //num_of_upvotes
		        
		        stmt.executeUpdate();

	        } catch (SQLException ex) {
	        	 String msg = "An exception occurs when adding new post";
		         handleSQLException(ex, sql, "msg={" + msg + "}");
		    } finally {
		            ConnectionManager.close(conn, stmt);
		    }
	   }

	   private static java.sql.Timestamp getCurrentTimeStamp() {

		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime());
	  }	
	   
	   // for search function 
	   public HashMap<Integer, Post> searchByKeyword(String searchText) {
	        HashMap<Integer, Post> postMap = new HashMap<>();

	        Connection conn = null;
	        ResultSet rs = null;
	        PreparedStatement preStmt = null;
	        Post tempPost = null;
	        try {
	            conn = ConnectionManager.getConnection();
	            String sql = "select * from " + TBLNAME + " where post_title like concat('%',?,'%') or post_content like concat('%',?,'%')";

	            preStmt = conn.prepareStatement(sql);
	            preStmt.setString(1, searchText);
	            preStmt.setString(2, searchText);
	            rs = preStmt.executeQuery();

	            while (rs.next()) {
	            	int avatar_id = rs.getInt(1);
	            	int parent_id = rs.getInt(2);
	            	int level = rs.getInt(3);
	            	int post_id = rs.getInt(4);
	            	String post_title = rs.getString(5);
	            	String post_content = rs.getString(6);
	            	boolean is_question = rs.getBoolean(7);
	            	boolean is_bot = rs.getBoolean(8);
	            	boolean is_qa_bountiful = rs.getBoolean(9);
	            	String timestamp = rs.getString(10);
	            	int time_limit_qa = rs.getInt(11);
	            	int time_limit_bot = rs.getInt(12);
	            	float qa_coin_basic = rs.getFloat(13);
	            	float qa_coin_bounty = rs.getFloat(14);
	            	float thoughfulness_score = rs.getFloat(15);
	            	boolean no_show = rs.getBoolean(16);
	            	int previous_version = rs.getInt(17);
	            	int number_of_upvotes = rs.getInt(18);
	            	int number_of_downvotes = rs.getInt(19);

	             
	            	tempPost = new Post(avatar_id, parent_id, level, post_id, post_title, post_content, is_question, is_bot, is_qa_bountiful, timestamp, time_limit_qa, time_limit_bot, qa_coin_basic, qa_coin_bounty, thoughfulness_score,
	            		 no_show, previous_version, number_of_upvotes, number_of_downvotes);
	            	
	            	       
	                postMap.put(post_id, tempPost);
	           
	                
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            ConnectionManager.close(conn, preStmt, rs);
	            
	        }
	        return postMap;
	    }
	   
	// get all posts by avatar_id , viewYourPosts.jsp
	    public List<Integer> retrieveAllPosts(int avatarID){
	    	List<Integer> returnList = new ArrayList<>();
		    Connection conn = null;
	        PreparedStatement stmt = null;
	        String sql = "";
	        Integer returnPostID = null;
	        ResultSet rs = null;

	        try {
	            conn = ConnectionManager.getConnection();

	            sql = "select (post_id) FROM " + TBLNAME + " where avatar_id = ?";
	            stmt = conn.prepareStatement(sql);	         
	            stmt.setInt(1, avatarID);
	            
	            rs = stmt.executeQuery();

	            while (rs.next()) {            	
	            	returnPostID = rs.getInt(1);
	            	returnList.add(returnPostID);
	             
	            	
	            }
	            //return resultUser;

	        } catch (SQLException ex) {
	            handleSQLException(ex, sql, "PostID={" + returnPostID + "}");
	        } finally {
	            ConnectionManager.close(conn, stmt, rs);
	        }
	        return returnList;
	    }

}
