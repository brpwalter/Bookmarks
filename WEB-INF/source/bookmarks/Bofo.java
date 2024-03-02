package bookmark;

import java.util.List;
import java.util.LinkedList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import sql.ConnectionProvider;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.Iterator;

public class Bofo {
	private static final String CNAME = "Bofo";
	
	private int bofoId;
	private int bmId;
	private int foId;
	
/**
  * Konstruktor, erstellt aus dem Resultset eine Bofo-Objekt mit Id, Bookmark-Id und Ordner-Id
  * @param rs       ResultSet aus Datenbankabfrage
  */
	public Bofo(ResultSet rs) throws SQLException {
		this.bofoId = (rs.getInt("vk_bookmark_folder_id")!=0)?rs.getInt ("vk_bookmark_folder_id"):0;
		this.bmId = (rs.getInt("tb_bookmark_id")!=0)?rs.getInt("tb_bookmark_id"):0;
		this.foId = (rs.getInt("tb_folder_id")!=0)?rs.getInt("tb_folder_id"):0;
	}
	
/**
  * Holt einzelnes Bofo-Objekt (=Verknüpfung zwischen Bookmark und Ordner) anhand seiner Id aus der Datenbank
  * @param bofoId     Id des Bofo-Objekts, das aus der Datenbank geholt werden soll 
  * @return           Bofo-Objekt
  */	
	public static Bofo getById(int bofoId){
		String mName = CNAME + ".getById(int bofoId): ";
		Bofo bf = null;
		Connection conn = null;
	
		try{
			conn = ConnectionProvider.getConnection();
			String sql = "SELECT * FROM vk_bookmark_folder WHERE vk_bookmark_folder_id=?";
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1,bofoId);
			ResultSet rs = pstmt.executeQuery();
			if(rs!=null && rs.next()) bf = new Bofo(rs);
			if(rs!=null) rs.close();	
		}
		catch (Exception ex1){
					System.out.println(mName + ex1);
		}
		finally{
			try{
				if(conn!=null) conn.close();
			} 
			catch(Exception ex2){
				System.out.println(mName + ex2);
			}
		}		
		return bf;
	}
	
/**
  * Holt komplette Liste der Bookmark-Folder-Verknüpfungen aus der Datenbank
  * @return      Liste aller Bookmark-Folder-Verknüpfungen
  */	
	public static List getList(){
		String mName = CNAME + ".getList(): ";
		List bfl = new LinkedList();
		Connection conn = null;
		try{
			conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM vk_bookmark_folder;");
			if(rs!=null){
				while(rs.next()) bfl.add(new Bofo(rs));
				rs.close();
			}
		} 
		catch (Exception ex1){
					System.out.println(mName + ex1);
		}
		finally{
			try{
				if(conn!=null) conn.close();
			} 
			catch(Exception ex2){
				System.out.println(mName + ex2);
			}
		}
		return bfl;
	}
	
	
	
