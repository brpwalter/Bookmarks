package bookmarks;

// import java.util.Date;
import java.sql.Date;
import java.util.List;
import java.util.LinkedList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import sql.ConnectionProvider;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Iterator;

public class Bookmark {
	private static final String CNAME = "Bookmark";
	
	private int bmId;
	private String bmName;
	private String url;
	private int rating;
	private int accessright;
	private Date creationdate;
	private Date accessdate;
	private int accessfrequency;
	private int coId;
	private int foId;
	private Comment co;
	private Folder fo;
	private User user;
	private int userId;
	private int etwas;
public Bookmark(){}	
/**
  * Konstruktor, erstellt aus dem Resultset eine Bookmark mit Id, Name, Url, Rating,
  * Zugangsrecht, Datum der Erzeugung und des Zugriffs, Zugriffsh�ufigkeit,
  * Comment-Id
  * @param rs       ResultSet aus Datenbankabfrage*/
  
	public Bookmark(ResultSet rs) throws SQLException {
		this.bmId = rs.getInt ("tb_bookmark_id");
		this.bmName = rs.getString("name");
		this.url =rs.getString("url");
		this.rating = rs.getInt("rating");
		this.accessright = rs.getInt("accessright");
		this.creationdate = new Date(rs.getTimestamp("date_of_creation").getTime());
		this.accessdate = new Date(rs.getTimestamp("date_of_access").getTime());
		this.accessfrequency = rs.getInt("frequency_of_access");
		this.coId = rs.getInt("comment_id");
	}

// Konstruktor wie oben mit �bergabe der Ordner-Id des Ordners, der die Bookmark enth�lt
/**
  * Konstruktor, erstellt aus dem Resultset eine Bookmark mit Id, Name, Url, Rating,
  * Zugangsrecht, Datum der Erzeugung, der Bearbeitung und des Zugriffs, Zugriffsh�ufigkeit,
  * Kopie-Eigenschaft (im Ggs. zu Original), Comment-Id und Ordners, der die Bookmark enth�lt
  * @param rs       ResultSet aus Datenbankabfrage
  * @param foId     Id des Ordners, der die Bookmark enth�lt
  */
	public Bookmark(ResultSet rs, int foid) throws SQLException {
		this.bmId = rs.getInt ("tb_bookmark_id");
		this.bmName = rs.getString("name");
		this.url =rs.getString("url");
		this.rating = rs.getInt("rating");
		this.accessright = rs.getInt("accessright");
		this.creationdate = new Date(rs.getTimestamp("date_of_creation").getTime());
		this.accessdate = new Date(rs.getTimestamp("date_of_access").getTime());
		this.accessfrequency = rs.getInt("frequency_of_access");
		this.coId = rs.getInt("comment_id");
		this.fo = Folder.getById(foid);
	}
	
