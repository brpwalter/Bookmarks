<%@ page import="javax.servlet.http.*,pagebeans.BookmarkBean,java.util.*,java.util.List,java.util.LinkedList,java.util.Iterator,sql.ConnectionProvider, bookmarks.*,java.io.*"%>

<jsp:useBean id="_pagebean" scope="request" class="pagebeans.BookmarkBean" type="pagebeans.BookmarkBean" >
</jsp:useBean>
<!--
<%@ include file="schutz.jsp"%>
 -->
<%
_pagebean.setPageAction("upload");
_pagebean.validate(request);
System.out.println("bookmarkimport.jsp");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">

<html>
<head>
	<title>Bookmark - Baum</title>
	<LINK REL="Stylesheet" HREF="css/common-2c.css" TYPE="text/css">
	<script language="JavaScript">
	<!--
		
	// -->
	</script>
</head>
<%


	// Sprungmarke wird gesetzt
	
	String spm = request.getParameter("spm");
	String onload = (spm!=null)?" onLoad=\"location.href='#"+spm+"'\"":"";
%>
<body bgcolor="#DDEFFC"<%=onload %>>
Ihre Bookmarks stehen ihnen nun im Ordnerbaum zur Verfuegung.
<form method="post" enctype="multipart/form-data" action="/cgi-bin/WebObjects/ov4.woa/6/wo/bEKEbTfCRrodMKk7e3NOHg/10.3"><table cellpadding="0" cellspacing="0">
 	<tr>
		<td><input type=file name="3.1"></td>
	</tr>
	<tr>
		<td height="9"><img src="/WebObjects/ov4Java.woa/Contents/WebServerResources/tp.gif" width="1" height="1"></td>
	</tr>
 	<tr>
		<td><span class="std">Importieren in den Ordner:</span></td>
	</tr>
	<tr>
		<td height="9"><img src="/WebObjects/ov4Java.woa/Contents/WebServerResources/tp.gif" width="1" height="1"></td>
	</tr>
	<tr>
		<td><span class="std">
<nobr><input border="0" align="absmiddle" onClick="javascript:saveScrollPosition();" type=image name="3.9.0.1.0.0.1.0.2" src="/WebObjects/ov4Java.woa/Contents/WebServerResources/lnp.gif" width="16" height="20">
<input type=radio name="lc" value="3.9.0.1.0.0.1.0.4.0" checked><img align="absmiddle" src="/WebObjects/ov4Java.woa/Contents/WebServerResources/cf.gif" width="16" height="16">&nbsp;<SPAN CLASS=std>Linksammlung</SPAN></nobr><br></span></td>
	</tr>
	<tr>
		<td height="9"><img src="/WebObjects/ov4Java.woa/Contents/WebServerResources/tp.gif" width="1" height="1"></td>
	</tr>
	<tr>
		<td><input type=submit value="Importieren" name="3.15"></input>&nbsp;&nbsp;<input type=submit value="Abbrechen" name="3.17"></input></td>
	</tr>
</table>

<br><hr><table border=0 cellpadding=0 cellspacing=0>
	<tr>
		
		
		<td>
			<input alt="Hilfe ausblenden" border="0" type=image name="3.21.3.1" src="/WebObjects/ov4Java.woa/Contents/WebServerResources/questionmark_off.gif" width="13" height="14">
			<span class=bstd>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[Hilfe]</span>
			<br><br>
			<span class=std>			
	<table>
		<tr>
			<td colspan="2"><span class="bstd">Lokale Links &nbsp;importieren</span><BR>
				<BR>
			</td>
		</tr>
		<tr>
			<td colspan="2"><span class="std">Um lokal auf der Festplatte gespeicherte Links (Favoriten oder Bookmarks) auch von anderen Computern aus abrufen zu k&ouml;nnen, haben Sie hier die M&ouml;glichkeit, diese online abzulegen.<BR>
				<BR>
				Exportieren Sie zun&auml;chst aus Ihrem Browser die lokal gespeicherten Links in eine bookmark.html-Datei. Dazu gehen Sie wie folgt vor:<BR>
				<BR>
				Netscape:<BR>
				Dr&uuml;cken Sie die Tastenkombination Strg+B, um Ihre Bookmarks zu bearbeiten.<BR>
				Dr&uuml;cken Sie die Tastenkombination Strg+S, um Ihre Bookmarks zu speichern.<BR>
				W&auml;hlen Sie einen Speicherort (z. B.den Desktop)<BR>
				Klicken Sie auf &quot;Speichern&quot;.<BR>
				<BR>
				Internet Explorer<BR>
				Klicken Sie auf &quot;Datei - Importieren und Exportieren&quot;, um den Import/Export Assistenten aufzurufen.<BR>
				Klicken Sie auf &quot;Weiter&quot;<BR>
				W&auml;hlen Sie &quot;Favoriten exportieren&quot;<BR>
				Folgen Sie den Anweisungen des Import/Export Assistenten.<BR>
				Beim Punkt &quot;Ziel f&uuml;r das Exportieren von Favoriten&quot; klicken Sie auf &quot;Durchsuchen&quot; und geben Sie ein
				Ziel f&uuml;r die bookmark.html-Datei an (z. B. den Desktop).<BR>
				Klicken Sie auf &quot;Weiter&quot;, dann auf &quot;Fertig stellen&quot;.<BR>
				<BR>
				Im zweiten Schritt importieren Sie Ihre bookmark.html-Datei in Ihren oneview-Account.<BR></span></td>
		</tr>
		<tr>
			<td>
			<OL>
				<LI><span class="std">Beginnen Sie die Suche nach der auf Ihrer Festplatte gespeicherten Datei mit &quot;Browse...&quot; / &quot;Durchsuchen...&quot;.</span>
				<LI><span class="std">W&auml;hlen Sie die Netscape-Bookmark-Datei mit einem Doppelklick aus.<BR>
					Diese Datei finden Sie an der Stelle, an der Sie sie im ersten Schritt abgespeichert haben (z. B. dem Desktop).</span>
				<LI><span class="std">Markieren Sie den oneview-Ordner, in den Ihre Links importiert werden sollen.</span>
				<LI><span class="std">Klicken Sie abschlie&szlig;end auf &quot;Importieren&quot;.</span>
			</OL>								
			<span class="std">Damit ist der Import abgeschlossen und Ihre Links stehen Ihnen jetzt<BR>
			online in oneview zur Verf&uuml;gung.<BR><BR>
			<BR>
			Bei Fragen oder Problemen wenden Sie sich bitte an unseren Support:<BR>
			<a href="mailto:support@oneview.com">support@oneview.com</a><BR>
			<BR>
			<BR>
			<BR></span>
			</td>
		</tr>
	</table></span>
		</td>
			
</tr>
</table>


</form>
</body>
</html>