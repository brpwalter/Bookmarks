package bookmarks;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.LinkedList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import sql.ConnectionProvider;
import java.util.Iterator;
import java.util.StringTokenizer;

public class Folder {
	private static final String CNAME = "Folder";
	
	private int foId;
	private String foName;
	private int allocation;
	private int coId;
	private List children;
	private Folder parentFolder;
	private Comment co;
	private List boList;
	private User us;
	private Folder root;
	private Date creationdate;
	private List fl=new LinkedList();
	private List rootListe=new LinkedList();
	private int rootId;
	private static boolean frequency_count=false;
	private int anm1;
	private int anm2;
	//private String rootName;
	
public Folder(){}
/**
  * Konstruktor, erstellt aus dem Resultset einen Ordner mit Id, Name, Allocation
  * (=Id des übergeordneten Ordners), Comment-Id, Liste seiner Kinder,
  * Vatereigenschaft, Liste seiner Bookmarks
  * @param rs       ResultSet aus Datenbankabfrage
  */
	public Folder(ResultSet rs) throws SQLException {
		this.foId = rs.getInt("tb_folder_id");
		this.foName = rs.getString("foldername");
		this.allocation = rs.getInt("allocation");
		this.coId = rs.getInt("comment_id");
		this.creationdate = new Date(rs.getTimestamp("date_of_creation").getTime());
		children = new LinkedList(); //getList(foId);
		for(Iterator c=getChildren().iterator();c.hasNext();){ //?
			((Folder) c.next()).parentFolder=this; // er ist der Vater seiner Kinder
		}	
		setBoList(); //?
	}
	

//--- wahrscheinlich überflüssig! ---
  /**
  * Konstruktor, erstellt aus dem Resultset einen Ordner mit Id, Name, Allocation
  * (=Zuordnung zu übergeordnetem Ordner), Comment-Id, userbezogene Liste seiner Kinder,
  * Vatereigenschaft, Liste seiner Bookmarks
  * @param rs       ResultSet aus Datenbankabfrage
  * @param anm1,anm2 Platzhalter
  * @param userId   Id des Users, dessen Ordner in der Liste der Kinder erscheinen sollen
  */
	public Folder(ResultSet rs, int userId, int anm1, int anm2) throws SQLException {
		this.foId = rs.getInt("tb_folder_id");
		this.foName = rs.getString("foldername");
		this.allocation = rs.getInt("allocation");
		this.coId = rs.getInt("comment_id");
		this.creationdate = new Date(rs.getTimestamp("date_of_creation").getTime());
		this.anm1=anm1;
		this.anm2=anm2;
		children = getChildrenByUserId(userId, foId);
		for(Iterator c=getChildren().iterator();c.hasNext();){ 
			((Folder) c.next()).parentFolder=this; // er ist der Vater seiner Kinder
		}		
		setBoList();
	}
	
	
	//zusätzlich erstellt
	/*public Folder(ResultSet rs, int userId, String etwas) throws SQLException {
				this.foId = rs.getInt("tb_folder_id");
				this.foName = rs.getString("foldername");
				this.allocation = rs.getInt("allocation");
				this.coId = rs.getInt("comment_id");
				this.creationdate = new Date(rs.getTimestamp("date_of_creation").getTime());
				
				rootListe=getRootListByUserId(userId);
				for(Iterator it1 = rootListe.iterator();it1.hasNext();){
					Folder fo1 = (Folder) it1.next();
					this.rootId=fo1.getFoId();
				}
			}*/
			//
/**
  * Konstruktor, erstellt aus dem Resultset einen Ordner mit Id, Name, Allocation
  * (=Zuordnung zu übergeordnetem Ordner), Comment-Id, userbezogene Liste seiner Kinder,
  * Vatereigenschaft, Liste seiner Bookmarks
  * @param rs       ResultSet aus Datenbankabfrage
  * @param userId   Id des Users, dessen Ordner in der Liste der Kinder erscheinen sollen
  */
	public Folder(ResultSet rs, int rootId) throws SQLException {
		this.foId = rs.getInt("tb_folder_id");
		this.foName = rs.getString("foldername");
		this.allocation = rs.getInt("allocation");
		this.coId = rs.getInt("comment_id");
		this.creationdate = new Date(rs.getTimestamp("date_of_creation").getTime());
		children = getChildrenByRootId(rootId, foId);
		for(Iterator c=getChildren().iterator();c.hasNext();){ 
			((Folder) c.next()).parentFolder=this; // er ist der Vater seiner Kinder
		}		
		setBoList();
	}
	