	public Bookmark(ResultSet rs, int userId,int etwas) throws SQLException {
		this.bmId = rs.getInt ("tb_bookmark_id");
		this.bmName = rs.getString("name");
		this.url =rs.getString("url");
		this.rating = rs.getInt("rating");
		this.accessright = rs.getInt("accessright");
		this.creationdate = new Date(rs.getTimestamp("date_of_creation").getTime());
		this.accessdate = new Date(rs.getTimestamp("date_of_access").getTime());
		this.accessfrequency = rs.getInt("frequency_of_access");
		this.coId = rs.getInt("comment_id");
		this.etwas=etwas;
		this.user= User.getById(userId);
		
	}
	
	
	
/**
  * Holt einzelne Bookmark anhand ihrer Id aus der Datenbank
  * @param bmId     Id der Bookmark, der aus der Datenbank geholt werden soll 
  * @return         Bookmark
  */	
	public static Bookmark getById(int bmId){
		String mName = CNAME + ".getById(int bmId): ";
		Bookmark b = null;
		Connection conn = null;
		try{
			conn = ConnectionProvider.getConnection();
			String sql = "SELECT * FROM tb_bookmark WHERE tb_bookmark_id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1,bmId);
			ResultSet rs = pstmt.executeQuery();
			if(rs!=null && rs.next()) b = new Bookmark(rs);		
		}catch (Exception ex1){
					System.out.println(mName + ex1);
				} finally{
					try{
						if(conn!=null) conn.close();
					} catch(Exception ex2){
						System.out.println(mName + ex2);
					}
		}		
		return b;
	}
	//added bw
	/**
  * Gibt Id des Ordners aus, in dem eine bestimmte Bookmark befindet
  * @param bmId     Id der Bookmark f�r die der sie enthaltende Ordner gefragt ist
  * @return         Id Ordners, der die Bookmark enth�lt
  */	
	public int getFoidByBmid(int bmId){
		String mName = CNAME + ".getFoidByBmid(int bmId): ";
		int foId = -1;
		Connection conn = null;
		try{
			conn = ConnectionProvider.getConnection();
			String sql = "SELECT tb_folder_id as foid FROM vk_bookmark_folder WHERE tb_bookmark_id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,bmId);
			ResultSet rs = pstmt.executeQuery();
			if( (rs!=null) && (rs.next()) ) {
				foId = rs.getInt("foid");
			}
		} catch(Exception ex){
			System.out.println(mName + ex);
		} finally{
			try{
				if(conn!=null) conn.close();
			} catch(Exception ex2){
				System.out.println(mName + ex2);
			}
		}
		return foId;
	}
/**
  * Holt Bookmark, die in einem bestimmten Ordner enthalten ist, anhand ihrer Id aus der Datenbank
  * @param bmId     Id der Bookmark, die aus der Datenbank geholt werden soll 
  * @param foId     Id des Ordners, der die Bookmark enth�lt 
  * @return         Bookmark
  */
	public static Bookmark getById(int bmId, int foId){
		String mName = CNAME + ".getById(int bmId, int foId): ";
		Bookmark b = null;
		Connection conn = null;
		try{
			conn = ConnectionProvider.getConnection();
			String sql = "SELECT * FROM tb_bookmark WHERE tb_bookmark_id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1,bmId);
			ResultSet rs = pstmt.executeQuery();
			if(rs!=null && rs.next()) b = new Bookmark(rs,foId);		
		}catch (Exception ex1){
					System.out.println(mName + ex1);
				} finally{
					try{
						if(conn!=null) conn.close();
					} catch(Exception ex2){
						System.out.println(mName + ex2);
					}
		}		
		return b;
	}

/**
  * Holt komplette Bookmark-Liste aus der Datenbank
  * @return      Liste aller Bookmarks
  */	
	public static List getList(){
		String mName = CNAME + ".getList(): ";
		List bl = new LinkedList();
		Connection conn = null;
		try{
			conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM tb_bookmark;");
			if(rs!=null){
				while(rs.next()) bl.add(new Bookmark(rs));
				rs.close();
			}
		} catch(Exception ex1){
			System.out.println(mName + ex1);
		} finally{
			try{
				if(conn!=null) conn.close();
			} catch(Exception ex2){
				System.out.println(mName + ex2);
			}
		}		
		return bl;
	}
	
	// hinzugef�gt bw
	
