package bookmarks;

import java.sql.*;
import java.util.*;
import sql.*;

public class User{

int userId;
String username="";
String passwd="";
private static Connection conn = null;
private static String select_userid="select id from tb_user where username='Bruno'";
private static String select_username="select username from tb_user where id=1";
private static final String CNAME = "User";

public User(){

}

public User(int id,String usr, String pwd){
userId=id;
username=usr;
passwd=pwd;

}

public User(ResultSet rs) throws SQLException {
		
this.userId=rs.getInt("id");
this.username=rs.getString("username");
this.passwd=rs.getString("passwd");
         

}

public int getUserId(){
String mName = CNAME + ".getById(): ";
Connection conn = null;
int user2Id=-1;

		
try {
            conn = ConnectionProvider.getConnection();
            System.out.println(mName+" conn: "+(conn==null)+new java.util.Date());
            Statement stmt = conn.createStatement ();
	  		ResultSet rs = stmt.executeQuery (select_userid);
	  		if( (rs!=null) && (rs.next()) ) {
				user2Id = rs.getInt("id");	
			}
	  			            
 } catch (Exception e) {
            System.err.println("Problem!");
            e.printStackTrace();
            
  }
	return user2Id;
}

public String getUserName(int id){
String mName = CNAME + ".getUserName(): ";
Connection conn = null;
String user3Id="";
String select_userid3="select username as user3Id from tb_user where id=?";

		
try {
            conn = ConnectionProvider.getConnection();
            System.out.println(mName+" conn: "+(conn==null)+new java.util.Date());
            PreparedStatement pstmt = conn.prepareStatement(select_userid3);
			pstmt.setInt(1,id);
			ResultSet rs = pstmt.executeQuery();
			if( (rs!=null) && (rs.next()) ) {
				user3Id = rs.getString("user3Id");
			}
	  			            
 } catch (Exception e) {
            System.err.println("Problem!");
            e.printStackTrace();
            
  }
	return user3Id;
}

public int getUserName(){
String mName = CNAME + ".getById(): ";
Connection conn = null;
int user2Id=-1;

		
try {
            conn = ConnectionProvider.getConnection();
            System.out.println(mName+" conn: "+(conn==null)+new java.util.Date());
            Statement stmt = conn.createStatement ();
	  		ResultSet rs = stmt.executeQuery (select_username);
	  		if( (rs!=null) && (rs.next()) ) {
				user2Id = rs.getInt("id");	
			}
	  			            
 } catch (Exception e) {
            System.err.println("Problem!");
            e.printStackTrace();
            
  }
	return user2Id;
}

public static User getById(int userId){
	return new User();
}


public static boolean addUser(String loginname,String password,String email,String lastname,String firstname){
return true;
}
public static void editUser(int userId,String loginname,String password,String email,String lastname,String firstname){

}

public static void deleteUser(int userId,int status){

}

public static boolean editPassword(int userId,String passwordold,String passwordnew1,String passwordnew2){
return true;
}

public static boolean editPassword(String loginname,String email,String randompass){
return true;
}
}