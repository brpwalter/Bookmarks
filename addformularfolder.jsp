<%@ page import="javax.servlet.http.*,pagebeans.BookmarkBean,java.util.*,java.util.List,java.util.LinkedList,java.util.Iterator,sql.ConnectionProvider, bookmarks.*,java.io.*"%>
 <jsp:useBean id="_pagebean" scope="request" class="pagebeans.BookmarkBean" type="pagebeans.BookmarkBean" >
</jsp:useBean>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<body>
<%
	_pagebean.setPageAction("addfolder");
_pagebean.validate();
int userId=1;
//int allocation=-1;
User u = new User();


if (u != null) userId = u.getUserId();
	out.println("userid: "+userId);
	List ul = Folders.getListByUserId2(userId);
%>
<table border=2 width=100%>
<form name="addfolder" action="addformularfolder.jsp" method="post">
<input type="hidden" name="pageAction" value="addfolder">

<input type="hidden" name="userId" value="<%=userId%>">
<tr><td bgcolor="lightblue">Folder einf&uuml;gen</td></tr>
<tr><td>Name: <input name="foName" type="text"></td></tr>
<tr><td>Zugeh. Ordner: <select name="allocation" size="1">
<%
	String name="";
int rootId=-1;
int allocation2;

for(Iterator it = ul.iterator();it.hasNext();){
		Folders f = (Folders) it.next();
		//if(f==null) continue;
		rootId=f.getFoId();
	//	name= f.getFoName();
	

		name= f.getFoName();
		allocation2=f.getAllocation();
		System.out.println("Ordner: "+name);
		System.out.println("Ordner: ");
%>
<option value=<%=rootId %>><%=name %></option>
<%
//}
}
%>
</select>
</td></tr>
<tr><td><input type="submit" name="los" value="los"></td></tr>
</form>
</table>
</body>
</html>