	/**
  * Holt komplette Bookmark-Liste aus der Datenbank
  * @return      Liste aller Bookmarks
  */	
	public static List showTopTen(int userId){
		String mName = CNAME + ".showTopTen("+userId+"): ";
		List bl = new LinkedList();
		Connection conn = null;
		try{
			conn = ConnectionProvider.getConnection();
			String sql="select distinct tb_bookmark.* from (vk_root_user INNER JOIN vk_folder_root ON vk_root_user.root_id=vk_folder_root.root_id INNER JOIN tb_folder ON vk_folder_root.root_id=tb_folder.tb_folder_id OR vk_folder_root.folder_id=tb_folder.tb_folder_id INNER JOIN vk_bookmark_folder ON tb_folder.tb_folder_id=vk_bookmark_folder.tb_folder_id INNER JOIN tb_bookmark ON vk_bookmark_folder.tb_bookmark_id=tb_bookmark.tb_bookmark_id) where vk_root_user.user_id=? order by tb_bookmark.frequency_of_access desc limit 10";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			//Statement stmt = conn.createStatement();
			ResultSet rs = pstmt.executeQuery();
 			pstmt.setInt(1,userId);
			if(rs!=null){
				while(rs.next()) bl.add(new Bookmark(rs,userId,-1));
				rs.close();
			}
		} catch(Exception ex1){
			System.out.println("**"+mName + ex1);
		} finally{
			try{
				if(conn!=null) conn.close();
			} catch(Exception ex2){
				System.out.println("hier "+mName + ex2);
			}
		}		
		return bl;
	}
	
/**
  * Holt Liste der in einem bestimmten Ordner enthaltenen Bookmarks aus der Datenbank
  * @param foId    Id des Ordners, der die Bookmarks enth�lt 
  * @return        Liste aller Bookmarks aus einem bestimmten Ordner
  */	
	public static List getListByFolderId(int foId){
		String mName = CNAME + ".getListByFolderId(int foId): ";
		List boLi = new LinkedList();
		Connection conn = null;
		try{
			conn = ConnectionProvider.getConnection();
			String sql = "SELECT tb_bookmark.* FROM (tb_bookmark INNER JOIN vk_bookmark_folder ON vk_bookmark_folder.tb_bookmark_id=tb_bookmark.tb_bookmark_id) WHERE vk_bookmark_folder.tb_folder_id=? ORDER BY name";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,foId);
			ResultSet rs = pstmt.executeQuery();
			if(rs!=null) {
				while(rs.next()) boLi.add(new Bookmark(rs));
			}
		} catch(Exception ex){
			System.out.println(mName + ex);
		} finally{
			try{
				if(conn!=null) conn.close();
			} catch(Exception ex2){
				System.out.println(mName + ex2);
			}
		}
		return boLi;
	}

	public static List getList2ByFolderId(int foId){
		String mName = CNAME + ".getListByFolderId(int foId): ";
		List boLi = new LinkedList();
		Connection conn = null;
		try{
			conn = ConnectionProvider.getConnection();
			String sql = "SELECT tb_bookmark.* FROM (tb_bookmark INNER JOIN vk_bookmark_folder ON vk_bookmark_folder.tb_bookmark_id=tb_bookmark.tb_bookmark_id) WHERE vk_bookmark_folder.tb_folder_id=? order by tb_bookmark.frequency_of_access desc limit 10";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,foId);
			ResultSet rs = pstmt.executeQuery();
			if(rs!=null) {
				while(rs.next()) boLi.add(new Bookmark(rs));
			}
		} catch(Exception ex){
			System.out.println(mName + ex);
		} finally{
			try{
				if(conn!=null) conn.close();
			} catch(Exception ex2){
				System.out.println(mName + ex2);
			}
		}
		return boLi;
	}


