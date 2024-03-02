<%@ page import="javax.servlet.http.*,pagebeans.BookmarkBean,java.util.*,java.util.List,java.util.LinkedList,java.util.Iterator,sql.ConnectionProvider, bookmarks.*,java.io.*"%>

<jsp:useBean id="_pagebean" class="pagebeans.BookmarkBean" scope="request">
	<jsp:setProperty name="_pagebean" property="request" value="<%= request %>"/>
	<jsp:setProperty name="_pagebean" property="response" value="<%= response %>"/>
	<jsp:setProperty name="_pagebean" property="application" value="<%= application %>"/>
	<jsp:setProperty name="_pagebean" property="*"/>
</jsp:useBean>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
	<title>Bookmark - Edit</title>
<script language="JavaScript"> <!--
function aendern(formular, ziel){
document.write("kkkk");
if (formular.addbookmark.deledit.value="edit") formular.addbookmark.pageAction.value="editbookmark";
else if (formular.addbookmark.deledit.value="delete") formular.addbookmark.pageAction.value="deletebookmark";
document.write("halloo: "+formular.addbookmark.pageAction.value);
//ziel=formular.addbookmark.pageAction.value;
formular.addbookmark.submit();
			return (true);
}
// -->
</script> </head><body>cx
<%
 	String action="";
 String fi="";
 System.out.println("par.: "+request.getParameter("deledit"));
 if (null!= request.getParameter("deledit")) action=request.getParameter("deledit");
 if (action instanceof String) System.out.println("action=String"); else System.out.println("nicht String");
 if (action.equals("edit")) {  fi="editbookmark"; System.out.println ("editbmk:edit");_pagebean.setPageAction("editbookmark");}
 else if (action.equals("delete")){  fi="deletebookmark"; System.out.println ("editbmk:del"); _pagebean.setPageAction("deletebookmark");}
 else System.out.println ("nichts: "+action);
 _pagebean.validate();
 String ziel="hallo";
 int userId=1;
 User u = new User();
 if (u != null) userId = u.getUserId();
 	//out.println("userid: "+userId);
 List ul = Folders.getListByUserId2(userId);
 %>
<table border=2 width=90%>
<form name="addbookmark" action="editbookmark.jsp" method="post">
<input type="hidden" name="pageAction" value="<%=fi%>">
<input type="hidden" name="rating" value="1">
<input type="hidden" name="accessright" value="1">
<input type="hidden" name="coText" value="irgendwas">
<tr><td bgcolor="lightblue"><bold><center>Bookmark editieren</center></bold></td></tr>
<tr><td>Zu editierende Bookmark: <select name="bmId" size="1">
<%
	String name="";
int rootId=-1;
//List ul = Folder.getListByUserId2(userId);
System.out.println("uid: "+userId);
	int x=0;

	String url="";
	String bm="";
	int af;
	int id=0;
	int co=0;
	String tmp="";
	int i=0;
	
for(Iterator it = ul.iterator();it.hasNext();){
		Folders f = (Folders) it.next();
		if(f==null) continue;
		rootId=f.getFoId();
	//	name= f.getFoName();
	

		name= f.getFoName();
		System.out.println("Ordner: "+name+"-id: "+rootId);
		List ul2 = f.getBoList2();
		System.out.println("ul2: "+ul2);
		for(Iterator it2 = ul2.iterator();it2.hasNext();){
		System.out.println("iterator ...");
		//for(Iterator it2 = ul.iterator();it2.hasNext();){
		Bookmark b = (Bookmark) it2.next();

		bm=b.getBmName();
		System.out.println("bm: "+bm);
		//array[i][1]=url;
		url=b.getUrl();
		id=b.getBmId();
		co=b.getCoId();
		af=b.getAccessfrequency();
%>
<option value=<%=id%>><%=bm%>
<%
	}
}
%>
</select>
</td></tr>
<tr><td>Name: <input name="bmName" type="text"></td></tr>
<tr><td>URL: <input name="url" type="text"></td></tr>
<tr><td>Zugeh. Ordner: <select name="foId" size="1">
<%
	for(Iterator it = ul.iterator();it.hasNext();){
		Folders f = (Folders) it.next();
		//if(f==null) continue;
		rootId=f.getFoId();
	//	name= f.getFoName();
	

		name= f.getFoName();
		System.out.println("Ordner: "+name+"-id: "+rootId);
%>
<option value=<%=rootId %>><%=name %>
<%
//}
}
%>
</select>
</td></tr>
<tr><td><select name="deledit"><option value="edit">edit</option><option value="delete">delete</option></select></td></tr>
<!-- <tr><td><input type=checkbox name="delete">Bookmark l&ouml;schen</td></tr> -->
<tr><td><input type="submit" name="los" value="los"  onClick="aendern(this.form, ziel)"></td></tr>
</form>
</table>
</body>
</html>