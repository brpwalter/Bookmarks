<%@ page import="java.lang.*,javax.servlet.*,pagebeans.BookmarkBean,java.util.*,java.util.List,java.util.LinkedList,java.util.Iterator,sql.ConnectionProvider, bookmarks.*,java.io.*"%>

<jsp:useBean id="_pagebean" class="pagebeans.BookmarkBean" scope="request">
	<jsp:setProperty name="_pagebean" property="request" value="<%= request %>"/>
	<jsp:setProperty name="_pagebean" property="response" value="<%= response %>"/>
	<jsp:setProperty name="_pagebean" property="application" value="<%= application %>"/>
	<jsp:setProperty name="_pagebean" property="*"/>
</jsp:useBean>
<% 
int bmId;
//java.sql.Date d2=null;
java.util.Date utilDatum=new java.util.Date();
java.sql.Date d2=new java.sql.Date(utilDatum.getTime());
//HttpServletResponseWrapper res=null;
//HttpServletResponse resp=(HttpServletResponseWrapper) res;
//res=new HttpServletResponseWrapper(response);
bmId=java.lang.Integer.parseInt(request.getParameter("bmId"));
Bookmark.increaseAccessfreq(bmId);
Bookmark b4=Bookmark.getById(bmId);
b4.setCreationDate (d2);
response.sendRedirect("http://localhost:8099/bookmarks/index.html");

%>
