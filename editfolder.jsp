<%@ page import="javax.servlet.http.*,pagebeans.BookmarkBean,java.util.*,java.util.List,java.util.LinkedList,java.util.Iterator,sql.ConnectionProvider, bookmarks.*,java.io.*"%>

<jsp:useBean id="_pagebean" scope="request" class="pagebeans.BookmarkBean" type="pagebeans.BookmarkBean" >
</jsp:useBean>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
	<title>Ordner - Edit</title>
<script language="JavaScript"> <!--
function aendern(formular, ziel){
document.write("kkkk");
if (formular.addfolder.deledit.value="edit") formular.addfolder.pageAction.value="editfolder";
else if (formular.addfolder.deledit.value="delete") formular.addfolder.pageAction.value="deletefolder";
document.write("halloo: "+formular.addfolder.pageAction.value);
//ziel=formular.addfolder.pageAction.value;
formular.addfolder.submit();
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
 if (action.equals("edit")) {  fi="editfolder"; System.out.println ("editbmk:edit");_pagebean.setPageAction("editfolder");}
 else if (action.equals("delete")){  fi="deletefolder"; System.out.println ("editbmk:del"); _pagebean.setPageAction("deletefolder");}
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
<form name="addfolder" action="editfolder.jsp" method="post">
<input type="hidden" name="pageAction" value="<%=fi%>">
<input type="hidden" name="rating" value="1">
<input type="hidden" name="accessright" value="1">
<input type="hidden" name="coText" value="irgendwas">
<tr><td bgcolor="lightblue"><bold><center>Ordner editieren</center></bold></td></tr>
<tr><td>Zu editierende Ordner: <select name="foId" size="1">
<%
	String name="";
int rootId=-1;
int rootId2=-1;
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
	int allocation=0;
	

		List ul2 = Folders.getListByUserId2(userId);
		System.out.println("ul2: "+ul2);
		for(Iterator it2 = ul2.iterator();it2.hasNext();){
		System.out.println("iterator ...");
		//for(Iterator it2 = ul.iterator();it2.hasNext();){
		Folders b = (Folders) it2.next();

		bm=b.getFoName();
		System.out.println("bm: "+bm);
		//array[i][1]=url;
		allocation=b.getAllocation();
		rootId2=b.getFoId();
		co=b.getCoId();
%>
<option value=<%=rootId2%>><%=bm%>
<%
	}
%>
</select>
</td></tr>

<tr><td>Zugeh. Ordner: <select name="allocation" size="1">
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
<!-- <tr><td><input type=checkbox name="delete">Ordner l&ouml;schen</td></tr> -->
<tr><td><input type="submit" name="los" value="los"  onClick="aendern(this.form, ziel)"></td></tr>
</form>
</table>
</body>
</html>