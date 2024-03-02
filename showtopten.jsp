<%@ page import="pagebeans.BookmarkBean,java.util.List,java.util.Iterator,sql.ConnectionProvider, bookmarks.*"%>

<jsp:useBean id="_pagebean" scope="request" class="pagebeans.BookmarkBean" type="pagebeans.BookmarkBean" >
</jsp:useBean>

<%@ include file="schutz.jsp"%>

<%
	_pagebean.validate();
	List fl = Folders.getList();
	User u=new User();
	//User u = (User) session.getAttribute("user");
	int userId = u.getUserId();
	System.out.println("userId: "+userId);
	String fi = (request.getParameter("fi")!=null)?request.getParameter("fi"):"_";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
	<title>Untitled</title>
</head>

<body>
Die 10 meistbenutzten Links sind:

<br><br>
<table width="40%" border="0" cellspacing="0" cellpadding="2" border=4>
<form action="searchbmframes.jsp" method="post" name="frm">
<input type="hidden" name="pageAction" value="deletebookmark">
<input type="hidden" name="bmId" value="">
<input type="hidden" name="coId" value="">
<th>&nbsp;</th><th>Ordner</th><th>Name der Bookmark</th><th>&nbsp;</th><th>URL der Bookmark</th><th>&nbsp;</th><th>Zugriffe</th>

<%
	System.out.println("userId2: "+userId);
	//
	//List ul=Bookmark.showTopTen(userId);
	List ul = Folders.getListByUserId2(userId);
	int x=0;
	String name="";
	String url="";
	String bm="";
	int af;
	int id=0;
	int co=0;
	String tmp="";
	int i=0;
	String[][] array=new String[30][30];
	
	for(Iterator it = ul.iterator();it.hasNext();){
		Folders f = (Folders) it.next();
		if(f==null) continue;
		
		name= f.getFoName();
		array[i][0]=name;
		//url=f.getUrl();
		List ul2 = f.getBoList2();
		for(Iterator it2 = ul2.iterator();it2.hasNext();){
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
	
<td><%=++x %> </td><td width="20" ><%= name %> </td><td><% if (tmp==bm) {  %> <font color="green"> %> <%=bm %></font><%  } else { %> <%= bm %><% tmp=bm;%> <% } %> </td><td><a href="<%=url %>" target="_blank"><%=url %> </a></td><td>(<%=af %>) </td>
	
	
</tr>

<%

}
}
%>

</form>
</table>
<br><br><br>
<hr size="1" noshade>
<table width="50%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td class="text"><a href="baumundcontent.jsp">zur Startseite</a></td>
		
	</tr>
</table>

</body>
</html>


</body>
</html>
