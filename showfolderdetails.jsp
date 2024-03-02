<%@ page import="pagebeans.BookmarkBean,java.util.List,java.util.Iterator,sql.ConnectionProvider, bookmarks.*"%>

<jsp:useBean id="_pagebean" class="pagebeans.BookmarkBean" scope="request">
	<jsp:setProperty name="_pagebean" property="request" value="<%= request %>"/>
	<jsp:setProperty name="_pagebean" property="response" value="<%= response %>"/>
	<jsp:setProperty name="_pagebean" property="application" value="<%= application %>"/>
	<jsp:setProperty name="_pagebean" property="*"/>
</jsp:useBean>

<%@ include file="schutz.jsp"%>

<%
	_pagebean.validate();
	int userId=1;
	List fl = Folders.getList();
	User u = new User();
if (u != null) userId = u.getUserId();
	//out.println("userid: "+userId);
	//User u = (User) session.getAttribute("user");
	//int userId = u.getUserId();
	System.out.println("userId: "+userId);
	String fi = (request.getParameter("fi")!=null)?request.getParameter("fi"):"_";
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<body>

<h3> Informationen zum markierten Ordner: </h3>
<%
	String name1="";
String foId = (request.getParameter("foId"));
int foId2=Integer.parseInt(foId);
%>
(FolderId:<%=foId2%>)
<%
	List ul = Folders.getList4(foId2);
String name="";

Folders fo=Folders.getById(foId2); 
name=fo.getFoName();
java.util.List l=Folders.getList(foId2);

//List ul = Folder.getListByUserId2(userId);
%>
<table border>
<tr><td>Name:</td><td> <%=name%></td></tr>
<tr><td>Erstellungsdatum:</td><td><%=fo.getCreationDate()%></td></tr>
<tr><td>zugeh&ouml;rige Ordner und Bookmarks:</td> </tr>

<%
	int x=0;
	String url="";
	String bm="";
	int af;
	int id=0;
	int co=0;
	String tmp="";
	int i=0;
	String[][] array=new String[30][30];
	Iterator it2;
	int size2=ul.size();
	out.println("Der Ordner enthaelt "+size2+" Elemente.");
	for(Iterator it = ul.iterator();it.hasNext();){
		//out.println ("halloooooooooooooooooooooooo");
		Folders f3 = (Folders) it.next();
		if(f3==null){ System.out.println ("<h6>f3 ist null!</h6>"); continue; }
		else System.out.println ("<h6>f3 ist nicht null!</h6>");
		name1= f3.getFoName();
		System.out.println ("Name1:"+name1);
		array[i][0]=name;
		//url=f.getUrl();
		List ul2 = f3.getBoList2();
		
		for(it2 = ul2.iterator();it2.hasNext();){
	if (ul2==null) continue;
		//for(Iterator it2 = ul.iterator();it2.hasNext();){
		Bookmark b = (Bookmark) it2.next();

		bm=b.getBmName();
		array[i][1]=url;
		url=b.getUrl();
		id=b.getBmId();
		co=b.getCoId();
		af=b.getAccessfrequency();
		//i++;
%>



<tr>
	
<td><% if (!name.equals(name1)) out.println (name1); %> </td><td><% if (tmp==bm) {  %> <font color="green"> %> <%=bm %></font><%  } else { %> <%= bm %><% tmp=bm;%> <% } %> </td><td><a href="<%=url %>" target="_blank"><%=url %> </a></td><td>(<%=af %>) </td>
	
	
</tr>

<%

}
}
%>
</table>
</body>
</html>