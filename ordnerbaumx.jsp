<%@page import="javax.servlet.http.*,java.util.*,sql.ConnectionProvider,bookmarks.*,pagebeans.*,java.io.*"%>
<jsp:useBean id="_pagebean" scope="request" class="pagebeans.BookmarkBean" type="pagebeans.BookmarkBean" >
</jsp:useBean>
<!--
<%@ include file="schutz.jsp"%>
 -->
<%
	// Deklaration der benötigten Variablen
	int userId=1;
	Folders current=null;
	Folders current2=null;
	String rootName="";
	String realpath = application.getRealPath("/xml/");
	User u = new User();//(User) session.getAttribute("user");
	out.println("User: "+u);
	if (u != null) userId = u.getUserId();
	out.println("userid: "+userId);
	System.out.println("??ordnerb.: userid: "+userId);
	DataOutputStream out2=null;
	String fi = (_pagebean.getFi()!=null)?_pagebean.getFi():"_";
	String bmAnzeige = _pagebean.getBmAnzeige();
	String foAnzeige = _pagebean.getFoAnzeige();
	boolean showBm = (bmAnzeige==null || bmAnzeige.equals("Bmja"));
	boolean showFo = (foAnzeige!=null && foAnzeige.equals("auf"));
	String imgsrc1 = "";
	String imgsrc2 = "";
	String fiStr1 = "";
	String fiStr2 = "";
	int pathIndex = 1;
	boolean test3=false;
	int bmId = -1;
	int rootId=-1;
	Stack stapel=new Stack();
	String header="<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>";
	String datei="_bmkfolders.xml";
	String datei2="bmkfolders.xsl";
	String datei3="bmkfolders.html";
	String dtd="<!DOCTYPE wurzel [ <!ELEMENT wurzel (rootfolder+,folder*,bookmarks*)><!ELEMENT rootfolder (folder*,bookmarks*)> <!ELEMENT folder (folder*,bookmarks*)>  <!ELEMENT bookmarks (#CDATA)> ]>";
	String s1=BookmarkBean.getPath(request, application,datei);
	String style2=BookmarkBean.getPath(request, application,datei2);
	String outFile=BookmarkBean.getPath(request, application,datei3);
	boolean test2=false;
	
	//File f = new File((getContext().getRealPath("")+ file));
	try { out2=new DataOutputStream(new BufferedOutputStream(new FileOutputStream(s1))); } catch (Exception e){}
	try { out2.writeBytes(header);}catch(Exception e){}
	//try { out2.writeBytes(dtd);}catch(Exception e){}
	try { out2.writeBytes("<wurzel>");stapel.push(new String("</wurzel>"));}catch(Exception e){}  
	// Neuinitialisierung des Navigationsobjektes (=Erstellung Ordnerbaum)
	
	Navigation.invalidate(session);
	Navigation nav = Navigation.getInstance(session,0);
	//Navigation nav2=Navigation.getInstance(session,1);
	// Wurzelordner wird erstellt
	if (nav!= null) current=nav.getRoots(0);
	//if (nav2!= null) current2=nav2.getRoots(1);
	//Folder current = nav.getFirstroot();
	if (current!=null) rootId = current.getFoId();
	out.println("rootId= "+rootId);
	List rootList = new LinkedList();
	rootList.add(current);
	//rootList.add(current2);
	if (current!=null) { rootName = current.getFoName(); System.out.println ("ob1: "+rootName); }

	// Liste der Unterordner ("Kinder") des Wurzelordners wird erstellt 
	// und in Form eines Iterator-Objekts in einen Stack gepackt
	
	LinkedList stack = new LinkedList();
//	Iterator it = (current!=null)?current.getChildren().iterator():null;
	Iterator it = (current!=null)?rootList.iterator():null;
	stack.add(it);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
	<title>Bookmark - Baum</title>
	<LINK REL="Stylesheet" HREF="css/common-2c.css" TYPE="text/css">
	<script language="JavaScript">
	<!--
		function openClose(fiStr,spmStr){
			document.forms.ordnerFrm.fi.value=fiStr;
			document.forms.ordnerFrm.spm.value=spmStr; // Sprungmarke
			//document.forms.ordnerFrm.action="ordnerbaum.jsp#"+spmStr;
			document.forms.ordnerFrm.submit();
			return false;
		}
		
		function loadinfo(bmId){
			parent.frames['content'].location.href="/showbookmarkdetails.jsp?bmId";
		}
	// -->
	</script>
</head>
<%
	// Sprungmarke wird gesetzt
	
	String spm = request.getParameter("spm");
	String onload = (spm!=null)?" onLoad=\"location.href='#"+spm+"'\"":"";
