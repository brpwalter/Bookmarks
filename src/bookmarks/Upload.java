package bookmarks;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.ServletRequest;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;
import htmlparser.*;
import bookmarks.*;
import java.text.*;

import java.util.*;
import javax.servlet.*;
import pagebeans.*;

public class Upload{

	private static int userId;
	public static Object o99=null;
	private static boolean isFolder = false;

	public static String upload(ServletRequest request, ServletContext application){
		System.out.println("*Upload.upload");
		String source = request.getParameter("file");
		System.out.println("Upload. source: "+source);
		File image=null;
		String imageName = request.getParameter("file.filename");

		if (source!=null) image = new File(source);

		String newImageName=null;

		if(image!=null && image.length()>0) {
			String suffix = (imageName!=null && imageName.lastIndexOf(".")>0)?(imageName.substring(imageName.lastIndexOf("."), imageName.length())).toLowerCase():".err";
			newImageName = new Date().getTime() + suffix;
			File f = new File((application.getRealPath("/htmlfiles/")+ newImageName));
			Upload.copy(image,f);
			//if(image.exists()) image.delete();

		}
		return newImageName;
	}

	public static boolean copy( File source, File dest ) {

		int i = 0;
		byte[] fbuf = new byte[4096];

		try {
			FileInputStream in = new FileInputStream( source );
			FileOutputStream out = new FileOutputStream( dest );
			while((i=in.read(fbuf))!=-1) {
				out.write(fbuf, 0, i);
			}
			out.close();
			in.close();
		}
		catch(Exception ignored) {
			return false;
		}

		return true;
	}			

	public static void parseHTML(String pfad, int allocation) throws Exception{

		System.out.println("parseHTML");

		boolean folder=true;
		boolean bookmark=false;
		//String folderId = request.getParameter("foId");
		Folder f=null;
		Bookmark b=null;
		HTMLTokenizer ht = new HTMLTokenizer(pfad);
		AttributeList a1=new AttributeList ();
		Enumeration e2=a1.names();
		Enumeration e = ht.getTokens();
		int zaehler=1;
		int lastId=1;
		if (e==null) System.out.println("e==0");
		while (e.hasMoreElements()){
			Object o=e.nextElement();
			if (folder)  f=new Folder();
			if (bookmark) b=new Bookmark();

			System.out.println("Element: "+o.toString());
			if ((o) instanceof TagToken){ 

				System.out.print("@tagt@"); 
				//if (!(o.toString()).equals ("</h3>")) 


			//	if (isFolder)
			//		Folder.addFolder(String.valueOf(o.toString()), allocation,userId,1,userId);

				if ((o.toString()).startsWith("<h3")){
					isFolder = true;
					folder = true;
					
				}
					

				TagToken tagtoken=new TagToken(o.toString()); 
				System.out.println ("�� "+o.toString()+" ��"); 
				System.out.println ("2��2 "+tagtoken.getName()+" 2��2");
				if ((tagtoken.getName()).startsWith("<h3")) {
					System.out.println("Folder: "+(tagtoken.getName())+" , "+o);
					//Folder.addFolder(String.valueOf(o.toString()), allocation,userId,1,userId);
					o99=o;
					lastId=zaehler;
					++zaehler;
					folder=true;
					if (f==null) f=new Folder();

					System.out.println ("(�~H3~#)"); 
					AttributeList al4=tagtoken.getAttributes ();
					Enumeration e3=al4.names();
					while(e3.hasMoreElements()){
						Object o3=e3.nextElement();
						String value3=al4.get(String.valueOf(o3.toString()));
						//	DateFormat df2 = DateFormat.getDateInstance(DateFormat.LONG, Locale.GERMAN);

						//	java.sql.Date d2=(java.sql.Date)df2.parse(value3);
						//	if ((o3.toString()).equals ("add_date")) f.setCreationDate(d2);

						System.out.println(" ## "+o3.toString()+" ##"+value3+" ## ");
					}
					if ((o) instanceof TextToken){ TextToken tt=new TextToken(); System.out.print("*tt*: "+tt.getText()+" - "); f.setFoName(tt.getText());}



				}
				else if ((tagtoken.getName()).equals("<a")) {
					bookmark=true;

					if (b==null) b=new Bookmark();

					System.out.println ("(�~a~#)"); 
					AttributeList al6=tagtoken.getAttributes ();
					Enumeration e4=al6.names();
					while(e4.hasMoreElements()){
						Object o4=e4.nextElement();
						String value4=al6.get(String.valueOf(o4.toString()));
						//	DateFormat df = DateFormat.getDateInstance(DateFormat.LONG, Locale.GERMAN);

						//java.sql.Date d4=(java.sql.Date)df.parse(value4);
						//	if ((o4.toString()).equals ("add_date")) b.setCreationDate(d4);
						if ((o4.toString()).equalsIgnoreCase ("HREF")){ b.setUrl(value4); System.out.println("BmURL= "+b.getUrl());}

						System.out.println(" ## "+o4.toString()+" ##"+value4+" ## ");

					}
					//if ((o) instanceof TextToken){ System.out.print("*tt*");TextToken tt=new TextToken(); b.setFoName(tt.getText());}

				}
			}
			if ((o) instanceof TextToken){ 
				System.out.print("*tt*"); 
				TextToken tt=new TextToken(); 
				
				if (isFolder){
					Folder.addFolder(String.valueOf(o.toString()), allocation,userId,1,userId);
					isFolder = false;
				}
					


				if (folder){ 
					System.out.println ("texttoken: folder=true");  
					f.setFoName(o.toString()); 
					System.out.println("Texttoken: "+f.getFoName()); 
					System.out.println("=======");
					System.out.println("??Fo: "+f.toString());
					System.out.println("========"); 
					folder=false;
				}
				else if (bookmark) { 
					System.out.println ("texttoken: bookmark=true"); 
					b.setBmName(o.toString()); 
					System.out.println ("Texttoken: "+b.getBmName()); 
					System.out.println("=======");
					System.out.println("??Bm: "+b.toString());
					System.out.println("========"); 
					Bookmark.addBookmark(b.getBmName(),b.getUrl(),1, 0, 1,lastId); 
					bookmark=false;

				}

				//if (bookmark) Bookmark.addBookmark(b.getBmName(),b.getUrl(),1, 0, 1,lastId); bookmark=false;
				}
			//addBookmark(String bmName,String url,int rating, int accessright, int coId,int foId)
			else if (folder) {  
				//folder=false; 
				}
			//addFolder(String foName,int allocation,int rootId,int coId,int userId)
			System.out.println("================================");

		}
		//System.out.println("??userId: "+userId);
	}	


}