	public Folder(ResultSet rs, int rootId,boolean frequency_count) throws SQLException {
		this.foId = rs.getInt("tb_folder_id");
		this.foName = rs.getString("foldername");
		this.allocation = rs.getInt("allocation");
		this.coId = rs.getInt("comment_id");
		this.creationdate = new Date(rs.getTimestamp("date_of_creation").getTime());
		children = getChildrenByRootId(rootId, foId);
		for(Iterator c=getChildren().iterator();c.hasNext();){ 
			((Folder) c.next()).parentFolder=this; // er ist der Vater seiner Kinder
		}		
		setBoList2();
	}
/**
  * Konstruktor, der nur einen Namen an das Ordner-Objekt übergibt (ursprünglich zur 
  * Erzeugung eines einfachen Root-Ordners)
  * @param foName     String mit dem gewünschten Ordnernamen
  */
	public Folder(String foName){
		this.foName = foName;
	}
	
	/*public Folder (int userId, int rootId){
		this.userId=userId;
		this.rootId=rootId;
	
	}*/
/**
  * Holt einzelnen Ordner anhand seiner Id aus der Datenbank
  * @param foId     Id des Folders, der aus der Datenbank geholt werden soll 
  * @return         Ordner
  */	
	public static Folder getById(int foId){
		String mName = CNAME + ".getById(int foId): ";
		Connection conn = null;
		Folder f = null;
		try{
			conn = ConnectionProvider.getConnection();
			System.out.println(mName+" conn: "+(conn==null)+new Date());
			
			String sql = "SELECT * FROM tb_folder WHERE tb_folder_id=?";		
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,foId);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs!=null && rs.next()) f = new Folder(rs);
		} catch(Exception ex1){
			System.out.println(mName + ex1);
		} finally{
			try{
				if(conn!=null) conn.close();
			} catch(Exception ex2){
				System.out.println(mName + ex2);
			}
		}
		return f;
	}
	
