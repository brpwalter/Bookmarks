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
	List fl = Folders.getList();
	User u=new User();
	Bookmark b=new Bookmark();
	//User u = (User) session.getAttribute("user");
	int userId = u.getUserId();
	String username = u.getUserName(userId);
	System.out.println("userId: "+userId);
	String fi = (request.getParameter("fi")!=null)?request.getParameter("fi"):"_";
	String bmId = (request.getParameter("bmId"));
	int bmId2=Integer.parseInt(bmId);
	Bookmark b1=b.getById(bmId2);
	String bname = b1.getBmName();
	String burl = b1.getUrl();
	int zugriffe=b1.getAccessfrequency();
	java.util.Date zugriffsdatum=b1.getAccessdate();
	java.util.Date eintragsdatum=b1.getCreationDate();
	int bewertung=b1.getRating();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
	<title>Untitled</title>
</head>

<body>
<h3>Informationen zum markierten Bookmark:</h3>
<%
System.out.println("User: "+userId);
System.out.println("BookmarkID: "+bmId);

%>
<table border>
<tr><td>User:<td>(Id: <%=userId %>) </td><td><%= username %></td></tr>
<tr><td>Bookmark:<td >(Id:<%= bmId %>) </td><td><%= bname %></td></tr>
<tr><td colspan=3><%= burl %></td></tr></tr>
<tr><td>Zugriffe:</td><td> <%= zugriffe %> (Zugriffsdatum: <%= zugriffsdatum %>)</td></tr>
<tr><td>Eintragsdatum:</td><td><%= eintragsdatum %></td></tr>
<tr><td>Bewertung der Bookmark:</td><td><%= bewertung %> </td></tr>
</table>
</body>
</html>



