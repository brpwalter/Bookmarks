package pagebeans;

import java.sql.*;
import sql.ConnectionProvider;
import java.text.*;
import bookmarks.*;
import enm.EasySendEmail;
import javax.servlet.ServletRequest;
import javax.servlet.http.*;
import javax.servlet.*;
import org.apache.xalan.xslt.*;
import org.w3c.dom.Node;
import org.w3c.dom.Document;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.traversal.*;
import org.apache.xerces.parsers.DOMParser;
import org.xml.sax.SAXException;
import  org.apache.xerces.dom.DocumentImpl;
import java.io.*;
import java.lang.*;
import java.util.List;
import java.util.LinkedList;
import org.apache.xml.serialize.XMLSerializer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.stream.StreamSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerConfigurationException;
import javax.servlet.http.*;

public class BookmarkBean extends AbstractPageBean {

	private Bookmark bo;
	private Folder fo;
	private Comment co;
	private User us;
	private Navigation nav;
	public java.util.Random rand=new java.util.Random();
	public int i=0;
	private String randompass = "";
	private int bmId;
	private String bmName;
	private String url;
	private int rating;
	private int accessright;
	private int accessfrequency;
	private int copy;
	private boolean correct;
	String commentText="";
	
	private int foId;
	private String foName;
	private int allocation;
//	private int level;
	private int open;

	private int coId;
	private String coText;
	
	private int userId;
	private String loginname;
	private String password;
	private String passwordold;
	private String passwordnew1;
	private String passwordnew2;
	private String email;
	private String lastname;
	private String firstname;
	private int locked;
	
	private Folder f2;
	List l=new LinkedList();
	