%>
<body bgcolor="#DDEFFC"<%=onload%>>

<TABLE height="40px" BORDER="1" CELLPADDING="0" CELLSPACING="0" align="center" width="100%">
<form action="ordnerbaum.jsp" method="post" name="ordnerFrm">
<input type="hidden" name="fi" value="<%=fi%>">
<input type="hidden" name="spm" value="">
	<tr>
		<td>
		<table BORDER="1" CELLPADDING="0" CELLSPACING="0" align="center">
			<tr>
				<td>Alle Ordner&nbsp;&nbsp;&nbsp;</td>
				<td>
					<input type="radio" name="foAnzeige" value="auf" onClick="return openClose('<%=Folders.openAll(userId)%>','');"<%=((showFo && foAnzeige!=null)?" checked":"")%>>&ouml;ffnen
					<input type="radio" name="foAnzeige" value="zu" onClick="return openClose('','');"<%=((!showFo && foAnzeige!=null)?" checked":"")%>>schlie&szlig;en
				</td>
			</tr>

<!--		<tr>
				<td>Ordnerauswahl:</td>
				<td>
					<input type="radio" name="markiert" value="alle">alle Ordner
					<input type="radio" name="markiert" value="markiert">nur markierte Ordner
				</td>
			</tr>
-->
			<tr>
				<td>Bookmarks anzeigen?&nbsp;&nbsp;&nbsp;</td>
				<td>
					<input type="radio" name="bmAnzeige" value="Bmja" onClick="document.forms.ordnerFrm.submit();"<%=((showBm)?" checked":"")%>>ja
					<input type="radio" name="bmAnzeige" value="Bmnein" onClick="document.forms.ordnerFrm.submit();"<%=((!showBm)?" checked":"")%>>nein
				</td>
			</tr>			
		</table>
		</td>
	</tr>
</form>
</TABLE>


<!-- <TABLE BORDER="0" CELLPADDING="0" CELLSPACING="0"> -->
	<form action="ordnerbaum.jsp" method="post" name="frm">
<!--	<input type="hidden" name="pageAction" value="delete">	-->
	<input type="hidden" name="foId" value="">
<!-- </TABLE> -->

<!-- <DIV style="padding:0px;MARGIN-LEFT:0px;MARGIN-RIGHT:0px;OVERFLOW:auto;HEIGHT:84%;"> -->
<table border="1" cellpadding="0" cellspacing="0" width="100%">
<!--	 <tr>
		<td><IMG SRC="/bookmarks/images/TreePics/menu_root.gif"  WIDTH="18" HEIGHT="18" BORDER="0" align="left"><%=rootName%></td>
	</tr> -->
	<javascript:openClose('<%=Folders.openAll(userId)%>','') />