/**
  * F�gt Bookmark zur Datenbank hinzu und gibt Id der eingef�gten Bookmark zur�ck
  * @param bmName             String, der den Namen der eingef�gten Bookmark enth�lt
  * @param url                String, der die komplette URL der eingef�gten Bookmark enth�lt 
  * @param rating             Wert zwischen 1 und 5, der die Bewertung der eingef�gten Bookmark ausdr�ckt
  * @param accessright        Wert, der das Zugangsrecht der eingef�gten Bookmark ausdr�ckt (0=nur ich; 1=alle) 
  * @param coId               Id des Kommentars, der zur eingef�gten Bookmark geh�rt 
  * @param foId               Id des Folders, in dem sich die eingef�gte Bookmark befindet
  * @return                   Id der eingef�gten Bookmark
  */	
	public static int addBookmark(String bmName,String url,int rating, int accessright, int coId,int foId){
		Connection conn = null;
		int insertedId = -1;
		try{
			conn = ConnectionProvider.getConnection();
			
			String sql = "INSERT INTO tb_bookmark(name,url,rating,accessright,date_of_creation,date_of_access,comment_id) VALUES(?,?,?,?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,bmName);
			pstmt.setString(2,url);
			pstmt.setInt(3,rating);
			pstmt.setInt(4,accessright);
			
			java.util.Date datum = new java.util.Date();
			java.sql.Timestamp sqldatetime = new java.sql.Timestamp(datum.getTime());
			
			pstmt.setTimestamp(5,sqldatetime);
			pstmt.setTimestamp(6,sqldatetime);
			
			pstmt.setInt(7,coId);

			pstmt.executeUpdate();
			
			sql = "SELECT LAST_INSERT_ID() as bmid";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if( (rs!=null) && (rs.next()) ) {
				insertedId = rs.getInt("bmid");	
			}
			
			sql = "INSERT INTO vk_bookmark_folder(tb_bookmark_id,tb_folder_id) VALUES(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,insertedId);
			pstmt.setInt(2,foId);

			pstmt.executeUpdate();
			
		} catch(Exception ex){
		} finally{
			try{
				if(conn!=null) conn.close();
			} catch(Exception ex2){
			}
		}
		return insertedId;
	}	

/**
  * Editiert Bookmark in der Datenbank
  * @param bmId            Id der zu editierenden Bookmark
  * @param bmName          String, der den Namen der zu zu editierenden Bookmark enth�lt
  * @param url             String, der die komplette URL der zu editierenden Bookmark enth�lt 
  * @param rating          Wert zwischen 1 und 5, der die Bewertung der zu editierenden Bookmark ausdr�ckt
  * @param accessright     Wert, der das Zugangsrecht der zu editierenden Bookmark ausdr�ckt (0=nur ich; 1=alle) 
  * @param coId            Id des Kommentars, der zu der zu editierenden Bookmark geh�rt 
  * @param foId            Id des Folders, in dem sich die zu editierende Bookmark befindet
  */	
	public static void editBookmark(int bmId,String bmName,String url,int rating,int accessright,int coId,int foId){
			String mName = CNAME + ".editBookmark(): ";
			Connection conn = null;
			String sql;
			PreparedStatement pstmt;
			try{
				conn = ConnectionProvider.getConnection();				
				sql = "UPDATE tb_bookmark SET name=?,url=?,rating=?,accessright=?,comment_id=? WHERE tb_bookmark_id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,bmName);
				pstmt.setString(2,url);
				pstmt.setInt(3,rating);
				pstmt.setInt(4,accessright);

				pstmt.setInt(5,coId);
				pstmt.setInt(6,bmId);

				pstmt.executeUpdate();
				
				sql = "DELETE FROM vk_bookmark_folder WHERE tb_bookmark_id=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,bmId);
				pstmt.executeUpdate();
				
				sql = "INSERT INTO vk_bookmark_folder(tb_bookmark_id,tb_folder_id) VALUES(?,?)";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1,bmId);
				pstmt.setInt(2,foId);
	
				pstmt.executeUpdate();
								
			} catch(Exception ex){
				System.out.println(mName+ex);
			} finally{
				try{
					if(conn!=null) conn.close();
				} catch(Exception ex2){
					System.out.println(mName+ex2);
				}
			}
		}


