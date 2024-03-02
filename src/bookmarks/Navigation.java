package bookmarks;

import javax.servlet.http.*;
import bookmarks.*;
import java.util.*;

public class Navigation{

Folder [] folder;
List l=new LinkedList();

public Navigation(){

folder=new Folder[100];
if (Folder.getList()==null) System.out.println ("Folder.getList(): null"); else System.out.println ("Folder.getList(): "+Folder.getList()); 
for(Iterator c=Folder.getList().iterator();c.hasNext();){ //?
	if ((c!=null)&&	((Folder) c.next()!=null))	l.add ((Folder) c.next()); 
	if ((c!=null) ||	((Folder) c.next()!=null)) System.out.println ("Folder.getList(): null - 2");
		}
if (l!=null &&l.size()>0) folder[0]=(Folder)l.get(0);
}

public Navigation(int i){

folder=new Folder[100];
List l = Folder.getList();


for(Iterator c=l.iterator();c.hasNext();){ //?
			l.add ((Folder) c.next()); 
		}
if (l!=null &&l.size()>0) folder[i]=(Folder)l.get(0);
}

public static void invalidate(HttpSession s){

if (s!=null)s=null;
}

public static Navigation getInstance(HttpSession session,int anz){
	return new Navigation(anz);
}
public Folder getRoots(int anz){
int id1=-1,id2=-1,id3=-1;
if (folder[0]!=null) id1=folder[0].getFoId();
if (folder[1]!=null) id2=folder[1].getFoId();
if (folder[2]!=null) id3=folder[2].getFoId();
System.out.println ("Nav.:" +id1+" , "+id2+" , "+id3);
return folder[anz];
}
}