/**
  * Holt komplette Ordnerliste aus der Datenbank	
  * @return   Liste aller Ordner aus der Datenbank
  */
	public static List getList(){
		String mName = CNAME + ".getList(): ";
		List fl = new LinkedList();
		Connection conn = null;
		try{
			conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM tb_folder;");
			if(rs!=null){
				while(rs.next()) fl.add(new Folder(rs));
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
		return fl;
	}

/**
  * Holt Unterordnerliste eines Ordners aus der Datenbank
  * @param foId     Id des Ordners, dessen Unterordner in die Liste aufgenommen werden sollen
  * @return         Liste aller Unterordner eines Ordners
  */
	public static List getList(int foId)throws NullPointerException{
		String mName = CNAME + ".getList(foId): ";
		List fl = new LinkedList();
		Connection conn = null;
		try{
			conn = ConnectionProvider.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM tb_folder WHERE allocation=?");
			pstmt.setInt(1,foId);
			ResultSet rs = pstmt.executeQuery();
			if(rs!=null){
				while(rs.next()) fl.add(new Folder(rs));
				rs.close();
			}
		} catch(Exception ex1){
			System.out.println(mName + ex1);
			ex1.printStackTrace();
		} finally{
			try{
				if(conn!=null) conn.close();
			} catch(Exception ex2){
				System.out.println(mName + ex2);
			}
		}		
		return fl;
	}
// hinzugefügt bw
/**
  * Holt komplette Ordnerliste eines Users aus der Datenbank
  * @param userId     Id des Users, dessen Ordner in die Liste aufgenommen werden sollen
  * @return           Liste aller Ordner eines Users
  */
	public static List getListByUserId(int userId){
		String mName = CNAME + ".getListByUserId(int userId): ";
		List ufl = new LinkedList();
		Connection conn = null;

		try{
			conn = ConnectionProvider.getConnection();
			String sql = "SELECT tb_folder.* FROM (tb_folder INNER JOIN vk_folder_user ON vk_folder_user.folder_id=tb_folder.tb_folder_id) WHERE vk_folder_user.user_id=?";		
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,userId);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs!=null){
			while(rs.next()) ufl.add(new Folder(rs,userId));
			
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
		return ufl;
	}
	
	public static List getListByUserId2(int userId){
		String mName = CNAME + ".getListByUserId(int userId): ";
		List ufl = new LinkedList();
		Connection conn = null;

		try{
			conn = ConnectionProvider.getConnection();
			String sql = "SELECT tb_folder.* FROM (tb_folder INNER JOIN vk_folder_user ON vk_folder_user.folder_id=tb_folder.tb_folder_id) WHERE vk_folder_user.user_id=?";		
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,userId);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs!=null){
			while(rs.next()) ufl.add(new Folder(rs,userId,true));
			
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
		return ufl;
	}
		
/**
  * Holt komplette Root-Ordner-Liste eines Users aus der Datenbank
  * @param userId     Id des Users, dessen Root-Ordner in die Liste aufgenommen werden sollen
  * @return           Liste aller Root-Ordner eines Users
  */
	public static List getRootListByUserId(int userId){
		String mName = CNAME + ".getRootListByUserId(int userId): ";
		List ufl = new LinkedList();
		Connection conn = null;

		try{
			conn = ConnectionProvider.getConnection();
			String sql = "SELECT tb_folder.* FROM (tb_folder INNER JOIN vk_root_user ON vk_root_user.root_id=tb_folder.tb_folder_id) WHERE vk_root_user.user_id=?";		
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,userId);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs!=null){
			while(rs.next()) ufl.add(new Folder(rs,userId,-1,-1));
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
		return ufl;
	}
	
/**
  * Holt komplette Unterordner-Liste eines Root-Ordners
  * @param userId     Id des Users, dessen Root-Ordner in die Liste aufgenommen werden sollen
  * @return           Liste aller Root-Ordner eines Users
  */
	public static List getListByRootId(int rootId) throws NullPointerException{
		String mName = CNAME + ".getListByRootId(int rootId): ";
		List ufl = new LinkedList();
		Connection conn = null;

		try{
			conn = ConnectionProvider.getConnection();
			String sql = "SELECT tb_folder.* FROM (tb_folder INNER JOIN vk_folder_root ON vk_folder_root.folder_id=tb_folder.tb_folder_id) WHERE vk_folder_root.root_id=?";		
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,rootId);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs!=null){
			while(rs.next()) ufl.add(new Folder(rs,rootId));
			rs.close();
			}	
		} catch(Exception ex1){
			System.out.println(mName + ex1);
			ex1.printStackTrace();
		} finally{
			try{
				if(conn!=null) conn.close();
			} catch(Exception ex2){
				System.out.println(mName + ex2);
			}
		}
		return ufl;
	}
	
