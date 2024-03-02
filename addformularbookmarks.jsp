<%@ page import="javax.servlet.http.*,pagebeans.BookmarkBean,java.util.*,java.util.List,java.util.LinkedList,java.util.Iterator,sql.ConnectionProvider, bookmarks.*,java.io.*"%>

<jsp:useBean id="_pagebean" scope="request" class="pagebeans.BookmarkBean" type="pagebeans.BookmarkBean" >
</jsp:useBean>

<%
	_pagebean.setPageAction("addbookmark");
_pagebean.validate();
int userId=1;
User u = new User();
if (u != null) userId = u.getUserId();
	//out.println("userid: "+userId);
List ul = Folders.getListByUserId2(userId);
%>
<table border=2 width=90%>
<form name="addbookmark" action="addformularbookmarks.jsp" method="post">
<input type="hidden" name="pageAction" value="addbookmark">
<input type="hidden" name="rating" value="1">
<input type="hidden" name="accessright" value="1">
<input type="hidden" name="coText" value="irgendwas">
<tr><td bgcolor="lightblue"><bold><center>Bookmark einf&uuml;gen</center></bold></td></tr>
<tr><td>Name: <input name="bmName" type="text"></td></tr>
<tr><td>URL: <input name="url" type="text"></td></tr>
<tr><td>Zugeh. Ordner: <select name="foId" size="1">
<%
	String name="";
int rootId=-1;
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
xx</td></tr>
<tr><td><input type="submit" name="los" value="los"></td></tr>
</form>
</table>
</body>
</html>