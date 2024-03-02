package greek;

// import java.util.Date;
import java.sql.Date;
import java.util.List;
import java.util.LinkedList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import sql.ConnectionProvider2;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Iterator;

public class Greek {
	private static final String CNAME = "Greek";
	
	private int Id;
	private String GrName;
	private String dtName;
	private String geschlecht;
	private String deklination;
	private String konjugation;
	private String fremdwort;
	private String NeugrName;
	private String Neugrdt;

public Greek(){}	
/**
  * Konstruktor, erstellt aus dem Resultset eine Bookmark mit Id, Name, Url, Rating,
  * Zugangsrecht, Datum der Erzeugung und des Zugriffs, Zugriffshäufigkeit,
  * Comment-Id
  * @param rs       ResultSet aus Datenbankabfrage*/
  
	public Greek(ResultSet rs) throws SQLException {
		this.Id = rs.getInt ("id");
		this.GrName = rs.getString("griechisch_wort");
		this.dtName =rs.getString("deutsch_wort");
		this.NeugrName = rs.getString("neugriechisch");
		this.Neugrdt = rs.getString("neugrdt");
		this.geschlecht = rs.getString("geschlecht");
		this.deklination = rs.getString("deklination");
		this.konjugation = rs.getString("konjugation");
		this.fremdwort = rs.getString("Fremdwort");
		
	}


	
	
/**
  * Holt einzelne Bookmark anhand ihrer Id aus der Datenbank
  * @param bmId     Id der Bookmark, der aus der Datenbank geholt werden soll 
  * @return         Bookmark
  */	
	public static Greek getById(int Id){
		String mName = CNAME + ".getById(int bmId): ";
		Greek b = null;
		Connection conn = null;
		try{
			conn = ConnectionProvider2.getConnection();
			String sql = "SELECT * FROM griechisch WHERE id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1,Id);
			ResultSet rs = pstmt.executeQuery();
			if(rs!=null && rs.next()) b = new Greek(rs);		
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
  * Holt komplette Bookmark-Liste aus der Datenbank
  * @return      Liste aller Bookmarks
  */	
	public static List getList(){
		String mName = CNAME + ".getList(): ";
		List bl = new LinkedList();
		Connection conn = null;
		try{
			conn = ConnectionProvider2.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM griechisch;");
			if(rs!=null){
				while(rs.next()) bl.add(new Greek(rs));
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
	
	// hinzugefügt bw

	
/**
  * Holt Liste der in einem bestimmten Ordner enthaltenen Bookmarks aus der Datenbank
  * @param foId    Id des Ordners, der die Bookmarks enthält 
  * @return        Liste aller Bookmarks aus einem bestimmten Ordner
  */	
	public static List getListByGreekId(int Id){
		String mName = CNAME + ".getListByGreekId(int Id): ";
		List boLi = new LinkedList();
		Connection conn = null;
		try{
			conn = ConnectionProvider2.getConnection();
			String sql = "SELECT griechisch FROM id=? ORDER BY griechisch_wort";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1,Id);
			ResultSet rs = pstmt.executeQuery();
			if(rs!=null) {
				while(rs.next()) boLi.add(new Greek(rs));
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
  * Fügt Bookmark zur Datenbank hinzu und gibt Id der eingefügten Bookmark zurück
  * @param bmName             String, der den Namen der eingefügten Bookmark enthält
  * @param url                String, der die komplette URL der eingefügten Bookmark enthält 
  * @param rating             Wert zwischen 1 und 5, der die Bewertung der eingefügten Bookmark ausdrückt
  * @param accessright        Wert, der das Zugangsrecht der eingefügten Bookmark ausdrückt (0=nur ich; 1=alle) 
  * @param coId               Id des Kommentars, der zur eingefügten Bookmark gehört 
  * @param foId               Id des Folders, in dem sich die eingefügte Bookmark befindet
  * @return                   Id der eingefügten Bookmark
  */

	
public static int addGreek(String GrName,String dtName,String geschlecht,String deklination,String konjugation,String fremdwort, String NeugrName, String Neugrdt){
		System.out.println("Greek:addGreek: "+GrName);
		Connection conn = null;
		int insertedId = -1;
		try{
			conn = ConnectionProvider2.getConnection();
			
			String sql = "INSERT INTO griechisch(griechisch_wort,deutsch_wort,geschlecht,deklination,konjugation,Fremdwort,neugriechisch,neugrdt) VALUES(?,?,?,?,?,?,?,?)";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1,GrName);
			pstmt.setString(2,dtName);
			pstmt.setString(3,geschlecht);
			pstmt.setString(4,deklination);
			pstmt.setString(5,konjugation);
			pstmt.setString(6,fremdwort);
			pstmt.setString(7,NeugrName);
			pstmt.setString(8,Neugrdt);
			

			pstmt.executeUpdate();
			
			sql = "SELECT LAST_INSERT_ID() as Id";
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if( (rs!=null) && (rs.next()) ) {
				insertedId = rs.getInt("Id");	
			}
			
			
			
		} catch(Exception ex){ System.out.println("Exception: Greek: addGreek: "+ex);
		} finally{
			try{
				if(conn!=null) conn.close();
			} catch(Exception ex2){
			}
		}
		return insertedId;
	}	




	
	
/**
  * Gibt den Namen einer Bookmark aus
  * @return      Name der Bookmark
  */	
	public String getfremdwort(){
		return fremdwort;
	}

public void setneugrName(String url){
	this.NeugrName=NeugrName;
}
	

	
/**
  * Gibt einen Wert aus, der die Bewertung einer Bookmark ausdrückt
  * @return      Bewertung der Bookmark
  */	
	public String getdtName(){
		return dtName;
	}

	public void setgrName(String grName){
		this.GrName=GrName;
	}	
		
/**
  * Gibt einen Wert aus, der das Zugangsrecht einer Bookmark ausdrückt
  * @return      Zugangsrecht der Bookmark
  */	
	public String getdeklination(){
		return deklination;
	}
	
public String getkonjugation(){
		return konjugation;
	}		
/**
  * Gibt das Erstellungsdatum einer Bookmark aus
  * @return      Erstellungsdatum der Bookmark
  */	
	public String getgeschlecht(){
		return geschlecht;
	}

	
/**
  * Gibt das letzte Zugriffsdatum einer Bookmark aus
  * @return      Zugriffsdatum der Bookmark
  */	
	public String getneugrName(){
		return NeugrName;
	}
			
/**
  * Gibt die Zugriffshäufigkeit auf eine Bookmark aus
  * @return      Zugriffshäufigkeit auf die Bookmark
  */	
	public String getgrName(){
		return GrName;
	}
	


/**
  * Gibt die Id einer Bookmark aus
  * @return      Id der Bookmark
  */	
	public int getId(){
		return Id;
	}
	

}