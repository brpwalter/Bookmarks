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
	String foId = (request.getParameter("foId"));
int foId2=Integer.parseInt(foId);
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<body>
<%
	try{
Folders f3=Folders.getById(foId2);
int coId=f3.getCoId();
Folders.deleteFolder(foId2, coId);
out.println("Ordner gelöscht!");
}
catch(Exception e){
out.println("Fehler beim Löschen!");
}
%>
</body>
</html>