	private String fi;
	private String bmAnzeige;
	private String foAnzeige;
	private int msg;
	private ServletContext application;
	 
// regelt die Kommunikation zwischen Klassendateien und JSP-Seiten	
	public void validate(){
		if("addbookmark".equals(getPageAction())){
			coId = Comment.addComment(coText);
			Bookmark.addBookmark(bmName,url,rating,accessright,coId,foId);
		} else if("copyBookmark".equals(getPageAction())){
			System.out.println("copyBookmark:");
				
//			Folder.editFolder(foId,foName,allocation,0,coId);
			commentText="Das ist eine Kopie von"+foId;
			coId = Comment.addComment(commentText);
			Bookmark.copyBookmark(bmName, url,rating,accessright,coId,foId);
			
		} else if("editbookmark".equals(getPageAction())){
			if(coId>0){	// wenn schon ein Kommentar existiert
				if(coText==null||coText.equals("")){	// und wenn dieser nach der Änderung leer ist
					Comment.deleteComment(coId);			// dann lösche den Kommentar
					coId=0;									// und setzte die Kommentar-Id (in tb_bookmark) auf Null
				} else Comment.editComment(coText,coId);// wenn der Kommentar nicht leer ist, editiere ihn
			} else {	// wenn es keinen Kommentar gab
				if(coText!=null) coId = Comment.addComment(coText);	// und Text eingegeben wurde, dann add.Comment()
																		// und übergebe neue id an coId
			}			// ansonsten tu nix
			Bookmark.editBookmark(bmId,bmName,url,rating,accessright,coId,foId);
		} else if("deletebookmark".equals(getPageAction())){
			Bookmark.deleteBookmark(bmId,coId);
//			Comment.deleteComment(coId);		
			setTargetPage("searchbookmrechts.jsp");	
			
			} else if("showtopten".equals(getPageAction())){
			

				Bookmark.showTopTen(userId);
		
			
		} else if("addfolder".equals(getPageAction())){
			System.out.println("addFolder:");
			
//			Folder.addFolder(foName,allocation,0,userId,coId);
			
			l=Folder.getRootListByUserId(userId);
			if (l.size()>0) f2=(Folder)l.get(0);
			int rootId2=f2.getFoId();
			Folder.addFolder(foName,allocation,rootId2,coId,userId);
			//Navigation.invalidate(getSession());
		} else if("addroot".equals(getPageAction())){
			System.out.println("addroot:");
				Folder.addRootFolder("Root",userId,coId);
			

		} else if("editfolder".equals(getPageAction())){
			if(coId>0){
				if(coText==null||coText.equals("")){
					Comment.deleteComment(coId);
					coId=0;
				} else Comment.editComment(coText,coId);
			} else {
				if(coText!=null) coId = Comment.addComment(coText);
			}
//			Folder.editFolder(foId,foName,allocation,0,coId);
			Folder.editFolder(foId,foName,allocation,coId);
		} else if("copyFolder".equals(getPageAction())){
			System.out.println("copyFolder:");
				
//			Folder.editFolder(foId,foName,allocation,0,coId);
			commentText="Das ist eine Kopie von"+foId;
			coId = Comment.addComment(commentText);
			l=Folder.getRootListByUserId(userId);
			if (l.size()>0) f2=(Folder)l.get(0);
			int rootId3=f2.getFoId();
			Folder.copyFolder(foName, allocation, rootId3, userId,coId);
			
		} 		
		else if("deletefolder".equals(getPageAction())){
	//		if(Folder.getById(foId).getBoList()!=null) Bookmark.deleteBmByFoId(foId);
			Folder.deleteFolderComplete(foId);	
		} else if("upload".equals(getPageAction())){
	//		if(Folder.getById(foId).getBoList()!=null) Bookmark.deleteBmByFoId(foId);
			Upload.upload(getRequest(), application);	
		}  
		else if("adduser".equals(getPageAction())){
			correct = User.addUser(loginname,password,email,lastname,firstname);
			if (correct == false){   
				setTargetPage("startindex.jsp"); 
				setMessage("Login-Name existiert bereits");
				
				}
			else { 
			switch (Login.checkByName(getRequest())) {
			case 0: { setTargetPage("index.jsp? target=\"_parent\"");
						break; }
			default: { setTargetPage("startindex.jsp? target=\"_parent\"");
						setMessage("Login-Name existiert bereits");
						break; }
			}
		}
		} else if("edituser".equals(getPageAction())){
			User.editUser(userId,loginname,password,email,lastname,firstname);
		} else if("deleteuser".equals(getPageAction())){
			User.deleteUser(userId,1);
		} else if("editpassword".equals(getPageAction())){
			correct = User.editPassword(userId,passwordold,passwordnew1,passwordnew2);
			if (correct == false) setMessage("Das alte Passwort ist falsch!");
		} else if("login".equals(getPageAction())){
			switch (Login.checkByName(getRequest())) {
			case 0: { setTargetPage("index.jsp");
						break; }
			case 1: { setTargetPage("startindex.jsp");
						setMsg(1);
						break; }
			case 2: { setTargetPage("startindex.jsp");
						setMsg(2);
						break; }
			case 3: { setTargetPage("startindex.jsp");
						setMsg(3);
						break; }
			}
		} else if("logout".equalsIgnoreCase(getPageAction())){
        	Login.logOut(getRequest());
			setTargetPage("startindex.jsp? target=_top");
		}
		else if("checkuseremail".equalsIgnoreCase(getPageAction()))
		{
			if (Login.checkByEmail(getRequest())!=1){
				setTargetPage("passwortvergessen.jsp");
				setMsg(2);
		}
		else{
			java.util.Random rand=new java.util.Random();
			char[] passchars = {'a','b','c','d','e','f','g','h','k','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','K','L','M','N','P','Q','R','S','T','U','V','W','X','Y','Z','0','1','2','3','4','5','6','7','8','9'};
			for (int i=0;i<8;i++){
				randompass += passchars[rand.nextInt(56)];
			}
			User.editPassword(loginname,email,randompass);
			EasySendEmail.sendMail(email,"Bookmark-Tool","Ihr neues Passwort!","Hallo "+loginname+"!\n Ihr neues Passwort lautet: "+randompass+"\n Bitte aendern sie das Password nach dem Login!\n\n \n Ihr Bookmark-Tool Team!");
			setTargetPage("startcontent.jsp");
			setMsg(1);
			
		}
		
		}
			
	}
	
	public static String getPath(ServletRequest request, ServletContext application, String file){
		//String datei="bmkfolders.xml";
		//String datei2="bmkfolders.xslt";
		//String datei3="bmkfolders.html";
		String s = application.getRealPath("/xmlfiles/")+ file;
		System.out.println("**BookmarkBean: "+s);
		//String style=application.getRealPath("/xslfiles/")+ datei2;
		//String outFile=application.getRealPath("/htmlfiles/")+ datei2;
		return s;
	}
	public static String getPath2(ServletRequest request, ServletContext application, String file){
		//String datei="bmkfolders.xml";
		//String datei2="bmkfolders.xslt";
		//String datei3="bmkfolders.html";
		String s = application.getRealPath("/htmlfiles/")+ file;
		System.out.println("**BookmarkBean: "+s);
		//String style=application.getRealPath("/xslfiles/")+ datei2;
		//String outFile=application.getRealPath("/htmlfiles/")+ datei2;
		return s;
	}
	   /**
     * Diese Methode konvertiert eine XML-Datei durch ein XSL-Stylesheet. DAs Ergebnis der Konvertierung wird auf den PrintWriter geschrieben.
     * @param source
     */
  public static void xml2html(String source, String style, String outFile)
                   //throws java.io.IOException, java.net.MalformedURLException
				    throws TransformerException, TransformerConfigurationException, 
           FileNotFoundException, IOException
    {
                //   try {
				   TransformerFactory tFactory = TransformerFactory.newInstance();
				   Transformer transformer = tFactory.newTransformer(new StreamSource(style));
				   transformer.transform(new StreamSource(source), new StreamResult(new 		FileOutputStream(outFile)));

           /* org.apache.xalan.xslt.XSLTProcessor processor = XSLTProcessorFactory.getProcessor();
                org.apache.xalan.xslt.XSLTResultTarget xrt=new org.apache.xalan.xslt.XSLTResultTarget(outFile);
            processor.process(new XSLTInputSource(source),
                new XSLTInputSource(style),
            xrt);
        } catch (SAXException e) {
         System.err.println (e);
            }*/
    }