/**
  * L�scht Bookmark in der Datenbank
  * @param bmId     Id der zu l�schenden Bookmark
  * @param coId     Id des dazugeh�rigen Kommentars, zur �bergabe an die Methode zum L�schen des Kommentars
  */	
	public static void deleteBookmark(int bmId, int coId){
		Connection conn = null;
		try{
			conn = ConnectionProvider.getConnection();
			String sql = "DELETE FROM vk_bookmark_folder WHERE tb_bookmark_id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,bmId);
			pstmt.executeUpdate();

			sql = "DELETE FROM tb_bookmark WHERE tb_bookmark_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,bmId);
			pstmt.executeUpdate();
				if(conn!=null) conn.close();
		} catch(Exception ex){
		} finally{
			try{
				if(conn!=null) conn.close();
			} catch(Exception ex2){
			}
		}
		Comment.deleteComment(coId);
	}		

/**
  * L�scht Bookmarks in der Datenbank, die in einem bestimmten Ordner enthalten sind	
  * @param foId     Id des Ordners, in dem die zu l�schenden Bookmarks enthalten sind
  */	
	public static void deleteBmByFoId(int foId){
		List bl = getListByFolderId(foId);
		if(bl!=null){
			for(Iterator it = bl.iterator();it.hasNext();){
				Bookmark bo = (Bookmark) it.next();
				if(bo==null) continue;
				deleteBookmark(bo.getBmId(),bo.getCoId());
			}
		}
	}

   /**
    * Inkrementiert das "frequency_of_access"-Feld eines Bookmarks anhand seiner Id
	*/		
	public static void increaseAccessfreq(int bmId){
		
		String mName = CNAME + ".increaseAccessfreq(int bmId): ";
		Connection conn = null;
		try{
			conn = ConnectionProvider.getConnection();
			String sql = "UPDATE tb_bookmark SET frequency_of_access=frequency_of_access+1 WHERE tb_bookmark_id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,bmId);
			pstmt.executeUpdate();
		} catch(Exception ex){
			System.out.println(mName + ex);
		} finally{
			try{
				if(conn!=null) conn.close();
			} catch(Exception ex2){
				System.out.println(mName + ex2);
			}
		}
	}
	
	
/**
  * Gibt den Namen einer Bookmark aus
  * @return      Name der Bookmark
  */	
	public String getBmName(){
		return bmName;
	}

public void setUrl(String url){
	this.url=url;
}
	
/**
  * Gibt die komplette URL einer Bookmark aus
  * @return      komplette URL der Bookmark
  */	
	public String getUrl(){
		return url;
	}
	
/**
  * Gibt einen Wert aus, der die Bewertung einer Bookmark ausdr�ckt
  * @return      Bewertung der Bookmark
  */	
	public int getRating(){
		return rating;
	}

	public void setBmName(String bmName){
		this.bmName=bmName;
	}	
		
/**
  * Gibt einen Wert aus, der das Zugangsrecht einer Bookmark ausdr�ckt
  * @return      Zugangsrecht der Bookmark
  */	
	public int getAccessright(){
		return accessright;
	}
			
/**
  * Gibt das Erstellungsdatum einer Bookmark aus
  * @return      Erstellungsdatum der Bookmark
  */	
	public Date getCreationDate(){
		return creationdate;
	}

	
/**
  * Gibt das letzte Zugriffsdatum einer Bookmark aus
  * @return      Zugriffsdatum der Bookmark
  */	
	public Date getAccessdate(){
		return accessdate;
	}
			
/**
  * Gibt die Zugriffsh�ufigkeit auf eine Bookmark aus
  * @return      Zugriffsh�ufigkeit auf die Bookmark
  */	
	public int getAccessfrequency(){
		return accessfrequency;
	}
	
/**
  * Gibt die Id des zu einer Bookmark geh�rigen Kommentars aus
  * @return      Name der Bookmark
  */	
	public int getCoId(){
		return coId;
	}

/**
  * Gibt die Id einer Bookmark aus
  * @return      Id der Bookmark
  */	
	public int getBmId(){
		return bmId;
	}
	
/**
  * Gibt Id des Ordners aus, der eine Bookmark enth�lt
  * @return      Id des die Bookmark enthaltenden Ordners
  */	
	public int getFoId(){
		return foId;
	}
}