/**
  * Holt einem User zugehörige Unterordnerliste eines Ordners aus der Datenbank
  * @param userId       Id des Users, dessen Ordner in die Liste aufgenommen werden sollen
  * @param int foId     Id des Ordners, dessen Unterordner in die Liste aufgenommen werden sollen
  * @return             userbezogene Liste aller Unterordner eines Ordners
  */
	public static List getChildrenByRootId(int rootId, int foId){
		String mName = CNAME + ".getChildrenByRootId(int rootId, int foId): ";
		List ufl = new LinkedList();
		Connection conn = null;

		try{
			conn = ConnectionProvider.getConnection();
			String sql = "SELECT tb_folder.* FROM (tb_folder INNER JOIN vk_folder_root ON vk_folder_root.folder_id=tb_folder.tb_folder_id) WHERE vk_folder_root.root_id=? AND tb_folder.allocation=?";		
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,rootId);
			pstmt.setInt(2,foId);
			ResultSet rs = pstmt.executeQuery();
			if(rs!=null){
			while(rs.next()) ufl.add(new Folder(rs,rootId));
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
		return ufl;
	}
	//hinzugefügt
	/**
  * Holt einem User zugehörige Unterordnerliste eines Ordners aus der Datenbank
  * @param userId       Id des Users, dessen Ordner in die Liste aufgenommen werden sollen
  * @param int foId     Id des Ordners, dessen Unterordner in die Liste aufgenommen werden sollen
  * @return             userbezogene Liste aller Unterordner eines Ordners
  */
	public static List getChildrenByUserId(int userId, int foId){
		String mName = CNAME + ".getChildrenByUserId(int userId, int foId): ";
		List ufl = new LinkedList();
		Connection conn = null;

		try{
			conn = ConnectionProvider.getConnection();
			String sql = "SELECT tb_folder.* FROM (tb_folder INNER JOIN vk_folder_user ON vk_folder_user.folder_id=tb_folder.tb_folder_id) WHERE vk_folder_user.user_id=? AND tb_folder.allocation=?";		
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,userId);
			pstmt.setInt(2,foId);
			ResultSet rs = pstmt.executeQuery();
			if(rs!=null){
			while(rs.next()) ufl.add(new Folder(rs,userId));
			
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
		return ufl;
	}
/**
  * Bildet einen String aus den Ordner-Ids aller zu einem User gehörigen Ordner
  * @param userId     Id des Users, dessen Ordner-Ids in dem String erscheinen sollen
  * @return           String aller Ordner-Ids eines Users
  */
	public static String openAll(int userId){
		String fiStr = "";
		int rootId;
		List fl=new LinkedList();
		List rootListe=new LinkedList();
		rootListe=Folder.getRootListByUserId(userId);
		for(Iterator it1 = rootListe.iterator();it1.hasNext();){
			Folder fo1 = (Folder) it1.next();
			rootId=fo1.getFoId();
			fiStr+=rootId+ "_";
			System.out.println("*****rootId: "+rootId);
			if (fl!=null )
			try { 
			fl = getListByRootId(rootId);
			} catch(NullPointerException e) { 
			System.out.println("**** Exception: "+e.getMessage()); e.printStackTrace();
			}

			
			for(Iterator it = fl.iterator();it.hasNext();){
				
				Folder fo = (Folder) it.next();
				if(fo==null) continue;
				int foId = fo.getFoId();
				System.out.println("*****foId: "+foId);
				fiStr = fiStr + foId + "_";
				System.out.println("+++fiStr: "+fiStr);
			}
			System.out.println("+++fiStr: "+fiStr);
		}
		return fiStr;
	}		
	
/**
  * Prüft, ob eine bestimmte Ordner-Id in einem String (openAll s.o.) aus lauter Ordner-Ids enthalten ist
  * @param fi     String, der überprüft werden soll
  * @return       Bool'sche Variable, die mit dem Wert true ausdrückt, daß eine bestimmte
  *               Ordner-Id in einem String von Ordner-Ids enthalten ist
  */

	public boolean isOpen(String fi){
		if(fi!=null){
			StringTokenizer st = new StringTokenizer( fi, "_" );
	        while( st.hasMoreTokens() )
	                if( st.nextToken().equals( ""+getFoId() ) )
	                        return true;
		}
        return false;
	}

/**
  * Liste aller Ordner eines Users für HTML-Auswahlmenü in Edit-Formularen mit Übergabe von 4 Parametern
  * @param userId         Id des Users, dessen Ordner in der Auswahl erscheinen sollen
  * @param allocation     Id des Ordners, dessen Kinder aus der Datenbank geholt werden sollen
  * @param einrueck       String, der die Einrücktiefe für die untergeordneten Ordner bestimmt
  * @param foId           Id des Ordners, der mit dem HTML-Wort "selected" vorausgewählt werden soll
  *                       weil er den Ordner oder die Bookmark, für die das Edit-Formular gilt, enthält
  * @return               String, der alle Ordner eines Users in einem HTML-Auswahlmenü auflistet
  */

	/*public static String auswahlMenueBaum(int userId, int allocation, int einrueck, int foId){
		
		List fl=new LinkedList();
		List rootListe=new LinkedList();
		rootListe=Folder.getRootListByUserId(userId);
		//fl = Folder.getChildrenByRootId(userId,allocation);
		String rv = "";
		//String ein = "";
		int rootId;
		String ein = "";
		System.out.println("einrueck: "+einrueck);
		String rootName="";
		String selected="selected";
		for(Iterator it1 = rootListe.iterator();it1.hasNext();){
			Folder fo1 = (Folder) it1.next();
			rootId=fo1.getFoId();
			rootName=fo1.getFoName();
			System.out.println("rootName:"+rootName);
			if (fl!=null )
			try { 
			fl = getListByRootId(rootId);
			} catch(NullPointerException e) { 
			System.out.println("**** Exception: "+e.getMessage()); e.printStackTrace();
			}
			rv = rv + "<option value=\"" + rootId + "\" " + ein+selected + ">" +  rootName + "</option>";
			for(Iterator it = fl.iterator();it.hasNext();){
				Folder fo = (Folder) it.next();
				if(fo==null) continue;
				int folderId = fo.getFoId();
				System.out.println("folderId"+folderId);
				selected = (foId==folderId)?" selected":"";
				for(int i = 0; i<einrueck; i++){
					System.out.println("@123: ");
					ein = ein + "&nbsp;&nbsp;&nbsp;&nbsp;";
					}
				rv = rv + "<option value=\"" + fo.getFoId() + "\" " + selected + ">" + ein + fo.getFoName() + "</option>";
				//einrueck=einrueck+1;
			}
			
		}
		return rv;
	}
*/

/**
  * Liste aller Ordner eines Users für HTML-Auswahlmenü in Add-Formularen mit Übergabe von 3 Parametern
  * @param userId         Id des Users, dessen Ordner im Auswahlmenü erscheinen sollen
  * @param allocation     Id des Ordners, dessen Kinder aus der Datenbank geholt werden sollen
  * @param einrueck       String, der die Einrücktiefe für die untergeordneten Ordner bestimmt
  * @return               Aufruf der gleichen Methode mit 4 Parametern, wobei der vierte auf -1 festgesetzt wird, damit
  *                       kein Ordner vorausgewählt wird
  */
/*	public static String auswahlMenueBaum(int userId, int allocation, int einrueck){
		return auswahlMenueBaum(userId, allocation, einrueck, 1);
	}
*/

//
/**
  * Liste aller Ordner eines Users für HTML-Auswahlmenü in Edit-Formularen mit Übergabe von 4 Parametern
  * @param userId         Id des Users, dessen Ordner in der Auswahl erscheinen sollen
  * @param allocation     Id des Ordners, dessen Kinder aus der Datenbank geholt werden sollen
  * @param einrueck       String, der die Einrücktiefe für die untergeordneten Ordner bestimmt
  * @param foId           Id des Ordners, der mit dem HTML-Wort "selected" vorausgewählt werden soll
  *                       weil er den Ordner oder die Bookmark, für die das Edit-Formular gilt, enthält
  * @return               String, der alle Ordner eines Users in einem HTML-Auswahlmenü auflistet
  */

	public static String auswahlMenueBaum(int userId, int allocation, int einrueck, int foId){
		List fl = new LinkedList();
		fl = Folder.getChildrenByUserId(userId,allocation);
		String rv = "";
		for(Iterator it = fl.iterator();it.hasNext();){
			Folder fo = (Folder) it.next();
			if(fo==null) continue;
			int folderId = fo.getFoId();
//			int open = fo.getOpen();
			String ein = "";
			String selected = (foId==folderId)?" selected":"";
			for(int i = 0; i<einrueck; i++)
				ein = ein + "&nbsp;&nbsp;&nbsp;&nbsp;";
			rv = rv + "<option value=\"" + fo.getFoId() + "\" " + selected + ">" + ein + fo.getFoName() + "</option>";
			rv = rv + auswahlMenueBaum(userId,folderId,einrueck+1,foId);	
		}
		return rv;
	}

/**
  * Liste aller Ordner eines Users für HTML-Auswahlmenü in Add-Formularen mit Übergabe von 3 Parametern
  * @param userId         Id des Users, dessen Ordner im Auswahlmenü erscheinen sollen
  * @param allocation     Id des Ordners, dessen Kinder aus der Datenbank geholt werden sollen
  * @param einrueck       String, der die Einrücktiefe für die untergeordneten Ordner bestimmt
  * @return               Aufruf der gleichen Methode mit 4 Parametern, wobei der vierte auf -1 festgesetzt wird, damit
  *                       kein Ordner vorausgewählt wird
  */
	public static String auswahlMenueBaum(int userId, int allocation, int einrueck){
		return auswahlMenueBaum(userId, allocation, einrueck, -1);
	}


//

/**
  * Fügt Ordner zur Datenbank hinzu
  * @param foName         Name des eingefügten Ordners
  * @param allocation     Id des Ordners, in dem der eingefügte Ordner angelegt wird
  * @param rootId         Id des Root-Ordners, unter den der eingefügte Ordner platziert wird
  * @param coId           Id des Kommentars, der zu dem eingefügten Ordner gehört
  * @return               Id des eingefügten Ordners
  */
	public static int addFolder(String foName,int allocation,int rootId,int coId,int userId){
		String mName = CNAME + ".addFolder(): ";
		Connection conn = null;
		int insertedId = -1;
		try{			
			conn = ConnectionProvider.getConnection();
			String sql = "INSERT INTO tb_folder(foldername,allocation,comment_id,date_of_creation) VALUES(?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,foName);
			pstmt.setInt(2,allocation);
			pstmt.setInt(3,coId);
			java.util.Date datum = new java.util.Date();
			java.sql.Timestamp sqldatetime = new java.sql.Timestamp(datum.getTime());
				
				pstmt.setTimestamp(4,sqldatetime);

			pstmt.executeUpdate();
			// Ende Eintrag in tb_folder
			
			sql = "SELECT tb_folder_id as foid FROM tb_folder WHERE tb_folder_id=LAST_INSERT_ID()";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if( (rs!=null) && (rs.next()) ) {
				insertedId = rs.getInt("foid");	
			}
			
			sql = "INSERT INTO vk_folder_root(folder_id,root_id) VALUES(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,insertedId);
			pstmt.setInt(2,rootId);
			pstmt.executeUpdate();
			
			sql = "INSERT INTO vk_folder_user(folder_id,user_id) VALUES(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,insertedId);
			pstmt.setInt(2,userId);
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
		return insertedId;
	}
	
/**
  * Fügt Root-Ordner eines Users zur Datenbank hinzu WURDE GEUPDATED
  * @param foName         Name des eingefügten Ordners
  * @param userId         Id des Users, zu dem der eingefügte Ordner gehört
  * @param coId           Id des Kommentars, der zu dem eingefügten Ordner gehört
  * @return               Id des eingefügten Root-Ordners
  */
	public static void addRootFolder(String foName,int userId,int coId){
		System.out.println("addRootFolder: "+userId);
		String mName = CNAME + ".addRootFolder(): ";
		Connection conn = null;
		int insertedRootId = -1;
		try{			
			conn = ConnectionProvider.getConnection();
			String sql = "INSERT INTO tb_folder(foldername,allocation,comment_id,date_of_creation) VALUES(?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,foName);
			pstmt.setInt(2,0); //Allocation eines Root Folders immer 0!
			pstmt.setInt(3,coId);
			
			java.util.Date datum = new java.util.Date();
			java.sql.Timestamp sqldatetime = new java.sql.Timestamp(datum.getTime());
			pstmt.setTimestamp(4,sqldatetime);

			pstmt.executeUpdate();
			
			sql = "SELECT tb_folder_id as foid FROM tb_folder WHERE tb_folder_id=LAST_INSERT_ID()";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if( (rs!=null) && (rs.next()) ) {
				insertedRootId = rs.getInt("foid");	
			}
			
			sql = "INSERT INTO vk_root_user(root_id,user_id) VALUES(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,insertedRootId);
			pstmt.setInt(2,userId);
			pstmt.executeUpdate();
			
			sql = "INSERT INTO vk_folder_user(folder_id,user_id) VALUES(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,insertedRootId);
			pstmt.setInt(2,userId);
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
  * Editiert Ordner in der Datenbank	
  * @param foId           Id des zu editierenden Ordners
  * @param foName     (geänderter) Name des zu editierenden Ordners
  * @param allocation     Id des (geänderten) übergeordneten Ordners
  * @param coId           Id des (geänderten) Kommentars, der zu dem zu editierenden Ordner gehört
  */

	public static void editFolder(int foId,String foName,int allocation,int coId){
			Connection conn = null;
			try{
				conn = ConnectionProvider.getConnection();
				String sql = "UPDATE tb_folder SET foldername=?,allocation=?,comment_id=? WHERE tb_folder_id=?";
				PreparedStatement pstmt = conn.prepareStatement(sql);
				pstmt.setString(1,foName);
				pstmt.setInt(2,allocation);
				pstmt.setInt(3,coId);
				pstmt.setInt(4,foId);
					
				pstmt.executeUpdate();
								
			} catch(Exception ex){
			} finally{
				try{
					if(conn!=null) conn.close();
				} catch(Exception ex2){
				}
			}
		}
	//
	/**
  * Kopiert einen Ordner in einen anderen Ordner
  * @param foId           Id des zu editierenden Ordners
  * @param foName     (geänderter) Name des zu editierenden Ordners
  * @param allocation     Id des (geänderten) übergeordneten Ordners
  * @param coId           Id des (geänderten) Kommentars, der zu dem zu editierenden Ordner gehört
  */
	
	public static void copyFolder(String foName,int allocation,int rootId,int coId,int userId){
			Connection conn = null;
			int insertedId=-1;
			try{
				conn = ConnectionProvider.getConnection();
				
			//
			conn = ConnectionProvider.getConnection();
			String sql = "INSERT INTO tb_folder(foldername,allocation,comment_id,date_of_creation) VALUES(?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,foName);
			pstmt.setInt(2,allocation);
			pstmt.setInt(3,coId);
			java.util.Date datum = new java.util.Date();
			java.sql.Timestamp sqldatetime = new java.sql.Timestamp(datum.getTime());
				
				pstmt.setTimestamp(4,sqldatetime);

			pstmt.executeUpdate();
			// Ende Eintrag in tb_folder
			
			sql = "SELECT tb_folder_id as foid FROM tb_folder WHERE tb_folder_id=LAST_INSERT_ID()";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if( (rs!=null) && (rs.next()) ) {
				insertedId = rs.getInt("foid");	
			}
			
			sql = "INSERT INTO vk_folder_root(folder_id,root_id) VALUES(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,insertedId);
			pstmt.setInt(2,rootId);
			pstmt.executeUpdate();
			
			sql = "INSERT INTO vk_folder_user(folder_id,user_id) VALUES(?,?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,insertedId);
			pstmt.setInt(2,userId);
			pstmt.executeUpdate();
			//			
			} catch(Exception ex){
			} finally{
				try{
					if(conn!=null) conn.close();
				} catch(Exception ex2){
				}
			}
		}
	//
/**
  * Löscht einzelnen Ordner in der Datenbank
  * @param foId     Id des zu löschenden Ordners
  * @param coId     Id des dazugehörigen Kommentars, zur Übergabe an die Methode zum Löschen des Kommentars
  */

	public static void deleteFolder(int foId, int coId){
		Connection conn = null;
		try{
			conn = ConnectionProvider.getConnection();
			String sql = "DELETE FROM vk_folder_user WHERE folder_id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,foId);
			pstmt.executeUpdate();

			sql = "DELETE FROM tb_folder WHERE tb_folder_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,foId);
			pstmt.executeUpdate();
			
			sql = "DELETE FROM  vk_folder_root WHERE folder_id=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,foId);
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
  * Löscht Ordner komplett mit Bookmarks und Unterordnern in der Datenbank
  * @param foId           Id des zu löschenden Ordners
  */

	public static void deleteFolderComplete(int foId){
		
		Folder fo = Folder.getById(foId);
		List ch = new LinkedList();
		ch = fo.getChildren();
		List bm = new LinkedList();
		bm = fo.getBoList();
		
		// wenn Kinder vorhanden, erst Kinder löschen
		if(ch!=null && ch.size()>0){
			for(Iterator it = ch.iterator();it.hasNext();){
				Folder fd = (Folder) it.next();
				if(fd==null) continue;
				deleteFolderComplete(fd.getFoId());  // rekursiver Neuaufruf der Methode für jedes einzelne Kind
			}
		}
		// wenn keine Kinder, dann mich selbst löschen
		if(bm!=null) Bookmark.deleteBmByFoId(foId); // erst Bookmarks
		deleteFolder(foId,fo.getCoId());		// dann mich
	}

/**
  * Gibt Id des Ordners aus, in dem eine bestimmte Bookmark befindet
  * @param bmId     Id der Bookmark für die der sie enthaltende Ordner gefragt ist
  * @return         Id Ordners, der die Bookmark enthält
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
  * Gibt die Tiefe eines Ordners aus, d.h., wie weit er untergeordnet ist
  * @return    Gibt einen Wert aus, der anzeigt, wieviele übergeordnete Ordner gezählt wurden
  */	
	public int getDepth(){
		return (parentFolder==null) ? 0 : parentFolder.getDepth()+1;			
	}
	
/**
  * Gibt den Wurzel-Ordner aus
  * @return     Wurzel-Ordner 
  */	
	public Folder getRoot(){
		if(parentFolder==null) root = this;
		return root;
	}
/**
  * Gibt den Ordnernamen aus
  * @return     Ordnername 
  */	
	public String getFoName(){
		return foName;
	}
	
/**
  * Gibt die Allocation eines Ordners aus, d.h. die Ordner-Id des direkt übergeordneten Ordners
  * @return     Ordner-Id des direkt übergeordneten Ordners 
  */	
	public int getAllocation(){
		return allocation;
	}	
		
/**
  * Gibt die Kommentar-Id des zum Ordner gehörigen Kommentars aus
  * @return     Kommentar-Id 
  */	
	public int getCoId(){
		return coId;
	}

/**
  * Gibt die Id eines Ordners aus
  * @return     Ordner-Id
  */	
	public int getFoId(){
		return foId;
	}
	
/**
  * Gibt den übergeordneten Ordner eines Ordners aus
  * @return     übergeordneter Ordner ("Vater")
  */	
	public Folder getParentFolder(){
		return parentFolder;
	}

/**
  * Gibt eine Liste der untergeordneten Ordner eines Ordners aus (seine "Kinder")
  * @return     Liste der untergeordneten Ordner eines Ordners
  */	
	public List getChildren() {
		return children;	
	}

/**
  * Setzt eine Liste der untergeordneten Ordner eines Ordners aus (seine "Kinder")
  * @param children     Liste der untergeordneten Ordner eines Ordners
  */	
	public void setChildren(List children) {
		this.children = children;	
	}

/**
  * Gibt eine Liste der in einem Ordner enthaltenen Bookmarks aus
  * @return     Liste der in einem Ordner enthaltenen Bookmarks
  */	
	public List getBoList(){
		return boList;
	}
	
	public void setFoName(String foName){
		this.foName=foName;
	}

	public Date getCreationDate(){
		return creationdate;
	}
	public List getBoList2(){
		return boList;
	}
/**
  * Setzt eine Liste der in einem Ordner enthaltenen Bookmarks
  * @return     Liste aller in einem Ordner enthaltenen Bookmarks
  */
	public void setBoList(){
		boList = Bookmark.getListByFolderId(foId);
	}
	public void setBoList2(){
		boList = Bookmark.getList2ByFolderId(foId);
	}
	// Aufbau des Ordnerbaums (wahrscheinlich alt und überholt)
/*	public static String rekTraverse(int allocation, int einrueck){
		List fl = new LinkedList();
		fl = getList(allocation);
		String rv = "";
		for(Iterator it = fl.iterator();it.hasNext();){
			Folder fo = (Folder) it.next();
			if(fo==null) continue;
			int folderId = fo.getFoId();
			String fn = fo.getFoName();
			int open = fo.getOpen();
			for(int i = 0; i<einrueck; i++)
				rv = rv + "&nbsp;&nbsp;&nbsp;";
			rv = rv + folderId + " " + fn + "<br>";
			if(open==1){
				rv = rv + rekTraverse(folderId, einrueck+1);	
			}	
		}				
		return rv;
	}
*/
	// achtung, es gab 3 verschiedene auswahlMenuebäume, 2 hier in der folder und 1 in der bofo, der jetzt hier rein kopiert wurde 


	// Aufbau des Ordnerbaums (wird im Auswahlmenü von Formularen verwendet)
	// nein, der hier nicht, sondern der, der ehemals in der bofo stand
	
/*	public static String auswahlMenueBaum(int allocation, int einrueck){
		List fl = new LinkedList();
		fl = Folder.getList(allocation);
		String rv = "";
		for(Iterator it = fl.iterator();it.hasNext();){
			Folder fo = (Folder) it.next();
			if(fo==null) continue;
			int folderId = fo.getFoId();
			int open = fo.getOpen();
			String ein = "";
			for(int i = 0; i<einrueck; i++)
				ein = ein + "&nbsp;&nbsp;&nbsp;&nbsp;";
			rv = rv + "<option value=\"" + fo.getFoId() + "\">" + ein + fo.getFoName() + "</option>";
			if(open==1){
				rv = rv + auswahlMenueBaum(folderId, einrueck+1);	
			}	
		}				
		return rv;
	}
*/

/*	
// Aufbau des Ordnerbaums (wird im Auswahlmenü von Formularen verwendet)
// nein, der hier nicht, sondern der, der ehemals in der bofo stand

	public static String auswahlMenueBaum(int userId, int allocation, int einrueck){
		List fl = new LinkedList();
		fl = Folder.getChildrenByUserId(userId,allocation);
		String rv = "<option value=\"1\">Root</option>";
		for(Iterator it = fl.iterator();it.hasNext();){
			Folder fo = (Folder) it.next();
			if(fo==null) continue;
			int folderId = fo.getFoId();
			int open = fo.getOpen();
			String ein = "";
			for(int i = 0; i<einrueck; i++)
				ein = ein + "&nbsp;&nbsp;&nbsp;&nbsp;";
			rv = rv + "<option value=\"" + fo.getFoId() + "\">" + ein + fo.getFoName() + "</option>";
			if(open==1){
				rv = rv + auswahlMenueBaum(userId, folderId, einrueck+1);	
			}	
		}				
		return rv;
	}
*/


}