	public boolean isCorrect(){	
		return correct;						
	}
	
	/**
    * erzeugt ein neues XMLDocument mit rootname als einziges XML-Element. 
    */
 /*  public Document createNewXMLDocument(String rootName) {
          Document Result=new DOMImplementation();
          Element root = Result.createElement(rootName);
          Result.appendChild( root );
          return Result;
   }*/


   /**
    * speichert das XML-Dokument doc als Datei unter filename ab. 
    */
  /* public boolean saveXMLDocument(Document doc,String filename) {
 	// Document doc ist das dom Dokument, das in filename gespeichert wird
 	try {
    	FileWriter w=new FileWriter(filename);
    	XMLSerializer xs=new XMLSerializer(w,null);
    	xs.serialize(doc);
   		w.flush();
   		w.close();
 	} catch (java.io.IOException e) {
         System.err.println (e);return false;
    }
    return true;

	}*/
// Get- und Set-Methoden
	
	public void setBmId(int bmId){
		this.bmId = bmId;
	}
	
	public void setBmName(String bmName){
		this.bmName = bmName;
	}
	
	public void setUrl(String url){
		this.url = url;
	}
	
	public void setRating(int rating){
		this.rating = rating;
	}
	
	public void setAccessright(int accessright){
		this.accessright = accessright;
	}
	
	/*public void setAccessfrequency(int accessfrequency){
		this.accessfrequency = accessfrequency;
	}     NICHTMEHR BENÖTIGT, DA Accessfrequency AUTOMATISCH GENERIERT    */
	
	public void setCopy(int copy){
		this.copy = copy;
	}
	
	public void setFoId(int foId){
		this.foId = foId;
	}
	
	public void setFoName(String foName){
		this.foName = foName;
	}

	public void setAllocation(int allocation){
		this.allocation = allocation;
	}
	
/*	public void setLevel(int level){
		this.level = level;
	}
*/
	public void setOpen(int open){
		this.open = open;
	}
	
	public void setCoId(int coId){
		this.coId = coId;
	}
	
	public void setCoText(String coText){
		this.coText = coText;
	}
	
	public void setUserId(int userId){
		this.userId = userId;
	}
	
	public void setLoginname(String loginname){
		this.loginname = loginname;
	}
	
	public void setPassword(String password){
		this.password = password;
	}
	
	public void setLastname(String lastname){
		this.lastname = lastname;
	}
	
	public void setFirstname(String firstname){
		this.firstname = firstname;
	}
	
	public void setEmail(String email){
		this.email = email;
	}
	
	public void setLocked(int locked){
		this.locked = locked;
	}
	
	public int getLocked(){
		return locked;
	}	
	
	public int getBmId(){
		return bmId;
	}	
	
	public int getFoId(){
		return foId;
	}
	
	public int getCoId(){
		return coId;
	}
	
	public int getUserId(){
		return userId;
	}
	
	public void setUser(User us){
		this.us = us;
	}
	
	public void setFolder(Folder fo){
		this.fo = fo;
	}
	
	public void setBookmark(Bookmark bo){
		this.bo = bo;
	}
	
	public void setFi(String fi){
		this.fi = fi;
	}
	
	public String getFi(){
		return fi;
	}
	
	public String getBmAnzeige(){
		return bmAnzeige;
	}
	
	public void setBmAnzeige(String bmAnzeige){
		this.bmAnzeige = bmAnzeige;
	}
	
	public String getFoAnzeige(){
		return foAnzeige;
	}
	
	public void setFoAnzeige(String foAnzeige){
		this.foAnzeige = foAnzeige;
	}
	
	public void setPasswordold(String passwordold){
		this.passwordold = passwordold;
	}
	
	public void setPasswordnew1(String passwordnew1){
		this.passwordnew1 = passwordnew1;
	}
	
	public void setPasswordnew2(String passwordnew2){
		this.passwordnew2 = passwordnew2;
	}
	
	public String getPasswordold(){
		return passwordold;
	}
	
	public String getPasswordnew1(){
		return passwordnew1;
	}
	
	public String getPasswordnew2(){
		return passwordnew2;
	}

	public int getMsg(){
		return msg;
	}
	
	public void setMsg(int msg){
		this.msg = msg;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getLastname(){
		return lastname;
	}
	
	public String getFirstname(){
		return firstname;
	}

}