<%
	// Folder werden aus dem Stack ausgelesen
	
	
	while (stack.size()>0) {
		System.out.println("stacksize= "+stack.size());
		 it = (Iterator) stack.removeLast();
		
		while(it!=null && it.hasNext()) {
		//	System.out.println("itsize= "+it.size());
	 Folders fo = (Folders) it.next();
	 System.out.println("ordnerb.: "+fo.getFoName()+"***");
	
	if(fo==null) continue;
	
	// Tabellenzelle mit Anker, Ordnerbaum-Bildern und Folderinformationen wird "gebaut"
%>
	<tr><td><a name="<%=fo.getFoName().replaceAll(" ", "")%>"></a> 
<%
			int foId = fo.getFoId();
			System.out.println("ob.: foId: "+foId);
			for(int i = 1; i<stack.size(); i++){
			
				if(!((Iterator)stack.get(i)).hasNext()){
%>
			<img src="/bookmarks/images/TreePics/menu_leer.gif" WIDTH="18" HEIGHT="18" BORDER="0" align="left">
<%
				} else { 
%>						
			<img src="/bookmarks/images/TreePics/menu_linie.gif" WIDTH="18" HEIGHT="18" BORDER="0" align="left">
<%
				}
				
			}

			if (fo != null && fo.getChildren().size()>0 || fo.getBoList().size()>0){
			
				if(!it.hasNext()){
					if(fo.isOpen(fi)){
						imgsrc1 = "menu_minus_2";
						imgsrc2 = "menu_ordner_auf";
						fiStr1 = fi.replaceAll("_" + fo.getFoId() + "_" ,"_");
					} else {
						imgsrc1 = "menu_plus_2";
						imgsrc2 = "menu_ordner_zu";
						fiStr1 = fi + fo.getFoId() + "_" ;
					}
				} else {
					if(fo.isOpen(fi)){
						imgsrc1 = "menu_minus_1";
						imgsrc2 = "menu_ordner_auf";
						fiStr1 = fi.replaceAll("_" + fo.getFoId() + "_","_");
					} else {
						imgsrc1 = "menu_plus_1";
						imgsrc2 = "menu_ordner_zu";
						fiStr1 = fi + fo.getFoId() +  "_";
					}
				}
			}
			else{
				if(!it.hasNext()){
					if(fo.isOpen(fi)){
					if (fo.getAllocation()!=0){
						System.out.println("ob.:fo.getAllocation()!=0");
						imgsrc1 = "menu_was_3";
						imgsrc2 = "menu_ordner_auf";
						}
						else{
						
							System.out.println("root: ordner zu ");
							//imgsrc1 = "menu_was_3";
							imgsrc1= "menu_root";
						
						}
						test2=true;
						
					} else {
						
						imgsrc1 = "menu_was_3";
						imgsrc2 = "menu_ordner_zu";
						fiStr1 = fi + fo.getFoId() + "_" ;
					
						 
					}
				} else {
					if(fo.isOpen(fi)){
						imgsrc1 = "menu_was_3";
						imgsrc2 = "menu_ordner_auf";
						fiStr1 = fi.replaceAll("_" + fo.getFoId() + "_","_");
					} else {
						imgsrc1 = "menu_was_";
						imgsrc2 = "menu_ordner_zu";
						fiStr1 = fi + fo.getFoId() +  "_";
				}
			}
		}
		if (fo.getAllocation()==0){
		System.out.println("root: @@@@@@@ ");
				//imgsrc1 = "menu_plus_1";
				imgsrc1= "menu_root";
				//rootList.add(new Folder("Root"));
				fiStr1 = fi.replaceAll("_" + fo.getFoId() + "_" ,"_");
		}
		
%>			
			<a href="#" onClick="return openClose('<%=fiStr1 %>','<%=fo.getFoName().replaceAll(" ", "")%>');"><img src="/bookmarks/images/TreePics/<%=imgsrc1 %>.gif" WIDTH="18" HEIGHT="18" BORDER="0" align="left" title="Ordner öffnen/schließen"></a>
		 <% if (fo.getAllocation()!=0){ %> <img src="/bookmarks/images/TreePics/<%=imgsrc2 %>.gif" ALT="<%=fo.getCreationDate()%> "WIDTH="18" HEIGHT="18" BORDER="0" align="left"> <% } %>

		 *ob_<%=fo.getFoName()%> &nbsp;&nbsp;&nbsp; <% if (fo.getAllocation()==0){try { out2.writeBytes("<rootfolder>"+fo.getFoName()); stapel.push(new String("</rootfolder>"));}catch(Exception e){}  } else if ((fo.getAllocation()!=0)&&(test2==false)){ try { out2.writeBytes("<folder>"+fo.getFoName()); test2=false; stapel.push(new String("</folder>"));} catch(Exception e){}  } else if (test2) { try { out2.writeBytes("<folder>"+fo.getFoName()); stapel.push( ("</folder>")); out2.writeBytes(new String(((stapel.pop()).toString()))+new String(((stapel.pop()).toString()))); test2=false; }catch(Exception e){} } 
		 if (fo.getAllocation()!=0){ %>
			<a href="editformularfolder.jsp?foId=<%=foId%>&fi=<%=fi %>" target="content" title="zum Formular 'Folder bearbeiten'">B</a> &nbsp;&nbsp;&nbsp;
			<a href="delformularfolder.jsp?foId=<%= foId%>&fi=<%=fi %>" target="content" title="zum Formular 'Folder löschen'"> L </a>&nbsp;&nbsp;&nbsp;
			<a href="showfolderdetails.jsp?foId=<%=foId%>" target="content" title="Details anzeigen">I</a>
			
<%
}
	// Tabellenzelle mit Bookmarks wird gebaut
	
			List bml = fo.getBoList();
%>
		</td></tr>
<%
			if(bml!=null && fo.isOpen(fi) && showBm){
				for(Iterator ir = bml.iterator();ir.hasNext();){
					Bookmark bo = (Bookmark) ir.next();
					if(bo==null) continue;
%>
		<tr><td>
<%
					for(int i = 1; i<stack.size(); i++){
						if(!((Iterator)stack.get(i)).hasNext()){
%>
			<img src="/bookmarks/images/TreePics/menu_leer.gif" WIDTH="18" HEIGHT="18" BORDER="0" align="left">
<%
						} else  {
%>						
			<img src="/bookmarks/images/TreePics/menu_linie.gif" WIDTH="18" HEIGHT="18" BORDER="0" align="left">
<%
						}
					}
					if(!it.hasNext()&&(fo.getAllocation()!=0)){
%>
			<img src="/bookmarks/images/TreePics/menu_leer.gif" WIDTH="18" HEIGHT="18" BORDER="0" align="left">
<%
					} else if (fo.getAllocation()!=0){
%>						
			<img src="/bookmarks/images/TreePics/menu_linie.gif" WIDTH="18" HEIGHT="18" BORDER="0" align="left">
<%
					}
					String bookmark = bo.getBmName();
					bmId = bo.getBmId();
					if (!ir.hasNext() && fo.getChildren().isEmpty()) {
					test3=true;
%>					
			<img src="/bookmarks/images/TreePics/menu_was_3.gif" WIDTH="18" HEIGHT="18" BORDER="0" align="left">
			<img src="/bookmarks/images/TreePics/menu_doc_url.gif" WIDTH="16" HEIGHT="16" BORDER="0" align="left">
<%
					}
					else {
%>				
			<img src="/bookmarks/images/TreePics/menu_was_3.gif" WIDTH="18" HEIGHT="18" BORDER="0" align="left">
			<img src="/bookmarks/images/TreePics/menu_doc_url.gif" WIDTH="16" HEIGHT="16" BORDER="0" align="left">
<%
					}

	// comment-Text wird in Variable cotext gespeichert, damit diese unter title="" aufgerufen werden kann
	
					String cotext = "";
					Comment co = Comment.getById(bo.getCoId());
					if (co!=null){
						cotext = cotext + co.getCoText();
					}
					//java.sql.Date date=null;
					
					//bmk=Bookmark.getCreationDate(bmId);
%>		

<!-- 
Alternative zur nachfolgenden Zeile: Kommentar erscheint bei onmouseover:
<a href="<%//=bo.getUrl()%>" target="_blank" title="<%//=cotext%>" onmouseover="parent.frames['content'].location.href='/showbookmarkdetails.jsp?bmId=<%//=bmId%>';"><%//=bookmark%></a>&nbsp;&nbsp;&nbsp; 
-->

		
						<a href="zaehler_redirect.jsp?bmId=<%=bmId%>" target="_blank" title="Komm.:<%=cotext%>,created: <%=bo.getCreationDate()%>" ><%=bookmark%></a>&nbsp;&nbsp;&nbsp;<% if (test3==false) {try { out2.writeBytes("<bookmarks>"+bookmark+"</bookmarks>");}catch(Exception e){} 
} else { try { out2.writeBytes("<folder>"+fo.getFoName()); stapel.push( ("</folder>")); out2.writeBytes(new String(((stapel.pop()).toString()))+new String(((stapel.pop()).toString())));  test3=false; }catch(Exception e){} } %>
						<a href="editformularbookmark.jsp?bmId=<%=bmId%>&fi=<%=fi %>" target="content" title="zum Formular 'Bookmark bearbeiten'">B</a>&nbsp;&nbsp;&nbsp;
						<a href="delformularbookmark.jsp?bmId=<%=bmId%>&fi=<%=fi %>" target="content" title="zum Formular 'Bookmark löschen'">L</a>&nbsp;&nbsp;&nbsp;
						<a href="showbookmarkdetails.jsp?bmId=<%=bmId%>" target="content" title="Details anzeigen">I</a>
		</td></tr>		
<%
				} // end for-Schleife Bookmarks
			} // end if-Schleife Bookmarks

	// Unterordner des aktuellen Ordners werden zusätzlich auf den Stack gepackt und VOR den weiteren Ordnern gleicher Ebene abgearbeitet
	
			LinkedList foChildren = (LinkedList) fo.getChildren();
			if(foChildren.size()>0 && fo.isOpen(fi)){
				stack.add(it);
				it = foChildren.iterator();
				pathIndex++;
			}
%>
	</td></tr>
<%
		} // end while-Schleife (it!=null && it.hasNext()) {
		pathIndex--;
	} // end while-Schleife (stack.size()>0) {
 try { 
 while (stapel.size()>0) 
 	out2.writeBytes(new String(((stapel.pop()).toString()))); 
	out2.flush();out2.close();
}catch(Exception e){} 
 BookmarkBean.xml2html(s1, style2, outFile);
%> 
<tr><td>&nbsp;&nbsp;</td></tr>
</table>
<!-- </div> -->
</form>

</body>
</html>