/**
  * Holt komplette Liste der Bookmark-Folder-Verknüpfungen eines bestimmten Ordners aus der Datenbank
  * @param foId    Id des Ordners, dessen Verknüpfungen ausgegeben werden sollen
  * @return        Liste aller Bookmark-Folder-Verknüpfungen
  */	
	public static List getList(int foId){
		String mName = CNAME + ".getList(): ";
		List bfl = new LinkedList();
		Connection conn = null;
		try{
			conn = ConnectionProvider.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM vk_bookmark_folder WHERE tb_folder_id="+foId+";");
			if(rs!=null){
				while(rs.next()) bfl.add(new Bofo(rs));
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
		return bfl;
	}

/**
  * Ursprüngliche Methode zum Aufbau des Ordnerbaums; wird nicht mehr benötigt; nur zur Ansicht aufbewahrt
  * @param allocation    Id eines übergeordneten Ordners
  * @param einrueck      Wert, der die Einrücktiefe untergeordneter Ordner bestimmt
  * @return              String, der in HTML den Ordnerbaum darstellt
  */	
/*	public static String rekTraverse(int allocation, int einrueck){
		List fl = new LinkedList();
		fl = Folder.getList(allocation);
		List bl = new LinkedList();
		bl = Bookmark.getList();
		List bfl = new LinkedList();
		String rv = "";

		for(Iterator it = fl.iterator();it.hasNext();){
			Folder fo = (Folder) it.next();
			if(fo==null) continue;
			int folderId = fo.getFoId();
			int open = fo.getOpen();
			for(int i = 0; i<einrueck; i++)
				rv = rv + "&nbsp;&nbsp;&nbsp;";
			rv = rv + " <input type=\"checkbox\" name=\"markiert\" value=\"markiert\"> <IMG SRC= \"/images/TreePics/menu_ordner_auf.gif\" WIDTH=18 HEIGHT=18  BORDER=0 VSPACE=0 HSPACE=0>" + 
			 fo.getFoName() + "&nbsp;&nbsp;&nbsp;" + "<a href=\"editformularfolder.jsp\" target=\"content\">B</a>" + "&nbsp;&nbsp;&nbsp;" + "<a href=>L</a>" + "<br>";
			if(open==1){
				bfl = getList(foId);
				if (!bfl.isEmpty()){
					for(Iterator ite = bfl.iterator();ite.hasNext();){
						Bofo bof = (Bofo) ite.next();
						if(bof==null) continue;
						int bofbmId = bof.getBmId();
						for(Iterator ir = bl.iterator();ir.hasNext();){
							Bookmark bo = (Bookmark) ir.next();
							if(bo==null) continue;
							int bmId = bo.getBmId();
							if(bofbmId==bmId){
								for(int i = 0; i<einrueck; i++)
									rv = rv + "&nbsp;&nbsp;&nbsp;";	
								rv = rv + "&nbsp;&nbsp;&nbsp;" + " <input type=\"checkbox\" name=\"markiert\" value=\"markiert\">" + "<img src = \"/images/TreePics/menu_doc_url.gif\"WIDTH=18 HEIGHT=18 BORDER=0 VSPACE=0 HSPACE=0> <a href=\"" + bo.getUrl() + " target=\"_blank\">" + bo.getName() + "</a>" + "&nbsp;&nbsp;&nbsp;" + "<a href=\"/editformularbookmark.jsp?bookmarkId=" + bmId + "\" target=\"content\">B</a>" + "&nbsp;&nbsp;&nbsp;" + "<a href=\"/delformularbookmark.jsp?bookmarkId=" + bmId + "\" target=\"content\">L</a>" + "<br>";
							}		
						}
					}
				}
				rv = rv + rekTraverse(folderId, einrueck+1);	
			}	
		}				
		return rv;
	}
*/
/*	

// beide nachfolgenden Methoden inzwischen in die Folder.java gewandert und damit wahrscheinlich hier überflüssig

	public static String auswahlMenueBaum(int userId, int allocation, int einrueck){
		return auswahlMenueBaum(userId, allocation, einrueck, -1);
	}
	
	public static String auswahlMenueBaum(int userId, int allocation, int einrueck, int foId){
		List fl = new LinkedList();
		fl = Folder.getChildrenByUserId(userId,allocation);
//		List bl = new LinkedList();
//		bl = Bookmark.getList();
//		List bfl = new LinkedList();
		String rv = "";
		for(Iterator it = fl.iterator();it.hasNext();){
			Folder fo = (Folder) it.next();
			if(fo==null) continue;
			int folderId = fo.getFoId();
			int open = fo.getOpen();
			String ein = "";
			String selected = (foId==folderId)?" selected":"";
			for(int i = 0; i<einrueck; i++)
				ein = ein + "&nbsp;&nbsp;&nbsp;&nbsp;";
			rv = rv + "<option value=\"" + fo.getFoId() + "\"" + selected + ">" + ein + fo.getFoName() + "</option>";
			if(open==1){
				rv = rv + auswahlMenueBaum(userId,folderId,einrueck+1,foId);	
			}
		}
		return rv;
	}
*/	

/**
  * Gibt die Id einer Bookmark aus
  * @return      Id der Bookmark
  */	
	public int getBmId(){
		return bmId;
	}	
		
/**
  * Gibt die Id eines Ordners aus
  * @return      Id des Ordners
  */	
	public int getFoId(){
		return foId;
	}

/**
  * Gibt die Id einer Bookmark-Folder-Verknüpfung aus
  * @return      Id der Bookmark-Folder-Verknüpfung
  */	
	public int getBofoId(){
		return bofoId;
	}
}